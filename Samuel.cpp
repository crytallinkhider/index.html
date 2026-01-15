#include <jni.h>
#include <string>
#include <unistd.h>
#include <math.h>

// --- VARIÁVEIS DE CONTROLE (Sincronizadas com seu HTML) ---
bool aimbot_ligado = false;
bool aimlock_fixar = false;
bool esp_linhas = false;
float fov_raio = 90.0f;
float camera_distancia = 90.0f;

// --- ESTRUTURAS DE POSIÇÃO ---
struct Vec2 { float x, y; };
struct Vec3 { float x, y, z; };

// --- LÓGICA DO AIMBOT / FOV ---
void aplicar_aimbot(Vec2 playerMira, Vec2 inimigoTela) {
    if (!aimbot_ligado) return;

    // Calcula a distância entre a mira e o inimigo
    float dist = sqrt(pow(inimigoTela.x - playerMira.x, 2) + pow(inimigoTela.y - playerMira.y, 2));

    // VERIFICAÇÃO DE FOV (Só puxa se o inimigo estiver dentro do círculo que você move no site)
    if (dist <= fov_raio) {
        if (aimlock_fixar) {
            // Aqui o código forçaria os ângulos de visão do jogador para o inimigo
            // write_memory(offsets.viewAngles, calcular_angulo(inimigoTela));
        }
    }
}

// --- PONTE DE CONEXÃO COM O JAVA (JNI) ---
extern "C" {

    // Recebe comandos de Ligar/Desligar (Checkbox)
    JNIEXPORT void JNICALL
    Java_com_yourpackagename_MainActivity_toggleCheat(JNIEnv *env, jobject thiz, jstring name, jboolean toggle) {
        const char *cmd = env->GetStringUTFChars(name, 0);

        if (strcmp(cmd, "aimbot") == 0) aimbot_ligado = toggle;
        if (strcmp(cmd, "aimlock") == 0) aimlock_fixar = toggle;
        if (strcmp(cmd, "esp") == 0) esp_linhas = toggle;

        env->ReleaseStringUTFChars(name, cmd);
    }

    // Recebe valores de Sliders (FOV)
    JNIEXPORT void JNICALL
    Java_com_yourpackagename_MainActivity_updateCheat(JNIEnv *env, jobject thiz, jstring name, jfloat val) {
        const char *cmd = env->GetStringUTFChars(name, 0);

        if (strcmp(cmd, "aim_fov") == 0) fov_raio = val;
        if (strcmp(cmd, "cam_fov") == 0) camera_distancia = val;

        env->ReleaseStringUTFChars(name, cmd);
    }
}

