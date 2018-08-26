package br.com.dixy.ldap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dixy.ldap.domain.Person;
import br.com.dixy.ldap.repository.LdapPersonRepository;

@Service
public class PersonService {

	@Autowired
	private LdapPersonRepository ldapPersonRepository;

	public List<Person> findAll() {
		return ldapPersonRepository.findAll();
	}

}
