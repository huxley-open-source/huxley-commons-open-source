package com.thehuxley.data.model.rest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

/**
 * Created by rodrigo on 22/01/15.
 */
@Generated("org.jsonschema2pojo")
public class Language {

    private Integer id;
    private String name;
    private String script;
    private String compiler;
    private String compileParams;
    private String execParams;
    private String plagConfig;
    private String extension;
    
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
     * The script
     */
    public String getScript() {
        return script;
    }

    /**
     *
     * @param script
     * The script
     */
    public void setScript(String script) {
        this.script = script;
    }

    /**
     *
     * @return
     * The compiler
     */
    public String getCompiler() {
        return compiler;
    }

    /**
     *
     * @param compiler
     * The compiler
     */
    public void setCompiler(String compiler) {
        this.compiler = compiler;
    }

    /**
     *
     * @return
     * The compileParams
     */
    public String getCompileParams() {
        return compileParams;
    }

    /**
     *
     * @param compileParams
     * The compileParams
     */
    public void setCompileParams(String compileParams) {
        this.compileParams = compileParams;
    }

    /**
     *
     * @return
     * The execParams
     */
    public String getExecParams() {
        return execParams;
    }

    /**
     *
     * @param execParams
     * The execParams
     */
    public void setExecParams(String execParams) {
        this.execParams = execParams;
    }

    /**
     *
     * @return
     * The plagConfig
     */
    public String getPlagConfig() {
        return plagConfig;
    }

    /**
     *
     * @param plagConfig
     * The plagConfig
     */
    public void setPlagConfig(String plagConfig) {
        this.plagConfig = plagConfig;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
