// Dentro da sua MainActivity.java

@SuppressLint("JavascriptInterface")
public class AndroidBridge {

    @JavascriptInterface
    public void executarComando(String nome, String valor) {
        runOnUiThread(() -> {
            Toast.makeText(MainActivity.this, nome + " : " + valor, Toast.LENGTH_SHORT).show();
            // Aqui você envia os comandos para o C++
        });
    }

    @JavascriptInterface
    public void injetarEIniciarJogo() {
        runOnUiThread(() -> {
            try {
                // 1. EXTRAIR O ARQUIVO .SO (Fundamental para não dar erro)
                extractAsset("lib_module.so", getPackageName(), "lib_module.so");

                // 2. DAR PERMISSÃO SHELL
                String path = "/data/data/" + getPackageName() + "/lib_module.so";
                Runtime.getRuntime().exec("chmod 777 " + path);
                
                // 3. ABRIR O JOGO (Tenta o Normal e o Max)
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.dts.freefireth");
                if (intent == null) {
                    intent = getPackageManager().getLaunchIntentForPackage("com.dts.freefiremax");
                }
                
                if (intent != null) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "INJETADO COM SUCESSO!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "ERRO: Instale o Free Fire!", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Erro Shell: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

// NO ONCREATE, NÃO ESQUEÇA DISSO:
// webView.getSettings().setJavaScriptEnabled(true);
// webView.addJavascriptInterface(new AndroidBridge(), "AndroidBridge");

