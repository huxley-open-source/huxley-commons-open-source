package com.thehuxley.data;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thehuxley.data.model.database.Language;

public class LanguageManager {
	private static Logger logger = LoggerFactory
			.getLogger(LanguageManager.class);
	private Hashtable<Long, Language> languageTable;
	private static LanguageManager instance;

	private LanguageManager() {

		languageTable = new Hashtable<Long, Language>();

	}

	public static LanguageManager getInstance() {

		if (instance == null) {
			instance = new LanguageManager();
		}

		return instance;
	}

	public Language getLanguage(long id) {

		if (languageTable.contains(id)) {
			return languageTable.get(id);

		} else {
			Language language = DataBaseManager.getLanguageById(id);

			if (language == null) {
				logger.error("Linguagem com o id " + id
						+ " não foi encontrada.");
			} else {
				languageTable.put(id, language);
				return language;
			}
		}
		return null;
	}

	public int getLanguageSize() {
		
		return languageTable.size();
	}

}
