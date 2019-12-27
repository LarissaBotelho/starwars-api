package com.example.app.repositories;

import java.util.List;

//importação da extensão mongoRepository para fazer a ligação com o banco

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.app.documents.Mundo;

//interface publica que referencia o Mundo como tipo String

public interface MundoRepository extends MongoRepository<Mundo, String> {

	//lista para efetuar busca pelo nome
	List<Mundo> findByNome(String nome);

}
