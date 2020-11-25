package com.thehuxley.data.dao.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.data.Connector;
import com.thehuxley.data.ResourcesUtil;
import com.thehuxley.data.model.database.Language;

public abstract class LanguageDao {

	static Logger logger = LoggerFactory.getLogger(LanguageDao.class);

	protected String SELECT_LANGUAGE;
	protected String UPDATE_LANGUAGE;
	protected String CREATE_LANGUAGE;
	protected String DELETE_LANGUAGE;

	public Language getLanguageById(long id) {
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		Language language = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo problem " + id + "...");
		}
		
		try {

			if (logger.isDebugEnabled()) {
				logger.debug(SELECT_LANGUAGE);
			}
			connection = Connector.getConnection();
			statement = connection.prepareStatement(SELECT_LANGUAGE);
			statement.setLong(1, id);
			rs = statement.executeQuery();

			if (rs.next()) {
				language = fillLanguage(rs);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return language;
	}

	public static Language fillLanguage(ResultSet rs) throws SQLException {
		Language language;
		language = new Language();
		language.setId(rs.getLong("l.id"));
		language.setVersion(rs.getLong("l.version"));
		language.setName(rs.getString("l.name"));
		language.setScript(rs.getString("l.script"));
		language.setExtension(rs.getString("l.extension"));
		return language;
	}

}
