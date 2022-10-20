package ch.zhaw.springboot.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.springboot.repositories.PersonRepository;

@RestController
@CrossOrigin
public class PersonRestController {
    @Autowired
    private PersonRepository repository;
}
