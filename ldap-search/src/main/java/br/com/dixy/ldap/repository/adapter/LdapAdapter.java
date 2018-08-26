package br.com.dixy.ldap.repository.adapter;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

public class LdapAdapter implements AutoCloseable {

	private DirContext context;

	LdapAdapter(DirContext context) {
		this.context = context;
	}

	@Override
	public void close() throws Exception {
		this.context.close();
	}

	public NamingEnumeration<?> search(String name, String filter) throws NamingException {
		return context.search(name, filter, getControls());
	}

	private SearchControls getControls() {
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchControls.setTimeLimit(30000);
		return searchControls;
	}

}
