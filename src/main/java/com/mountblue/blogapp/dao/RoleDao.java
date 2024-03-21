package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Integer> {
    Role findRoleByName(String roleName);
}
