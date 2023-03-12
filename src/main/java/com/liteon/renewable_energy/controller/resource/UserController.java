package com.liteon.renewable_energy.controller.resource;

import com.liteon.renewable_energy.DTO.GoogleLoginRequest;
import com.liteon.renewable_energy.model.Notification;
import com.liteon.renewable_energy.model.User;
import com.liteon.renewable_energy.service.NotificationDataServices;
import com.liteon.renewable_energy.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth-user")
public class UserController {
    @Autowired
    private UserServices userServices;

    @GetMapping("/list")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> allusers = userServices.getAllUsers();
        return new ResponseEntity<>(allusers, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User update) {
        User updateuser = userServices.updateUser(update);
        return new ResponseEntity<>(updateuser, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User add) {
        User adduser = userServices.addUser(add);
        return new ResponseEntity<>(adduser, HttpStatus.OK);
    }

    @GetMapping("/delete")
    public ResponseEntity<User> deleteUser(@RequestParam int userid) {
        User adduser = userServices.deleteUser(userid);
        return new ResponseEntity<>(adduser, HttpStatus.OK);
    }
}
