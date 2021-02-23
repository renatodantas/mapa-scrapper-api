package com.rexus.scrapper.api;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rexus.scrapper.util.WebClientBuilder;
import com.rexus.scrapper.dto.MenuItem;
import com.rexus.scrapper.dto.MenuList;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/menus")
public class MenusApi {

    @ConfigProperty(name = "sisrec.url")
    String url;

    @GET
    @Operation(summary = "Parâmetros usados pela API de Documentos")
    @APIResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = MenuList.class, type = SchemaType.ARRAY)))
    public MenuList getMenus() throws Exception {
        try (final WebClient webClient = WebClientBuilder.build()) {
            webClient.getOptions().setThrowExceptionOnScriptError(false);

            // Obtendo form e definindo parâmetros para pesquisa
            final HtmlPage pagePesquisa = webClient.getPage(url);
            final HtmlForm form = pagePesquisa.getFormByName("manterDocumentoForm");

            var tipos = getOptionAsMenuItems(form, "normaCertificado.csDocumento");
            var especies = getOptionAsMenuItems(form, "normaCertificado.grupoEspecieAnimal.idGrupoEspecieAnimal");
            var mercadorias = getOptionAsMenuItems(form, "normaCertificado.grupoMercadoriaAnimal.idGrupoMercadoriaAnimal");
            var finalidades = getOptionAsMenuItems(form, "normaCertificado.finalidade.idFinalidade");
            var blocos = getOptionAsMenuItems(form, "normaCertificado.idMercadoComum");
            var paises = getOptionAsMenuItems(form, "normaCertificado.sgPais");

            return new MenuList(tipos, especies, mercadorias, finalidades, blocos, paises);
        }
    }

    private List<MenuItem> getOptionAsMenuItems(HtmlForm form, String campo) {
        return form.getSelectByName(campo)
                .getOptions()
                .stream()
                .map(option -> new MenuItem(option.getText(), option.getValueAttribute()))
                .collect(toList());
    }
}