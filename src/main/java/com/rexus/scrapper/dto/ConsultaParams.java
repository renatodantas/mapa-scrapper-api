package com.rexus.scrapper.dto;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class ConsultaParams {

    @QueryParam("tipo")
    @DefaultValue("1")
    private String codigoTipo;

    @QueryParam("especie")
    @DefaultValue("8")
    private String codigoEspecie;

    @QueryParam("mercadoria")
    @DefaultValue("2")
    private String codigoMercadoria;

    @QueryParam("finalidade")
    @DefaultValue("5")
    private String codigoFinalidade;

    @QueryParam("bloco")
    @DefaultValue("")
    private String codigoBloco;

    @QueryParam("pais")
    @DefaultValue("DEU")
    private String codigoPais;

    public String getCodigoTipo() {
        return codigoTipo;
    }

    public void setCodigoTipo(String codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    public String getCodigoEspecie() {
        return codigoEspecie;
    }

    public void setCodigoEspecie(String codigoEspecie) {
        this.codigoEspecie = codigoEspecie;
    }

    public String getCodigoMercadoria() {
        return codigoMercadoria;
    }

    public void setCodigoMercadoria(String codigoMercadoria) {
        this.codigoMercadoria = codigoMercadoria;
    }

    public String getCodigoFinalidade() {
        return codigoFinalidade;
    }

    public void setCodigoFinalidade(String codigoFinalidade) {
        this.codigoFinalidade = codigoFinalidade;
    }

    public String getCodigoBloco() {
        return codigoBloco;
    }

    public void setCodigoBloco(String codigoBloco) {
        this.codigoBloco = codigoBloco;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    @Override
    public String toString() {
        return String.format("tipo [%s], espécie [%s], mercadoria [%s], finalidade [%s], bloco [%s], país [%s]",
                codigoTipo, codigoEspecie, codigoMercadoria, codigoFinalidade, codigoBloco, codigoPais);
    }
}