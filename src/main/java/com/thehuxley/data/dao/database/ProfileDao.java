package com.thehuxley.data.dao.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.data.Connector;
import com.thehuxley.data.ResourcesUtil;
import com.thehuxley.data.model.database.Profile;

/**
 * Created by rodrigo on 15/07/14.
 */
public abstract class ProfileDao {

    static Logger logger = LoggerFactory.getLogger(ProfileDao.class);

    protected String SELECT_PROFILE_BY_USER;

    public Profile getProfileByUserId(long userId){

        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        Profile profile = null;

        if (logger.isDebugEnabled()) {
            logger.debug("Retrieving profile by user_id: " + userId);
        }

        try {

            if (logger.isDebugEnabled()) {
                logger.debug(SELECT_PROFILE_BY_USER);
            }
            connection = Connector.getConnection();
            statement = connection.prepareStatement(SELECT_PROFILE_BY_USER);
            statement.setLong(1, userId);
            rs = statement.executeQuery();

            if (rs.next()) {
                profile = new Profile();
                profile.setId(rs.getLong("id"));
                profile.setProblemsCorrect(rs.getInt("problems_correct"));
                profile.setProblemsTried(rs.getInt("problems_tryed"));
                profile.setSubmissionCorrectCount(rs.getInt("submission_correct_count"));
                profile.setSubmissionCount(rs.getInt("submission_count"));
                profile.setUserId(rs.getLong("user_id"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ResourcesUtil.release(rs, statement, connection);
        }

        return profile;

    }
}
