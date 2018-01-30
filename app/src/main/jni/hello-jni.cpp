//
// Created by gugu on 2018-01-29.
//
#include "com_gugu_biom_Main2Activity.h"

JNIEXPORT jstring JNICALL Java_com_gugu_biom_Main2Activity_stringFromJNI(JNIEnv *env, jobject obj) {
    return env->NewStringUTF("구구구");
}