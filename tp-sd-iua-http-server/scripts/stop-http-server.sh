#!/bin/bash
pid=`ps aux | grep tp-sd-iua-http-server-1.0-SNAPSHOT-jar-with-dependencies.jar | awk '{print $2}'`
kill -9 $pid
