package com.thehuxley.data.dao.database.mysql;

import com.thehuxley.data.dao.database.ProfileDao;

/**
 * Created by rodrigo on 15/07/14.
 */
public class ProfileDaoMySQL extends ProfileDao {

    public ProfileDaoMySQL(){
        SELECT_PROFILE_BY_USER = "select * from profile where user_id=?";
    }


}
