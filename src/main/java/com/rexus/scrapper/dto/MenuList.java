package com.rexus.scrapper.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Schema(description = "Lista de valores para os parâmetros de pesquisa de documentos")
public class MenuList {

    @Schema(description = "Tipos de documento")
    private final List<MenuItem> tipos;

    @Schema(description = "Grupos da espécie animal")
    private final List<MenuItem> especies;

    @Schema(description = "Grupos da mercadoria")
    private final List<MenuItem> mercadorias;

    @Schema(description = "Finalidades do documento")
    private final List<MenuItem> finalidades;

    @Schema(description = "Blocos econômicos")
    private final List<MenuItem> blocos;

    @Schema(description = "Países")
    private final List<MenuItem> paises;

    public MenuList(
            List<MenuItem> tipos,
            List<MenuItem> especies,
            List<MenuItem> mercadorias,
            List<MenuItem> finalidades,
            List<MenuItem> blocos,
            List<MenuItem> paises) {
        this.tipos = tipos;
        this.especies = especies;
        this.mercadorias = mercadorias;
        this.finalidades = finalidades;
        this.blocos = blocos;
        this.paises = paises;
    }

    public List<MenuItem> getTipos() {
        return tipos;
    }

    public List<MenuItem> getEspecies() {
        return especies;
    }

    public List<MenuItem> getMercadorias() {
        return mercadorias;
    }

    public List<MenuItem> getFinalidades() {
        return finalidades;
    }

    public List<MenuItem> getBlocos() {
        return blocos;
    }

    public List<MenuItem> getPaises() {
        return paises;
    }
}