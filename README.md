# statline_data

## Cloud Functions

A collection of cloud functions deployed on gcp invoked by cloud scheduler periodically throughout the day. Functions connect via unix socket as cloud functions by default use public ip addresses, to test locally whitelist your own ip. 

## data-backend

A simple spring application that sets up routes for Statline frontend to query and display. Uses google secret manager to maintain a list of api keys (should be a small number 2 at most). Do not have to wory about whitelisted public ip address or via 
proxy in order to access cloud sql since we are using the JDBC socket factory. 
Note you must assign GOOGLE_APPLICATION_CREDENTIALS in the environment 

### Containerization
#### ON GCP:
...

#### NOT ON GCP:
Dockerfile will build the containerized jar if you plan on deploying locally or not on gcp.
You must use docker-swarm since we use secrets.
docker secret create ${GOOGLE_APPLICATION_CREDENTIALS} gcp_auth
Run
`docker stack deploy --compose-file=docker-compose.yml staline`
to deploy the container 