#include <jni.h>
#include <string.h>

// Variáveis que o site controla
float aim_fov = 90.0f;
bool aim_lock = false;

extern "C" JNIEXPORT void JNICALL
Java_com_seunome_MainActivity_setNativeCheat(JNIEnv *env, jobject thiz, jstring name, jfloat val) {
    const char *cmd = env->GetStringUTFChars(name, 0);
    
    if (strcmp(cmd, "set_fov") == 0) {
        aim_fov = val; // Altera o tamanho do círculo de mira
    }
    
    env->ReleaseStringUTFChars(name, cmd);
}
