#!/bin/bash
cd `dirname $0`

APP_DIR=../lib
APP_NAME="${project.build.finalName}.${project.packaging}"

PID_FILE="${runtime.pidfile}"

if [ -f "$PID_FILE" ]; then
    PID=`cat "$PID_FILE"`
    if [ -n "$PID" ]; then
    
        kill $PID > /dev/null 2>&1
        echo "killing $PID"
        COUNT=0
        while [ $COUNT -lt 1 ]; do
            echo -e ".\c"
            sleep 1
            
            PID_EXIST=`ps -f -p $PID | grep java`
            
            if [ -z "$PID_EXIST" ]; then
                rm -rf "$PID_FILE"
                echo "stop pid $PID"
                exit 0
            fi
        done
        
    fi
fi

PIDS=`ps -ef | grep java | grep "$APP_NAME" | awk '{print $2}'`
if [ -z "$PIDS" ]; then
    echo "ERROR: The $APP_NAME does not started!"
    exit 1
fi

echo -e "Stopping the $APP_NAME ...\n"
for PID in $PIDS;do
    kill $PID > /dev/null 2>&1
done

COUNT=0
while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    sleep 1
    for PID in $PIDS;do
        PID_EXIST=`ps -f -p $PID | grep java`
    if [ -z "$PID_EXIST" ]; then
        echo "stop pid $PID"
        exit 0
    fi
    done
done

echo "PID: $PIDS"
echo "$APP_NAME is stopped."