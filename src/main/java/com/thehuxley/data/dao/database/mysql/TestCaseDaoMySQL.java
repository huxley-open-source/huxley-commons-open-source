package com.thehuxley.data.dao.database.mysql;

import com.thehuxley.data.dao.database.TestCaseDao;

public class TestCaseDaoMySQL extends TestCaseDao {
	
	public TestCaseDaoMySQL() {
		LIST_BY_PROBLEM = "SELECT * FROM test_case WHERE problem_id = ?  order by type desc LIMIT " + LIMIT +" offset ?";
			
	}
	
}
