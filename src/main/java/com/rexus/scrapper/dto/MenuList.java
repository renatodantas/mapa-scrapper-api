package com.rexus.scrapper.dto;

import java.util.Map;

public class MenuList {

    private final Map<String, String> tipos;
    private final Map<String, String> especies;
    private final Map<String, String> mercadorias;
    private final Map<String, String> finalidades;
    private final Map<String, String> blocos;
    private final Map<String, String> paises;

    public MenuList(
            Map<String, String> tipos,
            Map<String, String> especies,
            Map<String, String> mercadorias,
            Map<String, String> finalidades,
            Map<String, String> blocos,
            Map<String, String> paises) {
        this.tipos = tipos;
        this.especies = especies;
        this.mercadorias = mercadorias;
        this.finalidades = finalidades;
        this.blocos = blocos;
        this.paises = paises;
    }

    public Map<String, String> getTipos() {
        return tipos;
    }

    public Map<String, String> getEspecies() {
        return especies;
    }

    public Map<String, String> getMercadorias() {
        return mercadorias;
    }

    public Map<String, String> getFinalidades() {
        return finalidades;
    }

    public Map<String, String> getBlocos() {
        return blocos;
    }

    public Map<String, String> getPaises() {
        return paises;
    }
}
