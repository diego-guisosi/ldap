package br.com.dixy.ldap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dixy.ldap.domain.Person;
import br.com.dixy.ldap.service.PersonService;

@RestController
@RequestMapping(value = "/people")
public class PersonController {

	@Autowired
	private PersonService personService;

	@GetMapping
	public List<Person> getPeople() {
		return personService.findAll();
	}

}
