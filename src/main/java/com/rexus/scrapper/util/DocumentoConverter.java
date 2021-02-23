package com.rexus.scrapper.util;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.ToXMLContentHandler;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Conversor de documento Word para HTML.
 */
@ApplicationScoped
public class DocumentoConverter {

    /**
     * Converte o conteúdo do Word em {@link InputStream} em string HTML.
     * @param documentInputStream documento do Word
     * @return o HTML do documento convertido no formato {@link Document}
     * @throws Exception se houver erro durante a conversão
     */
    public Document convertToDocument(InputStream documentInputStream) throws Exception {
        ContentHandler handler = new ToXMLContentHandler();
        new AutoDetectParser().parse(documentInputStream, handler, new Metadata());
        String html = handler.toString();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(html.getBytes()));
    }

    public String convertToString(Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter stringWriter = new StringWriter();

        transformer.transform(
                new DOMSource(document),
                new StreamResult(stringWriter));

        return stringWriter.toString();
    }
}