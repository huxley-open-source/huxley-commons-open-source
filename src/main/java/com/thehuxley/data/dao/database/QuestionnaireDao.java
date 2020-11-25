package com.thehuxley.data.dao.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.data.Connector;
import com.thehuxley.data.DeadLockHandler;
import com.thehuxley.data.ResourcesUtil;
import com.thehuxley.data.model.database.Questionnaire;
import com.thehuxley.data.model.database.QuestionnaireStatistics;

public abstract class QuestionnaireDao {

	private static Logger logger = LoggerFactory.getLogger(QuestionnaireDao.class);

	protected String SELECT_QUESTIONNAIRE;
	protected String SELECT_QUESTIONNAIRES;

	protected String UPDATE_QUESTIONNAIRE;
	protected String CREATE_QUESTIONNAIRE;
	protected String DELETE_QUESTIONNAIRE;

	protected String LIST_QUESTIONNAIRE_BY_SUBMISSION;
	protected String UPDATE_QUEST_SCORE;
	protected String UPDATE_QUEST_SCORE_WITH_EMPTY_PROBLEM_LIST;
	protected String SELECT_QUEST_PROBLEMS;

	protected String USERS_IN_GROUP;
	protected String SELECT_GROUP_BY_QUESTIONNAIRE;
	protected String USERS_TRYED;
	protected String GREATER_SEVEN;
	protected String SCORE_AVERAGE;
	protected String LIST_SCORE_QUEST;
	protected String FIND_QUEST_STATISTICS;
	protected String UPDATE_STATISTICS;
	protected String INSERT_STATISTICS;

	protected String GET_QUEST_USER;
	protected String GET_SCORE_BY_QUEST_USER;

	public Questionnaire getQuestionnaireById(long id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		Questionnaire questionnaire = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving questionnaire " + id + "...");
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
				questionnaire = fillQuestionnaire(rs);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return questionnaire;
	}

	public List<Questionnaire> getQuestionnairesById(List<Long> ids) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		List<Questionnaire> questionnaires = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving questionnaires " + ids + "...");
		}

		try {
			String sql = SELECT_QUESTIONNAIRES;

			sql = replaceInClause(sql, "[#questInClause]", ids.size());

			if (logger.isDebugEnabled()) {
				logger.debug(sql);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(sql);

			for (int i = 0; i < ids.size(); i++) {
				statement.setLong(i + 1, ids.get(i));
			}
			rs = statement.executeQuery();

			questionnaires = new ArrayList<Questionnaire>(ids.size());
			while (rs.next()) {
				questionnaires.add(fillQuestionnaire(rs));
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return questionnaires;
	}

	public Long getUserCountByGroup(long id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;

		long count = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo user count do grupo " + id + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(USERS_IN_GROUP);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(USERS_IN_GROUP);
			statement.setLong(1, id);
			rs = statement.executeQuery();

			if (rs.next()) {
				count = rs.getLong("user_count");
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}

		return count;
	}

	public Long getUserTryes(long questId, long groupId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		long count = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo user tries do grupo " + groupId + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(USERS_TRYED);
			}

			connection = Connector.getConnection();
			statement = connection.prepareStatement(USERS_TRYED);
			statement.setLong(1, questId);
			statement.setLong(2, groupId);
			rs = statement.executeQuery();

			if (rs.next()) {
				count = rs.getLong("try_count");
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}

		return count;
	}

	private Questionnaire fillQuestionnaire(ResultSet rs) throws SQLException {
		Questionnaire questionnaire;
		questionnaire = new Questionnaire();
		questionnaire.setId(rs.getLong("id"));
		questionnaire.setVersion(rs.getLong("version"));
		questionnaire.setStartDate(rs.getDate("start_date"));
		questionnaire.setEvaluationDetail(rs.getInt("evaluation_detail"));
		questionnaire.setScore(rs.getDouble("score"));
		questionnaire.setEndDate(rs.getDate("end_date"));
		questionnaire.setTitle(rs.getString("title"));
		questionnaire.setDescription(rs.getString("description"));
		return questionnaire;
	}

	public void deleteQuestionnaireById(long id) {
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

	public void createQuestionnaire(Questionnaire questionnaire) {
		PreparedStatement statement = null;
		Connection connection = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Criando questionnaire...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_QUESTIONNAIRE);
			}

			connection = Connector.getConnection();

			statement = connection.prepareStatement(CREATE_QUESTIONNAIRE);

			statement.setDate(1, new Date(questionnaire.getStartDate()
					.getTime()));
			statement.setInt(2, questionnaire.getEvaluationDetail());
			statement.setDouble(3, questionnaire.getScore());
			statement
					.setDate(4, new Date(questionnaire.getEndDate().getTime()));
			statement.setString(5, questionnaire.getTitle());
			statement.setString(6, questionnaire.getDescription());

			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}

	public void updateQuestionnaire(Questionnaire questionnaire) {
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

			statement.setDate(1, new Date(questionnaire.getStartDate()
					.getTime()));
			statement.setInt(2, questionnaire.getEvaluationDetail());
			statement.setDouble(3, questionnaire.getScore());
			statement
					.setDate(4, new Date(questionnaire.getEndDate().getTime()));
			statement.setString(5, questionnaire.getTitle());
			statement.setString(6, questionnaire.getDescription());
			statement.setLong(7, questionnaire.getId());

			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}

	/**
	 * Retorna os ids de todos os questionários do usuário que fez a submissão e
	 * que estão abertos
	 */
	public ArrayList<Long> getQuestBySub(long id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		ArrayList<Long> questList = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving questionnaires from submission:" + id
					+ "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(LIST_QUESTIONNAIRE_BY_SUBMISSION);
			}

			connection = Connector.getConnection();
			statement = connection
					.prepareStatement(LIST_QUESTIONNAIRE_BY_SUBMISSION);
			statement.setLong(1, id);
			rs = statement.executeQuery();

			questList = new ArrayList<Long>();
			while (rs.next()) {
				questList.add(rs.getLong("questionnaire"));
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return questList;
	}

	public void updateScore(String problems, long submissionId, long questId) {
		PreparedStatement statement = null;
		Connection connection = null;
		if (logger.isDebugEnabled()) {
			logger.debug("Updating the score of an user in a questionnaire: "
					+ questId);
		}

		try {

			// processar a clausula In
			String sql = UPDATE_QUEST_SCORE;
			String replacementKey = "[#problemInClause]";
			List<Integer> problemList = ResourcesUtil
					.extractIntListFromString(problems);

			int size = problemList.size();
			sql = replaceInClause(sql, replacementKey, size);

			if (logger.isDebugEnabled()) {
				logger.debug(sql);
			}

			connection = Connector.getConnection();
			statement = connection.prepareStatement(sql);

			int i;
			for (i = 0; i < size; i++) {
				statement.setLong(i + 1, problemList.get(i));
			}
			statement.setLong(++i, submissionId);
			statement.setLong(++i, questId);
			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			
				
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}

	private String replaceInClause(String originalSQL, String replacementKey,
			int size) {
		StringBuilder inClause = new StringBuilder();
		for (int i = 0; i < size; i++) {
			inClause.append('?');
			if (i < (size - 1)) {
				inClause.append(',');
			}
		}
		originalSQL = StringUtils.replace(originalSQL, replacementKey,
				inClause.toString());
		return originalSQL;
	}

	public void updateScoreWithEmptyProbList(long submissionId, long questId) {
		PreparedStatement statement = null;
		Connection connection = null;
		if (logger.isDebugEnabled()) {
			logger.debug("Updating the score of an user in a questionnaire: "
					+ questId);
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_QUEST_SCORE_WITH_EMPTY_PROBLEM_LIST);
			}

			connection = Connector.getConnection();
			statement = connection
					.prepareStatement(UPDATE_QUEST_SCORE_WITH_EMPTY_PROBLEM_LIST);

			statement.setLong(1, submissionId);
			statement.setLong(2, questId);
			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}

	public Long getGreaterSeven(long questId, long groupId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		long count = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo notas maiores que 7 do grupo " + groupId
					+ "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(GREATER_SEVEN);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(GREATER_SEVEN);
			statement.setLong(1, groupId);
			statement.setLong(2, questId);
			rs = statement.executeQuery();

			if (rs.next()) {
				count = rs.getLong("greater_seven");
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return count;
	}

	public ArrayList<Long> getGroupByQuest(long id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Long> questList = null;
		Connection connection = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo grupos do questionario: " + id + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_GROUP_BY_QUESTIONNAIRE);
			}

			connection = Connector.getConnection();
			statement = connection
					.prepareStatement(SELECT_GROUP_BY_QUESTIONNAIRE);
			statement.setLong(1, id);
			rs = statement.executeQuery();

			while (rs.next()) {
				if (questList == null) {
					questList = new ArrayList<Long>();
				}
				questList.add(rs.getLong("group_id"));
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return questList;
	}

	public ArrayList<Long> getProblemCorrect(long questId, long submissionId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		ArrayList<Long> questList = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo problemas corretos do questionario a partir da submissão: "
					+ submissionId + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_QUEST_PROBLEMS);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(SELECT_QUEST_PROBLEMS);
			statement.setLong(1, questId);
			statement.setLong(2, submissionId);
			rs = statement.executeQuery();

			while (rs.next()) {
				if (questList == null) {
					questList = new ArrayList<Long>();
				}
				questList.add(rs.getLong("correct_problem_id"));
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return questList;
	}

	public Double getScoreSum(long questId, long groupId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		double count = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo a soma das notas do grupo " + groupId + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(SCORE_AVERAGE);
			}

			connection = Connector.getConnection();
			statement = connection.prepareStatement(SCORE_AVERAGE);
			statement.setLong(1, groupId);
			statement.setLong(2, questId);
			rs = statement.executeQuery();

			if (rs.next()) {
				count = rs.getDouble("total_score");
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return count;
	}

	public ArrayList<Double> getGroupScore(long questId, long groupId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Double> questList = null;
		Connection connection = null;
		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo notas do questionario: " + questId + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(LIST_SCORE_QUEST);
			}

			connection = Connector.getConnection();
			statement = connection.prepareStatement(LIST_SCORE_QUEST);
			statement.setLong(1, groupId);
			statement.setLong(2, questId);
			rs = statement.executeQuery();
			while (rs.next()) {
				if (questList == null) {
					questList = new ArrayList<Double>();
				}
				questList.add(rs.getDouble("score"));
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return questList;
	}

	public Long getStatisticsId(long questId, long groupId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		long count = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo statistics do grupo [" + groupId
					+ "] em um questionário ...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(FIND_QUEST_STATISTICS);
			}

			connection = Connector.getConnection();
			statement = connection.prepareStatement(FIND_QUEST_STATISTICS);
			statement.setLong(1, groupId);
			statement.setLong(2, questId);
			rs = statement.executeQuery();

			if (rs.next()) {
				count = rs.getLong("statistics_id");
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return count;
	}

	public void updateStatistics(QuestionnaireStatistics statistics) {
		PreparedStatement statement = null;
		Connection connection = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando staatistics " + statistics.getId()
					+ "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_STATISTICS);
			}

			connection = Connector.getConnection();
			statement = connection.prepareStatement(UPDATE_STATISTICS);

			statement.setDouble(1, statistics.getAverageNote());
			statement.setDouble(2, statistics.getGreaterThenEqualsSeven());
			statement.setLong(3, statistics.getGroupId());
			statement.setDouble(4, statistics.getLessSeven());
			statement.setLong(5, statistics.getQuestionnaireId());
			statement.setDouble(6, statistics.getStandartDeviaton());
			statement.setDouble(7, statistics.getTryPercentage());
			statement.setLong(8, statistics.getId());

			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}

	public void saveStatistics(QuestionnaireStatistics statistics) {
		PreparedStatement statement = null;
		Connection connection = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Criando statisticas para o questionnaire "
					+ statistics.getId() + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(INSERT_STATISTICS);
			}

			connection = Connector.getConnection();
			statement = connection.prepareStatement(INSERT_STATISTICS);

			statement.setDouble(1, statistics.getAverageNote());
			statement.setDouble(2, statistics.getGreaterThenEqualsSeven());
			statement.setLong(3, statistics.getGroupId());
			statement.setDouble(4, statistics.getLessSeven());
			statement.setLong(5, statistics.getQuestionnaireId());
			statement.setDouble(6, statistics.getStandartDeviaton());
			statement.setDouble(7, statistics.getTryPercentage());
			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}

	public ArrayList<Long> getQuestUserList(long id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		ArrayList<Long> questList = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Questionnarios de usuario do questionario: " + id
					+ "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(GET_QUEST_USER);
			}

			connection = Connector.getConnection();
			statement = connection.prepareStatement(GET_QUEST_USER);
			statement.setLong(1, id);
			rs = statement.executeQuery();

			while (rs.next()) {
				if (questList == null) {
					questList = new ArrayList<Long>();
				}
				questList.add(rs.getLong("quest_user_id"));
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}
		return questList;
	}

	public double getScore(long questUserId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;

		double score = 0;

		if (logger.isDebugEnabled()) {
			logger.debug("Problemas corretos para questionario usuario: "
					+ questUserId + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(GET_SCORE_BY_QUEST_USER);
			}

			connection = Connector.getConnection();
			statement = connection.prepareStatement(GET_SCORE_BY_QUEST_USER);
			statement.setLong(1, questUserId);
			rs = statement.executeQuery();

			while (rs.next()) {
				score += (rs.getDouble("score") * 1000);
			}
			score = (score/1000);
			if (logger.isDebugEnabled()) {
				logger.debug("Score= " + score + "...");
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}
		return score;
	}
}
