package org.shutiak.lab8.repository;

import org.shutiak.lab8.model.BookEntity;
import org.shutiak.lab8.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<BookEntity> {
    List<UserEntity> findAll();
    Optional<UserEntity> findByLogin(final String login);
    boolean existsByLogin(final String login);

}
