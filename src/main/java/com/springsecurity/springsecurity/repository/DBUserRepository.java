package com.springsecurity.springsecurity.repository;

import com.springsecurity.springsecurity.model.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBUserRepository extends JpaRepository<DBUser, Long> {

    public DBUser findByUsername(String username);
}
