package com.esliceu.deliveryPizza.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Role {
    @Id
    private  Integer id;
    private  String name;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<Person> person;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getUsers() {
        return person;
    }

    public void setUsers(Set<Person> users) {
        this.person = users;
    }
}
