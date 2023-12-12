package com.example.domains.entities;

import java.io.Serializable;
import java.util.Objects;

import com.example.domains.core.entities.EntityBase;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


/**
 * The persistent class for the film_text database table.
 * 
 */
@Entity
@Table(name="film_text")
@NamedQuery(name="Film.findAll", query="SELECT f FROM Film f")
public class Film extends EntityBase<Film> implements Serializable {
	private static final long serialVersionUID = 1L;
    public static class Partial {}
    public static class Complete extends Partial {}

	@Id
	@Column(name="film_id", unique=true, nullable=false)
	@JsonView(Film.Partial.class)
	private short filmId;

	@Lob
	@JsonView(Film.Complete.class)
	private String description;

	@Column(nullable=false, length=255)
	@NotBlank
	@Size(max = 255)
	@JsonView(Film.Partial.class)
	private String title;

	public Film() {
	}

	public Film(short filmId) {
		super();
		this.filmId = filmId;
	}

	public Film(short filmId, @NotBlank @Size(max = 255) String title, String description) {
		super();
		this.filmId = filmId;
		this.title = title;
		this.description = description;
	}

	public short getFilmId() {
		return this.filmId;
	}

	public void setFilmId(short filmId) {
		this.filmId = filmId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(filmId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		return filmId == other.filmId;
	}

	@Override
	public String toString() {
		return "FilmText [filmId=" + filmId + ", title=" + title + "]";
	}

}