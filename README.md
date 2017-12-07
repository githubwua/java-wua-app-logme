# What is this?

Logme is a HTTP web servlet that displays and logs incoming requests. It simply extracts information from HttpServletRequest and writes it to logs and to HttpServletResponse (i.e. your browser).

It is a good tool for HTTP request troubleshooting.  For example, you can specify its URL as a webhook URL, and visualize what the incoming webhook requests look like.

# Prerequisites

- Google Cloud SDK
- A Google Cloud Project (with Google App Engine enabled)
- Maven
- Java


# Installation

If you need help with installing any of the prerequisites above, see: https://cloud.google.com/appengine/docs/standard/java/quickstart-java8

Installing Google Cloud SDK (Linux/Mac) is easy, just do this:

```
curl https://sdk.cloud.google.com | bash
gcloud components install app-engine-java
```

# Deploy
```
# Change YOUR_GAE_PROJECT below to your own GAE project name
gcloud config set project YOUR_GAE_PROJECT
mvn appengine:deploy
```

# Try it out

Access https://YOUR_GAE_PROJECT.appspot.com/logme from a web browser or from curl.

e.g.

```
#GET request
curl 'https://YOUR_GAE_PROJECT/logme?colour=blue&behaviour=normal&organisation=acme'

#POST request
curl -d 'abc=def' 'https://YOUR_GAE_PROJECT/logme?colour=blue&behaviour=normal&organisation=acme'
curl -d 'abc=def' 'https://YOUR_GAE_PROJECT/logme?colour=blue&behaviour=normal&organisation=acme,globex'
```
