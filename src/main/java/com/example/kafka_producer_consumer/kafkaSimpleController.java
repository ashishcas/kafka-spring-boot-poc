package com.example.kafka_producer_consumer;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo/kafka")
public class kafkaSimpleController {

    private KafkaTemplate<String , String> kafkaTemplate;
    private Gson jsonConverter;
    @Autowired
    public kafkaSimpleController(KafkaTemplate<String, String> kafkaTemplate, Gson jsonConverter){
            this.kafkaTemplate = kafkaTemplate;
            this.jsonConverter = jsonConverter;
    }

    @PostMapping(value = "/")
    public void post(@RequestBody  ModelKafka ModelKafka){
        kafkaTemplate.send("myTopic2",jsonConverter.toJson(ModelKafka));
    }

    @KafkaListener(topics ="myTopic2")
    public void getFromKafka(String modelKafka){
        System.out.println(modelKafka.toString());

        ModelKafka simpleModel1 = (ModelKafka) jsonConverter.fromJson(modelKafka, ModelKafka.class);
        System.out.println(simpleModel1.toString());
    }
}
