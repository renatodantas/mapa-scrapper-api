package com.rexus.scrapper.dto;

public class ConsultaReturn {

    private final int linkIndex;
    private final String documento;

    public ConsultaReturn(int linkIndex, String documento) {
        this.linkIndex = linkIndex;
        this.documento = documento;
    }

    public int getLinkIndex() {
        return linkIndex;
    }

    public String getDocumento() {
        return documento;
    }
}