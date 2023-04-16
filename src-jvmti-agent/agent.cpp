#include <string.h>
#include <jvmti.h>
#include <jni.h>

static jvmtiEnv *jvmti = NULL;

jthread jthr(JNIEnv *env)
{
    jclass thrClass;
    jmethodID cid;
    jthread res;

    thrClass = env->FindClass("java/lang/Thread");

    cid = env->GetMethodID(thrClass, "<init>", "()V");

    res = env->NewObject(thrClass, cid);

    return res;
}

void JNICALL agent_start(jvmtiEnv *jvmti_env, JNIEnv *jni_env, void *p)
{

    JNIEnv *env;

    printf("agent_start--start\n");

    char *className = "org/example/openj916480/Main";

    char *methodName = "agentMethodeEntry";

    char *descriptor = "()V";

    jclass callbackClass = jni_env->FindClass(className);

    if (!callbackClass)
    {

        fprintf(stderr, "Native Agent:\tUnable to locate callback class.\n");

        return;
    }

    jmethodID callbackMethodID = jni_env->GetStaticMethodID(callbackClass, methodName, descriptor);

    jni_env->CallStaticVoidMethodV(callbackClass, callbackMethodID, NULL);

    printf("agent_start--end\n");
}

void JNICALL callback_on_VMStart(jvmtiEnv *jvmti, JNIEnv *jni)

{

    printf("callback_on_VMStart\n");
}

void JNICALL callback_on_SampledObjectAlloc(

    jvmtiEnv *jvmti_env,

    JNIEnv *jni_env,

    jthread thread,

    jobject object,

    jclass object_klass,

    jlong size)

{

    // printf("call_back_on_sampled\n");
}

void JNICALL callback_on_VMInit(jvmtiEnv *jvmti_env, JNIEnv *env, jthread thread)
{

    printf("callback_on_VMInit\n");

    jvmti->RunAgentThread(jthr(env), agent_start, (void *)999, 5);
}

JNIEXPORT jint JNICALL Agent_OnLoad(JavaVM *javavm, char *options, void *reserved)

{

    printf("Agent_OnLoad\n");

    {

        int result = javavm->GetEnv((void **)&jvmti, JVMTI_VERSION_1_0);

        if (result != JNI_OK || jvmti == NULL)
        {

            printf("COMPATIBILITY ERROR - Unable to access JVMTI Version 1 (0x%x) - is your J2SE a 1.5 or newer version? JavaVM->GetEnv() returned %d\n", JVMTI_VERSION_1, result);

            return result;
        }
    }

    jvmtiEventCallbacks callbacks;

    memset(&callbacks, 0, sizeof(callbacks));

    callbacks.SampledObjectAlloc = &callback_on_SampledObjectAlloc;

    callbacks.VMInit = &callback_on_VMInit;

    jvmtiCapabilities caps;

    memset(&caps, 0, sizeof(caps));

    caps.can_generate_sampled_object_alloc_events = 1;

    jvmti->AddCapabilities(&caps);

    jvmti->SetEventCallbacks(&callbacks, sizeof(callbacks));

    jvmti->SetEventNotificationMode(JVMTI_ENABLE, JVMTI_EVENT_SAMPLED_OBJECT_ALLOC, NULL);

    jvmti->SetEventNotificationMode(JVMTI_ENABLE, JVMTI_EVENT_VM_INIT, NULL);

    jvmti->SetHeapSamplingInterval(1);

    return JNI_OK;
}

JNIEXPORT void JNICALL Agent_OnUnload(JavaVM *vm)

{

    printf("Agent_OnUnload\n");
}