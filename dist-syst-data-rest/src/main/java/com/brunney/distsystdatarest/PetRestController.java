package com.brunney.distsystdatarest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/petops")
public class PetRestController {

    @Autowired
    private PetJPARepository petJPA; //Dependency Injection

    @GetMapping(path="/getByAgeGreaterThan")
    public List<Pet> queryPetsByAgeGreaterThan(@RequestParam int age, HttpServletRequest httpReq) {

        return petJPA.findByAgeGreaterThan(age);
    }

}
