package vk.kolosov.insidejwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import vk.kolosov.insidejwt.model.Message;
import vk.kolosov.insidejwt.model.MessageModel;
import vk.kolosov.insidejwt.service.LocalService;

import java.util.List;

@RestController
public class LocalController {

    @Autowired
    LocalService localService;

    @GetMapping("/hello")  //it's work
    public ResponseEntity hello() {
        return ResponseEntity.ok("hello");
    }

    @GetMapping("/m/getall")
    public ResponseEntity getMessages(@AuthenticationPrincipal UserDetails currentUser) {
        return ResponseEntity.ok(localService.findAllMessagesByUserInfoName(currentUser.getUsername()));
    }

    @PostMapping("/m/save")
    public ResponseEntity saveMessage(@AuthenticationPrincipal UserDetails currentUser, @RequestParam String message) {

        if (message.startsWith("history") && message.length() < 12) {
            List<Message> messageList = localService.findAllMessagesByUserInfoName(currentUser.getUsername());

            String[] parse = message.split(" ");
            int finalcount = Integer.parseInt(parse[1]);

            if (finalcount > messageList.size())
                finalcount = messageList.size();

            List<Message> resultList = messageList.subList(messageList.size() - finalcount, messageList.size());

            return ResponseEntity.ok(resultList);
        }

        localService.saveMessage(currentUser.getUsername(), message);
        return ResponseEntity.ok("Your message saved");
    }

    @PostMapping("/mbody/save")
    public ResponseEntity saveMessage2(@AuthenticationPrincipal UserDetails currentUser, @RequestBody MessageModel message) {

        if (message.getMessage().startsWith("history") && message.getMessage().length() < 12) {
            List<Message> messageList = localService.findAllMessagesByUserInfoName(currentUser.getUsername());

            String[] parse = message.getMessage().split(" ");
            int finalcount = Integer.parseInt(parse[1]);

            if (finalcount > messageList.size())
                finalcount = messageList.size();

            List<Message> resultList = messageList.subList(messageList.size() - finalcount, messageList.size());

            return ResponseEntity.ok(resultList);
        }

        localService.saveMessage(currentUser.getUsername(), message.getMessage());
        return ResponseEntity.ok("Your message saved");
    }

}
