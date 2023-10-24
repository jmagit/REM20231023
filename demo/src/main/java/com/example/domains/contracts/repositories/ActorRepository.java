package com.example.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.domains.entities.Actor;


public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor> {
	List<Actor> findTop5ByFirstNameStartingWithOrderByLastNameDesc(String prefijo);
	List<Actor> findTop5ByFirstNameStartingWith(String prefijo, Sort orden);

	List<Actor> findByActorIdGreaterThanEqual(int idInical, Pageable paginado);

	List<Actor> findByActorIdGreaterThanEqual(int idInical);
	@Query(value = "from Actor a where actorId >= :id")
	List<Actor> findNovedadesByJPQL(@Param("id") int idInical);
	@Query(value = "select * from actor a where actor_id >= ?1", nativeQuery = true)
	List<Actor> findNovedadesBySQL(int idInical);
	
}
