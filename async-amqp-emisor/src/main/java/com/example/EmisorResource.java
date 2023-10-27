package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmisorResource {
	private List<MessageDTO> respuestas = new ArrayList<>();

	@Value("${spring.application.name}:${server.port}")
	private String origen;

	@Autowired
	private AmqpTemplate amqp;
	@Autowired
	private DirectExchange exchange;

	@GetMapping(path = "/saludo/{nombre}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String saluda(@PathVariable String nombre) {
		String msg = "Hola " + nombre;
		amqp.convertAndSend("demo.saludos", new MessageDTO(msg, origen));
		return "SEND: " + msg;
	}

	@GetMapping(path = "/multienvio/{cantidad}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String multienvio(@PathVariable int cantidad) {
		for(int i=0; i < cantidad; i++) {
			amqp.convertAndSend("demo.saludos", new MessageDTO("Envio nº: " + i, origen));
		}
		return "Enviados: " + cantidad;
	}


	@GetMapping(path = "/x-rpc/respuestas")
	public List<MessageDTO> respuestas() {
		return respuestas;
	}

	private void procesaRespuesta(Object response) {
		System.err.println("Respuesta recibida: " + response);
		respuestas.add((MessageDTO) response);
	}
	/*
	@Autowired
	private AsyncRabbitTemplate amqpAsync;

	@GetMapping(path = "/x-rpc/solicita/{nombre}")
	public String solicita(@PathVariable String nombre) {
		String msg = "Hola " + nombre + " (con respuesta)";
//		new Thread(() -> procesaRespuesta(
//				amqp.convertSendAndReceive(exchange.getName(), "solicitud", new MessageDTO(msg, origen)))).run();
		var reply = amqp.convertSendAndReceive(exchange.getName(), "solicitud", new MessageDTO(msg, origen));
//		var reply = amqp.receive(exchange.getName(), 2000);
		if(reply == null) {
			return "SEND: " + msg + " [sin respuesta]";
		}
		procesaRespuesta(reply);
		return "SEND: " + msg + " [recibida respuesta]";
	}


	@GetMapping(path = "/x-rpc/solicita-async/{nombre}")
	public String solicitaAsync(@PathVariable String nombre) {
		String msg = "Hola " + nombre + " (con respuesta)";
		amqpAsync.convertSendAndReceive(exchange.getName(), "solicitud", new MessageDTO(msg, origen))
				.thenAccept(result -> procesaRespuesta(result))
				.exceptionally(ex -> {
					System.out.println(ex.getMessage()); return null;
				});
		return "SEND: " + msg + " (esperando respuesta)";
	}
*/
	@Autowired
	private AsyncRabbitTemplate amqpAsync;

	@GetMapping(path = "/x-rpc/solicita/{nombre}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String solicita(@PathVariable String nombre) {
		String msg = "Hola " + nombre;
		amqpAsync.convertSendAndReceive(exchange.getName(), "solicitud", new MessageDTO(msg, origen))
				.thenAccept(result -> procesaRespuesta(result))
				.exceptionally(ex -> {
					System.out.println(ex.getMessage()); return null;
				});
		return "SEND: " + msg + " (esperando respuesta)";
	}

}
