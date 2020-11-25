package com.thehuxley.data.model.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.thehuxley.data.Configurator;
import com.thehuxley.data.Constants;

/**
 * Created by rodrigo on 24/07/14.
 */
public class OracleSubmissions {

    private static final int DAYS_TO_BE_OLD = Integer.parseInt(Configurator.getProperty(Constants.CONF_FILENAME, "oracle.days_to_be_old"));

    public static final int NUMBER_OF_SUBMISSIONS = 5;

    private long id;
    private long problemId;

    private List<Submission> submissions = new ArrayList<Submission>(NUMBER_OF_SUBMISSIONS);
    private Date lastModified;

    public long getProblemId() {
        return problemId;
    }

    public void setProblemId(long problemId) {
        this.problemId = problemId;
    }

    public void addSubmission(Submission s){
        submissions.add(s);
    }

    public List<Submission> getSubmissions(){
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions){
        this.submissions = submissions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public boolean shouldUpdate(){
        if (submissions.size()<NUMBER_OF_SUBMISSIONS){
            return true;
        }
        if (lastModified==null){
            return true;
        }
        Calendar oldDateCalendar = Calendar.getInstance();
        oldDateCalendar.add(Calendar.DAY_OF_YEAR,-DAYS_TO_BE_OLD);

        Calendar lastModifiedCalendar = Calendar.getInstance();
        lastModifiedCalendar.setTime(lastModified);

        if (oldDateCalendar.after(lastModifiedCalendar)){
            return true;
        }
        return false;
    }
}
