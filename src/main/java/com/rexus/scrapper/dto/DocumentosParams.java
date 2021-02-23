package com.rexus.scrapper.dto;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

/**
 * Parâmetros de pesquisa de um documento.
 */
public class DocumentosParams {

    @Parameter(description = "Tipo de documento")
    @QueryParam("tipo")
    @DefaultValue("")
    private String codigoTipo;

    @Parameter(description = "Grupo da espécie animal")
    @QueryParam("especie")
    @DefaultValue("")
    private String codigoEspecie;

    @Parameter(description = "Grupo da mercadoria")
    @QueryParam("mercadoria")
    @DefaultValue("")
    private String codigoMercadoria;

    @Parameter(description = "Finalidade do documento")
    @QueryParam("finalidade")
    @DefaultValue("")
    private String codigoFinalidade;

    @Parameter(description = "Bloco econômico")
    @QueryParam("bloco")
    @DefaultValue("")
    private String codigoBloco;

    @Parameter(description = "País")
    @QueryParam("pais")
    @DefaultValue("")
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