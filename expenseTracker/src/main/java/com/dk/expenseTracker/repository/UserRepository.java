package com.dk.expenseTracker.repository;

import com.dk.expenseTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "select u from user u where user.email =:email", nativeQuery = true) // query which runs on sql
    public User findByEmailAddress(String email);

    @Query(value = "select u from User u where u.email =:email") // query which runs on hibernate uses class or model names
    public User findByEmailAddressJPQL(String email); // it is a good practice to use JPQL query, you can see the error in the application itself but it is not in the case of nativeQuery

    //we don't even have to write the queries -> standard ways by which we can simply write methods and we don't need to write queries

    public User findByEmail(String email);
    // search user data where name starts with A

    public User findByNameLike(String pattern); //->jpa will write query for us.

    public User findByCreatedAtGreaterThanAndNameLike(Date CreateAt, String name);
}
// native query is something which runs on sql, which do not run by hibernate
// if we are writing native query, we have to tell the native query should be true.
// Sequence is managed by hibernate, but Identity is managed by the native sql.