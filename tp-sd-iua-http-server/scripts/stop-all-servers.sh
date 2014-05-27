#!/bin/bash

if [ "$1" == "-h" ]; then
  echo "Use like this: ./stop-all-servers.sh [SOMETHING_IF_WIN]"
  exit 0
fi

if [[ -n $1 ]]; then
	pid=`ps aux | grep java | awk '{print $1}'`
else
	pid=`ps aux | grep tp-sd-iua-http-server-1.0-SNAPSHOT-jar-with-dependencies.jar | awk '{print $2}'`
fi

echo PID $pid

kill $pid
