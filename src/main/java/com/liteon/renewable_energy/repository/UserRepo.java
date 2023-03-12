package com.liteon.renewable_energy.repository;

import com.liteon.renewable_energy.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {

    User findUserByEmail(String email);

    @Query(value = "select * from \"user\" where email = ?1 and hash_key = ?2", nativeQuery = true)
    User findUserByEmailPassword(String email, String hashkey);

    @Query(value = "select * from \"user\" where access_token = ?1", nativeQuery = true)
    User findUserByAccessToken(String accesstoken);

    @Query(value = "select * from \"user\" where refresh_token = ?1", nativeQuery = true)
    User findUserByRefreshToken(String token);

    User findUserById(int parseInt);
}
