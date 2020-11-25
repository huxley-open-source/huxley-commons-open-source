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
import com.thehuxley.data.model.database.Submission;
import com.thehuxley.data.model.database.UserProblem;

public abstract class UserProblemDao {


	static Logger logger = LoggerFactory.getLogger(UserProblemDao.class);

	protected String SELECT_USER_PROBLEM;
	protected String UPDATE_USER_PROBLEM;
	protected String CREATE_USER_PROBLEM;

	public UserProblem getByUserAndProblemId(long userId, long problemId) {
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		UserProblem userProblem = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo o user problem para o usuario " + userId
					+ " e problema " + problemId + "...");
		}

		
		try {

			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_USER_PROBLEM);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(
					SELECT_USER_PROBLEM);
			statement.setLong(1, userId);
			statement.setLong(2, problemId);
			rs = statement.executeQuery();

			if (rs.next()) {
				userProblem = new UserProblem();
				userProblem.setId(rs.getLong("id"));
				userProblem.setVersion(rs.getLong("version"));
				userProblem.setProblemId(rs.getLong("problem_id"));
				userProblem.setUserId(rs.getLong("user_id"));
				userProblem.setStatus(rs.getInt("status"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return userProblem;
	}

	public void createUserProblem(long userId, long problemId, int status) {
		PreparedStatement statement = null;
		Connection connection = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Criando user problem. UserId["+userId+"], problemId["+problemId+"], status["+status+"]");
		}
		
		try {

			if (logger.isDebugEnabled()) {
				logger.debug(CREATE_USER_PROBLEM);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(
					CREATE_USER_PROBLEM);

			statement.setLong(1, userId);
			statement.setLong(2, problemId);
			statement.setInt(3, status);
			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}
	}

//	public void updateUserProblem(long userId, long problemId, int status) {
//
//		if (logger.isDebugEnabled()) {
//			logger.debug("Atualizando user problem ...");
//		}
//		PreparedStatement statement = null;
//		try {
//
//			if (logger.isDebugEnabled()) {
//				logger.debug(UPDATE_USER_PROBLEM);
//			}
//			UserProblem userProblem = getByUserAndProblemId(userId, problemId);
//			if (userProblem == null) {
//				createUserProblem(userId, problemId, status);
//			} else if (status == 1 && userProblem.getStatus() != 1) {
//				statement = Connector.getConnection().prepareStatement(
//						UPDATE_USER_PROBLEM);
//
//				statement.setInt(1, status);
//				statement.setLong(2, userProblem.getId());
//
//				DeadLockHandler.executeUpdate(statement);
//
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			ResourcesUtil.closeStatement(statement);
//		}
//	}

	private void setStatusAsCorrect(UserProblem userProblem) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_USER_PROBLEM);
			}
			connection =Connector.getConnection();
			statement = connection.prepareStatement(
					UPDATE_USER_PROBLEM);
			statement.setInt(1, UserProblem.STATUS_CORRECT);
			statement.setLong(2, userProblem.getId());
			DeadLockHandler.executeUpdate(statement);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}

	}

	public void updateUserProblem(Submission submission) {
		long userId = submission.getUserId();
		long problemId = submission.getProblemId();
        if (userId == 0 || problemId==0){
            throw new IllegalArgumentException("user_id=["+userId+"], problem_id=["+problemId+"]. They cannot be 0.");
        }
		int userProblemStatus = submission.isEvaluationCorrect() ? UserProblem.STATUS_CORRECT
				: UserProblem.STATUS_TRIED;

		if (logger.isDebugEnabled()) {
			logger.debug("Atualizando user_problem. userId["+userId+"], problemId["+problemId+"], submissionStatus["+userProblemStatus+"].");
		}

		// Verifica se o problema já existe
		UserProblem userProblem = getByUserAndProblemId(userId, problemId);
		if (userProblem == null) {
			createUserProblem(userId, problemId, userProblemStatus);
		} else {
            if (logger.isDebugEnabled()){
                logger.debug("Já existe um user problem");
            }
			// Só precisa atualizar se até aquele momento o usuário não acertou
			// o problema
			// e se a submissão atual está correta.
			if (userProblem.getStatus() == UserProblem.STATUS_TRIED
					&& userProblemStatus == UserProblem.STATUS_CORRECT) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Saiu de TENTADO para correto.");
                }
				setStatusAsCorrect(userProblem);
			}
		}

	}

}
