package com.thehuxley.data.model.database;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thehuxley.data.Configurator;
import com.thehuxley.data.Constants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Problem {
		private long id;
		private long version;
		private int timeLimit;
		private int evaluationDetail;
		private int code;
		private int level;
		private double nd;
		private String name;
		private String status;
		private long userApprovedId;
		private long userSuggestId;
		private double fastestSubmissionTime;

		public long getId() {
			return id;
		}
		
		public void setId(long id) {
			this.id = id;
		}
		
		public long getVersion() {
			return version;
		}
		
		public void setVersion(long version) {
			this.version = version;
		}
		
		public int getTimeLimit() {
			return timeLimit;
		}
		
		public void setTimeLimit(int timeLimit) {
			this.timeLimit = timeLimit;
		}
		
		public int getEvaluationDetail() {
			return evaluationDetail;
		}
		
		public void setEvaluationDetail(int evaluationDetail) {
			this.evaluationDetail = evaluationDetail;
		}
		
		public int getCode() {
			return code;
		}
		
		public void setCode(int code) {
			this.code = code;
		}
						
		public int getLevel() {
			return level;
		}
		
		public void setLevel(int level) {
			this.level = level;
		}
		
		public double getNd() {
			return nd;
		}
		
		public void setNd(double nd) {
			this.nd = nd;
		}
						
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}			
		
		public String getStatus() {
			return status;
		}
		
		public void setStatus(String status) {
			this.status = status;
		}
		
		public long getUserApprovedId() {
			return userApprovedId;
		}
		
		public void setUserApprovedId(long userApprovedId) {
			this.userApprovedId = userApprovedId;
		}
		
		public long getUserSuggestId() {
			return userSuggestId;
		}
		
		public void setUserSuggestId(long userSuggestId) {
			this.userSuggestId = userSuggestId;
		}
				
		public String mountProblemRoot(){  
			  return Configurator.getProperty(Constants.CONF_FILENAME,"problemdb.dir") + this.id + System.getProperty("file.separator");
		}
		
		public String getInput(){
			return mountProblemRoot()+"input.in";
		}
		
		public String getOutput(){
			return mountProblemRoot()+"output.in";
		}
		
		public double getFastestSubmissionTime() {
			return fastestSubmissionTime;
		}

		public void setFastestSubmissionTime(double fastestSubmissionTime) {
			this.fastestSubmissionTime = fastestSubmissionTime;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + code;
			result = prime * result + evaluationDetail;
			long temp;
			temp = Double.doubleToLongBits(fastestSubmissionTime);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + (int) (id ^ (id >>> 32));
			result = prime * result + level;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			temp = Double.doubleToLongBits(nd);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result
					+ ((status == null) ? 0 : status.hashCode());
			result = prime * result + timeLimit;
			result = prime * result
					+ (int) (userApprovedId ^ (userApprovedId >>> 32));
			result = prime * result
					+ (int) (userSuggestId ^ (userSuggestId >>> 32));
			result = prime * result + (int) (version ^ (version >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Problem other = (Problem) obj;
			if (code != other.code)
				return false;
			if (evaluationDetail != other.evaluationDetail)
				return false;
			if (Double.doubleToLongBits(fastestSubmissionTime) != Double
					.doubleToLongBits(other.fastestSubmissionTime))
				return false;
			if (id != other.id)
				return false;
			if (level != other.level)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (Double.doubleToLongBits(nd) != Double
					.doubleToLongBits(other.nd))
				return false;
			if (status == null) {
				if (other.status != null)
					return false;
			} else if (!status.equals(other.status))
				return false;
			if (timeLimit != other.timeLimit)
				return false;
			if (userApprovedId != other.userApprovedId)
				return false;
			if (userSuggestId != other.userSuggestId)
				return false;
			if (version != other.version)
				return false;
			return true;
		}
		
		
}
