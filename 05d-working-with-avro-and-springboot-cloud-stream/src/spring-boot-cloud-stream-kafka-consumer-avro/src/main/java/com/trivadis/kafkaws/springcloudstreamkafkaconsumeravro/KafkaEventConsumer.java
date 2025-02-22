package com.trivadis.kafkaws.springcloudstreamkafkaconsumeravro;

import com.trivadis.kafkaws.avro.v1.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaEventConsumer.class);

    @StreamListener(Processor.INPUT)
    public void receive(Message<Notification> msg) {
        Notification value = msg.getPayload();
        Long key = (Long)msg.getHeaders().get(KafkaHeaders.RECEIVED_MESSAGE_KEY);
        LOGGER.info("received key = '{}' with payload='{}'", key, value);
    }

}
