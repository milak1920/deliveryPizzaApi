package com.esliceu.deliveryPizza.Repository;

import com.esliceu.deliveryPizza.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role findRoleByName(String name);
}

