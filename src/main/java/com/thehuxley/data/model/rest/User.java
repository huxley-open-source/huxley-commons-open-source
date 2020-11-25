package com.thehuxley.data.model.rest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodrigo on 22/01/15.
 */
public class User {

    private Integer id;
    private String name;
    private Boolean enabled;
    private String photo;
    private String smallPhoto;
    private Integer problemsCorrect;
    private Integer problemsTried;
    private Integer submissionCorrectCount;
    private Integer submissionCount;
    private Integer topCoderPosition;
    private Double topCoderScore;
    private String lastLogin;
    private String dateCreated;
    private String lastUpdated;
    private Institution institution;
    private Boolean accountExpired;
    private Boolean accountLocked;
    private Boolean passwordExpired;
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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     *
     * @param enabled
     * The enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     *
     * @return
     * The photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     *
     * @param photo
     * The photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     *
     * @return
     * The smallPhoto
     */
    public String getSmallPhoto() {
        return smallPhoto;
    }

    /**
     *
     * @param smallPhoto
     * The smallPhoto
     */
    public void setSmallPhoto(String smallPhoto) {
        this.smallPhoto = smallPhoto;
    }

    /**
     *
     * @return
     * The problemsCorrect
     */
    public Integer getProblemsCorrect() {
        return problemsCorrect;
    }

    /**
     *
     * @param problemsCorrect
     * The problemsCorrect
     */
    public void setProblemsCorrect(Integer problemsCorrect) {
        this.problemsCorrect = problemsCorrect;
    }

    /**
     *
     * @return
     * The problemsTried
     */
    public Integer getProblemsTried() {
        return problemsTried;
    }

    /**
     *
     * @param problemsTried
     * The problemsTried
     */
    public void setProblemsTried(Integer problemsTried) {
        this.problemsTried = problemsTried;
    }

    /**
     *
     * @return
     * The submissionCorrectCount
     */
    public Integer getSubmissionCorrectCount() {
        return submissionCorrectCount;
    }

    /**
     *
     * @param submissionCorrectCount
     * The submissionCorrectCount
     */
    public void setSubmissionCorrectCount(Integer submissionCorrectCount) {
        this.submissionCorrectCount = submissionCorrectCount;
    }

    /**
     *
     * @return
     * The submissionCount
     */
    public Integer getSubmissionCount() {
        return submissionCount;
    }

    /**
     *
     * @param submissionCount
     * The submissionCount
     */
    public void setSubmissionCount(Integer submissionCount) {
        this.submissionCount = submissionCount;
    }

    /**
     *
     * @return
     * The topCoderPosition
     */
    public Integer getTopCoderPosition() {
        return topCoderPosition;
    }

    /**
     *
     * @param topCoderPosition
     * The topCoderPosition
     */
    public void setTopCoderPosition(Integer topCoderPosition) {
        this.topCoderPosition = topCoderPosition;
    }

    /**
     *
     * @return
     * The topCoderScore
     */
    public Double getTopCoderScore() {
        return topCoderScore;
    }

    /**
     *
     * @param topCoderScore
     * The topCoderScore
     */
    public void setTopCoderScore(Double topCoderScore) {
        this.topCoderScore = topCoderScore;
    }

    /**
     *
     * @return
     * The lastLogin
     */
    public String getLastLogin() {
        return lastLogin;
    }

    /**
     *
     * @param lastLogin
     * The lastLogin
     */
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     *
     * @return
     * The dateCreated
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     *
     * @param dateCreated
     * The dateCreated
     */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     *
     * @return
     * The lastUpdated
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     *
     * @param lastUpdated
     * The lastUpdated
     */
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     *
     * @return
     * The institution
     */
    public Institution getInstitution() {
        return institution;
    }

    /**
     *
     * @param institution
     * The institution
     */
    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    /**
     *
     * @return
     * The accountExpired
     */
    public Boolean getAccountExpired() {
        return accountExpired;
    }

    /**
     *
     * @param accountExpired
     * The accountExpired
     */
    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    /**
     *
     * @return
     * The accountLocked
     */
    public Boolean getAccountLocked() {
        return accountLocked;
    }

    /**
     *
     * @param accountLocked
     * The accountLocked
     */
    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    /**
     *
     * @return
     * The passwordExpired
     */
    public Boolean getPasswordExpired() {
        return passwordExpired;
    }

    /**
     *
     * @param passwordExpired
     * The passwordExpired
     */
    public void setPasswordExpired(Boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
