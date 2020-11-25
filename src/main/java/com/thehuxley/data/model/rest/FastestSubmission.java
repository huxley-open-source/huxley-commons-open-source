package com.thehuxley.data.model.rest;

import java.util.HashMap;
import java.util.Map;

public class FastestSubmission {

    private Integer id;
    private Double time;
    private Integer tries;
    private Object comment;
    private String submissionDate;
    private String evaluation;
    private User user;
    private Object problem;
    private Language language;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The time
     */
    public Double getTime() {
        return time;
    }

    /**
     *
     * @param time
     * The time
     */
    public void setTime(Double time) {
        this.time = time;
    }

    /**
     *
     * @return
     * The tries
     */
    public Integer getTries() {
        return tries;
    }

    /**
     *
     * @param tries
     * The tries
     */
    public void setTries(Integer tries) {
        this.tries = tries;
    }

    /**
     *
     * @return
     * The comment
     */
    public Object getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     * The comment
     */
    public void setComment(Object comment) {
        this.comment = comment;
    }

    /**
     *
     * @return
     * The submissionDate
     */
    public String getSubmissionDate() {
        return submissionDate;
    }

    /**
     *
     * @param submissionDate
     * The submissionDate
     */
    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    /**
     *
     * @return
     * The evaluation
     */
    public String getEvaluation() {
        return evaluation;
    }

    /**
     *
     * @param evaluation
     * The evaluation
     */
    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    /**
     *
     * @return
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     * The problem
     */
    public Object getProblem() {
        return problem;
    }

    /**
     *
     * @param problem
     * The problem
     */
    public void setProblem(Object problem) {
        this.problem = problem;
    }

    /**
     *
     * @return
     * The language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     *
     * @param language
     * The language
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}