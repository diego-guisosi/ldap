package br.com.dixy.ldap.domain;

public class Person {

	private String commonName;
	private String simpleName;

	public Person(String commonName, String simpleName) {
		this.commonName = commonName;
		this.simpleName = simpleName;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

}
