package com.zanoshky.currencyfair.controller;

import com.zanoshky.currencyfair.common.model.VolumeMessage;
import com.zanoshky.currencyfair.model.Message;
import com.zanoshky.currencyfair.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @GetMapping("/volume-messages/{lastProcessedId:[\\d]+}")
    public List<VolumeMessage> getVolumeMessagesSinceLastProcessedId(@PathVariable("lastProcessedId") final long lastProcessedId) {
        return messageRepository.findAllOnlyVolumeInfo(lastProcessedId);
    }

    @PostMapping("/message")
    public Message createMessage(@Valid @RequestBody final Message message) {
        return messageRepository.save(message);
    }

}
