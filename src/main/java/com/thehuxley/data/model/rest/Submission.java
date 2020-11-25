package com.thehuxley.data.model.rest;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Submission {

    public enum Evaluation {
        CORRECT,
        WRONG_ANSWER,
        RUNTIME_ERROR,
        COMPILATION_ERROR,
        EMPTY_ANSWER,
        TIME_LIMIT_EXCEEDED,
        WAITING,
        EMPTY_TEST_CASE,
        WRONG_FILE_NAME,
        PRESENTATION_ERROR,
        HUXLEY_ERROR
    }


    private Long id;
    private Double time;
    private Integer tries;
    private Object comment;
    private String submissionDate;
    private String filename;
    private String evaluation;
    private User user;
    private Problem problem;
    private Language language;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private File submissionFile;

    private String sourceCode;

    private List<TestCase> testCases;

    private boolean isReevaluation;

    public boolean isReevaluation() {
        return isReevaluation;
    }

    public void setIsReevaluation(boolean isReevaluation) {
        this.isReevaluation = isReevaluation;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public void setSubmissionId(Long submissionId) {
        this.setId(submissionId);
    }


    /**
     * @return The id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return The time
     */
    public Double getTime() {
        return time;
    }

    /**
     * @param time The time
     */
    public void setTime(Double time) {
        this.time = time;
    }

    /**
     * @return The tries
     */
    public Integer getTries() {
        return tries;
    }

    /**
     * @param tries The tries
     */
    public void setTries(Integer tries) {
        this.tries = tries;
    }

    /**
     * @return The comment
     */
    public Object getComment() {
        return comment;
    }

    /**
     * @param comment The comment
     */
    public void setComment(Object comment) {
        this.comment = comment;
    }

    /**
     * @return The submissionDate
     */
    public String getSubmissionDate() {
        return submissionDate;
    }

    /**
     * @param submissionDate The submissionDate
     */
    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return The evaluation
     */
    public String getEvaluation() {
        return evaluation;
    }

    /**
     * @param evaluation The evaluation
     */
    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    /**
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return The problem
     */
    public Problem getProblem() {
        return problem;
    }

    /**
     * @param problem The problem
     */
    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    /**
     * @return The language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * @param language The language
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setSubmissionFile(File file) {
        this.submissionFile = file;
    }

    public File getSubmissionFile() {
        return this.submissionFile;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}