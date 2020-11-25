package com.thehuxley.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.WebTarget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.data.dao.rest.LanguageDaoRest;
import com.thehuxley.data.dao.rest.SubmissionDaoRest;
import com.thehuxley.data.dao.rest.TestCaseDaoRest;
import com.thehuxley.data.model.rest.Language;
import com.thehuxley.data.model.rest.Submission;
import com.thehuxley.data.model.rest.SubmissionResult;
import com.thehuxley.data.model.rest.TestCase;

public class RestManager {

    private static Logger logger = LoggerFactory
            .getLogger(RestManager.class);

    public static Submission getSubmissionById(Long id, String fileDir) {
        SubmissionDaoRest dao = new SubmissionDaoRest();
        try {
            return dao.getSubmissionById(id, fileDir);
        } catch (Exception e) {
            logger.warn("Erro ao tentar recuperar a submissão id=["+id+"], fileDir=["+fileDir+"]", e);
            return null;
        }
    }

    public static boolean updateSubmission(SubmissionResult submissionResult) {
        SubmissionDaoRest dao = new SubmissionDaoRest();
        return dao.updateSubmission(submissionResult);
    }

    public static List<TestCase> getTestCases(Long problemId, Map<String, String> params) {
        TestCaseDaoRest testCaseDaoRest = new TestCaseDaoRest();
        try {
            return testCaseDaoRest.getTestCases(problemId, params);
        } catch (Exception e) {
            logger.warn("Erro ao realizar uma requisicao ao servidor REST", e);
            return new ArrayList<TestCase>();
        }
    }

    public static Language getLanguageById(Long id) {
        LanguageDaoRest dao = new LanguageDaoRest();
        try {
            return dao.getLanguageById(id);
        } catch (Exception e) {
            logger.warn("Erro ao realizar uma requisicao ao servidor REST", e);
            return null;
        }
    }

    public static WebTarget addParams(WebTarget path, Map<String, String> params) {
        if (params==null) return path;
        for (String key : params.keySet()) {
            path = path.queryParam(key, params.get(key));
        }
        return path;
    }
}
