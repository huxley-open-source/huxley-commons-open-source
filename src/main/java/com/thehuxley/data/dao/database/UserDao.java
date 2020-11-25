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
import com.thehuxley.data.model.database.User;

public abstract class UserDao {

	static Logger logger = LoggerFactory.getLogger(UserDao.class);

	protected String SELECT_USER;
	protected String UPDATE_PROFILE;

	public User getUserById(long id) {
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		User user = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo user " + id + "...");
		}
		
		
		try {

			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_USER);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(SELECT_USER);
			statement.setLong(1, id);
            rs = statement.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setId(rs.getLong("id"));
				user.setVersion(rs.getLong("version"));
				user.setPasswordHash(rs.getString("password_hash"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setStatus(rs.getString("status"));
				user.setLastLogin(rs.getDate("last_login"));
				user.setTopCoderPosition(rs.getInt("top_coder_position"));
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return user;
	}

	public void updateProfile(long userId) {
		PreparedStatement statement = null;
		Connection connection = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("updateProfile " + userId + "...");
		}
		
		try {

			if (logger.isDebugEnabled()) {
				logger.debug(UPDATE_PROFILE);
			}

			connection = Connector.getConnection();
			statement = connection.prepareStatement(
					UPDATE_PROFILE);
			statement.setLong(1, userId);
			DeadLockHandler.executeUpdate(statement);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(null, statement, connection);
		}

	}
}
