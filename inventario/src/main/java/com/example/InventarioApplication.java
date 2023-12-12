package com.example;

import java.io.Serializable;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.example.application.proxies.CatalogoProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.Data;

@OpenAPIDefinition(
        info = @Info(
                title = "Microservicio: Inventario de peliculas",
                version = "1.0",
                description = "Ejemplo de Microservicio utilizando la base de datos **Sakila**.",
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(name = "Javier Martín", url = "https://github.com/jmagit", email = "support@example.com")
        ),
        externalDocs = @ExternalDocumentation(description = "Documentación del proyecto", url = "https://github.com/jmagit/REM20231023")
)
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@EnableFeignClients(basePackages = "com.example.application.proxies")
//@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class InventarioApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(InventarioApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("Instancia arrancada");
	}

    @Bean
    OpenApiCustomizer sortSchemasAlphabetically() {
        return openApi -> {
            var schemas = openApi.getComponents().getSchemas();
            openApi.getComponents().setSchemas(new TreeMap<>(schemas));
        };
    }
	@Data
	public static class MessageDTO implements Serializable {
		private static final long serialVersionUID = 1L;
		private String evento;
		private String origen;
		private int id;
	}

	private static final Logger LOG = LoggerFactory.getLogger(InventarioApplication.class);
	private ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	CatalogoProxy proxi;
	
	@KafkaListener(topics = "${topic.name}", topicPattern = "${topic.name}")
	public void listenWithHeaders(@Header(KafkaHeaders.RECEIVED_KEY) String key, @Payload String message,
			@Header(KafkaHeaders.OFFSET) String offset) throws JsonMappingException, JsonProcessingException {
		var event = objectMapper.readValue(message, MessageDTO.class);
		if(!"Film".equals(event.getOrigen())) return;
		switch (event.getEvento()) {
		case "add":
			var nuevo = proxi.getPeli(event.getId());
			System.err.println("Añadiendo: " + nuevo.toString());
			break;
		case "modify":
			var modificado = proxi.getPeli(event.getId());
			System.err.println("Modificando: " + modificado.toString());
			break;
		case "delete":
			System.err.println("Borrado: " + event.getId());
			break;
		}
	}


}
