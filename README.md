# statline_data

## Cloud Functions

A collection of cloud functions deployed on gcp invoked by cloud scheduler periodically throughout the day.

## data-backend

A simple spring application that sets up routes for Statline frontend to query and display. Uses google secret manager to maintain a list of api keys (should be a small number 2 at most).

TODO:
  - Dockerfile 
  - Deploy on cloud run 
  - Add nytimes counties routes
