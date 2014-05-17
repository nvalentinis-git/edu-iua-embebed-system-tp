#!/bin/bash

if [ "$1" == "-h" ]; then
  echo "Use like this: ./start-actuator-server-mock.sh [ACTUATOR_PORT_NUMBER]"
  exit 0
fi

ACTUATOR_PORT_NUMBER=8001
if [[ -n $1 ]]; then
	ACTUATOR_PORT_NUMBER=$1    
fi

NETWORK_CONFIG=" -DactuatorClientPortNumber=$ACTUATOR_PORT_NUMBER "

# Configuration Actuator Protocol
actuatorClientLogicalJson=getLogicValueJSON
actuatorClientLogicalXml=getLogicValueXML
actuatorClientAnalogicalJson=getAnalogicValueJSON
actuatorClientAnalogicalXml=getAnalogicValueXML

PROTOCOL_CONFIG=" -DactuatorClientLogicalJson=$actuatorClientLogicalJson -DactuatorClientLogicalXml=$actuatorClientLogicalXml -DactuatorClientAnalogicalJson=$actuatorClientAnalogicalJson -DactuatorClientAnalogicalXml=$actuatorClientAnalogicalXml "

echo Actuator Port Number used: $ACTUATOR_PORT_NUMBER
echo --------------------------------------------------------------

echo Actuator Protocol Configuration: 
echo 	$actuatorClientLogicalJson
echo 	$actuatorClientLogicalXml
echo 	$actuatorClientAnalogicalJson
echo 	$actuatorClientAnalogicalXml
echo --------------------------------------------------------------

echo Starting Java App...
echo .................

java -cp ~/work/edu-iua-embebed-system-tp/tp-sd-iua-http-server/target/tp-sd-iua-http-server-1.0-SNAPSHOT-jar-with-dependencies.jar $NETWORK_CONFIG $PROTOCOL_CONFIG ar.edu.iua.practicoSD.actuatorServer.ActuatorServerMock
