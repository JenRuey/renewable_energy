package com.liteon.renewable_energy.service;

import com.liteon.renewable_energy.model.Notification;
import com.liteon.renewable_energy.model.User;
import com.liteon.renewable_energy.repository.NotificationRepo;
import com.liteon.renewable_energy.repository.UserRepo;
import com.liteon.renewable_energy.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationDataServices {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private NotificationRepo notificationRepo;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public Iterable<Notification> getAllNotifications(String token) {
        String jwtToken = token.substring(7);
        String email = jwtTokenUtil.getUsernameFromToken(jwtToken);

        User user = userRepo.findUserByEmail(email);

        return notificationRepo.findAllByUserID(user == null ? 0 : user.getId());
    }
}
