release:
	mvn -DaltDeploymentRepository=repo::default::file:releases clean deploy

#mvn -DoutputDirectory=releases/lib dependency:copy-dependencies

