package br.com.dixy.ldap.repository.adapter;

import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LdapAdapterFactory {

	@Autowired
	private LdapAdapterConfiguration configuration;

	public LdapAdapter getAdapter() throws NamingException {
		Hashtable<String, Object> environment = configuration.getLdapsPooledEnvironment();
		InitialDirContext context = new InitialDirContext(environment);
		return new LdapAdapter(context);
	}

}
