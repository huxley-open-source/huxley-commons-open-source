package com.thehuxley.data.dao.database.mysql;

import com.thehuxley.data.dao.database.SubmissionDao;
import com.thehuxley.data.model.database.Submission;

public class SubmissionDaoMySQL extends SubmissionDao {

    public SubmissionDaoMySQL() {
        CREATE_SUBMISSION = "INSERT INTO submission (problem_id, submission, submission_dir, evaluation, submission_date, detailed_log, input_test_case, diff_file, language_id, tries, output, user_id, time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DELETE_SUBMISSION = "DELETE submission WHERE id = ?";
        SELECT_SUBMISSION = "SELECT * FROM submission AS s left join problem AS p on s.problem_id = p.id left join language AS l on s.language_id = l.id WHERE s.id = ?";
        UPDATE_SUBMISSION = "UPDATE submission SET evaluation = ?, time = ? , input_test_case=?, detailed_log = ?, error_msg = ?, test_case_id = ?, version=?  WHERE id = ?";
        INSERT_SUBMISSION = "INSERT INTO submission (version, evaluation, time, input_test_case, detailed_log, error_msg, test_case_id, problem_id, submission, submission_date, diff_file, language_id, tries, output, user_id) VALUES (0, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        IS_PROBLEM_CORRECT = "SELECT count(*) correct_count from submission where problem_id = ? and user_id = ? and evaluation = " + Submission.CORRECT;
        UPDATE_ORACLE_SUBMISSIONS = "UPDATE oracle_submissions SET problem_id=?, sub1_id=?, sub2_id=?, sub3_id=?, sub4_id=?, sub5_id=?, last_modified=? where id=?";
        INSERT_ORACLE_SUBMISSIONS = "insert into oracle_submissions (problem_id, sub1_id, sub2_id, sub3_id, sub4_id, sub5_id, last_modified) VALUES (?,?,?,?,?,?,?)";
        DELETE_ORACLE_SUBMISSIONS = "delete from oracle_submissions where problem_id=?";

        SELECT_ORACLE_SUBMISSIONS = "SELECT * from oracle_submissions where problem_id=?";
        SELECT_SUBMISSION_OF_CREATOR = "select s.* from problem p inner join submission s on p.user_suggest_id=s.user_id where s.evaluation=" + Submission.CORRECT + " and p.id=? and s.problem_id=? order by submission_date desc limit 1";
        SELECT_SUBMISSION_OF_TOPCODERS = "select s.* from shiro_user u left join submission s on s.user_id=u.id left join problem p on s.problem_id=p.id where s.problem_id=? and s.evaluation=" + Submission.CORRECT + " and u.id<>p.user_suggest_id group by u.id order by u.top_coder_score desc limit ?";

    }
}