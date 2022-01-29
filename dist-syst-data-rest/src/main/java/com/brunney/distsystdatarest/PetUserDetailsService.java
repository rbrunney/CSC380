package com.brunney.distsystdatarest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PetUserDetailsService implements UserDetailsService {

    @Autowired
    private PetUserJPARepository petUserJPA;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return petUserJPA.findById(username).orElse(null);
    }
}
