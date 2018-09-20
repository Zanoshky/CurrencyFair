package com.zanoshky.currencyfair.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zanoshky.currencyfair.common.dto.VolumeMessage;
import com.zanoshky.currencyfair.repository.MessageRepository;

@RestController
@RequestMapping("/api")
public class VolumeMessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/volume-message/{volumeMessageId:[\\d]+}")
    public VolumeMessage getVolumeMessageById(@PathVariable("volumeMessageId") final long volumeMessageId) {
        return messageRepository.findVolumeMessageById(volumeMessageId);
    }

    @GetMapping("/volume-messages")
    public List<VolumeMessage> getAllVolumeMessage() {
        return messageRepository.findAllVolumeMessages();
    }

    @GetMapping("/volume-messages/above/{lastProcessedId:[\\d]+}")
    public List<VolumeMessage> getVolumeMessagesAboveId(@PathVariable("lastProcessedId") final long lastProcessedId) {
        return messageRepository.findAllVolumeMessagesAboveId(lastProcessedId);
    }
}
