package com.thehuxley.data.model.database;

public class Profile {

    private long id;
    private int problemsCorrect;
    private int problemsTried;
    private long userId;
    private int submissionCorrectCount;
    private int submissionCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getProblemsCorrect() {
        return problemsCorrect;
    }

    public void setProblemsCorrect(int problemsCorrect) {
        this.problemsCorrect = problemsCorrect;
    }

    public int getProblemsTried() {
        return problemsTried;
    }

    public void setProblemsTried(int problemsTried) {
        this.problemsTried = problemsTried;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getSubmissionCorrectCount() {
        return submissionCorrectCount;
    }

    public void setSubmissionCorrectCount(int submissionCorrectCount) {
        this.submissionCorrectCount = submissionCorrectCount;
    }

    public int getSubmissionCount() {
        return submissionCount;
    }

    public void setSubmissionCount(int submissionCount) {
        this.submissionCount = submissionCount;
    }



}
