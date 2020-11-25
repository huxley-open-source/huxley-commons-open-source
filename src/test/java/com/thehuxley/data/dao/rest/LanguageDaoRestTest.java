package com.thehuxley.data.dao.rest;

import org.junit.Assert;
import org.junit.Test;

import com.thehuxley.data.model.rest.Language;

public class LanguageDaoRestTest {
    
    @Test
    public void testGetLanguageById() {
        LanguageDaoRest dao = new LanguageDaoRest();
        Language language = dao.getLanguageById(1L);
        
        Assert.assertEquals(1, (int)language.getId());
        Assert.assertEquals("C", language.getName());
        Assert.assertEquals("gcc 4.8.2", language.getCompiler());
    }
}
