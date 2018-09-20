package com.zanoshky.currencyfair.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zanoshky.currencyfair.model.Message;
import com.zanoshky.currencyfair.repository.MessageRepository;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/message/{messageId:[\\d]+}")
    public Message getMessage(@PathVariable("messageId") final long messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @PostMapping("/message")
    public Message createMessage(@Valid @RequestBody final Message message) {
        return messageRepository.save(message);
    }

}
