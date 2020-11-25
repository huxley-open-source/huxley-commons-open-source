package com.thehuxley.data.dao.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.thehuxley.data.model.rest.TestCase;

public class TestCaseDaoRestTest {

    @Test
    public void testGetTestCases() {
        TestCaseDaoRest rest = new TestCaseDaoRest();
        List<TestCase> testCases = rest.getTestCases(3L, null);
        
        // Esse problema possui 4 casos de teste cadastrados. Se o teste quebrar, confira se o número 
        // de casos de teste foi alterado.

        assertEquals(4, testCases.size());

        for (TestCase testCase : testCases) {
            assertNotNull( testCase.getId() );
            assertNotNull( testCase.getInput() );
            assertNotNull(testCase.getOutput());
            assertNotNull( testCase.isExample() );
        }
    }

    @Test
    public void testGetTestCasesWithParams() {
        TestCaseDaoRest rest = new TestCaseDaoRest();
        Map<String, String> params = new HashMap<String, String>();
        params.put("max",String.valueOf(5));
        params.put("offset",String.valueOf(0));
        List<TestCase> testCases = rest.getTestCases(7L, params);

        assertEquals(5, testCases.size());

        for (TestCase testCase : testCases) {
            assertNotNull( testCase.getId() );
            assertNotNull( testCase.getInput() );
            assertNotNull( testCase.getOutput() );
            assertNotNull(testCase.isExample());
        }

        params.put("max", String.valueOf(2));
        params.put("offset", String.valueOf(0));
        testCases = rest.getTestCases(7L, params);
        assertEquals(2, testCases.size());

        params.put("max", String.valueOf(2));
        params.put("offset", String.valueOf(2));
        testCases = rest.getTestCases(7L, params);
        assertEquals(2, testCases.size());

        params.put("max", String.valueOf(2));
        params.put("offset",String.valueOf(4));
        testCases = rest.getTestCases(7L, params);
        assertEquals(1, testCases.size());

    }
}
