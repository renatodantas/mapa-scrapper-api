package com.rexus.scrapper.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.enterprise.context.ApplicationScoped;

/**
 * Classe de remoção de conteúdo do documento.
 */
@ApplicationScoped
public class ContentCleaner {

    /**
     * Limpa as tags consideradas desnecessárias no documento HTML.
     * @param document documento HTML no formato {@link Document}
     */
    public void clean(Document document) {
        // Removendo elementos desnecessários: 1ª DIV e 1ª TABLE
        Element element = (Element) document.getElementsByTagName("div").item(0);
        element.getParentNode().removeChild(element);
        element = (Element) document.getElementsByTagName("table").item(0);
        element.getParentNode().removeChild(element);

        // Remove todos os parágrafos depois da label "Carimbo / Stamp"
        NodeList lista = document.getElementsByTagName("p");
        boolean shouldRemove = false;
        for (int i = 0; i < lista.getLength(); i++) {
            element = (Element) lista.item(i);
            if (element.getTextContent().toLowerCase().contains("carimbo / stamp")) {
                shouldRemove = true;
            }
            if (shouldRemove) {
                element.getParentNode().removeChild(element);
            }
        }
    }
}
