package ch.zhaw.springboot.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.springboot.repositories.PsychoProfileRepository;

@RestController
@CrossOrigin
public class PsychoProfileRestController {
    @Autowired
    private PsychoProfileRepository repository;
}
