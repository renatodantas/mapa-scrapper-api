package com.rexus.scrapper.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Item do menu.
 */
@Schema(description = "Representação de um parâmetro de pesquisa")
public class MenuItem {

    @Schema(description = "Descrição por extenso do parâmetro")
    private final String descricao;

    @Schema(description = "Valor do parâmetro (normalmente uma sigla)")
    private final String valor;

    /**
     * Construtor.
     *
     * @param descricao do item
     * @param valor do item
     */
    public MenuItem(String descricao, String valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getValor() {
        return valor;
    }
}