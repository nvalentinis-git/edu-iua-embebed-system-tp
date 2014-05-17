#!/bin/bash

if [ "$1" == "-h" ]; then
  echo "Use like this: ./start-http-server.sh [SERVER_HOST_NUMBER] [ACTUATOR_CLIENT_HOST_NUMBER] [ACTUATOR_CLIENT_PORT_NUMBER]"
  exit 0
fi

SERVER_HOST_NUMBER=8000
if [[ -n $1 ]]; then
	SERVER_HOST_NUMBER=$1    
fi

ACTUATOR_CLIENT_HOST_NUMBER=192.168.1.10
if [[ -n $2 ]]; then
	ACTUATOR_CLIENT_HOST_NUMBER=$2    
fi

ACTUATOR_CLIENT_PORT_NUMBER=80
if [[ -n $3 ]]; then
	ACTUATOR_CLIENT_PORT_NUMBER=$3    
fi

NETWORK_CONFIG=" -DhttpServerPortNumber=$SERVER_HOST_NUMBER -DactuatorClientHostNumber=$ACTUATOR_CLIENT_HOST_NUMBER -DactuatorClientPortNumber=$ACTUATOR_CLIENT_PORT_NUMBER "

# Configuration Actuator Protocol
actuatorClientLogicalJson=getLogicValueJSON
actuatorClientLogicalXml=getLogicValueXML
actuatorClientAnalogicalJson=getAnalogicValueJSON
actuatorClientAnalogicalXml=getAnalogicValueXML

PROTOCOL_CONFIG=" -DactuatorClientLogicalJson=$actuatorClientLogicalJson -DactuatorClientLogicalXml=$actuatorClientLogicalXml -DactuatorClientAnalogicalJson=$actuatorClientAnalogicalJson -DactuatorClientAnalogicalXml=$actuatorClientAnalogicalXml "


echo Http Server Port Number used $SERVER_HOST_NUMBER
echo Actuator Client Host Number used $ACTUATOR_CLIENT_HOST_NUMBER
echo Actuator Client Port Number used $ACTUATOR_CLIENT_PORT_NUMBER
echo --------------------------------------------------------------

echo Actuator Protocol Configuration: 
echo 	$actuatorClientLogicalJson
echo 	$actuatorClientLogicalXml
echo 	$actuatorClientAnalogicalJson
echo 	$actuatorClientAnalogicalXml
echo --------------------------------------------------------------

echo Starting Java App...
echo .................

#chmod a+x *.sh
java -cp ../target/tp-sd-iua-http-server-1.0-SNAPSHOT-jar-with-dependencies.jar $NETWORK_CONFIG $PROTOCOL_CONFIG ar.edu.iua.practicoSD.httpServer.HttpServer





