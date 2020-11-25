package com.thehuxley.data.model.database;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thehuxley.data.Configurator;
import com.thehuxley.data.Constants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Submission{

    public static final byte CORRECT = 0;
    public static final byte WRONG_ANSWER = 1;
    public static final byte RUNTIME_ERROR = 2;
    public static final byte COMPILATION_ERROR = 3;
    public static final byte EMPTY_ANSWER = 4;
    public static final byte TIME_LIMIT_EXCEEDED = 5;
    public static final byte WAITING = 6;
    public static final byte EMPTY_TEST_CASE = 7;
    public static final byte WRONG_FILE_NAME = 8;
    public static final byte PRESENTATION_ERROR = 9;
    public static final byte HUXLEY_ERROR = -1;
	
	private static final String FILESEPARATOR = System
			.getProperty("file.separator");
	

	private long id;
	private long version;
	private long problemId;
	private String submission;
	private byte evaluation;
	private Date submissionDate;
	private boolean detailedLog;
	private String diffFile;
	private long linguageId;
	private int tries;
	private String output;
	private long userId;
	private double time;
	private Problem problem;
	private Language language;
	private String inputTestCase;
    private String errorMsg;
    private long idTestCase;

	public Submission() {
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

    public void setIdTestCase(long idTestCase) {
        this.idTestCase = idTestCase;
    }

    public long getIdTestCase() {
        return idTestCase;
    }

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public long getProblemId() {
        if (problem!=null){
            return problem.getId();
        }

        return problemId;
	}

	public void setProblemId(long problemId) {
		this.problemId = problemId;
	}

	public String getSubmission() {
		return submission;
	}

	public void setSubmission(String submission) {
		this.submission = submission;
	}

	public byte getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(byte evaluation) {
		this.evaluation = evaluation;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public boolean isDetailedLog() {
		return detailedLog;
	}

	public void setDetailedLog(boolean detailedLog) {
		this.detailedLog = detailedLog;
	}

	public String getDiffFile() {
		return diffFile;
	}

	public void setDiffFile(String diffFile) {
		this.diffFile = diffFile;
	}

	public long getLanguageId() {
		return linguageId;
	}

	public void setLinguageId(long linguageId) {
		this.linguageId = linguageId;
	}

	public int getTries() {
		return tries;
	}

	public void setTries(int tries) {
		this.tries = tries;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return Long.toString(id);
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Language getLanguage() {
		return language;
	}
	
	public void setInputTestCase(String inputTestCase){
		this.inputTestCase = inputTestCase;
	}
	
	public String getInputTestCase(){
		return inputTestCase;
	}

	public void setEvaluationAsHuxleyError() {
		evaluation = HUXLEY_ERROR;
	}

	public void setEvaluationAsEmpyAnswer() {
		evaluation = EMPTY_ANSWER;

	}

	public void setEvaluationAsCompilationError() {
		evaluation = COMPILATION_ERROR;
	}

	public void setEvaluationAsRuntimeError() {
		evaluation = RUNTIME_ERROR;

	}

	public void setEvaluationAsTimeLimitExceeded() {
		evaluation = TIME_LIMIT_EXCEEDED;
	}

	public String getSubPath() {
		return Configurator.getProperty(Constants.CONF_FILENAME,"problemdb.dir")
				+ getProblemId() + FILESEPARATOR + getUserId() + FILESEPARATOR
				+ getLanguage().getName() + FILESEPARATOR + getTries()
				+ FILESEPARATOR;
	}

	public void setEvaluationAsEmptyTestCase() {
		evaluation = EMPTY_TEST_CASE;
	}

	public void setEvaluationAsCorrect() {
		evaluation = CORRECT;
	}

	public void setEvaluationAsWrongAnswer() {
		evaluation = WRONG_ANSWER;
	}

	public void setEvaluationAsWrongFileName() {
		evaluation = WRONG_FILE_NAME;
		
	}
	
	public void setEvaluationAsPresentationError() {
		evaluation = PRESENTATION_ERROR;
	}
	
	public boolean isEvaluationCorrect(){
		return CORRECT == evaluation;
	}

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Submission that = (Submission) o;

        if (detailedLog != that.detailedLog) return false;
        if (evaluation != that.evaluation) return false;
        if (id != that.id) return false;
        if (idTestCase != that.idTestCase) return false;
        if (linguageId != that.linguageId) return false;
        if (problemId != that.problemId) return false;
        if (Double.compare(that.time, time) != 0) return false;
        if (tries != that.tries) return false;
        if (userId != that.userId) return false;
        if (version != that.version) return false;
        if (diffFile != null ? !diffFile.equals(that.diffFile) : that.diffFile != null) return false;
        if (errorMsg != null ? !errorMsg.equals(that.errorMsg) : that.errorMsg != null) return false;
        if (inputTestCase != null ? !inputTestCase.equals(that.inputTestCase) : that.inputTestCase != null)
            return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (output != null ? !output.equals(that.output) : that.output != null) return false;
        if (problem != null ? !problem.equals(that.problem) : that.problem != null) return false;
        if (submission != null ? !submission.equals(that.submission) : that.submission != null) return false;
        if (submissionDate != null ? !submissionDate.equals(that.submissionDate) : that.submissionDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (version ^ (version >>> 32));
        result = 31 * result + (int) (problemId ^ (problemId >>> 32));
        result = 31 * result + (submission != null ? submission.hashCode() : 0);
        result = 31 * result + (int) evaluation;
        result = 31 * result + (submissionDate != null ? submissionDate.hashCode() : 0);
        result = 31 * result + (detailedLog ? 1 : 0);
        result = 31 * result + (diffFile != null ? diffFile.hashCode() : 0);
        result = 31 * result + (int) (linguageId ^ (linguageId >>> 32));
        result = 31 * result + tries;
        result = 31 * result + (output != null ? output.hashCode() : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        temp = Double.doubleToLongBits(time);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (problem != null ? problem.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (inputTestCase != null ? inputTestCase.hashCode() : 0);
        result = 31 * result + (errorMsg != null ? errorMsg.hashCode() : 0);
        result = 31 * result + (int) (idTestCase ^ (idTestCase >>> 32));
        return result;
    }
}
