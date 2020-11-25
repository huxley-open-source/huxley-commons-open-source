package com.thehuxley.data.model.rest;

/**
 * Essa classe é utilizada pelo avaliador para atualizar uma submissão.
 * Ela contém somente os campos necessários para serem atualizados em uma submissão.
 * 
 * Isso torna a resposta do avaliador menor e evita problemas de parser dos outros campos.
 * * * 
 */
public class SubmissionResult {
	
    private long id;
    
    private String evaluation;
    
    private Double time;
    
    private String diff;
    
    private TestCase testCase;
    
    private String errorMsg;

    /**
     * Total de casos de teste do problema dessa submissão
     */
    private int testCasesTotal;

    /*
    Quantidade de casos de teste que essa submissão acertou.
    Lembrando que quando ele encontra o primeiro caso de teste errado ele pára.
     */
    private int testCasesCorrect;
    
    public SubmissionResult() {
    	super();
    }
    
    public SubmissionResult(Submission sub) {
    	super();
        this.id = sub.getId();
        this.evaluation = sub.getEvaluation();
        this.time = sub.getTime();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
    
    public boolean hasDiff() {
    	return this.diff != null && !this.diff.isEmpty();
    }

	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	public String getDiff() {
		return diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

    public int getTestCasesTotal() {
        return testCasesTotal;
    }

    public void setTestCasesTotal(int testCasesTotal) {
        this.testCasesTotal = testCasesTotal;
    }

    public int getTestCasesCorrect() {
        return testCasesCorrect;
    }

    public void setTestCasesCorrect(int testCasesCorrect) {
        this.testCasesCorrect = testCasesCorrect;
    }
}
