package com.thehuxley.data.model.rest;

import java.util.Comparator;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TestCase implements Comparable<TestCase>{

    private Long id;
    private String input;
    private String output;
    private boolean example;
    private String tip;
    private Date dateCreated;
    private Date lastUpdated;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getInput() {
        return input;
    }
    public void setInput(String input) {
        this.input = input;
    }
    public String getOutput() {
        return output;
    }
    public void setOutput(String output) {
        this.output = output;
    }
    public boolean isExample() {
        return example;
    }
    public void setExample(boolean example) {
        this.example = example;
    }
    public String getTip() {
        return tip;
    }
    public void setTip(String tip) {
        this.tip = tip;
    }
    public Date getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    public Date getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


    /**
     * Usado para ordenar os casos de teste para o avaliador. Assim, manterá sempre essa ordem estabelecida
     * nesse método.
     * Primeiro ficarão os exemplos.
     * Depois os que tiverem menor id
     * @param other
     * @return
     */
    @Override
    public int compareTo(TestCase other) {
        if (example) return -1; // menor, vai ser colocado primeiro na lista
        if (other.example) return 1; // maior, o segundo será colocado primeiro na lista
        if (id == other.id) return 0;
        return (id < other.id)? -1 : 1;
    }
}
