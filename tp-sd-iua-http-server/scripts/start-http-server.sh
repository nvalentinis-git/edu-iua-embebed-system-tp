#!/bin/bash

if [ "$1" == "-h" ]; then
  echo "Use like this: ./start-http-server.sh [SERVER_HOST_NUMBER] [SERVER_TCP_HOST_NUMBER] [SERVER_TCP_PORT_NUMBER]"
  exit 0
fi

SERVER_HOST_NUMBER=8000
if [[ -n $1 ]]; then
	SERVER_HOST_NUMBER=$1
fi

SERVER_TCP_HOST_NUMBER=192.168.1.10
if [[ -n $2 ]]; then
	SERVER_TCP_HOST_NUMBER=$2
fi

SERVER_TCP_PORT_NUMBER=80
if [[ -n $3 ]]; then
	SERVER_TCP_PORT_NUMBER=$3
fi

NETWORK_CONFIG=" -DhttpServerPortNumber=$SERVER_HOST_NUMBER -DsensorTCPHostNumber=$SERVER_TCP_HOST_NUMBER -DsensorTCPPortNumber=$SERVER_TCP_PORT_NUMBER "

# Configuration Actuator Protocol
sensorTCPCommandLogicalJson=getLogicValueJSON
sensorTCPCommandLogicalXml=GetLogicValueXML
sensorTCPCommandAnalogicalJson=getAnalogicValueJSON
sensorTCPCommandAnalogicalXml=getAnalogicValueXML

PROTOCOL_CONFIG=" -DsensorTCPCommandLogicalJson=$sensorTCPCommandLogicalJson -DsensorTCPCommandLogicalXml=$sensorTCPCommandLogicalXml -DsensorTCPCommandAnalogicalJson=$sensorTCPCommandAnalogicalJson -DsensorTCPCommandAnalogicalXml=$sensorTCPCommandAnalogicalXml "


echo Http Server Port Number used $SERVER_HOST_NUMBER
echo Sensor Server TCP Host Number used $SERVER_TCP_HOST_NUMBER
echo Sensor Server TCP Port Number used $SERVER_TCP_PORT_NUMBER
echo --------------------------------------------------------------

echo Actuator Protocol Configuration: 
echo 	$sensorTCPCommandLogicalJson
echo 	$sensorTCPCommandLogicalXml
echo 	$sensorTCPCommandAnalogicalJson
echo 	$sensorTCPCommandAnalogicalXml
echo --------------------------------------------------------------

echo Starting Java App...
echo .................

java -cp /home/pi/http-server/lib/tp-sd-iua-http-server-1.0-SNAPSHOT-jar-with-dependencies.jar $NETWORK_CONFIG $PROTOCOL_CONFIG ar.edu.iua.practicoSD.httpServer.HttpServer





