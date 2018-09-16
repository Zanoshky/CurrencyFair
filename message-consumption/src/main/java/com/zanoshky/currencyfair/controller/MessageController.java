package com.zanoshky.currencyfair.controller;

import com.zanoshky.currencyfair.common.model.VolumeMessage;
import com.zanoshky.currencyfair.model.Message;
import com.zanoshky.currencyfair.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/messages")
    public List<Message> getAllTrades() {
        return messageRepository.findAll();
    }

    @GetMapping("/volume-messages/{lastProcessedId}")
    public List<VolumeMessage> getVolumeTrades(@PathVariable("lastProcessedId") final long lastProcessedId) {
        return messageRepository.findAllOnlyVolumeInfo(lastProcessedId);
    }

    @PostMapping("/message")
    public Message createTrade(@Valid @RequestBody final Message message) {
        return messageRepository.save(message);
    }

}
