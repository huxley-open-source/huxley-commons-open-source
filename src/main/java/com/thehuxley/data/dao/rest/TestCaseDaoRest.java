package com.thehuxley.data.dao.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thehuxley.data.Configurator;
import com.thehuxley.data.Constants;
import com.thehuxley.data.model.rest.TestCase;

public class TestCaseDaoRest {

    private WebTarget server;
    private String authorization;

    public TestCaseDaoRest() {
        Client client = ClientBuilder.newClient();
        server = client.target(Configurator.getProperty(Constants.CONF_FILENAME, "huxleyRestServer"));

        authorization = Configurator.getProperty(Constants.CONF_FILENAME, "authentication.method") + " " +
                Configurator.getProperty(Constants.CONF_FILENAME, "authentication.key");
    }

    public List<TestCase> getTestCases(Long problemId, Map<String, String> params) {

        WebTarget path = server
                .path("problems")
                .path(Long.toString(problemId))
                .path("testcases-list");


        if (params != null) {
            for (String key : params.keySet()) {
                path = path.queryParam(key, params.get(key));
            }
        }
        
        Response response = path
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", authorization)
                .get();

        List<TestCase> testCases = response.readEntity(new GenericType<List<TestCase>>() {});
        
        for (TestCase testCase : testCases) {
	        WebTarget input = server
	                .path("problems")
	                .path(Long.toString(problemId))
	                .path("testcases")
	                .path(Long.toString(testCase.getId()))
	                .path("input");
	        
	        response = input
	                .request(MediaType.TEXT_PLAIN)
	                .header("Authorization", authorization)
	                .get();
	        
	        testCase.setInput(response.readEntity(String.class));
	        
	        WebTarget output = server
	                .path("problems")
	                .path(Long.toString(problemId))
	                .path("testcases")
	                .path(Long.toString(testCase.getId()))
	                .path("output");
	        
	        response = output
	                .request(MediaType.TEXT_PLAIN)
	                .header("Authorization", authorization)
	                .get();
	        
	        testCase.setOutput(response.readEntity(String.class));
        }

        return testCases;
    }

}
