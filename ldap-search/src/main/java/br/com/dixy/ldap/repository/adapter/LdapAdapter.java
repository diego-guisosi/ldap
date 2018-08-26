package br.com.dixy.ldap.repository.adapter;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LdapAdapter implements AutoCloseable {

	private static final Logger LOG = LoggerFactory.getLogger(LdapAdapter.class);
	private static final int SEARCH_TIMEOUT_IN_MILLIS = 30000;

	private DirContext context;

	LdapAdapter(DirContext context) {
		this.context = context;
	}

	public NamingEnumeration<?> search(String name, String filter) {
		try {
			return context.search(name, filter, getControls());
		} catch (NamingException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(
					String.format("Unable to execute search with name=%s and filter=%s", name, filter), e);
		}
	}

	private SearchControls getControls() {
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchControls.setTimeLimit(SEARCH_TIMEOUT_IN_MILLIS);
		return searchControls;
	}

	@Override
	public void close() {
		closeQuietly();
	}

	private void closeQuietly() {
		try {
			this.context.close();
		} catch (Exception e) {
			LOG.error("Unable to close DirContext = " + context, e);
		}
	}

}
