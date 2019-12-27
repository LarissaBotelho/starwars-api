package com.example.app.services;

import java.util.List;
import java.util.Optional;

import com.example.app.documents.Mundo;

public interface MundoService {
	
	//função para listar todos os Mundos
	List<Mundo> listarTodos();
	
	//salva o planeta e retorna as aparições
	Mundo criarMundo(Mundo mundo);
	
	//listar por id
	Optional<Mundo> listarPorId(String id);
	
	//listar por nome
	List<Mundo> listarPorNome(String nome);
	
	//cadastrar um novo mundo
	Mundo cadastrar(Mundo mundo);
	
	//atualizar mundo existente
	Mundo atualizar(Mundo mundo);
	
	//remover mundo por id
	void remover(String id);


	
}
