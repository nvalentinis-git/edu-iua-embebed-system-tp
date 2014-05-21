#!/bin/bash

echo
echo ----------------------------------
echo Starting Http Server...
echo ----------------------------------

/bin/bash start-http-server.sh 8000 localhost 8001 Y &


echo
echo ----------------------------------
echo Starting Sensor TCP Server
echo ----------------------------------

/bin/bash start-sensor-tcp-server-mock.sh 8001 &


echo
echo ----------------------------------
echo Starting Running unit test...
echo ----------------------------------

cd .. 
mvn test

echo
echo ----------------------------------
echo Stopping Http and Sensor Server...
echo ----------------------------------

/bin/bash stop-http-server.sh

