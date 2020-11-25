package com.thehuxley.data.model.database;

/** 
 * @author Marcio Augusto Guimarães
 * @author Romero Malaquias
 * @version 1.1.0
 * @since huxley-avaliador 1.0.0
 */
public class Language {
	
	private long id;
	private long version;
	private String execParams;
	private String plagConfig;
	private String name;
	private String compileParams;
	private String compile;
	private String script;
	private String extension;
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getExecParams() {
		return execParams;
	}

	public void setExecParams(String execParams) {
		this.execParams = execParams;
	}

	public String getPlagConfig() {
		return plagConfig;
	}

	public void setPlagConfig(String plagConfig) {
		this.plagConfig = plagConfig;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompileParams() {
		return compileParams;
	}

	public void setCompileParams(String compileParams) {
		this.compileParams = compileParams;
	}

	public String getCompile() {
		return compile;
	}

	public void setCompile(String compile) {
		this.compile = compile;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
	
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((compile == null) ? 0 : compile.hashCode());
		result = prime * result
				+ ((compileParams == null) ? 0 : compileParams.hashCode());
		result = prime * result
				+ ((execParams == null) ? 0 : execParams.hashCode());
		result = prime * result
				+ ((extension == null) ? 0 : extension.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((plagConfig == null) ? 0 : plagConfig.hashCode());
		result = prime * result + ((script == null) ? 0 : script.hashCode());
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
		Language other = (Language) obj;
		if (compile == null) {
			if (other.compile != null)
				return false;
		} else if (!compile.equals(other.compile))
			return false;
		if (compileParams == null) {
			if (other.compileParams != null)
				return false;
		} else if (!compileParams.equals(other.compileParams))
			return false;
		if (execParams == null) {
			if (other.execParams != null)
				return false;
		} else if (!execParams.equals(other.execParams))
			return false;
		if (extension == null) {
			if (other.extension != null)
				return false;
		} else if (!extension.equals(other.extension))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (plagConfig == null) {
			if (other.plagConfig != null)
				return false;
		} else if (!plagConfig.equals(other.plagConfig))
			return false;
		if (script == null) {
			if (other.script != null)
				return false;
		} else if (!script.equals(other.script))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
	
	
	
}
