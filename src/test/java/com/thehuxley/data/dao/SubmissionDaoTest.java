package com.thehuxley.data.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.thehuxley.data.dao.database.ProblemDao;
import com.thehuxley.data.dao.database.SubmissionDao;
import com.thehuxley.data.dao.database.mysql.ProblemDaoMySQL;
import com.thehuxley.data.dao.database.mysql.SubmissionDaoMySQL;
import com.thehuxley.data.model.database.OracleSubmissions;
import com.thehuxley.data.model.database.Problem;
import com.thehuxley.data.model.database.Submission;

public class SubmissionDaoTest {

	@Test
	public void testGetSubmissionById() {
		SubmissionDao dao = new SubmissionDaoMySQL();
		Submission s = null;
		long id = 1;
		while ((s = dao.getSubmissionById(id)) == null) {
			id++;
			assertTrue(id < 100);
		}

		assertEquals(id, s.getId());
		assertNotNull(s.getEvaluation());
		assertTrue(s.getTime() != 0);
		assertNotNull(s.getProblem());
		assertNotNull(s.getLanguage());

	}
	
	@Test
	public void testGetRetrieveManySubmissions(){
		SubmissionDao dao = new SubmissionDaoMySQL();
		Submission s = null;
		long id = 1;
		while (id < 5000) {
			s = dao.getSubmissionById(id);
			id++;			
		}
		
	}

    @Test
    public void testUpdateSubmission(){
        SubmissionDao dao = new SubmissionDaoMySQL();
        Submission s = null;
        long id = 1;
        while (id < 5000 && s==null) {
            s = dao.getSubmissionById(id);
            id++;
        }
        assertNotNull(s);
        String tempStr = ""+System.currentTimeMillis();
        s.setErrorMsg(tempStr);
        dao.updateSubmission(s);
        s = dao.getSubmissionById(s.getId());
        assertEquals(tempStr, s.getErrorMsg());

    }

    @Test
    public void testGetOracleSubmissions(){
        SubmissionDao dao = new SubmissionDaoMySQL();
        OracleSubmissions submissions = dao.getOracleSubmissionsByProblemId(-8);
        assertNull(submissions);


        long problemId = 3;
        // submissoes do problema 3
        long subs[] = {46,279};


        dao.deleteOracleSubmissionsByProblemId(problemId);


        OracleSubmissions complete = new OracleSubmissions();
        complete.setProblemId(problemId);
        for (int i = 0; i < subs.length; i++) {
            complete.addSubmission(dao.getSubmissionById(subs[i]));
        }

        long id = dao.insertOracleSubmissions(complete).getId();


        submissions = dao.getOracleSubmissionsByProblemId(3);
        assertEquals(subs.length, submissions.getSubmissions().size());

        assertEquals(problemId, submissions.getProblemId());

    }

    @Test
    public void testGetCorrectSubmissionsOfProblemCreator(){
        long problemId = 3;
        long submissionId = 29665;
        SubmissionDao dao = new SubmissionDaoMySQL();
        Submission submission = dao.getCorrectSubmissionsOfProblemCreator(problemId);
        assertEquals(submissionId,submission.getId());
        assertEquals(problemId,submission.getProblemId());
        assertEquals(Submission.CORRECT,submission.getEvaluation());

        ProblemDao problemDao = new ProblemDaoMySQL();
        Problem problem = problemDao.getProblemById(problemId);
        assertEquals(submission.getUserId(), problem.getUserSuggestId());
    }

    @Test
    public void testGetSubmissionsOfTopCoders(){
        long problemId = 3;
        SubmissionDao dao = new SubmissionDaoMySQL();
        List<Submission> submissions = dao.getSubmissionsOfTopCoders(problemId,4);
        assertEquals(4,submissions.size());

        for (int i = 0; i < submissions.size(); i++) {
            for (int j = i+1; j < submissions.size(); j++) {
                // eles não podem ser o mesmo usuário
                assertTrue(submissions.get(i).getUserId()!=submissions.get(j).getUserId());
            }
            // todas tem que ser corretas
            assertEquals(Submission.CORRECT, submissions.get(i).getEvaluation());
            // todas tem que apontar para o mesmo problema
            assertEquals(problemId, submissions.get(i).getProblemId());

        }

    }

    @Test
    public void testInsertOracleSubmissions(){
        long problemId = 4;
        // submissoes do problema 3
        long subs[] = {46,279,1550,1598,1620};


        SubmissionDao dao = new SubmissionDaoMySQL();
        dao.deleteOracleSubmissionsByProblemId(problemId);

        OracleSubmissions complete = new OracleSubmissions();
        complete.setProblemId(problemId);
        for (int i = 0; i < subs.length; i++) {
            complete.addSubmission(dao.getSubmissionById(subs[i]));
        }

        long id = dao.insertOracleSubmissions(complete).getId();

        assertTrue(id!=-1);
        OracleSubmissions fromDb = dao.getOracleSubmissionsByProblemId(problemId);
        assertEquals(subs.length,fromDb.getSubmissions().size());
        assertEquals(id,fromDb.getId());
        assertEquals(problemId,fromDb.getProblemId());
        assertNotNull(fromDb.getLastModified());

    }


}
