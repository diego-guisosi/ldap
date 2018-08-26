package br.com.dixy.ldap.repository.adapter;

import java.util.Hashtable;

import javax.naming.directory.DirContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LdapAdapterConfiguration {

	@Value("${ldap.environment.hostnameLdap}")
	private String hostnameLdap;

	@Value("${ldap.environment.hostnameLdaps}")
	private String hostnameLdaps;

	@Value("${ldap.environment.username}")
	private String username;

	@Value("${ldap.environment.password}")
	private String password;

	@Value("${ldap.environment.trustStore}")
	private String trustStore;

	@Value("${ldap.environment.connect.pool.protocol}")
	private String connectPoolProtocol;

	@Value("${ldap.environment.connect.pool.authentication}")
	private String connectPoolAuthentication;

	@Value("${ldap.environment.connect.pool.initsize}")
	private String connectPoolInitSize;

	@Value("${ldap.environment.connect.pool.maxsize}")
	private String connectPoolMaxSize;

	@Value("${ldap.environment.connect.pool.prefsize}")
	private String connectPoolPrefSize;

	@Value("${ldap.environment.connect.pool.timeout}")
	private String connectPoolTimeout;

	@Value("${ldap.environment.connect.pool.debug}")
	private String connectPoolDebugLevel;

	public Hashtable<String, Object> getLdapEnvironment() {
		return environmentOf(hostnameLdap);
	}

	public Hashtable<String, Object> getLdapPooledEnvironment() {
		Hashtable<String, Object> env = getLdapEnvironment();
		setupPoolConfiguration(env);
		return env;
	}

	public Hashtable<String, Object> getLdapsEnvironment() {
		System.setProperty("javax.net.ssl.trustStore", trustStore);
		System.setProperty("javax.net.debug", "ssl");
		return environmentOf(hostnameLdaps);
	}

	public Hashtable<String, Object> getLdapsPooledEnvironment() {
		Hashtable<String, Object> env = getLdapsEnvironment();
		setupPoolConfiguration(env);
		return env;
	}

	private void setupPoolConfiguration(Hashtable<String, Object> env) {
		env.put("com.sun.jndi.ldap.connect.pool", "true");
		System.setProperty("com.sun.jndi.ldap.connect.pool.protocol", connectPoolProtocol);
		System.setProperty("com.sun.jndi.ldap.connect.pool.authentication", connectPoolAuthentication);
		System.setProperty("com.sun.jndi.ldap.connect.pool.initsize", connectPoolInitSize);
		System.setProperty("com.sun.jndi.ldap.connect.pool.maxsize", connectPoolMaxSize);
		System.setProperty("com.sun.jndi.ldap.connect.pool.prefsize", connectPoolPrefSize);
		System.setProperty("com.sun.jndi.ldap.connect.pool.timeout", connectPoolTimeout);
		System.setProperty("com.sun.jndi.ldap.connect.pool.debug", connectPoolDebugLevel);
	}

	private Hashtable<String, Object> environmentOf(String hostname) {
		Hashtable<String, Object> env = new Hashtable<>();
		env.put(DirContext.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(DirContext.PROVIDER_URL, hostname);
		env.put(DirContext.SECURITY_AUTHENTICATION, "simple");
		env.put(DirContext.SECURITY_PRINCIPAL, username);
		env.put(DirContext.SECURITY_CREDENTIALS, password);
		return env;
	}

}
