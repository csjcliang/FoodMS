package com.example.rjgcproject.dao;

import com.example.rjgcproject.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer>{
    @Query(value = "select * from loginuser where username=:username and password=:password",nativeQuery = true)
    User findUserByUsernameAndPassword(String username,String password);
    User save(User user);
}

