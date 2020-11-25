package com.thehuxley.data.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import com.thehuxley.data.model.database.OracleSubmissions;
import com.thehuxley.data.model.database.Submission;

/**
 * Created by rodrigo on 24/07/14.
 */
public class OracleSubmissionsTest {

    @Test
    public void testShouldUpdate(){
        OracleSubmissions oracleSubmissions = new OracleSubmissions();
        assertTrue(oracleSubmissions.shouldUpdate());
        oracleSubmissions.addSubmission(new Submission());
        assertTrue(oracleSubmissions.shouldUpdate());
        oracleSubmissions.addSubmission(new Submission());
        oracleSubmissions.addSubmission(new Submission());
        oracleSubmissions.addSubmission(new Submission());
        oracleSubmissions.addSubmission(new Submission());
        assertTrue(oracleSubmissions.shouldUpdate());

        Calendar oneYearAgo = Calendar.getInstance();
        oneYearAgo.add(Calendar.YEAR,-1);
        oracleSubmissions.setLastModified(oneYearAgo.getTime());
        assertTrue(oracleSubmissions.shouldUpdate());

        Calendar oneDayAgo = Calendar.getInstance();
        oneDayAgo.add(Calendar.DAY_OF_YEAR,-1);
        oracleSubmissions.setLastModified(oneDayAgo.getTime());
        assertFalse(oracleSubmissions.shouldUpdate());

    }
}
