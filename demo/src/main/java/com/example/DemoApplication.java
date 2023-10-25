package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.support.DaoSupport;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("Aplicación arracada ...");
//		demosDatos();
	}
	
	@Autowired
//	ActorRepository dao;
	ActorService srv;
	
	private void demosDatos() {
//		var a = new Actor(0, "Pepito", "Grillo");
//		dao.save(a);
//		var item = dao.findById(201);
//		if(item.isPresent()) {
//			var a = item.get();
//			a.setFirstName(a.getFirstName().toUpperCase());
//			dao.save(a);
//		} else {
//			System.out.println("No encontrado");
//		}
//		dao.deleteById(201);
//		dao.findAll().forEach(System.out::println);
//		dao.findTop5ByFirstNameStartingWithOrderByLastNameDesc("P").forEach(System.out::println);
//		dao.findTop5ByFirstNameStartingWith("P", Sort.by("firstName").descending()).forEach(System.out::println);
//		dao.findByActorIdGreaterThanEqual(200).forEach(System.out::println);
//		dao.findNovedadesByJPQL(200).forEach(System.out::println);
//		dao.findNovedadesBySQL(200).forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.greaterThanOrEqualTo(root.get("actorId"), 200))
//			.forEach(System.out::println);
//		dao.findAll(PageRequest.of(0, 10, Sort.by("firstName", "lastName"))).forEach(System.out::println);
//		var item = dao.findById(1);
//		if(item.isPresent()) {
//			var a = item.get();
//			System.out.println(a);
////			a.getFilmActors().forEach(e -> System.out.println(e.getFilm().getTitle()));
//		} else {
//			System.out.println("No encontrado");
//		}
//		var a = new Actor(0, null, "4G");
//		if(a.isInvalid())
//			System.err.println(a.getErrorsMessage());
//		else
//			dao.save(a);
//		dao.findByActorIdGreaterThanEqual(200).forEach(e->System.out.println(ActorDTO.from(e)));
//		dao.readByActorIdGreaterThanEqual(200).forEach(System.out::println);
//		dao.queryByActorIdGreaterThanEqual(200).forEach(e->System.out.println(e.getId() + " - " + e.getNombre()));
//		dao.findAllBy(ActorDTO.class).forEach(System.out::println);
//		dao.findAllBy(ActorShort.class).forEach(e->System.out.println(e.getId() + " - " + e.getNombre()));
		srv.getByProjection(ActorShort.class).forEach(e->System.out.println(e.getId() + " - " + e.getNombre()));
		try {
			srv.modify(new Actor(9990, "KK", "4G"));
			srv.add(new Actor(0, null, "4G"));
		} catch (DuplicateKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@Transactional
	void transaccion() {
		
	}

}
