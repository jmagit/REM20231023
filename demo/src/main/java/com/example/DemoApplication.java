package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorShort;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@EnableDiscoveryClient
@EnableFeignClients("com.example.application.proxies")
@OpenAPIDefinition(
        info = @Info(title = "Microservicio: Demos",  version = "1.0",
                description = "**Demos** de Microservicios.",
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(name = "Javier Martín", url = "https://github.com/jmagit", email = "support@example.com")
        ),
        externalDocs = @ExternalDocumentation(description = "Documentación del proyecto", url = "https://github.com/jmagit/curso")
)
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
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
	
	@Bean 
	@Primary
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	@Bean 
	@LoadBalanced
	public RestTemplate restTemplateLB(RestTemplateBuilder builder) {
		return builder.build();
	}

//	@Bean
//	public OpenApiCustomiser sortSchemasAlphabetically() {
//	    return openApi -> {
//	        var schemas = openApi.getComponents().getSchemas();
//	        openApi.getComponents().setSchemas(new TreeMap<>(schemas));
//	    };
//	}
	
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
