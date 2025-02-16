package com.duoc.SendSignalKafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSenderService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "senales_vitales";

    public void sendMessage(String message){
        kafkaTemplate.send(TOPIC,message);
    }

}
