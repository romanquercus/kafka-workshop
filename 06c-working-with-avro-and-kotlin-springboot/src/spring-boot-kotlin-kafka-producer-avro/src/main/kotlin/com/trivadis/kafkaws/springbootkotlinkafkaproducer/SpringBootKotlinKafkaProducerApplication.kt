package com.trivadis.kafkaws.springbootkotlinkafkaproducer

import com.trivadis.kafkaws.avro.v1.Notification
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.time.Instant
import java.time.LocalDateTime


@SpringBootApplication
class SpringBootKotlinKafkaProducerApplication{

	@Bean
	fun init(kafkaEventProducer: KafkaEventProducer) = CommandLineRunner { args ->
		val sendMessageCount = if (args.size==0) 100 else args[0].toInt()
		val waitMsInBetween = if (args.size==0) 10 else args[1].toLong()
		val id = if (args.size==0) 0 else args[2].toLong()

		for (index in 1 .. sendMessageCount) {
			val value = Notification.newBuilder()
								.setId(id)
								.setMessage("[$id" + "] Hello Kafka " + index + " => " + LocalDateTime.now())
								.setCreatedAt(Instant.now())
							.build()
			kafkaEventProducer.produce(id, value);

			// Simulate slow processing
			Thread.sleep(waitMsInBetween);
		}
	}
}

fun main(args: Array<String>) {
	runApplication<SpringBootKotlinKafkaProducerApplication>(*args)
}
