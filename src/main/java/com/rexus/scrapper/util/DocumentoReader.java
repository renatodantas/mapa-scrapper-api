package com.rexus.scrapper.util;

import org.jboss.logging.Logger;
import org.w3c.dom.Document;

import javax.enterprise.context.ApplicationScoped;
import java.io.InputStream;

@ApplicationScoped
public class DocumentoReader {

    private final LimpezaConteudo cleaner;
    private final DocumentoConverter converter;
    private final Logger log;

    public DocumentoReader(
            DocumentoConverter converter,
            LimpezaConteudo limpezaConteudo,
            Logger log) {
        this.cleaner = limpezaConteudo;
        this.converter = converter;
        this.log = log;
    }

    /**
     * Lê a entrada do documento Word no formato {@link InputStream},
     * faz a remoção do conteúdo desnecessário e devolve o conteúdo
     * no formato XHTML.
     *
     * @param documentInputStream documento do Word
     * @return o HTML do documento
     * @throws Exception em caso de erro na leitura
     */
    public String read(InputStream documentInputStream) throws Exception {
        final Document document = converter.convertToDocument(documentInputStream);
        cleaner.limpar(document);
        return converter.convertToString(document);
    }
}