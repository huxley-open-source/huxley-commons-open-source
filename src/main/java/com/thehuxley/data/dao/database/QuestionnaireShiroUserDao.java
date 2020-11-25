package com.thehuxley.data.dao.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.data.Connector;
import com.thehuxley.data.DeadLockHandler;
import com.thehuxley.data.ResourcesUtil;
import com.thehuxley.data.model.database.QuestionnaireShiroUser;

public abstract class QuestionnaireShiroUserDao {

	static Logger logger = LoggerFactory
			.getLogger(QuestionnaireShiroUserDao.class);

	protected String SELECT_QUESTIONNAIRE;
	protected String UPDATE_QUESTIONNAIRE;
	protected String CREATE_QUESTIONNAIRE;
	protected String DELETE_QUESTIONNAIRE;
	protected String CORRECT_QUESTIONNAIRE;
	protected String UPDATE_SCORE;

	public QuestionnaireShiroUser getQuestionnaireShiroUserById(long id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		QuestionnaireShiroUser questionnaire = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo questionnaire " + id + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_QUESTIONNAIRE);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(SELECT_QUESTIONNAIRE);
			statement.setLong(1, id);
			rs = statement.executeQuery();

			if (rs.next()) {
				questionnaire = new QuestionnaireShiroUser();
				questionnaire.setId(rs.getLong("id"));
				questionnaire.setVersion(rs.getLong("version"));
				questionnaire
						.setQuestionnaireId(rs.getLong("questionnaire_id"));
				questionnaire.setUserId(rs.getLong("user_id"));
				questionnaire.setScore(rs.getDouble("score"));
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return questionnaire;
	}

	public void deleteQuestionnaireShiroUserById(long id) {
		PreparedStatement statement = null;
		Connection connection = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Deletando questionnaire " + id + "...");
		}

		try {
			if (logger.isDebugEnabled()) {
				logger.debug(DELETE_QUESTIONNAIRE);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(DELETE_QUESTIONNAIRE);
			statement.setLong(1, id);
			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}

	public void createQuestionnaireShiroUser(
			QuestionnaireShiroUser questionnaire) {
		PreparedStatement statement = null;
		Connection connection = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Criando questionnaire...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(CREATE_QUESTIONNAIRE);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(CREATE_QUESTIONNAIRE);

			statement.setLong(1, questionnaire.getQuestionnaireId());
			statement.setLong(2, questionnaire.getUserId());
			statement.setDouble(3, questionnaire.getScore());

			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}

	public void updateQuestionnaireShiroUser(
			QuestionnaireShiroUser questionnaire) {
		PreparedStatement statement = null;
		Connection connection = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando questionnaire " + questionnaire.getId()
					+ "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_QUESTIONNAIRE);
			}

			connection = Connector.getConnection();
			statement = connection.prepareStatement(UPDATE_QUESTIONNAIRE);

			statement.setLong(1, questionnaire.getQuestionnaireId());
			statement.setLong(2, questionnaire.getUserId());
			statement.setDouble(3, questionnaire.getScore());

			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}

	/**
	 * Essa função a partir de um id de uma submissão atualiza as notas de todos
	 * os questionários que dada submissão faz parte
	 * 
	 * @param submissionId
	 *            id da submissão
	 */
	public void correctQuestionnairesBySubmissionId(long submissionId) {
		PreparedStatement statement = null;
		Connection connection = null;
		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando questionnaire_shiro_user ...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(CORRECT_QUESTIONNAIRE);
			}

			connection = Connector.getConnection();
			statement = connection.prepareStatement(CORRECT_QUESTIONNAIRE);

			statement.setLong(1, submissionId);

			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}
	
	public void updateScore(long questId, Double score) {
		PreparedStatement statement = null;
		Connection connection = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando questionnaire " + questId + "... colocando o score " + score);
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_SCORE);
			}

			connection = Connector.getConnection();
			statement = connection.prepareStatement(UPDATE_SCORE);

			statement.setDouble(1, score);
			statement.setLong(2, questId);

			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}
}
