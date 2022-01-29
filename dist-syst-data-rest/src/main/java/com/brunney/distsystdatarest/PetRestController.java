package com.brunney.distsystdatarest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/petops")
public class PetRestController {

    @Autowired
    private PetJPARepository petJPA; //Dependency Injection

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PetUserJPARepository petUserJPA;

    @PostMapping(path="/newPetUser")
    @ResponseStatus(code= HttpStatus.CREATED)
    public void createPetUser(@RequestBody PetUser user) {


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        petUserJPA.save(user);
    }

    @GetMapping(path="/getByAgeGreaterThan")
    public List<Pet> queryPetsByAgeGreaterThan(@RequestParam int age, HttpServletRequest httpReq) {

        return petJPA.findByAgeGreaterThan(age);
    }

}
