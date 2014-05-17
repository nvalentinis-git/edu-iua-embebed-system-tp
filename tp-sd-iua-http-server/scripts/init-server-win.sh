#!/bin/bash
# tp-sd-iua-http-server-1.0-SNAPSHOT-jar-with-dependencies.jar
#
# description: iniciar Http Server

case $1 in
    start)
        /bin/bash /home/pi/http-server/start-http-server.sh
    ;;
    stop)
        /bin/bash /home/pi/http-server/stop-http-server.sh
    ;;
    restart)
        /bin/bash /home/pi/http-server/stop-http-server.sh
        /bin/bash /home/pi/http-server/start-http-server.sh
    ;;
esac
exit 0