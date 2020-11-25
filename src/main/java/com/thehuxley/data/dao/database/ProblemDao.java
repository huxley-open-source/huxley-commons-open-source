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
import com.thehuxley.data.model.database.Problem;
import com.thehuxley.data.model.database.Submission;

public abstract class ProblemDao {

	static Logger logger = LoggerFactory.getLogger(ProblemDao.class);

	protected String SELECT_PROBLEM;
	protected String UPDATE_PROBLEM;
	protected String CREATE_PROBLEM;
	protected String DELETE_PROBLEM;
	protected String FASTEST_UPDATE;

	public Problem getProblemById(long id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		Problem problem = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving problem " + id + ".");
		}
		
		try {

			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_PROBLEM);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(SELECT_PROBLEM);
			statement.setLong(1, id);
			rs = statement.executeQuery();

			if (rs.next()) {
				problem = fillProblem(rs);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return problem;
	}

	public static Problem fillProblem(ResultSet rs) throws SQLException {
		Problem problem;
		problem = new Problem();
		problem.setId(rs.getLong("p.id"));
		problem.setVersion(rs.getLong("p.version"));
		problem.setTimeLimit(rs.getInt("p.time_limit"));
		problem.setEvaluationDetail(rs.getInt("p.evaluation_detail"));
		problem.setCode(rs.getInt("p.code"));
		problem.setLevel(rs.getInt("p.level"));
		problem.setNd(rs.getDouble("p.nd"));
		problem.setName(rs.getString("p.name"));
		problem.setStatus(rs.getString("p.status"));
		problem.setUserApprovedId(rs.getLong("p.user_approved_id"));
		problem.setUserSuggestId(rs.getLong("p.user_suggest_id"));
		problem.setFastestSubmissionTime(rs.getDouble("time"));
		return problem;
	}

	public void deleteProblemById(long id) {
		PreparedStatement statement = null;
		Connection connection = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Deleting the problem: " + id + ".");
		}

		try {
			if (logger.isDebugEnabled()) {
				logger.debug(DELETE_PROBLEM);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(DELETE_PROBLEM);
			statement.setLong(1, id);
			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}

	public void updateFastestSub(Submission submission) {
		PreparedStatement statement = null;
		Connection connection = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando submissão mais rapida");
		}

		try {
			if (logger.isDebugEnabled()) {
				logger.debug(FASTEST_UPDATE);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(FASTEST_UPDATE);
			statement.setLong(1, submission.getProblemId());

			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}

	public void createProblem(Problem problem) {
		PreparedStatement statement = null;
		Connection connection = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Criando problem...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_PROBLEM);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(CREATE_PROBLEM);

			statement.setInt(1, problem.getTimeLimit());
			statement.setInt(2, problem.getEvaluationDetail());
			statement.setInt(3, problem.getCode());
			statement.setInt(5, problem.getLevel());
			statement.setDouble(6, problem.getNd());
			statement.setString(8, problem.getName());
			statement.setString(11, problem.getStatus());
			statement.setLong(12, problem.getUserApprovedId());
			statement.setLong(13, problem.getUserSuggestId());

			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}

	public void updateProblem(Problem problem) {
		PreparedStatement statement = null;
		Connection connection = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando problem " + problem.getId() + "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_PROBLEM);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(UPDATE_PROBLEM);

			statement.setInt(1, problem.getTimeLimit());
			statement.setInt(2, problem.getEvaluationDetail());
			statement.setInt(3, problem.getCode());
			statement.setInt(5, problem.getLevel());
			statement.setDouble(6, problem.getNd());
			statement.setString(8, problem.getName());
			statement.setString(11, problem.getStatus());
			statement.setLong(12, problem.getUserApprovedId());
			statement.setLong(13, problem.getUserSuggestId());
			statement.setLong(14, problem.getId());

			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}

}
