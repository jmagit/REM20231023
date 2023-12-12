package com.example.domains.entities.dtos;

import org.springframework.beans.factory.annotation.Value;

import com.example.domains.entities.Film;

public interface InventoryDetails {
	int getInventoryId();
	@Value("#{target.film.title}")
	String getFilm();
	int getStoreId();
}
