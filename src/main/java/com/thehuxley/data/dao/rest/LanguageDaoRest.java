package com.thehuxley.data.dao.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.thehuxley.data.Configurator;
import com.thehuxley.data.Constants;
import com.thehuxley.data.model.rest.Language;

public class LanguageDaoRest {

    private WebTarget server;
    private String authorization;

    public LanguageDaoRest() {
        Client client = ClientBuilder.newClient();
        server = client.target(Configurator.getProperty(Constants.CONF_FILENAME, "huxleyRestServer"));

        authorization = Configurator.getProperty(Constants.CONF_FILENAME, "authentication.method") + " " +
                Configurator.getProperty(Constants.CONF_FILENAME, "authentication.key");

    }

    public Language getLanguageById(Long id) {
        return server
                .path("languages")
                .path(Long.toString(id))
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", authorization)
                .get(Language.class);
    }
}
