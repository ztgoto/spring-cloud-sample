#!/bin/bash
cd `dirname $0`/..

APP_DIR=./lib
# CONFIG_FILE=${spring.config.location}
# LOG_DIR=${logging.path}

APP_NAME="${project.build.finalName}.${project.packaging}"
# SERVER_PORT="${equipment.server.port}"
# PID_FILE="${runtime.pidfile}"
PID_FILE="./pid"

# PROFILES=${spring.profiles.active}

DEBUG_PARAM=""

GC_PARAM=""

APP_PARAMS=""

if [ "debug" = "$1" ]; then

    DEBUG_PORT="9090"

    if [ -n "$2" ]; then
        DEBUG_PORT="$2"
    fi

    DEBUG_PARAM="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=$DEBUG_PORT,suspend=n"
elif [ "printGC" = "$1" ]; then
    GC_PARAM="-XX:+PrintGCDetails -Xloggc:./gc.log -XX:+PrintGCTimeStamps"
fi

for p in "$@"; do
   if [[ ${p:0:2} = "--" ]]; then
     APP_PARAMS="$APP_PARAMS $p ";
   fi; 
done;


if [ -f "$PID_FILE" ]; then
    PID=`cat "$PID_FILE"`
    if [ -n "$PID" ]; then
    	RUNPID=`ps -ef | grep "$APP_NAME" | awk '{print $2}' | grep -E "^$PID$"`
	    if [ -n "$RUNPID" ]; then
	    	echo "ERROR: The $APP_NAME PID:$RUNPID already started!"
	        exit 1
	    fi
    fi
fi

# PIDS=`ps -ef | grep java | grep "$APP_NAME" | awk '{print $2}'`

# if [ -n "$PIDS" ]; then
#     echo "ERROR: The $APP_NAME already started!"
#     echo "PID: $PIDS"
#     exit 1
# fi

# if [ -n "$SERVER_PORT" ]; then
#     SERVER_PORT_COUNT=`netstat -tln | grep $SERVER_PORT | wc -l`
#     if [ $SERVER_PORT_COUNT -gt 0 ]; then
#         echo "ERROR: The $APP_NAME port $SERVER_PORT already used!"
#         exit 1
#     fi
# fi

# if [ ! -d "$LOG_DIR" ] ; then
#    mkdir $LOG_DIR
# fi

JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "

JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server -Xms512m -Xmx2g "
else
    JAVA_MEM_OPTS=" -server -Xms512m -Xmx2g "
fi

echo -e "Starting the $APP_NAME ...\n"
# nohup java $DEBUG_PARAM $GC_PARAM $JAVA_MEM_OPTS -jar $APP_DIR/$APP_NAME --spring.config.location=$CONFIG_FILE > /dev/null 2>&1 & 
nohup java $DEBUG_PARAM $GC_PARAM $JAVA_MEM_OPTS -jar $APP_DIR/$APP_NAME $APP_PARAMS > /dev/null 2>&1 &   


PIDS=`ps -f | grep java | grep "$APP_NAME" | awk '{print $2}'`
echo "PID: $PIDS"
echo "$APP_NAME is running..."
