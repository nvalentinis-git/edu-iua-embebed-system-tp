#!/bin/bash

if [ "$1" == "-h" ]; then
  echo "Use like this: ./start-actuator-server-mock.sh [ACTUATOR_PORT_NUMBER]"
  exit 0
fi

ACTUATOR_PORT_NUMBER=8001
if [[ -n $1 ]]; then
	ACTUATOR_PORT_NUMBER=$1    
fi

echo Actuator Port Number used: $ACTUATOR_PORT_NUMBER

echo Starting Java App...
echo .................

#chmod a+x *.sh
java -cp ../target/tp-sd-iua-http-server-1.0-SNAPSHOT-jar-with-dependencies.jar -DactuatorClientPortNumber=$ACTUATOR_PORT_NUMBER ar.edu.iua.practicoSD.actuatorServer.ActuatorServerMock
