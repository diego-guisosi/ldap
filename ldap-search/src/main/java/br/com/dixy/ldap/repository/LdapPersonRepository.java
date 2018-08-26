package br.com.dixy.ldap.repository;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
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
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException("Unable to find all people", e);
		}
	}

	private List<Person> extractPeopleFrom(NamingEnumeration<?> result) throws NamingException {
		List<Person> people = new ArrayList<>();
		while (result.hasMoreElements()) {
			Person person = extractPersonFrom(result.nextElement());
			people.add(person);
		}
		return people;
	}

	private Person extractPersonFrom(Object element) throws NamingException {
		SearchResult searchResult = (SearchResult) element;
		Attributes attributes = searchResult.getAttributes();
		String commonName = (String) attributes.get("cn").get();
		String simpleName = (String) attributes.get("sn").get();
		return new Person(commonName, simpleName);
	}

}
