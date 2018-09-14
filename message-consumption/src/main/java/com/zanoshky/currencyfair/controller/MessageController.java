package com.zanoshky.currencyfair.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zanoshky.currencyfair.common.model.VolumeMessage;
import com.zanoshky.currencyfair.model.Message;
import com.zanoshky.currencyfair.repository.MessageRepository;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/messages")
    public List<Message> getAllTrades() {
        return messageRepository.findAll();
    }

    @GetMapping("/volume-messages")
    public List<VolumeMessage> getVolumeTrades() {
        return messageRepository.findAllOnlyVolumeInfo();
    }

    @PostMapping("/trade")
    public Message createTrade(@Valid @RequestBody final Message message) {
        return messageRepository.save(message);
    }

}
