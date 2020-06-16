package com.jee.messagingapp.service;

import com.jee.messagingapp.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    <S extends Role>S save(S r);

    Role findByName(String name);
}
