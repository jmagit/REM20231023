package com.example.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.domains.entities.Contacto;

public interface ContactoRepository extends MongoRepository<Contacto, String> {
	List<Contacto> findByConflictivoTrue();
	<T> List<T> searchTop20ByNombreContainsOrderByNombreAsc(String fragmento, Class<T> tipo);
	<T> List<T> findAllBy(Class<T> tipo);
}
