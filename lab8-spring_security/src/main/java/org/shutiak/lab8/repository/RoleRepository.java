package org.shutiak.lab8.repository;

import org.shutiak.lab8.enums.Role;
import org.shutiak.lab8.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    List<RoleEntity> findAll();
    RoleEntity findByRole(final Role role);
}

