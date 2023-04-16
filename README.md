# openj9-bug-16480
Repository contains code to reproduce the issue: https://github.com/eclipse-openj9/openj9/issues/16480#

The `src` folder contains Java Code. The `src-jvmti-agent` folder contains the code for the native agent. The native agent starts a thread after `JVMTI_EVENT_VM_INIT` which executes `org.example.openj916480.Main.agentMethodeEntry()`.

To reproduce the issue load a native agent library (`-agentpath`)
- The native agent has to register a callback for the `JVMTI_EVENT_SAMPLED_OBJECT_ALLOC` event
- The native agent is also required to start a thread in which data is allocated
- The Java code is a Spring Application with `@Configuration` classes

To increase the probability to hit the error `/src/main/java/org/example/openj916480/configuration` contains 10000 Configuration classes.
Also the started native agent thread is kept busy by generating random data `src/main/java/org/example/openj916480/Main.java`

## pre build
Download `openj9-bug-16480.tar.gz` extract and exec

`/opt/java/openjdk/bin/java -Xmx6g -agentpath:./agent.o -cp ./app.jar:./libs/* org.example.openj916480.Main`


## build & run
```
root@notebook:~/project$ docker build . -t openj9-bug-16480
root@notebook:~/project$ docker run openj9-bug-16480
generateRandomData Thread:30
17:58:19.266 0x21f00    j9vm.225    *   ** ASSERTION FAILED ** at /home/jenkins/workspace/build-scripts/jobs/jdk17u/jdk17u-linux-x64-openj9/workspace/build/src/openj9/runtime/vm/VMAccess.cpp:1163: (!((0 != (((currentThread->publicFlags)) & ((0x1000))))))
JVMDUMP039I Processing dump event "traceassert", detail "" at 2023/04/16 17:58:19 - please wait.
JVMDUMP032I JVM requested System dump using '/app/core.20230416.175819.7.0001.dmp' in response to an event
JVMDUMP012E Error in System dump: The core file created by child process with pid = 45 was not found. Expected to find core file with name "/mnt/wslg/dumps/core.main.45"
JVMDUMP032I JVM requested Java dump using '/app/javacore.20230416.175819.7.0002.txt' in response to an event
JVMDUMP010I Java dump written to /app/javacore.20230416.175819.7.0002.txt
JVMDUMP032I JVM requested Snap dump using '/app/Snap.20230416.175819.7.0003.trc' in response to an event
JVMDUMP010I Snap dump written to /app/Snap.20230416.175819.7.0003.trc
JVMDUMP013I Processed dump event "traceassert", detail "".
Agent_OnLoad
callback_on_VMInit
agent_start--start
```
