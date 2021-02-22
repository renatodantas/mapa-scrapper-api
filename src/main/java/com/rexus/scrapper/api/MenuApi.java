package com.rexus.scrapper.api;

import static java.util.stream.Collectors.toMap;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rexus.scrapper.dto.MenuList;

@Path("/menus")
public class MenuApi {

    @ConfigProperty(name = "sisrec.url")
    private String url;

    @GET
    public MenuList getMenus() throws Exception {
        try (final WebClient webClient = new WebClient()) {
            webClient.getOptions().setThrowExceptionOnScriptError(false);

            // Obtendo form e definindo parâmetros para pesquisa
            final HtmlPage pagePesquisa = webClient.getPage(url);
            final HtmlForm form = pagePesquisa.getFormByName("manterDocumentoForm");

            var tipos = getOptionAsMap(form, "normaCertificado.csDocumento");
            var especies = getOptionAsMap(form, "normaCertificado.grupoEspecieAnimal.idGrupoEspecieAnimal");
            var mercadorias = getOptionAsMap(form, "normaCertificado.grupoMercadoriaAnimal.idGrupoMercadoriaAnimal");
            var finalidades = getOptionAsMap(form, "normaCertificado.finalidade.idFinalidade");
            var blocos = getOptionAsMap(form, "normaCertificado.idMercadoComum");
            var paises = getOptionAsMap(form, "normaCertificado.sgPais");

            return new MenuList(tipos, especies, mercadorias, finalidades, blocos, paises);
        }
    }

    private Map<String, String> getOptionAsMap(HtmlForm form, String campo) {
        return form.getSelectByName(campo)
                .getOptions()
                .stream()
                .collect(toMap(HtmlOption::getValueAttribute, HtmlOption::getText));
    }
}
