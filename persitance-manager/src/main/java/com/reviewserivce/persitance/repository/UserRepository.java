package com.reviewserivce.persitance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reviewserivce.persitance.db.objects.DBUser;

public interface UserRepository extends JpaRepository<DBUser, String> {
    
    public List<DBUser> findByEmail(String email);
    
    public List<DBUser> findByUserName(String userName);
}
