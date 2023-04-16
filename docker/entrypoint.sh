#!/bin/bash

cd /app

/opt/java/openjdk/bin/java -Xmx6g -agentpath:./agent.o -cp /app/app.jar:/app/libs/* org.example.openj916480.Main