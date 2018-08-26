package br.com.dixy.ldap.repository.adapter;

import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LdapAdapterFactory {

	private static final Logger LOG = LoggerFactory.getLogger(LdapAdapterFactory.class);

	@Autowired
	private LdapAdapterConfiguration configuration;

	public LdapAdapter getAdapter() {
		try {
			InitialDirContext context = accessDirContext();
			return new LdapAdapter(context);
		} catch (NamingException e) {
			LOG.error(e.getMessage(), e);
			throw new IllegalStateException("Unable to reach LDAP server", e);
		}
	}

	private InitialDirContext accessDirContext() throws NamingException {
		Hashtable<String, Object> environment = configuration.getLdapsPooledEnvironment();
		InitialDirContext context = new InitialDirContext(environment);
		return context;
	}

}
