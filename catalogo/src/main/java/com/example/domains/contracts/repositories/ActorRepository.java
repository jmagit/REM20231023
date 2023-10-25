package com.example.domains.contracts.repositories;

import java.util.List;
import java.sql.Timestamp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domains.core.contracts.repositories.RepositoryWithProjections;
import com.example.domains.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer>, RepositoryWithProjections {
	List<Actor> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
}
