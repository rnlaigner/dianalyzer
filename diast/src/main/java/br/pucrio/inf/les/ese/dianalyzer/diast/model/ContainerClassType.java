package br.pucrio.inf.les.ese.dianalyzer.diast.model;

public enum ContainerClassType {
	
	SPRING("ApplicationContext","ApplicationContextHolder.getApplicationContext()","getBean"),
	//Based on https://stackoverflow.com/questions/8166187/can-i-and-how-lookup-cdi-managed-beans-using-javax-naming-contextlookup-in-ej
	CDI("BeanManager","CDI.current().getBeanManager()","getReference");
	
	private String className;
	private String containerObtainedBy;
	private String containerCall;
	
	ContainerClassType(String className, String containerObtainedBy, String containerCall ) {
		this.className = className;
		this.containerObtainedBy = containerObtainedBy;
		this.containerCall = containerCall;
    }

	public String getClassName() {
		return className;
	}

	public String getContainerObtainedBy() {
		return containerObtainedBy;
	}

	public String getContainerCall() {
		return containerCall;
	}
	
}
