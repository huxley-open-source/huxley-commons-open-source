package com.thehuxley.data.model.database;

/**
 * @author Romero Malaquias
 * @version 1.1.1
 * @since huxley-avaliador 1.0.0
 */
public class TestCase {
	private long id;
	private long problemId;
	private String input;
	private String output;
    private double maxOutputSize;
    private int type;

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setProblemId(long problemId) {
		this.problemId = problemId;
	}

	public long getProblemId() {
		return problemId;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getInput() {
		return input;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getOutput() {
		return output;
	}

    public void setMaxOutputSize(double maxOutputSize){
        this.maxOutputSize = maxOutputSize;
    }

    public Double getMaxOutputSize(){
        return maxOutputSize;
    }

    public void setType(int type){
        this.type = type;
    }

    public int getType(){
        return this.type;
    }

}
