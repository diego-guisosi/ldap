package br.com.dixy.ldap.repository;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.dixy.ldap.domain.Person;
import br.com.dixy.ldap.repository.adapter.LdapAdapter;
import br.com.dixy.ldap.repository.adapter.LdapAdapterFactory;

@Repository
public class LdapPersonRepository {

	private static final Logger LOG = LoggerFactory.getLogger(LdapPersonRepository.class);

	@Autowired
	private LdapAdapterFactory ldapAdapterFactory;

	public List<Person> findAll() {
		try (LdapAdapter adapter = ldapAdapterFactory.getAdapter()) {
			NamingEnumeration<?> result = adapter.search("ou=people,o=DixyCorporation,dc=example,dc=com",
					"(objectClass=person)");
			return extractPeopleFrom(result);
		}
	}

	private List<Person> extractPeopleFrom(NamingEnumeration<?> result) {
		List<Person> people = new ArrayList<>();
		while (result.hasMoreElements()) {
			Person person = extractPersonFrom(result.nextElement());
			people.add(person);
		}
		return people;
	}

	private Person extractPersonFrom(Object element) {
		SearchResult searchResult = (SearchResult) element;
		Attributes attributes = searchResult.getAttributes();
		String commonName = getStringAttribute(attributes, "cn");
		String simpleName = getStringAttribute(attributes, "sn");
		return new Person(commonName, simpleName);
	}

	private String getStringAttribute(Attributes attributes, String attributeName) {
		try {
			return (String) attributes.get(attributeName).get();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new IllegalArgumentException("Unable to get attribute " + attributeName, e);
		}
	}

}
