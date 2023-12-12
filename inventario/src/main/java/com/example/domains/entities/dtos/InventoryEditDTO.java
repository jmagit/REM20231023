package com.example.domains.entities.dtos;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import com.example.domains.core.entities.EntityBase;
import com.example.domains.entities.Inventory;


@Data @AllArgsConstructor
public class InventoryEditDTO  {
	private int inventoryId;
	private short filmId;
	private int storeId;
	
	public static InventoryEditDTO from(Inventory source) {
		return new InventoryEditDTO(
				source.getInventoryId(),
				source.getFilm().getFilmId(),
				source.getStoreId()
				);
	}
	
	public static Inventory from(InventoryEditDTO source) {
		return new Inventory(
				source.getInventoryId(),
				source.getFilmId(),
				source.getStoreId()
				);
	}
	
}