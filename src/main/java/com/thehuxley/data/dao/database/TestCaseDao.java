package com.thehuxley.data.dao.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.data.Connector;
import com.thehuxley.data.ResourcesUtil;
import com.thehuxley.data.model.database.TestCase;

public abstract class TestCaseDao {

	static Logger logger = LoggerFactory.getLogger(TestCaseDao.class);

	protected String LIST_BY_PROBLEM;
	protected long offset;
	protected long problemId;
	public static final long LIMIT = 5;

	public void setProblemId(long problemId) {
		this.problemId = problemId;
	}

	public List<TestCase> listTestCases(long offset) {
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		List<TestCase> testCases = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Obtendo test case para o problem " + problemId
					+ "...");
		}

		try {

			if (logger.isDebugEnabled()) {
				logger.debug(LIST_BY_PROBLEM);
			}

			connection = Connector.getConnection();
			statement = connection.prepareStatement(LIST_BY_PROBLEM);
			statement.setLong(1, problemId);
			statement.setLong(2, offset);
			rs = statement.executeQuery();

			while (rs.next()) {
				TestCase testCase = new TestCase();
				testCase.setId(rs.getLong("id"));
				testCase.setProblemId(rs.getLong("problem_id"));
				testCase.setInput(rs.getString("input"));
				testCase.setOutput(rs.getString("output"));
				testCase.setMaxOutputSize(rs.getDouble("max_output_size"));
				testCase.setType(rs.getInt("type"));
				if (testCases == null) {
					testCases = new ArrayList<TestCase>();
				}
				testCases.add(testCase);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ResourcesUtil.release(rs, statement, connection);
		}

		return testCases;
	}

}
