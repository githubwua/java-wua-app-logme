# What is this?

Logme is an HTTP web servlet that displays and logs incoming requests. It reads requests from [HttpServletRequest](https://docs.oracle.com/javaee/6/api/javax/servlet/http/HttpServletRequest.html) and writes them to log and to [HttpServletResponse](https://docs.oracle.com/javaee/6/api/javax/servlet/http/HttpServletResponse.html) (i.e. like a mirror, playing requests back to originating web browsers).

It is a useful tool for examining HTTP requests.  For example, you can use Logme as a [webhook](https://en.wikipedia.org/wiki/Webhook) URL, and visualize what the incoming webhook requests look like.  As a matter of fact, I built this tool to figure out what [Pub/Sub push messages](https://cloud.google.com/pubsub/docs/push) look like, and how frequently they are pushed.

# Try it out

To see how this works, here is a deployed version running on Google App Engine.

http://wualogme.appspot.com/logme

It displays your request and allows you to see how your request is received by the web server.  You can access the link from a web browser or from curl.

e.g.

```
#GET request
curl 'https://wualogme.appspot.com/logme?colour=blue&behaviour=normal&organisation=acme'

#POST request
curl -d 'abc=def' 'https://wualogme.appspot.com/logme?colour=blue&behaviour=normal&organisation=acme'
curl -d 'abc=def' 'https://wualogme.appspot.com/logme?colour=blue&behaviour=normal&organisation=acme,globex'
curl -d @sample_message.json --header "Content-Type: application/json" https://wualogme.appspot.com/logme?token=xyz
```
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
