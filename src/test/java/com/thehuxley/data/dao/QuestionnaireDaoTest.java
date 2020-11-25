package com.thehuxley.data.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.thehuxley.data.dao.database.QuestionnaireDao;
import com.thehuxley.data.dao.database.mysql.QuestionnaireDaoMySQL;
import com.thehuxley.data.model.database.Questionnaire;

public class QuestionnaireDaoTest {

	@Test
	public void testGetQuestionnairesById() {
		QuestionnaireDao dao = new QuestionnaireDaoMySQL();
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < 50; i++) {
			ids.add((long) i);
		}

		int counter = 0;
		for (Long id : ids) {
			if (dao.getQuestionnaireById(id) != null) {
				counter++;
			}
		}

		List<Questionnaire> quests = dao.getQuestionnairesById(ids);
		assertEquals(counter, quests.size());
	}
	
	@Test
	public void testGetStatisticsId(){
		QuestionnaireDao dao = new QuestionnaireDaoMySQL();
		Long statisticsId = dao.getStatisticsId(3, 1);
		assertTrue(statisticsId!=0);
	}

}
