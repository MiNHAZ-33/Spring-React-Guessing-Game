package com.airen.guessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.airen.guessing.model.UserInfo;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

    @Query("SELECT c FROM UserInfo c WHERE c.userName = ?1")
    List<UserInfo> findByUsername(String userName);
}

