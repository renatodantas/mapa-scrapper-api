package com.rexus.scrapper.api;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindowAdapter;
import com.gargoylesoftware.htmlunit.WebWindowEvent;
import com.gargoylesoftware.htmlunit.html.*;
import com.rexus.scrapper.util.WebClientBuilder;
import com.rexus.scrapper.dto.DocumentosParams;
import com.rexus.scrapper.dto.LinkDocumento;
import com.rexus.scrapper.dto.MenuList;
import com.rexus.scrapper.util.DocumentoReader;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/documentos")
public class DocumentosApi {

    final Logger log;
    final DocumentoReader documentoReader;

    @ConfigProperty(name = "sisrec.url")
    String url;

    DocumentosApi(final Logger log, final DocumentoReader documentoReader) {
        this.log = log;
        this.documentoReader = documentoReader;
    }

    /**
     * Obtém os documentos disponíveis na pesquisa realizada.
     *
     * @param params parâmetros de pesquisa (ver {@link DocumentosParams}
     * @return a lista de documentos com os índices para de pesquisa (índice non-zero-based)
     * @throws Exception caso algum erro inesperado seja encontrado
     */
    @GET
    @Operation(summary = "Informações sobre os documentos disponíveis no SISREC")
    @APIResponse(responseCode = "200", description = "Retorna a lista de documentos encontrados",
            content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = MenuList.class, type = SchemaType.ARRAY)))
    public List<LinkDocumento> consultar(@BeanParam DocumentosParams params) throws Exception {
        log.infof("Parâmetros de pesquisa: " + params);

        try (final WebClient webClient = WebClientBuilder.build()) {
            final HtmlPage pageResultados = submeterFormulario(params, webClient);

            final HtmlTable table = pageResultados.querySelector("table.table-grid");
            if (table == null) {
                log.warn("Nenhum registro encontrado");
                throw new NotFoundException();
            }

            final HtmlTableBody body = table.getBodies().get(0);
            final List<LinkDocumento> itens = new ArrayList<>(0);
            log.infof("Quantidade de documentos: %d", body.getRows().size());

            for (int index = 0; index < body.getRows().size(); index++) {
                // Index 1: nome do documento
                // Index 7: link do documento
                final HtmlTableRow row = body.getRows().get(index);
                final String nomeDocumento = row.getCell(1).asText();
                log.infof("  - Documento %d: %s", index+1, nomeDocumento);
                itens.add(new LinkDocumento(index + 1, nomeDocumento));
            }
            return itens;
        }
    }

    /**
     * Obtém o conteúdo do documento no formato HTML.
     *
     * @param linkIndex índice do documento fornecido pela API {@link #consultar(DocumentosParams)}
     * @param response objeto de resposta assíncrona com o conteúdo HTML
     * @throws Exception caso algum erro inesperado seja encontrado
     */
    @GET
    @Path("/{index}")
    @Produces(MediaType.TEXT_HTML)
    @Operation(summary = "Conteúdo do documento baseado no índice e nos parâmetros de pesquisa")
    @APIResponse(responseCode = "200", description = "Obtém o conteúdo do documento no formato HTML",
            content = @Content(mediaType = MediaType.TEXT_HTML))
    @APIResponse(responseCode = "400", description = "Índice inválido na lista de documentos")
    @APIResponse(responseCode = "404", description = "Nenhum registro encontrado com os parâmetros de pesquisa")
    public void consultarNumero(
            @PathParam("index")
            @Parameter(description = "Índice do documento na lista")
            int linkIndex,
            @BeanParam DocumentosParams params,
            @Suspended AsyncResponse response)
        throws Exception {

        log.infof("Parâmetros de pesqu  isa: " + params + " / Link: %d", linkIndex);

        try (final WebClient webClient = WebClientBuilder.build()) {

            // Obtendo form e definindo parâmetros para pesquisa
            final HtmlPage pageResultados = submeterFormulario(params, webClient);

            final HtmlTable table = pageResultados.querySelector("table.table-grid");
            if (table == null) {
                response.resume(Response.status(NOT_FOUND).entity("Nenhum registro encontrado").build());
                return;
            }

            webClient.addWebWindowListener(new WebWindowAdapter() {
                @Override
                public void webWindowContentChanged(WebWindowEvent event) {
                    log.info("Documento obtido");

                    try (InputStream documento = event.getWebWindow().getEnclosedPage().getWebResponse().getContentAsStream()) {
                        String documentoHtml = documentoReader.read(documento);
                        response.resume(documentoHtml);
                    } catch (Exception e) {
                        log.error("Erro ao ler documento baixado", e);
                    }
                }
            });

            // Clicando no form
            log.info("Clicando no link do documento...");
            List<HtmlTableRow> tableRows = table.getBodies().get(0).getRows();
            if (tableRows.size() < linkIndex) {
                response.resume(Response.status(BAD_REQUEST).entity("Índice inválido na lista de documentos").build());
                return;
            }
            HtmlAnchor link = (HtmlAnchor) tableRows
                    .get(linkIndex - 1)
                    .getCell(7)
                    .getFirstElementChild();
            link.click();
        }
    }

    private HtmlPage submeterFormulario(DocumentosParams params, WebClient webClient) throws java.io.IOException {
        // Obtendo form e definindo parâmetros para pesquisa
        final HtmlPage pagePesquisa = webClient.getPage(url);
        final HtmlForm form = pagePesquisa.getFormByName("manterDocumentoForm");

        form.getSelectByName("normaCertificado.csDocumento")
                .setSelectedAttribute(params.getCodigoTipo(), true);
        form.getSelectByName("normaCertificado.grupoEspecieAnimal.idGrupoEspecieAnimal")
                .setSelectedAttribute(params.getCodigoEspecie(), true);
        form.getSelectByName("normaCertificado.grupoMercadoriaAnimal.idGrupoMercadoriaAnimal")
                .setSelectedAttribute(params.getCodigoMercadoria(), true);
        form.getSelectByName("normaCertificado.finalidade.idFinalidade")
                .setSelectedAttribute(params.getCodigoFinalidade(), true);
        form.getSelectByName("normaCertificado.idMercadoComum")
                .setSelectedAttribute(params.getCodigoBloco(), true);
        form.getSelectByName("normaCertificado.sgPais")
                .setSelectedAttribute(params.getCodigoPais(), true);

        log.info("Submetendo formulário...");
        final HtmlSubmitInput submit = form.getInputByName("method:consultarDocumento");
        return submit.click();
    }
}