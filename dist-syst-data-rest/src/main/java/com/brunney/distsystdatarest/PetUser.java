package com.brunney.distsystdatarest;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class PetUser {

    @Id
    private String username;
    private String password;
}
