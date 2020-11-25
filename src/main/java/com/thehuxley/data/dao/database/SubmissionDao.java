package com.thehuxley.data.dao.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.data.Connector;
import com.thehuxley.data.DeadLockHandler;
import com.thehuxley.data.ResourcesUtil;
import com.thehuxley.data.dao.database.mysql.LanguageDaoMySQL;
import com.thehuxley.data.dao.database.mysql.ProblemDaoMySQL;
import com.thehuxley.data.model.database.OracleSubmissions;
import com.thehuxley.data.model.database.Submission;

public abstract class SubmissionDao {


	protected String CREATE_SUBMISSION;
	protected String DELETE_SUBMISSION;
	protected String SELECT_SUBMISSION;
	protected String UPDATE_SUBMISSION;
    protected String INSERT_SUBMISSION;
	protected String IS_PROBLEM_CORRECT;
    protected String SELECT_ORACLE_SUBMISSIONS;
    protected String SELECT_SUBMISSION_OF_CREATOR;
    protected String SELECT_SUBMISSION_OF_TOPCODERS;
    protected String UPDATE_ORACLE_SUBMISSIONS;
    protected String INSERT_ORACLE_SUBMISSIONS;
    protected String DELETE_ORACLE_SUBMISSIONS;

    static Logger logger = LoggerFactory.getLogger(SubmissionDao.class);

	private Submission fillSubmission(ResultSet rs) throws SQLException {
		Submission submission = new Submission();
		submission.setId(rs.getLong("s.id"));
		submission.setVersion(rs.getLong("s.version"));
		submission.setProblemId(rs.getLong("s.problem_id"));
		submission.setSubmission(rs.getString("s.submission"));
		submission.setEvaluation(rs.getByte("s.evaluation"));
		submission.setSubmissionDate(rs.getTimestamp("s.submission_date"));
		submission.setDetailedLog(rs.getBoolean("s.detailed_log"));
		submission.setDiffFile(rs.getString("s.diff_file"));
		submission.setInputTestCase(rs.getString("s.input_test_case"));
		submission.setLinguageId(rs.getLong("s.language_id"));
		submission.setTries(rs.getInt("s.tries"));
		submission.setOutput(rs.getString("s.output"));
		submission.setUserId(rs.getLong("s.user_id"));
		submission.setTime(rs.getDouble("s.time"));
        submission.setErrorMsg(rs.getString("s.error_msg"));
		return submission;
	}

	public Submission getSubmissionById(long id) {
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		Submission submission = null;
        if (id<=0){
            logger.warn("submission_id ["+id+"]<= 0");
            return null;
        }

		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo submission " + id + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_SUBMISSION);
			}

			connection = Connector.getConnection();
			statement = connection.prepareStatement(SELECT_SUBMISSION);
			statement.setLong(1, id);
			rs = statement.executeQuery();

			if (rs.next()) {
				submission = fillSubmission(rs);
				submission.setProblem(ProblemDao.fillProblem(rs));
				submission.setLanguage(LanguageDao.fillLanguage(rs));
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return submission;
	}


    /**
     * ATENĆÃO: ESSE MÉTODO NÃO ATUALIZA TODOS OS CAMPOS!!!!
     *
     * @param submission
     */
	public void updateSubmission(Submission submission) {
		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando submission " + submission.getId() + "...");
		}
        PreparedStatement statement = null;
        Connection connection = null;
        try {

            connection = Connector.getConnection();
            statement = connection.prepareStatement(UPDATE_SUBMISSION);

            statement.setByte(1, submission.getEvaluation());
            statement.setDouble(2, submission.getTime());
            statement.setString(3, submission.getInputTestCase());
            statement.setBoolean(4, submission.isDetailedLog());
            statement.setString(5, submission.getErrorMsg());
            statement.setLong(6, submission.getIdTestCase());
            /*
            É importante mudar o número da versão para evitar que o hibernate faca cache no projeto web
             */
            statement.setLong(7, submission.getVersion()+1);
            statement.setLong(8, submission.getId());

            DeadLockHandler.executeUpdate(statement);


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ResourcesUtil.release(null, statement, connection);
        }
	}

    public long insertSubmission(Submission submission) {
        if (logger.isDebugEnabled()) {
            logger.debug("Insert submission " + submission.getId() + "...");
        }

        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet rs = null;
        long id = -1;
        try {

            connection = Connector.getConnection();
            statement = connection.prepareStatement(INSERT_SUBMISSION,Statement.RETURN_GENERATED_KEYS);

            statement.setByte(1, submission.getEvaluation());
            statement.setDouble(2, submission.getTime());
            statement.setString(3, submission.getInputTestCase());
            statement.setBoolean(4, submission.isDetailedLog());
            statement.setString(5, submission.getErrorMsg());
            statement.setLong(6, submission.getIdTestCase());

            statement.setLong(7,submission.getProblemId());
            statement.setString(8, submission.getSubmission());
            statement.setDate(9, new Date(submission.getSubmissionDate().getTime()));
            statement.setString(10,submission.getDiffFile());
            statement.setLong(11,submission.getLanguage().getId());
            statement.setInt(12, submission.getTries());
            statement.setString(13, submission.getOutput());
            statement.setLong(14, submission.getUserId());

            DeadLockHandler.executeUpdate(statement);
            rs = statement.getGeneratedKeys();
            rs.next();
            id = rs.getLong(1);

            assert(id!=-1);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ResourcesUtil.release(rs, statement, connection);
        }
        return id;
    }

    /**
     *
     * @param problemId
     * @return uma lista de submissões que serão utilizadas pelo oráculo para dar a resposta para uma entrada do usuário.
     */
    public OracleSubmissions getOracleSubmissionsByProblemId(long problemId){
        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        OracleSubmissions oracleSubmissions = null;

        if (logger.isDebugEnabled()) {
            logger.debug("getOracleSubmissionsByProblemId. problemId:" + problemId + "...");
        }

        try {

            if (logger.isDebugEnabled()) {
                logger.debug(SELECT_ORACLE_SUBMISSIONS);
            }

            connection = Connector.getConnection();
            statement = connection.prepareStatement(SELECT_ORACLE_SUBMISSIONS);
            statement.setLong(1, problemId);
            rs = statement.executeQuery();

            if (rs.next()) {
                ProblemDao problemDao = new ProblemDaoMySQL();
                LanguageDao languageDao = new LanguageDaoMySQL();

                oracleSubmissions = new OracleSubmissions();
                String fields[] = {"sub1_id","sub2_id","sub3_id","sub4_id","sub5_id"};
                for (int i = 0; i < fields.length; i++) {
                    Submission submission =getSubmissionById(rs.getLong(fields[i]));
                    if (submission !=null){
                        submission.setProblem( problemDao.getProblemById(problemId));
                        submission.setLanguage( languageDao.getLanguageById( submission.getLanguageId()));
                        oracleSubmissions.addSubmission(submission);
                    }
                }

                oracleSubmissions.setId(rs.getLong("id"));
                oracleSubmissions.setLastModified(rs.getTimestamp("last_modified"));
                oracleSubmissions.setProblemId(rs.getLong("problem_id"));



            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ResourcesUtil.release(rs, statement, connection);
        }

        return oracleSubmissions;
    }


    /**
     * Retorna a última submissão correta do criador do problema
     *
     * @param problemId
     * @return
     */
    public Submission getCorrectSubmissionsOfProblemCreator(long problemId){
        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        Submission submission = null;
        if (problemId<=0){
            throw new IllegalArgumentException("problemId ["+problemId+"] <=0");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("getCorrectSubmissionsOfProblemCreator problemId ["+problemId+"]");
        }

        try {

            if (logger.isDebugEnabled()) {
                logger.debug(SELECT_SUBMISSION_OF_CREATOR);
            }

            connection = Connector.getConnection();
            statement = connection.prepareStatement(SELECT_SUBMISSION_OF_CREATOR);
            statement.setLong(1, problemId);
            statement.setLong(2, problemId);
            rs = statement.executeQuery();


            if (rs.next()) {
                ProblemDao problemDao = new ProblemDaoMySQL();
                LanguageDao languageDao = new LanguageDaoMySQL();

                submission = fillSubmission(rs);
                submission.setProblem( problemDao.getProblemById(problemId));
                submission.setLanguage( languageDao.getLanguageById( submission.getLanguageId()));
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ResourcesUtil.release(rs, statement, connection);
        }

        return submission;
    }

    public List<Submission> getSubmissionsOfTopCoders(long problemId, int limit){
        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        List<Submission> submissions = new ArrayList<Submission>();
        if (problemId<=0 || limit <=0 ){
            throw new IllegalArgumentException("problemId ["+problemId+"] <=0 || limit ["+limit+"] <=0");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("getSubmissionsOfTopCoders problemId ["+problemId+"] limit ["+limit+"]");
        }

        try {

            if (logger.isDebugEnabled()) {
                logger.debug(SELECT_SUBMISSION_OF_TOPCODERS);
            }

            connection = Connector.getConnection();
            statement = connection.prepareStatement(SELECT_SUBMISSION_OF_TOPCODERS);
            statement.setLong(1, problemId);
            statement.setLong(2, limit);
            rs = statement.executeQuery();

            ProblemDao problemDao = new ProblemDaoMySQL();
            LanguageDao languageDao = new LanguageDaoMySQL();
            while (rs.next()) {
                Submission submission = fillSubmission(rs);
                submission.setProblem( problemDao.getProblemById(problemId));
                submission.setLanguage( languageDao.getLanguageById( submission.getLanguageId()));
                submissions.add(submission);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ResourcesUtil.release(rs, statement, connection);
        }

        return submissions;
    }

    public void updateOracleSubmissions(OracleSubmissions oracleSubmissions) {
        if (logger.isDebugEnabled()) {
            logger.debug("updateOracleSubmissions");
        }
        if (oracleSubmissions.getId()==0){
            throw new IllegalArgumentException("I can't update an oraclesubmission with id==0. You should insert it first");
        }
        PreparedStatement statement = null;
        Connection connection = null;
        try {

            connection = Connector.getConnection();
            statement = connection.prepareStatement(UPDATE_ORACLE_SUBMISSIONS);

            statement.setLong(1, oracleSubmissions.getProblemId());

            for (int i = 0; i < OracleSubmissions.NUMBER_OF_SUBMISSIONS ; i++) {
                if (i < oracleSubmissions.getSubmissions().size()){
                    statement.setLong(i+2,oracleSubmissions.getSubmissions().get(i).getId());
                }else{
                    statement.setNull(i+2, Types.BIGINT);
                }
            }

            statement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            statement.setLong(8, oracleSubmissions.getId());

            DeadLockHandler.executeUpdate(statement);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ResourcesUtil.release(null, statement, connection);
        }
    }



    public OracleSubmissions insertOracleSubmissions(OracleSubmissions oracleSubmissions) {
        if (logger.isDebugEnabled()) {
            logger.debug("insertOracleSubmissions");
        }
        if (oracleSubmissions.getId()!=0){
            throw new IllegalArgumentException("I can't insert an oraclesubmission with id!=0.");
        }
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet rs = null;
        try {

            connection = Connector.getConnection();
            statement = connection.prepareStatement(INSERT_ORACLE_SUBMISSIONS, Statement.RETURN_GENERATED_KEYS);

            statement.setLong(1, oracleSubmissions.getProblemId());
            int statementIndex = 2;
            for (Submission s : oracleSubmissions.getSubmissions()){
                statement.setLong(statementIndex, s.getId());
                statementIndex++;
            }
            for (int i = statementIndex; i < 7; i++) {
                statement.setNull(i, Types.BIGINT);
            }

            statement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

            DeadLockHandler.executeUpdate(statement);
            rs = statement.getGeneratedKeys();
            rs.next();
            oracleSubmissions.setId(rs.getLong(1));

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ResourcesUtil.release(rs, statement, connection);
        }
        return oracleSubmissions;
    }

    public void deleteOracleSubmissionsByProblemId(long problemId) {
        if (logger.isDebugEnabled()) {
            logger.debug("deleteOracleSubmissionsByProblemId");
        }
        PreparedStatement statement = null;
        Connection connection = null;
        try {

            connection = Connector.getConnection();
            statement = connection.prepareStatement(DELETE_ORACLE_SUBMISSIONS);

            statement.setLong(1, problemId);
            DeadLockHandler.executeUpdate(statement);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ResourcesUtil.release(null, statement, connection);
        }
    }



}
