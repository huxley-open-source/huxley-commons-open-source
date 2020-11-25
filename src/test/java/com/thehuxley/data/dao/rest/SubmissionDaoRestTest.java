package com.thehuxley.data.dao.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.thehuxley.data.model.rest.Submission;
import com.thehuxley.data.model.rest.SubmissionResult;

public class SubmissionDaoRestTest {

    @Test
    public void testGetSubmissionById() throws Exception{
        long subId = 201;
        SubmissionDaoRest dao = new SubmissionDaoRest();
        Submission submission = dao.getSubmissionById(subId, "/home/huxley/data/temp/");
        // Verifica se a submissao é realmente a 23
        assertEquals( subId, (long) submission.getId() );
        
        // Verifica se está conseguindo fazer o unmarshal das entidades do json corretamente
        assertEquals (28, (long) submission.getProblem().getId());
        assertEquals(50, (long) submission.getProblem().getSuggestedBy().getId() );
        
        // Verifica se o arquivo está present
        assertNotNull( submission.getSubmissionFile() );
        
        // Verifica se de fato tem alguma coisa dentro dele
        assertTrue ( submission.getSubmissionFile().length() > 0);
    }
    
    @Test
    public void testUpdateSubmission() throws IOException {
        long subId = 351;
        SubmissionDaoRest dao = new SubmissionDaoRest();
        
        Submission submission = dao.getSubmissionById(subId, "/home/huxley/data/temp/");
        String oldEvaluation = submission.getEvaluation();
        

        Submission.Evaluation newEvaluation;
        if (oldEvaluation.equals(Submission.Evaluation.CORRECT.toString())) {
            newEvaluation = Submission.Evaluation.WRONG_ANSWER;
        } else {
            newEvaluation = Submission.Evaluation.CORRECT;
        }

        SubmissionResult update = new SubmissionResult(submission);
        update.setEvaluation(newEvaluation.toString());
        
        dao.updateSubmission(update);

        submission = dao.getSubmissionById(subId, "/home/huxley/data/temp/");
        
        assertEquals(newEvaluation.toString(), submission.getEvaluation());
        
        
        
    }
}
