package wua.app.logme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class ServletToolBox {
  public static String getRequestAsString(HttpServletRequest request) throws IOException {
    StringBuilder sb = new StringBuilder();

    String strParams = getParamsAsString(request.getParameterMap());
    if (!strParams.isEmpty()) {
      sb.append("\n[PARAMETERS]\n");
      sb.append(strParams);
    }

    sb.append("\n");
    sb.append(request.getMethod() + " ");
    sb.append(request.getRequestURL());
    sb.append("\n");
    sb.append(getHeadersInRequest(request));
    sb.append("\n");
    sb.append(getBodyInRequest(request));
    return sb.toString();
  }

  public static String getBodyInRequest(HttpServletRequest request) throws IOException {

    String requestBody;
    try {
      // This oneliner may fail with: java.lang.IllegalStateException: STREAMED
      requestBody = request.getReader().lines().collect(Collectors.joining("\n"));
    } catch (Exception e) {
      // Workaround: if getReader() fails, use getInputStream() instead
      // Reference: http://apache-wicket.1842946.n4.nabble.com/java-lang-IllegalStateException-STREAMED-Consuming-JSON-fails-td3850442.html
      BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
      requestBody = in.lines().collect(Collectors.joining("\n"));
    }

    try {
      // return Base64 decoded string if possible
      // Reference: https://gist.github.com/komiya-atsushi/d878e6e4bf9ba6dae8fa
      return new String(Base64.getDecoder().decode(requestBody));
    } catch (Exception e) {
      // if not, return as is.
      return requestBody;
    }
  }

  public static String getCookiesInRequest(HttpServletRequest request) {
    Map<String, String> map = new HashMap<String, String>();
    Cookie[] cookies = request.getCookies();// Get an array of Cookies associated with this domain
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        map.put(cookies[i].getName(), cookies[i].getValue());
      }
    }
    return getMapAsString(map);
  }

  public static String getHeadersInRequest(HttpServletRequest request) {
    Map<String, String> map = new HashMap<String, String>();
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String key = (String) headerNames.nextElement();
      String value = request.getHeader(key);
      map.put(key, value);
    }
    return getMapAsString(map);
  }

  // returns the content of a map as string
  // sorting: https://www.sejuku.net/blog/16176, http://javatechnology.net/java/map-key-sort/
  public static String getMapAsString(Map<String, String> map) {

    // 2.Map.Entryのリストを作成する
    List<Entry<String, String>> list_entries = new ArrayList<Entry<String, String>>(map.entrySet());

    // 3.比較関数Comparatorを使用してMap.Entryの値を比較する(昇順)
    Collections.sort(list_entries, new Comparator<Entry<String, String>>() {
      public int compare(Entry<String, String> obj1, Entry<String, String> obj2) {
        // 4. 昇順
        return obj1.getKey().compareTo(obj2.getKey());
      }
    });

    // 5. ループで要素順に値を取得する
    StringBuilder sb = new StringBuilder();
    for (Entry<String, String> entry : list_entries) {
      sb.append(entry.getKey());
      sb.append(": ");
      sb.append(entry.getValue());
      sb.append("\n");
    }

    return sb.toString();
  }

  // returns the content of a ParameterMap as string
  public static String getParamsAsString(Map<String, String[]> map) {    
    StringBuilder sb = new StringBuilder();
    for (String key : map.keySet()) {
      sb.append(key);
      sb.append("=");
      String[] val = (String[]) map.get(key);
      for (int i = 0; i < val.length; i++) {
        sb.append(val[i]);
        if (i < val.length - 2)
          sb.append(",");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}