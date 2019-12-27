package com.example.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.documents.Mundo;
import com.example.app.events.RecursoCriadoEvent;
import com.example.app.repositories.MundoRepository;
import com.example.app.responses.Response;
import com.example.app.services.MundoService;

@RestController
@RequestMapping(path = "/api")
public class MundoController{

	@Autowired
	private MundoService mundoService;
	
	@Autowired
	MundoRepository mundoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//requisição tipo get
	@GetMapping(path = "/mundos")
	public ResponseEntity<Response<List<Mundo>>> listarTodos() {
		return ResponseEntity.ok(new Response<List<Mundo>>(this.mundoService.listarTodos()));
	}
	
	//retornar por id
	//id é o valor dinamico
	//requisição tipo get
	@GetMapping(path = "mundos/id/{id}")
	public ResponseEntity<Response<Optional<Mundo>>> listarPorId(@PathVariable(name = "id") String id) {
		return ResponseEntity.ok(new Response<Optional<Mundo>>(this.mundoService.listarPorId(id)));
	}
	
	@GetMapping(path = "mundos/nome/{nome}")
	public ResponseEntity<Response<List<Mundo>>> listarPorNome(@PathVariable(name = "nome") String nome) {
		return ResponseEntity.ok(new Response<List<Mundo>>(this.mundoService.listarPorNome(nome)));
	}
	
	//requisição tipo post
//	@PostMapping(path = "/mundos")
//	public ResponseEntity<Response<Mundo>> cadastrar(@Valid @RequestBody Mundo mundo, BindingResult result) {
//	if(result.hasErrors()) {
//		List<String> erros = new ArrayList<String>();
//		result.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
//		return ResponseEntity.badRequest().body(new Response<Mundo>(erros));
//	}
//		
//	
//		return ResponseEntity.ok(new Response<Mundo>(this.mundoService.cadastrar(mundo)));
//	}
	
	
	//requisição tipo put para atualizar
	@PutMapping(path = "mundos/id/{id}")
	public ResponseEntity<Response<Mundo>> atualizar(@PathVariable(name = "id") String id, 
			@Valid @RequestBody Mundo mundo, BindingResult resultado) {
		if(resultado.hasErrors()) {
			//gera uma lista de erros
			List<String> erros = new ArrayList<String>();
			//para cada um dos erros encontrados, será adicionado na lista
			resultado.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
			//informa erro 400 e a lista para o usuario
			return ResponseEntity.badRequest().body(new Response<Mundo>(erros));
		}
		
		mundo.setId(id);
		//informa o mundo já atualizado
		return ResponseEntity.ok(new Response<Mundo>(this.mundoService.atualizar(mundo)));
	}
	
	//metodo para remoção por id
	@DeleteMapping(path = "mundos/id/{id}")
	public ResponseEntity<Response<Integer>> remover(@PathVariable(name = "id") String id) {
		this.mundoService.remover(id);
		return ResponseEntity.ok(new Response<Integer>(1));
	}
	
	//requisição tipo post com validação da api para retornar as aparições
	@PostMapping(path = "/mundos")
	public ResponseEntity<Mundo> saveMundo(@Valid @RequestBody Mundo mundo, HttpServletResponse response) {
		Mundo mundoSalvo = mundoService.criarMundo(mundo);
		if (mundoSalvo != null) {
			publisher.publishEvent(new RecursoCriadoEvent(this, response, mundo.getId()));
			return ResponseEntity.status(HttpStatus.CREATED).body(mundoSalvo);
		} else {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}

	}
	
	
}
