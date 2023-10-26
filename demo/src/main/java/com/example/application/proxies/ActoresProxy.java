package com.example.application.proxies;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;


//@HttpExchange(url = "/actores/v1", accept = "application/json", contentType = "application/json")
public interface ActoresProxy {
	public record ActorShort(@JsonProperty("actorId") int id, @Schema(description = "Nombre del actor") String nombre) {}
	public record ActorEdit(int id, String nombre, String apellidos) {}
	
	@GetExchange
	List<ActorShort> getAll();
	@GetExchange("/{id}")
	ActorEdit getOne(@PathVariable int id);
	@PostExchange
	ResponseEntity<ActorEdit> add(@RequestBody ActorEdit item);
	@PutExchange("/{id}")
	void change(@PathVariable int id, @RequestBody ActorEdit item);
	@DeleteExchange("/{id}")
	void delete(@PathVariable int id);
}
