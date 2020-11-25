package com.thehuxley.data.dao.database.mysql;

import com.thehuxley.data.dao.database.UserDao;
import com.thehuxley.data.model.database.Submission;

public class UserDaoMySQL extends UserDao {

	public UserDaoMySQL() {

		SELECT_USER = "SELECT * FROM shiro_user WHERE id = ?";
		UPDATE_PROFILE = "UPDATE profile u SET "
				+ "problems_tryed = (SELECT count(up.id) FROM user_problem up where up.user_id =u.user_id), "
				+ "problems_correct = (SELECT count(up.id) FROM user_problem up where up.user_id =u.user_id and up.status = 1), "
				+ "submission_correct_count = (SELECT count(s.id) from submission s where s.user_id = u.user_id and evaluation = "+ Submission.CORRECT+"), "
				+ "submission_count = (SELECT count(s.id) from submission s where s.user_id = u.user_id) "
				+ "where u.user_id = ?";

	}

}
