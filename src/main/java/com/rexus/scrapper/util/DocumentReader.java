package com.rexus.scrapper.util;

import org.jboss.logging.Logger;
import org.w3c.dom.Document;

import javax.enterprise.context.ApplicationScoped;
import java.io.InputStream;

@ApplicationScoped
public class DocumentReader {

    private final ContentCleaner cleaner;
    private final DocumentConverter converter;
    private final Logger log;

    public DocumentReader(
            DocumentConverter converter,
            ContentCleaner contentCleaner,
            Logger log) {
        this.cleaner = contentCleaner;
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
        cleaner.clean(document);
        return converter.convertToString(document);
    }
}
