package com.liteon.renewable_energy.controller.authentication;

import com.liteon.renewable_energy.DTO.GoogleLoginRequest;
import com.liteon.renewable_energy.model.User;
import com.liteon.renewable_energy.service.TokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/token")
public class TokenController {

    @Autowired
    private TokenServices tokenServices;

    @GetMapping("/list")
    public ResponseEntity<Iterable<User>> getUsers() {
        Iterable<User> allusers = tokenServices.getAllUser();
        return new ResponseEntity<>(allusers, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User login) {
        User successuser = tokenServices.obtainToken(login);
        return new ResponseEntity<>(successuser, HttpStatus.OK);
    }

    @GetMapping("/renew")
    public ResponseEntity<User> renew(@RequestParam String token) {
        User newtoken = tokenServices.refreshToken(token);
        return new ResponseEntity<>(newtoken, HttpStatus.OK);
    }

    @PostMapping("/googlelogin")
    public ResponseEntity<User> googlelogin(@RequestBody GoogleLoginRequest req) {
        User successuser = tokenServices.googlelogin(req);
        return new ResponseEntity<>(successuser, HttpStatus.OK);
    }
}
