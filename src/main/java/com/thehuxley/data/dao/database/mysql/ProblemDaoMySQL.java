package com.thehuxley.data.dao.database.mysql;

import com.thehuxley.data.dao.database.ProblemDao;

public class ProblemDaoMySQL extends ProblemDao {
	
	public ProblemDaoMySQL() {
		
		CREATE_PROBLEM = "INSERT INTO problem (time_limit, evaluation_detail, code, input, level, nd, description, name, problem_root, output, status, user_approved_id, user_suggest_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		DELETE_PROBLEM = "DELETE problem WHERE id = ?";
//		SELECT_PROBLEM = "SELECT * FROM problem WHERE id = ?";
		SELECT_PROBLEM = "select *, s.time from problem as p inner join submission as s on p.fastest_submision_id=s.id where p.id=?";
		UPDATE_PROBLEM = "UPDATE problem SET time_limit = ?, evaluation_detail = ?, code = ?, input = ?, level = ?, nd = ?, description = ?, name = ?, problem_root = ?, output = ?, status = ?, user_approved_id = ?, user_suggest_id = ? WHERE = ?";
		FASTEST_UPDATE = "UPDATE problem p set fastest_submision_id = (select s.id from submission s where s.problem_id = p.id and s.time > 0 and s.evaluation = 0  order by s.time asc limit 1) where p.id = ?";
	}
	
}
