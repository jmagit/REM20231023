package com.example.application.resources;

import java.util.List;
import java.util.Optional;

import org.springdoc.core.converters.models.PageableAsQueryParam;
//import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@RestController
@Tag(name = "peliculas-service", description = "Mantenimiento de peliculas")
@RequestMapping(path = "/peliculas/v1")
public class FilmResource {
	@Autowired
	private FilmService srv;

	@Operation(
			summary = "Listado de las peliculas", 
			description = "Recupera la lista de peliculas en formato corto o detallado, se puede paginar.", 
			parameters = {
					@Parameter(in = ParameterIn.QUERY, name = "mode", required = false, description = "Formato de las peliculas", schema = @Schema(type = "string", allowableValues = {
					"details", "short" }, defaultValue = "short")) },
			responses = {
					@ApiResponse(responseCode = "200", description = "OK", content = 
							@Content(mediaType = "application/json", schema = @Schema(anyOf = {Film.class, Film.class}) ))
			})
	@GetMapping(params = { "page", "mode=details" })
	@PageableAsQueryParam
	@Transactional
//	@JsonView(Film.Complete.class)
	public Page<Film> getAllDetailsPage(@Parameter(hidden = true) Pageable pageable) {
		return srv.getAll(pageable);
	}

	@Hidden
	@GetMapping(params = { "page", "mode=short" })
	@Transactional
//	@JsonView(Film.Partial.class)
	public Page<Film> getAllShortPage(@Parameter(hidden = true) Pageable pageable) {
		return srv.getAll(pageable);
	}

	@Hidden
	@GetMapping
//	@JsonView(Film.Partial.class)
	public List<Film> getAll(@RequestParam(defaultValue = "short") String mode) {
		return srv.getAll();
	}

	@Hidden
	@GetMapping(params = "mode=details")
//	@JsonView(Film.Complete.class)
	public List<Film> getAllDetails() {
		return srv.getAll();
	}

	@GetMapping(path = "/{id}", params = "mode=short")
//	@JsonView(Film.Partial.class)
	public Film getOneCorto(
			@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable short id,
			@Parameter(required = false, allowEmptyValue = true, schema = @Schema(type = "string", allowableValues = {
					"details", "short" }, defaultValue = "short")) @RequestParam(required = false, defaultValue = "short") String mode)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return rslt.get();
	}

	@GetMapping(path = "/{id}", params = "mode=details")
//	@JsonView(Film.Partial.class)
	public Film getOneDetalle(
			@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable short id,
			@Parameter(required = false, schema = @Schema(type = "string", allowableValues = { "details", "short",
					"edit" }, defaultValue = "edit")) @RequestParam(required = false, defaultValue = "edit") String mode)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return rslt.get();
	}

}
