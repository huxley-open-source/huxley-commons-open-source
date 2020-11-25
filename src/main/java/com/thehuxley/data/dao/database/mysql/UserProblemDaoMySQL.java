package com.thehuxley.data.dao.database.mysql;

import com.thehuxley.data.dao.database.UserProblemDao;

public class UserProblemDaoMySQL extends UserProblemDao {

	public UserProblemDaoMySQL() {

		SELECT_USER_PROBLEM = "SELECT * FROM user_problem WHERE user_id = ? and problem_id = ?";
		CREATE_USER_PROBLEM = "INSERT INTO user_problem (user_id,problem_id,status,version,similarity) VALUES (?,?,?,0,0)";
		UPDATE_USER_PROBLEM = "UPDATE user_problem set status = ? WHERE id = ?";

	}

}
