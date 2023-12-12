package com.example;

import java.io.Serializable;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
public class DomainEventService {

	@Data @AllArgsConstructor
	public class MessageDTO implements Serializable {
		private static final long serialVersionUID = 1L;
		private String evento;
		private String origen;
		private int id;
	}

	private static final Logger LOG = LoggerFactory.getLogger(DomainEventService.class);
	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaBootstraServers;
	@Value("${topic.name}")
	private String topic;
	@Value("${sensor.id}")
	private String key;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private ObjectMapper objectMapper = new ObjectMapper();
	
	public void sendAdd(String entity, int id) {
		try {
			String json = objectMapper.writeValueAsString(new MessageDTO("add", entity, id));
			sendMessage(json);
		} catch (JsonProcessingException e) {
			LOG.error("Error send add", e);
		}
	}

	public void sendModify(String entity, int id) {
		try {
			String json = objectMapper.writeValueAsString(new MessageDTO("modify", entity, id));
			sendMessage(json);
		} catch (JsonProcessingException e) {
			LOG.error("Error send modify", e);
		}
	}

	public void sendDelete(String entity, int id) {
		try {
			String json = objectMapper.writeValueAsString(new MessageDTO("delete", entity, id));
			sendMessage(json);
		} catch (JsonProcessingException e) {
			LOG.error("Error send delete", e);
		}
	}

	private void sendMessage(String message) {
		kafkaTemplate.send(topic, key, message)
			.thenAccept(result -> LOG.info(String.format("TOPIC: %s, KEY: %s, MESSAGE: %s, OFFSET: %s", topic, key, message,
					result.getRecordMetadata().offset())))
			.exceptionally(ex -> {
				LOG.error(String.format("TOPIC: %s, KEY: %s, MESSAGE: %s, ERROR: %s", topic, key, message,
						ex.getMessage())); 
				return null;
			});
	}
}
