package com.example.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;


@RepositoryRestResource(path="idiomas", itemResourceRel="idioma", collectionResourceRel="idiomas")
public interface IdiomasRepository extends JpaRepository<Language, Integer>, JpaSpecificationExecutor<Actor> {
	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);
}
