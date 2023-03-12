package com.liteon.renewable_energy.controller.websocket;

import com.liteon.renewable_energy.DTO.StationDataResponse;
import com.liteon.renewable_energy.DTO.TextMessageDTO;
import com.liteon.renewable_energy.model.Event;
import com.liteon.renewable_energy.model.History;
import com.liteon.renewable_energy.model.Notification;
import com.liteon.renewable_energy.model.User;
import com.liteon.renewable_energy.repository.EventRepo;
import com.liteon.renewable_energy.repository.HistoryRepo;
import com.liteon.renewable_energy.repository.NotificationRepo;
import com.liteon.renewable_energy.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;

@RestController
@RequestMapping("api/notification")
public class NotificationController {
    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    NotificationRepo notificationRepo;
    @Autowired
    UserRepo userRepo;

    @Autowired
    HistoryRepo historyRepo;
    @Autowired
    EventRepo eventRepo;

    @PostMapping("/send")
    public ResponseEntity<Void> send(@RequestBody TextMessageDTO textMessageDTO) {
        template.convertAndSend("/public/notification", textMessageDTO);

        notificationRepo.save(new Notification(null, textMessageDTO.getMessage(), new Date()));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/private-send")
    public ResponseEntity<Void> privateSend(@RequestBody TextMessageDTO textMessageDTO) {
        template.convertAndSendToUser(String.valueOf(textMessageDTO.getUserid()), "/notification", textMessageDTO);

        User user = userRepo.findUserById(textMessageDTO.getUserid());

        notificationRepo.save(new Notification(user == null ? null : user.getId(), textMessageDTO.getMessage(), new Date()));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/station-trigger-record")
    public ResponseEntity<Void> triggerStation(@RequestBody History history) {
        Event event = eventRepo.findByTagname(history.getEvent_tagname());
        if (event == null)
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Tagname.");

        History hist = new History();
        hist.setDatetime(history.getDatetime());
        hist.setEvent_tagname(event.getTagname());
        hist.setValue(history.getValue());
        historyRepo.save(hist);

        template.convertAndSend("/public/trend", history);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
