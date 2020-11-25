package com.thehuxley.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeadLockHandler {
	private static final String DEAD_LOCK_CODE = "40001";
	private static Logger logger = LoggerFactory
			.getLogger(DeadLockHandler.class);
	private static final int MAX_ATTEMPTS = Integer.parseInt(Configurator
			.getProperty(Constants.CONF_FILENAME, "deadlock.max_attempts"));

	public static void executeUpdate(PreparedStatement statement)
			throws SQLException {
		int attempts = 0;
		SQLException lastException = null;
		do {
			try {
				statement.executeUpdate();
				return;
			} catch (SQLException e) {
				lastException = e;
				if (DEAD_LOCK_CODE.equals(e.getSQLState())) {
					logger.warn("Deadlock detected. I will try again.");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						logger.error(e1.getMessage(), e1);
					}
				} else {
					throw e;
				}
			}
			attempts++;
		} while (attempts < MAX_ATTEMPTS);

		logger.error("Giving up. Reached max number [" + MAX_ATTEMPTS
				+ "]of attempts for deadlocks. ", lastException);
	}
}
