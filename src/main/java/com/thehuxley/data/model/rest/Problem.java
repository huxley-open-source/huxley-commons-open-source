package com.thehuxley.data.model.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

/**
 * Created by rodrigo on 22/01/15.
 */
@Generated("org.jsonschema2pojo")
public class Problem {

    private Integer id;
    private String name;
    private String description;
    private String inputFormat;
    private String outputFormat;
    private String source;
    private Integer level;
    private Integer timeLimit;
    private Double nd;
    private String dateCreated;
    private String lastUpdated;
    private UserApproved approvedBy;
    private UserSuggest suggestedBy;
    private FastestSubmission fastestSubmission;
    private List<Topic> topics = new ArrayList<Topic>();
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
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The inputFormat
     */
    public String getInputFormat() {
        return inputFormat;
    }

    /**
     *
     * @param inputFormat
     * The inputFormat
     */
    public void setInputFormat(String inputFormat) {
        this.inputFormat = inputFormat;
    }

    /**
     *
     * @return
     * The outputFormat
     */
    public String getOutputFormat() {
        return outputFormat;
    }

    /**
     *
     * @param outputFormat
     * The outputFormat
     */
    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    /**
     *
     * @return
     * The source
     */
    public String getSource() {
        return source;
    }

    /**
     *
     * @param source
     * The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     *
     * @return
     * The level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     *
     * @param level
     * The level
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     *
     * @return
     * The timeLimit
     */
    public Integer getTimeLimit() {
        return timeLimit;
    }

    /**
     *
     * @param timeLimit
     * The timeLimit
     */
    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    /**
     *
     * @return
     * The nd
     */
    public Double getNd() {
        return nd;
    }

    /**
     *
     * @param nd
     * The nd
     */
    public void setNd(Double nd) {
        this.nd = nd;
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
     * The approvedBy
     */
    public UserApproved getApprovedBy() {
        return approvedBy;
    }

    /**
     *
     * @param approvedBy
     * The approvedBy
     */
    public void setApprovedBy(UserApproved approvedBy) {
        this.approvedBy = approvedBy;
    }

    /**
     *
     * @return
     * The suggestedBy
     */
    public UserSuggest getSuggestedBy() {
        return suggestedBy;
    }

    /**
     *
     * @param suggestedBy
     * The suggestedBy
     */
    public void setSuggestedBy(UserSuggest suggestedBy) {
        this.suggestedBy = suggestedBy;
    }

    /**
     *
     * @return
     * The fastestSubmission
     */
    public FastestSubmission getFastestSubmission() {
        return fastestSubmission;
    }

    /**
     *
     * @param fastestSubmission
     * The fastestSubmission
     */
    public void setFastestSubmission(FastestSubmission fastestSubmission) {
        this.fastestSubmission = fastestSubmission;
    }

    /**
     *
     * @return
     * The topics
     */
    public List<Topic> getTopics() {
        return topics;
    }

    /**
     *
     * @param topics
     * The topics
     */
    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
