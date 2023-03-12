package com.liteon.renewable_energy.controller.websocket;

import com.liteon.renewable_energy.DTO.TextMessageDTO;
import com.liteon.renewable_energy.model.Notification;
import com.liteon.renewable_energy.model.User;
import com.liteon.renewable_energy.repository.NotificationRepo;
import com.liteon.renewable_energy.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class WebSocketTextController {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    NotificationRepo notificationRepo;
    @Autowired
    UserRepo userRepo;

    @MessageMapping("/sendMessage") // /app/sendMessage
    @SendTo("/public/notification")
    public TextMessageDTO sendMessage(@Payload TextMessageDTO textMessageDTO) {

        notificationRepo.save(new Notification(null,textMessageDTO.getMessage(),new Date()));

        return textMessageDTO;
    }

    @MessageMapping("/privateMessage") // /app/privateMessage
    public TextMessageDTO privateMessage(@Payload TextMessageDTO textMessageDTO) {
        template.convertAndSendToUser(String.valueOf(textMessageDTO.getUserid()),"/notification", textMessageDTO);

        User user = userRepo.findUserById(textMessageDTO.getUserid());

        notificationRepo.save(new Notification(user== null? null : user.getId(),textMessageDTO.getMessage(),new Date()));

        return textMessageDTO;
    }
}