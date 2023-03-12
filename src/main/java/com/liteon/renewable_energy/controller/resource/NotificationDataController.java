package com.liteon.renewable_energy.controller.resource;

import com.liteon.renewable_energy.model.Notification;
import com.liteon.renewable_energy.model.User;
import com.liteon.renewable_energy.service.NotificationDataServices;
import com.liteon.renewable_energy.service.TokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth-notification")
public class NotificationDataController {
    @Autowired
    private NotificationDataServices notificationDataServices;

    @GetMapping("/list")
    public ResponseEntity<Iterable<Notification>> getAllNotifications(@RequestHeader("Authorization") String token) {
        Iterable<Notification> allNotif = notificationDataServices.getAllNotifications(token);
        return new ResponseEntity<>(allNotif, HttpStatus.OK);
    }
}
