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
    
    /*
     * TODO:
     * This Rest Controller has to be extended if an group sorting logic will 
     * enable to reconsider Psychological Profile. 
     * Currently due to amount of work it is not set but is prepared to be added.
     *  
     */
}
