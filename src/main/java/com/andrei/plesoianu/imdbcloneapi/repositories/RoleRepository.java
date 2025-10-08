package com.andrei.plesoianu.imdbcloneapi.repositories;

import com.andrei.plesoianu.imdbcloneapi.enums.AppRole;
import com.andrei.plesoianu.imdbcloneapi.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
