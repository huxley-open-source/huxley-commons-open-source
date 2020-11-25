package com.thehuxley.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Connector {

	static Logger logger = LoggerFactory.getLogger(Connector.class);
	private static Connector singleton = null;

	private ComboPooledDataSource pool;

	private Connector() {
		Properties properties;
		try {
			pool = new ComboPooledDataSource();

			properties = Configurator.getProperties(Constants.CONF_FILENAME);
			String url = "jdbc:mysql://"
					+ properties.getProperty("data.host")
					+ (properties.getProperty("data.port") == null ? ""
							: (":" + properties.getProperty("data.port")))
					+ "/";
			url += properties.getProperty("data.database");
			String userName = properties.getProperty("data.username");
			String password = properties.getProperty("data.password");

			if (logger.isInfoEnabled()) {
				logger.info("Criando uma conexão com o banco de dados...");
			}

			pool.setDriverClass("com.mysql.jdbc.Driver"); // loads the jdbc
															// driver
			pool.setJdbcUrl(url);
			pool.setUser(userName);
			pool.setPassword(password);
            pool.getProperties().put("zeroDateTimeBehavior","convertToNull");

			// If you want to turn on PreparedStatement pooling, you must also
			// set maxStatements and/or maxStatementsPerConnection (both default
			// to 0):
			pool.setMaxStatements(180);

			if (logger.isDebugEnabled()) {
				logger.debug("url = " + url + "?username = " + userName);
			}

			if (logger.isInfoEnabled()) {
				logger.info("Conexão com o banco de dados estabelecida.");
			}

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Erro durante a conexão com o banco de dados.", e);
			}
		}
	}

	public static Connection getConnection() {
		if (singleton == null) {
			singleton = new Connector();
		}
		try {
			return singleton.pool.getConnection();
		} catch (SQLException e) {
			if (logger.isErrorEnabled()) {
				logger.error("Problemas na comunicacão com o banco de dados.",
						e);
			}
		}
		return null;

	}
	
}
