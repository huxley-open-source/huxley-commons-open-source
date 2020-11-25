package com.thehuxley.data.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.thehuxley.data.dao.database.LanguageDao;
import com.thehuxley.data.dao.database.mysql.LanguageDaoMySQL;
import com.thehuxley.data.model.database.Language;

public class LanguageDaoTest {

	@Test
	public void testGetLanguageById() {
		LanguageDao dao = new LanguageDaoMySQL();
		Language l = dao.getLanguageById(1);
		assertNotNull(l);
		assertNotNull(l.getExtension());
		assertNotNull(l.getName());
		assertNotNull(l.getScript());
		assertNotNull(l.getVersion());
	}

}
