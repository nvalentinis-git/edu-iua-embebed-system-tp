#!/bin/bash

HOST_NUMBER=8001
if [[ -n $1 ]]; then
	HOST_NUMBER=$1    
fi

echo Host Number used $HOST_NUMBER

echo Starting Java App...
echo .................

#chmod a+x *.sh
java -cp ../target/tp-sd-iua-http-server-1.0-SNAPSHOT-jar-with-dependencies.jar -DactuatorClientPortNumber=$HOST_NUMBER ar.edu.iua.practicoSD.actuatorServer.ActuatorServerMock
