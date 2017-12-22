# What is this?

Logme is a HTTP web servlet that displays and logs incoming requests. It reads the information from HttpServletRequest and writes it to log and to HttpServletResponse (i.e. playing it back to your browser).

It is a useful tool for HTTP request troubleshooting.  For example, you can specify its URL as a webhook URL, and visualize what the incoming webhook requests look like.

To demostrate how it works, here is a deployed version of this web application running on Google App Engine.

http://wualogme.appspot.com/

# Download
```
git clone https://github.com/githubwua/logme/
```

# Prerequisites

- Google Cloud SDK
- A Google Cloud Project (with Google App Engine enabled)
- Maven
- Java


# Installation

Installing Google Cloud SDK on Linux is easy, just do this:

```
curl https://sdk.cloud.google.com | bash
gcloud components install app-engine-java
```

If you need help with installing any of the prerequisites, see: https://cloud.google.com/appengine/docs/standard/java/quickstart-java8

# Deploy

This is how I deployed this app to Google App Engine:

```
# Modify YOUR_GAE_PROJECT below to match your own GAE project name
# You just need to set this once if you are deploying for the first time.

# gcloud config set project YOUR_GAE_PROJECT
cd logme
mvn appengine:deploy
```

# Try it out

After deploying this app to Google App Engine, you can access it at https://YOUR_GAE_PROJECT.appspot.com/logme from a web browser or from curl.

e.g.

```
#GET request
curl 'https://wualogme.appspot.com/logme?colour=blue&behaviour=normal&organisation=acme'

#POST request
curl -d 'abc=def' 'https://wualogme.appspot.com/logme?colour=blue&behaviour=normal&organisation=acme'
curl -d 'abc=def' 'https://wualogme.appspot.com/logme?colour=blue&behaviour=normal&organisation=acme,globex'
curl -d @sample_message.json --header "Content-Type: application/json" https://wualogme.appspot.com/logme?token=xyz
```
