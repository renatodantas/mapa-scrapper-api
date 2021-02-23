package com.rexus.scrapper.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Representa um link para um documento.
 */
@Schema(description = "Representação para o link de um documento")
public class LinkDocumento {

    @Schema(description = "Índice (posição) do documento na tabela")
    private final int linkIndex;

    @Schema(description = "Nome completo do documento")
    private final String nome;

    public LinkDocumento(int linkIndex, String nome) {
        this.linkIndex = linkIndex;
        this.nome = nome;
    }

    public int getLinkIndex() {
        return linkIndex;
    }

    public String getNome() {
        return nome;
    }
}