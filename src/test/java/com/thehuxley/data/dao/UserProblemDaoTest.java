package com.thehuxley.data.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.data.dao.database.ProblemDao;
import com.thehuxley.data.dao.database.UserDao;
import com.thehuxley.data.dao.database.UserProblemDao;
import com.thehuxley.data.dao.database.mysql.ProblemDaoMySQL;
import com.thehuxley.data.dao.database.mysql.UserDaoMySQL;
import com.thehuxley.data.dao.database.mysql.UserProblemDaoMySQL;
import com.thehuxley.data.model.database.Problem;
import com.thehuxley.data.model.database.Submission;
import com.thehuxley.data.model.database.User;
import com.thehuxley.data.model.database.UserProblem;

public class UserProblemDaoTest {

    static Logger logger = LoggerFactory.getLogger(UserProblemDaoTest.class);

    private final int MAX_USERS = 100;
    private final int MAX_PROBLEMS = 200;
    private UserProblemDao userProblemDao;
    private UserDao userDao ;
    private ProblemDao problemDao ;
    private User user ;
    private Problem problem ;


    @Before
    public void setUp(){
        userProblemDao = new UserProblemDaoMySQL();
        userDao = new UserDaoMySQL();
        problemDao = new ProblemDaoMySQL();
        user = null;
        problem = null;
    }

    /**
     Neste cenário, iremos recuperar um usuário que ainda
     não acertou um determinado problema e submeter uma submissão correta
     Ele deve atualizar a tabela de user_problem
     */
    @Test
    public void testUserProblemNullToCorrectSubmission(){
        findNull();
        Submission submission = new Submission();
        submission.setUserId(user.getId());
        submission.setProblem(problem);
        submission.setEvaluationAsCorrect();

        userProblemDao.updateUserProblem(submission);

        UserProblem fromDb = userProblemDao.getByUserAndProblemId(user.getId(),problem.getId());
        assertNotNull(fromDb);
        assertEquals(UserProblem.STATUS_CORRECT,fromDb.getStatus());
    }

    private void findNull() {
        boolean found = false;
        UserProblem userProblem = null;
        for (int userId = 0; userId < MAX_USERS && !found; userId++) {
            user = userDao.getUserById(userId);
            if (user!=null){
                for (int problemId = 0; problemId < MAX_PROBLEMS && !found; problemId++) {
                    problem = problemDao.getProblemById(problemId);
                    if (problem!=null){
                        userProblem = userProblemDao.getByUserAndProblemId(user.getId(), problem.getId());
                        if (userProblem==null){
                            /* Encontrou um usuário que ainda não submeteu para aquele problema */
                            found=true;
                        }
                    }
                }
            }
        }
        assertNull(userProblem);
    }

    private void findNotNull(int userProblemStatus) {
        boolean found = false;
        UserProblem userProblem = null;
        for (int userId = 0; userId < MAX_USERS && !found; userId++) {
            user = userDao.getUserById(userId);
            if (user!=null){
                for (int problemId = 0; problemId < MAX_PROBLEMS && !found; problemId++) {
                    problem = problemDao.getProblemById(problemId);
                    if (problem!=null){
                        userProblem = userProblemDao.getByUserAndProblemId(user.getId(), problem.getId());
                        if (userProblem!=null && userProblem.getStatus()==userProblemStatus){
                            /* Encontrou um usuário que ainda não submeteu para aquele problema */
                            found=true;
                        }
                    }
                }
            }
        }
        assertNotNull(userProblem);
    }

    @Test
    public void testUserProblemNullToWrongSubmission(){
        findNull();
        Submission submission = new Submission();
        submission.setUserId(user.getId());
        submission.setProblem(problem);
        submission.setEvaluationAsWrongAnswer();

        userProblemDao.updateUserProblem(submission);

        UserProblem fromDb = userProblemDao.getByUserAndProblemId(user.getId(),problem.getId());
        assertNotNull(fromDb);
        assertEquals(UserProblem.STATUS_TRIED,fromDb.getStatus());
    }

    /**
     * Testa um caso onde o usuário já fez uma submissão anterior e está errada.
     * A submissão atual está correta
     */
    @Test
    public void testUserProblemWrongToCorrectSubmission(){
        findNotNull(UserProblem.STATUS_TRIED);

        Submission submission = new Submission();
        submission.setUserId(user.getId());
        submission.setProblem(problem);
        submission.setEvaluationAsCorrect();

        userProblemDao.updateUserProblem(submission);

        UserProblem fromDb = userProblemDao.getByUserAndProblemId(user.getId(),problem.getId());
        assertNotNull(fromDb);
        assertEquals(UserProblem.STATUS_CORRECT,fromDb.getStatus());

    }

    @Test
    public void testUserProblemWrongToWrongSubmission(){
        findNotNull(UserProblem.STATUS_TRIED);

        Submission submission = new Submission();
        submission.setUserId(user.getId());
        submission.setProblem(problem);
        submission.setEvaluationAsCompilationError();

        userProblemDao.updateUserProblem(submission);

        UserProblem fromDb = userProblemDao.getByUserAndProblemId(user.getId(),problem.getId());
        assertNotNull(fromDb);
        assertEquals(UserProblem.STATUS_TRIED,fromDb.getStatus());
    }

    @Test
    public void testUserProblemCorrectToWrongSubmission(){
        findNotNull(UserProblem.STATUS_CORRECT);

        Submission submission = new Submission();
        submission.setUserId(user.getId());
        submission.setProblem(problem);
        submission.setEvaluationAsRuntimeError();

        userProblemDao.updateUserProblem(submission);

        UserProblem fromDb = userProblemDao.getByUserAndProblemId(user.getId(),problem.getId());
        assertNotNull(fromDb);
        assertEquals(UserProblem.STATUS_CORRECT,fromDb.getStatus());
    }

    @Test
    public void testUserProblemCorrectToCorrectSubmission(){
        findNotNull(UserProblem.STATUS_CORRECT);

        Submission submission = new Submission();
        submission.setUserId(user.getId());
        submission.setProblem(problem);
        submission.setEvaluationAsCorrect();

        userProblemDao.updateUserProblem(submission);

        UserProblem fromDb = userProblemDao.getByUserAndProblemId(user.getId(),problem.getId());
        assertNotNull(fromDb);
        assertEquals(UserProblem.STATUS_CORRECT,fromDb.getStatus());

    }
}
