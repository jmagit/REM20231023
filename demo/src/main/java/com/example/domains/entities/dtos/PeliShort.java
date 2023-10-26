package com.example.domains.entities.dtos;

import org.springframework.data.rest.core.config.Projection;

import com.example.domains.entities.Film;

@Projection(name = "peli", types = Film.class)
public interface PeliShort {
	int getFilmId();
	String getTitle();
}
