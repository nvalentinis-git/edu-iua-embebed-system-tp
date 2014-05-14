#!/bin/bash

SERVER_HOST_NUMBER=8000
if [[ -n $1 ]]; then
	SERVER_HOST_NUMBER=$1    
fi

ACTUATOR_CLIENT_HOST_NUMBER=localhost
if [[ -n $2 ]]; then
	ACTUATOR_CLIENT_HOST_NUMBER=$2    
fi

ACTUATOR_CLIENT_PORT_NUMBER=8001
if [[ -n $3 ]]; then
	ACTUATOR_CLIENT_PORT_NUMBER=$3    
fi

echo Http Server Port Number used $SERVER_HOST_NUMBER
echo Actuator Client Host Number used $ACTUATOR_CLIENT_HOST_NUMBER
echo Actuator Client Port Number used $ACTUATOR_CLIENT_PORT_NUMBER

echo Starting Java App...
echo .................

#chmod a+x *.sh
java -cp ../target/tp-sd-iua-http-server-1.0-SNAPSHOT-jar-with-dependencies.jar -DhttpServerPortNumber=$SERVER_HOST_NUMBER -DactuatorClientHostNumber=$ACTUATOR_CLIENT_HOST_NUMBER -DactuatorClientPortNumber=$ACTUATOR_CLIENT_PORT_NUMBER ar.edu.iua.practicoSD.httpServer.HttpServer
