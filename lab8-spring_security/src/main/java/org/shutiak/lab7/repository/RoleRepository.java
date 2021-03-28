package org.shutiak.lab7.repository;

import org.shutiak.lab7.enums.Role;
import org.shutiak.lab7.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    List<RoleEntity> findAll();
    RoleEntity findByRole(final Role role);
}

