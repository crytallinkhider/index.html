// Dentro da sua MainActivity.java
public class AndroidBridge {

    @JavascriptInterface
    public void executarComando(String nome, String valor) {
        // Envia valores para o seu módulo C++ ou Shell
        runOnUiThread(() -> Toast.makeText(MainActivity.this, nome + " ajustado para " + valor, Toast.LENGTH_SHORT).show());
    }

    @JavascriptInterface
    public void injetarEIniciarJogo() {
        runOnUiThread(() -> {
            // 1. Extrai seu arquivo .so ou script da pasta assets
            extractAsset("lib_module.so", getPackageName(), "lib_module.so");
            
            // 2. Dá permissão de execução (Shell)
            try {
                Runtime.getRuntime().exec("chmod 777 /data/data/" + getPackageName() + "/lib_module.so");
            } catch (Exception e) {}

            // 3. Abre o Free Fire Automaticamente
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.dts.freefireth");
            if (intent == null) intent = getPackageManager().getLaunchIntentForPackage("com.dts.freefiremax");
            
            if (intent != null) {
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Injetado com Sucesso!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
                        // Desativar ESP
                        showToast("ESP (Wallhack) DESATIVADO!");
                    }
                } else if (nomeFuncao.equals("set_camera_fov")) {
                    // Mudar FOV da Câmera
                    showToast("FOV Câmera: " + valor);
                } else if (nomeFuncao.equals("painel_oculto")) {
                    // Ocultou o painel, talvez mostrar um botão flutuante
                    createFloatingButton();
                } else if (nomeFuncao.equals("painel_visivel")) {
                    // Mostrou o painel, remover botão flutuante
                    removeFloatingButton();
                }
            });
        }

        @JavascriptInterface
        public void injetarEIniciarJogo() {
            runOnUiThread(() -> {
                // Opcional: Mostrar uma barra de progresso ou Toast enquanto injeta
                // showToast("Injetando módulos... aguarde!");
                
                // 1. Criar diretórios (Seus blocos de FileUtil.makeDir)
                // Exemplo:
                // FileUtil.makeDir(FileUtil.getExternalStorageDir().concat("/android/data/com.dts.freefireth/files/contentcache/compulsory/android/gameassetbundles/config/"));
                // ... (TODOS OS SEUS MAKEDIR AQUI) ...

                // 2. Extrair o binário C++ (lib_module.so) para a pasta do seu app
                // Você precisaria ter o lib_module.so na pasta 'assets' do seu projeto Android
                extractAsset("lib_module.so", getApplicationContext().getPackageName(), "lib_module.so");
                
                // 3. Dar permissão de execução (chmod 777)
                // Isso requer ROOT ou que o arquivo esteja na sua pasta de dados
                String modulePath = getApplicationInfo().dataDir + "/lib_module.so";
                executeShellCommand("chmod 777 " + modulePath);

                // 4. Executar o binário C++ (que agora é o 'hack')
                // Isso também requer ROOT ou o ambiente adequado
                executeShellCommand(modulePath);

                // 5. Abrir o Free Fire
                abrirFreeFire();

                showToast("Injeção COMPLETA e Free Fire iniciado!");
            });
        }
    }

    // --- Funções Auxiliares para Java ---

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        // Se você usa TastyToast, pode substituir por:
        // TastyToast.makeText(getApplicationContext(), message, TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
    }

    private void extractAsset(String assetName, String packageName, String destFileName) {
        try {
            InputStream input = getAssets().open(assetName);
            File destDir = new File("/data/data/" + packageName);
            if (!destDir.exists()) {
                destDir.mkdirs(); // Garante que o diretório existe
            }
            OutputStream output = new FileOutputStream(destDir.getAbsolutePath() + "/" + destFileName);
            byte[] data = new byte[1024];
            int count;
            while ((count = input.read(data)) > 0) {
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            showToast("Erro ao extrair asset: " + e.getMessage());
        }
    }

    private void executeShellCommand(String command) {
        try {
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            showToast("Erro Shell: " + e.getMessage());
        }
    }

    private void abrirFreeFire() {
        Intent intent = getPackageManager().getLaunchIntentForPackage(FF_NORMAL_PACKAGE);
        if (intent == null) {
            intent = getPackageManager().getLaunchIntentForPackage(FF_MAX_PACKAGE); // Tenta o Max se o Normal não for encontrado
        }

        if (intent != null) {
            startActivity(intent);
        } else {
            showToast("Free Fire não encontrado!");
        }
    }

    // --- Funções para o Botão Flutuante (Floating Window) ---
    private void createFloatingButton() {
        if (!Settings.canDrawOverlays(this)) {
            // Se não tiver permissão, pede ao usuário
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }

        if (floatingView != null) return; // Já existe, não cria novamente

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        floatingView = inflater.inflate(R.layout.floating_button_layout, null); // Crie um XML para este layout

        int layout_flag;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layout_flag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layout_flag = WindowManager.LayoutParams.TYPE_PHONE;
        }

        floatingParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layout_flag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                android.graphics.PixelFormat.TRANSLUCENT);

        floatingParams.gravity = Gravity.TOP | Gravity.START; // Posição inicial
        floatingParams.x = 0;
        floatingParams.y = 100;

        // Tornar o botão arrastável
        floatingView.findViewById(R.id.floating_button_id).setOnTouchListener(new View.OnTouchListener() { // Adapte o ID
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = floatingParams.x;
                        initialY = floatingParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        floatingParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                        floatingParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(floatingView, floatingParams);
                        return true;
                    case MotionEvent.ACTION_UP:
                        // Ao soltar, se foi um clique (pouco movimento), mostrar o painel
                        float deltaX = event.getRawX() - initialTouchX;
                        float deltaY = event.getRawY() - initialTouchY;
                        if (Math.abs(deltaX) < 10 && Math.abs(deltaY) < 10) { // Tolerância pequena para clique
                            removeFloatingButton(); // Remove o botão
                            webView.loadUrl("javascript:mostrar()"); // Mostra o painel
                        }
                        return true;
                }
                return false;
            }
        });
        
        windowManager.addView(floatingView, floatingParams);
    }

    private void removeFloatingButton() {
        if (floatingView != null && windowManager != null) {
            windowManager.removeView(floatingView);
            floatingView = null;
        }
    }

    // Lembre-se de destruir o botão flutuante quando o app é fechado
    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeFloatingButton();
    }package com.yourpackagename; // Substitua pelo seu nome de pacote

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

// Para o seu "tastytoast", se você o tiver configurado como biblioteca
// import com.sdsmdg.tastytoast.TastyToast; 

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private WindowManager windowManager;
    private View floatingView; // Para o botão flutuante
    private WindowManager.LayoutParams floatingParams; // Parâmetros do botão flutuante
    private Timer timer;
    private int progress = 0; // Para a barra de progresso (exemplo)

    // Definições para os pacotes do Free Fire
    private static final String FF_NORMAL_PACKAGE = "com.dts.freefireth";
    private static final String FF_MAX_PACKAGE = "com.dts.freefiremax";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // O layout da sua Activity deve ter um WebView. Ex: activity_main.xml
        // <WebView
        //     android:id="@+id/webview_painel"
        //     android:layout_width="match_parent"
        //     android:layout_height="match_parent" />
        setContentView(R.layout.activity_main); // Garanta que seu layout tem uma WebView com ID webview_painel

        webView = findViewById(R.id.webview_painel); // Seu WebView no layout
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true); // Para o HTML armazenar dados se precisar

        // Carrega o HTML da pasta assets
        webView.loadUrl("file:///android_asset/index.html");

        // Interface JavaScript
        webView.addJavascriptInterface(new AndroidBridge(), "AndroidBridge");

        // Opcional: Se quiser que o WebView não abra links externos no navegador
        webView.setWebViewClient(new WebViewClient());
    }

    // Classe da Ponte JavaScript
    public class AndroidBridge {

        @JavascriptInterface
        public void executarComando(String nomeFuncao, String valor) {
            runOnUiThread(() -> {
                // Toast.makeText(MainActivity.this, "Comando JS: " + nomeFuncao + " -> " + valor, Toast.LENGTH_SHORT).show();

                // *** AQUI VOCÊ INTEGRARIA SEUS BLOCOS DE LÓGICA DO SKETCHWARE ***
                // EXEMPLOS:

                if (nomeFuncao.equals("toggle_aimbot")) {
                    if (Boolean.parseBoolean(valor)) {
                        // Ativar Aimbot - Chamar função C++ (ex: callNativeFunction("toggleAimbot", 1))
                        showToast("Aimbot ATIVADO!");
                    } else {
                        // Desativar Aimbot - Chamar função C++ (ex: callNativeFunction("toggleAimbot", 0))
                        showToast("Aimbot DESATIVADO!");
                    }
                } else if (nomeFuncao.equals("set_aimbot_sens")) {
                    // Mudar sensibilidade do Aimbot - Chamar função C++ (ex: callNativeFunction("setAimbotSens", Integer.parseInt(valor)))
                    showToast("Sensibilidade Aimbot: " + valor);
                } else if (nomeFuncao.equals("toggle_aimlock")) {
                    if (Boolean.parseBoolean(valor)) {
                        // Ativar Aimlock
                        showToast("Aim Lock ATIVADO!");
                    } else {
                        // Desativar Aimlock
                        showToast("Aim Lock DESATIVADO!");
                    }
                } else if (nomeFuncao.equals("set_aimlock_fov")) {
                    // Mudar FOV do Aimlock
                    showToast("FOV Aim Lock: " + valor);
                } else if (nomeFuncao.equals("toggle_esp")) {
                    if (Boolean.parseBoolean(valor)) {
                        // Ativar ESP
                        showToast("ESP (Wallhack) ATIVADO!");
                    } else {
                        // Desativar ESP
                        showToast("ESP (Wallhack) DESATIVADO!");
                    }
                } else if (nomeFuncao.equals("set_camera_fov")) {
                    // Mudar FOV da Câmera
                    showToast("FOV Câmera: " + valor);
                } else if (nomeFuncao.equals("painel_oculto")) {
                    // Ocultou o painel, talvez mostrar um botão flutuante
                    createFloatingButton();
                } else if (nomeFuncao.equals("painel_visivel")) {
                    // Mostrou o painel, remover botão flutuante
                    removeFloatingButton();
                }
            });
        }

        @JavascriptInterface
        public void injetarEIniciarJogo() {
            runOnUiThread(() -> {
                // Opcional: Mostrar uma barra de progresso ou Toast enquanto injeta
                // showToast("Injetando módulos... aguarde!");
                
                // 1. Criar diretórios (Seus blocos de FileUtil.makeDir)
                // Exemplo:
                // FileUtil.makeDir(FileUtil.getExternalStorageDir().concat("/android/data/com.dts.freefireth/files/contentcache/compulsory/android/gameassetbundles/config/"));
                // ... (TODOS OS SEUS MAKEDIR AQUI) ...

                // 2. Extrair o binário C++ (lib_module.so) para a pasta do seu app
                // Você precisaria ter o lib_module.so na pasta 'assets' do seu projeto Android
                extractAsset("lib_module.so", getApplicationContext().getPackageName(), "lib_module.so");
                
                // 3. Dar permissão de execução (chmod 777)
                // Isso requer ROOT ou que o arquivo esteja na sua pasta de dados
                String modulePath = getApplicationInfo().dataDir + "/lib_module.so";
                executeShellCommand("chmod 777 " + modulePath);

                // 4. Executar o binário C++ (que agora é o 'hack')
                // Isso também requer ROOT ou o ambiente adequado
                executeShellCommand(modulePath);

                // 5. Abrir o Free Fire
                abrirFreeFire();

                showToast("Injeção COMPLETA e Free Fire iniciado!");
            });
        }
    }

    // --- Funções Auxiliares para Java ---

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        // Se você usa TastyToast, pode substituir por:
        // TastyToast.makeText(getApplicationContext(), message, TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
    }

    private void extractAsset(String assetName, String packageName, String destFileName) {
        try {
            InputStream input = getAssets().open(assetName);
            File destDir = new File("/data/data/" + packageName);
            if (!destDir.exists()) {
                destDir.mkdirs(); // Garante que o diretório existe
            }
            OutputStream output = new FileOutputStream(destDir.getAbsolutePath() + "/" + destFileName);
            byte[] data = new byte[1024];
            int count;
            while ((count = input.read(data)) > 0) {
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            showToast("Erro ao extrair asset: " + e.getMessage());
        }
    }

    private void executeShellCommand(String command) {
        try {
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            showToast("Erro Shell: " + e.getMessage());
        }
    }

    private void abrirFreeFire() {
        Intent intent = getPackageManager().getLaunchIntentForPackage(FF_NORMAL_PACKAGE);
        if (intent == null) {
            intent = getPackageManager().getLaunchIntentForPackage(FF_MAX_PACKAGE); // Tenta o Max se o Normal não for encontrado
        }

        if (intent != null) {
            startActivity(intent);
        } else {
            showToast("Free Fire não encontrado!");
        }
    }

    // --- Funções para o Botão Flutuante (Floating Window) ---
    private void createFloatingButton() {
        if (!Settings.canDrawOverlays(this)) {
            // Se não tiver permissão, pede ao usuário
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }

        if (floatingView != null) return; // Já existe, não cria novamente

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        floatingView = inflater.inflate(R.layout.floating_button_layout, null); // Crie um XML para este layout

        int layout_flag;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layout_flag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layout_flag = WindowManager.LayoutParams.TYPE_PHONE;
        }

        floatingParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layout_flag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                android.graphics.PixelFormat.TRANSLUCENT);

        floatingParams.gravity = Gravity.TOP | Gravity.START; // Posição inicial
        floatingParams.x = 0;
        floatingParams.y = 100;

        // Tornar o botão arrastável
        floatingView.findViewById(R.id.floating_button_id).setOnTouchListener(new View.OnTouchListener() { // Adapte o ID
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = floatingParams.x;
                        initialY = floatingParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        floatingParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                        floatingParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(floatingView, floatingParams);
                        return true;
                    case MotionEvent.ACTION_UP:
                        // Ao soltar, se foi um clique (pouco movimento), mostrar o painel
                        float deltaX = event.getRawX() - initialTouchX;
                        float deltaY = event.getRawY() - initialTouchY;
                        if (Math.abs(deltaX) < 10 && Math.abs(deltaY) < 10) { // Tolerância pequena para clique
                            removeFloatingButton(); // Remove o botão
                            webView.loadUrl("javascript:mostrar()"); // Mostra o painel
                        }
                        return true;
                }
                return false;
            }
        });
        
        windowManager.addView(floatingView, floatingParams);
    }

    private void removeFloatingButton() {
        if (floatingView != null && windowManager != null) {
            windowManager.removeView(floatingView);
            floatingView = null;
        }
    }

    // Lembre-se de destruir o botão flutuante quando o app é fechado
    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeFloatingButton();
    }
}

}

