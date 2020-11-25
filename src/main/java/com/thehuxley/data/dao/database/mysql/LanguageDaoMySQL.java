package com.thehuxley.data.dao.database.mysql;

import com.thehuxley.data.dao.database.LanguageDao;

public class LanguageDaoMySQL extends LanguageDao {

	public LanguageDaoMySQL() {

		SELECT_LANGUAGE = "SELECT * FROM language as l WHERE l.id = ?";

	}

}
