package com.rexus.scrapper.util;

import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.DefaultJavaScriptErrorListener;

public final class WebClientBuilder {

    /**
     * Cria uma instância configurada do {@link WebClient}.
     *
     * @return uma instância do {@link WebClient}
     */
    public static WebClient build() {
        final WebClient client = new WebClient();
        // Desabilita erros do javascript
        client.getOptions().setThrowExceptionOnScriptError(false);
        // Desabilita logs de erro de arquivos JS mal informados
        client.setJavaScriptErrorListener(new DefaultJavaScriptErrorListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void scriptException(HtmlPage page, ScriptException scriptException) {
            }
        });
        return client;
    }
}