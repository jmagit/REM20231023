package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import com.example.domains.core.entities.EntityBase;


/**
 * The persistent class for the inventory database table.
 * 
 */
@Entity
@Table(name="inventory")
@NamedQuery(name="Inventory.findAll", query="SELECT i FROM Inventory i")
public class Inventory extends EntityBase<Inventory> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="inventory_id", unique=true, nullable=false)
	private int inventoryId;

	@Column(name="last_update", nullable=false, insertable = false, updatable = false)
	private Timestamp lastUpdate;

	//bi-directional many-to-one association to Film
	@ManyToOne
	@JoinColumn(name="film_id", nullable=false)
	@NotNull
	private Film film;

	@Column(name = "store_id", nullable=false)
	@NotNull
	private int storeId;

	public Inventory() {
	}

	public Inventory(int inventoryId, @NotNull Film film, @NotNull int storeId) {
		super();
		this.inventoryId = inventoryId;
		this.film = film;
		this.storeId = storeId;
	}

	public Inventory(int inventoryId, short filmId, @NotNull int storeId) {
		super();
		this.inventoryId = inventoryId;
		this.film = new Film(filmId);
		this.storeId = storeId;
	}

	public int getInventoryId() {
		return this.inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public Film getFilm() {
		return this.film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public void setFilm(short filmId) {
		this.film = new Film(filmId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(inventoryId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		return inventoryId == other.inventoryId;
	}


}