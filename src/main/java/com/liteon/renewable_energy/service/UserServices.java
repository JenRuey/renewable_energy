package com.liteon.renewable_energy.service;

import com.liteon.renewable_energy.model.User;
import com.liteon.renewable_energy.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.regex.Pattern;

@Service
public class UserServices {
    @Autowired
    private UserRepo userRepo;

    public Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User updateUser(User update) {
        update.setModified_datetime(new Date());
  
        return userRepo.save(update);
    }

    public User addUser(User add) {
        if (Pattern.compile("^(.+)@(\\S+)$").matcher(add.getEmail()).matches() && add.getHash_key() == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid request");
        }
        User duplicateuser = userRepo.findUserByEmail(add.getEmail());
        if(duplicateuser != null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Email registered.");
        }

        add.setModified_datetime(new Date());
        add.setId(0);
        add.setAccess_token(null);
        add.setRefresh_token(null);

        return userRepo.save(add);
    }

    public User deleteUser(int userid) {
        User deluser = userRepo.findUserById(userid);
        userRepo.delete(deluser);

        return deluser;
    }
}
