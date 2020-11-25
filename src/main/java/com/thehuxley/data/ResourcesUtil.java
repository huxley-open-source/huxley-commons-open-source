package com.thehuxley.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourcesUtil {
	static Logger logger = LoggerFactory.getLogger(ResourcesUtil.class);

	public static void release(ResultSet rs, Statement statement,
			Connection connection) {
		closeResultSet(rs);
		closeStatement(statement);
		closeConnection(connection);

	}

	private static void closeConnection(Connection c) {
		if (c != null) {
			try {
				c.close();
				c = null;
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	private static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (Exception e1) {
				logger.error(e1.getMessage());
			}
		}
	}

	private static void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
				statement = null;
			} catch (Exception e1) {
				logger.error(e1.getMessage());
			}
		}
	}

	public static void closeBufferedWriter(BufferedWriter bW) {
		if (bW != null) {
			try {
				bW.close();
				bW = null;
			} catch (Exception e1) {
				logger.error(e1.getMessage());
			}
		}
	}

	public static void closeBufferedReader(BufferedReader bR) {
		if (bR != null) {
			try {
				bR.close();
				bR = null;
			} catch (Exception e1) {
				logger.error(e1.getMessage());
			}
		}
	}

	// public static void closeOutputWriter(OutputWriter oW) {
	// if (oW != null) {
	// try {
	// oW.close();
	// oW = null;
	// } catch (Exception e1) {
	// logger.error(e1.getMessage());
	// }
	// }
	// }

	public static void closeDataInputStream(DataInputStream dI) {
		if (dI != null) {
			try {
				dI.close();
				dI = null;
			} catch (Exception e1) {
				logger.error(e1.getMessage());
			}
		}
	}

	public static void closeProcess(Process proc) {
		if (proc != null) {
			try {
				proc.destroy();
			} catch (Exception e1) {
				logger.error(e1.getMessage());
			}
		}
	}

	public static List<Integer> extractIntListFromString(String intList) {
		intList = StringUtils.remove(intList, "(");
		intList = StringUtils.remove(intList, ")");
		intList = StringUtils.remove(intList, " ");
		List<Integer> list = new ArrayList<Integer>();
		for (String str : intList.split(",")) {
			list.add(new Integer(str));
		}
		return list;

	}

}
