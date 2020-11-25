package com.thehuxley.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.thehuxley.data.model.database.Language;
import com.thehuxley.data.model.database.Submission;

public class JsonUtilsTest {

	@Test
	public void testWriteAndRead() {
		Submission s = new Submission();
		s.setDetailedLog(true);
		s.setDiffFile("diffFile");
		s.setErrorMsg("errorMsg");
		s.setEvaluation(Submission.CORRECT);
		s.setId(1);
		s.setIdTestCase(1);
		Language language = new Language();
		language.setCompile("compile");
		language.setCompileParams("compileParams");
		language.setExecParams("execParams");
		language.setExtension("extension");
		language.setId(1);
		language.setName("name");
		language.setPlagConfig("plagConfig");
		s.setLanguage(language);
		String json = JsonUtils.toJson(s);
		assertNotNull(json);
		Submission s2 = (Submission)JsonUtils.fromJson(json, Submission.class);
		assertNotNull(s2);
		
		assertEquals(s, s2);
	}

}
