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

import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;


@RepositoryRestResource(exported = false)
public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor> {
	List<Actor> findTop5ByFirstNameStartingWithOrderByLastNameDesc(String prefijo);
	List<Actor> findTop5ByFirstNameStartingWith(String prefijo, Sort orden);

	List<Actor> findByActorIdGreaterThanEqual(int idInical, Pageable paginado);

	List<Actor> findByActorIdGreaterThanEqual(int idInical);
	@Query(value = "from Actor a where actorId >= :id")
	List<Actor> findNovedadesByJPQL(@Param("id") int idInical);
	@Query(value = "select * from actor a where actor_id >= ?1", nativeQuery = true)
	List<Actor> findNovedadesBySQL(int idInical);

	List<ActorDTO> readByActorIdGreaterThanEqual(int idInical);
	List<ActorShort> queryByActorIdGreaterThanEqual(int idInical);

	<T> List<T> findAllBy(Class<T> tipo);
	<T> Iterable<T> findAllBy(Sort orden, Class<T> tipo);
	<T> Page<T> findAllBy(Pageable page, Class<T> tipo);

}
