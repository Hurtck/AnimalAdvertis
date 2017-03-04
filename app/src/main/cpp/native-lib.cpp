#include <jni.h>
#include <string>

extern "C"
jstring
Java_animaladvertis_com_animaladvertis_UserActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
