package com.thehuxley.data.dao.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thehuxley.data.Configurator;
import com.thehuxley.data.Constants;
import com.thehuxley.data.model.rest.Submission;
import com.thehuxley.data.model.rest.SubmissionResult;

public class SubmissionDaoRest {
    
    private WebTarget server;
    private String authorization;
    
    public SubmissionDaoRest() {
        Client client = ClientBuilder.newClient();
        server = client.target(Configurator.getProperty(Constants.CONF_FILENAME, "huxleyRestServer"));

        authorization = Configurator.getProperty(Constants.CONF_FILENAME, "authentication.method") + " " +
                Configurator.getProperty(Constants.CONF_FILENAME, "authentication.key");
    }


    /**
     * * 
     * @param id
     * @param fileDir Esse parâmetro deve conter a / no final da string
     * @return
     * @throws IOException
     */
    public Submission getSubmissionById(Long id, String fileDir) throws IOException {
        if (fileDir==null) {
            throw new IOException("O nome do diretório onde a submissão será salva não pode ser nulo");
        }

        Submission submission = server
                .path("submissions")
                .path(Long.toString(id))
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", authorization)
                .get(Submission.class);

        File file = server
                .path("submissions")
                .path(Long.toString(id))
                .path("sourcecode")
                .request(MediaType.TEXT_PLAIN)
                .header("Authorization", authorization)
                .get(File.class);



        File distDir = new File(fileDir);

        if(distDir.exists() || distDir.mkdirs()) {
            submission.setSubmissionFile(
                    Files.move(file.toPath(), Paths.get(distDir.toString(), submission.getFilename()),
                            StandardCopyOption.REPLACE_EXISTING).toFile()
            );
        }

        return submission;
    }

    /**
     * Se o servidor estiver off-line ele retorna uma exceção do tipo javax.ws.rs.NotFoundException.
     * Essa exceção herda de RuntimeException e, portanto, não precisa ser explicitamente tratada.
     *
     * @param submissionResult
     * @return
     */
    public boolean updateSubmission(SubmissionResult submissionResult) {
        Response response = server
                .path("submissions")
                .path(Long.toString(submissionResult.getId()))
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", authorization)
                .put(Entity.entity(submissionResult, MediaType.APPLICATION_JSON));
        return response.getStatus() == 200;
        
        
    }
}
