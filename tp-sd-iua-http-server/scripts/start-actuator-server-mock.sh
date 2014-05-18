#!/bin/bash

if [ "$1" == "-h" ]; then
  echo "Use like this: ./start-actuator-server-mock.sh [SERVER_TCP_PORT_NUMBER]"
  exit 0
fi

SERVER_TCP_PORT_NUMBER=8001
if [[ -n $1 ]]; then
	SERVER_TCP_PORT_NUMBER=$1    
fi

NETWORK_CONFIG=" -DsensorTCPPortNumber=$SERVER_TCP_PORT_NUMBER "

# Configuration Actuator Protocol
sensorTCPCommandLogicalJson=getLogicValueJSON
sensorTCPCommandLogicalXml=getLogicValueXML
sensorTCPCommandAnalogicalJson=getAnalogicValueJSON
sensorTCPCommandAnalogicalXml=getAnalogicValueXML

PROTOCOL_CONFIG=" -DsensorTCPCommandLogicalJson=$sensorTCPCommandLogicalJson -DsensorTCPCommandLogicalXml=$sensorTCPCommandLogicalXml -DsensorTCPCommandAnalogicalJson=$sensorTCPCommandAnalogicalJson -DsensorTCPCommandAnalogicalXml=$sensorTCPCommandAnalogicalXml "

echo Actuator Port Number used: $SERVER_TCP_PORT_NUMBER
echo --------------------------------------------------------------

echo Actuator Protocol Configuration: 
echo 	$sensorTCPCommandLogicalJson
echo 	$sensorTCPCommandLogicalXml
echo 	$sensorTCPCommandAnalogicalJson
echo 	$sensorTCPCommandAnalogicalXml
echo --------------------------------------------------------------

echo Starting Java App...
echo .................

java -cp ~/work/edu-iua-embebed-system-tp/tp-sd-iua-http-server/target/tp-sd-iua-http-server-1.0-SNAPSHOT-jar-with-dependencies.jar $NETWORK_CONFIG $PROTOCOL_CONFIG ar.edu.iua.practicoSD.sensorServerTCP.SensorTCPServerMock
