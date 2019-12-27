package com.example.app.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.app.documents.Mundo;
import com.example.app.repositories.MundoRepository;
import com.example.app.services.MundoService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

//notação service para que a classe possa ser adicionada a outros componentes
@Service
public class MundoServiceImpl implements MundoService {
	
	//instancia do repositório
	@Autowired
	private MundoRepository mundoRepository;
	
	//url da api para buscar os dados
	private static final String SWAPI_URL = "https://swapi.co/api/planets/?search=";
	
	public Mundo criarMundo(Mundo mundo) {
		Integer qtd = this.obterAparicoes(mundo.getNome());
		if (qtd == null) {
			return null;
		}
		mundo.setAparicoes(qtd);
		return mundoRepository.save(mundo);
	}
	
	private Integer obterAparicoes(String name) {
		StringBuilder completeUrl = new StringBuilder();
		completeUrl.append(SWAPI_URL).append(name);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

		Object object;
		try {
			object = restTemplate.exchange(completeUrl.toString(), HttpMethod.GET,
					new HttpEntity<String>("parameters", headers), Object.class);
		} catch (Exception e) {
			return null;
		}
		
		Gson gson = new Gson();
		JsonArray results = gson.fromJson(gson.toJson(object), JsonObject.class).getAsJsonObject("body")
				.getAsJsonArray("results");

		JsonElement correctResult = null;

		for (JsonElement e : results) {
			if (e.getAsJsonObject().get("name").getAsString().equalsIgnoreCase(name)) {
				correctResult = e;
			}
		}

		if (correctResult == null) {
			return 0;
		} else {
			return correctResult.getAsJsonObject().getAsJsonArray("films").size();
		}
	}

	
	//metodo para listar todos os mundos cadastrados
	@Override
	public List<Mundo> listarTodos() {
		return this.mundoRepository.findAll();
	}

	//metodo para listar os mundos cadastrados bucando pelo id
	@Override
	public Optional<Mundo> listarPorId(String id) {
		return this.mundoRepository.findById(id);
	}

	//metodo para listar os mundos cadastrados pelo nome
	@Override
	public List<Mundo> listarPorNome(String nome) {
		return this.mundoRepository.findByNome(nome);
	}

	//metodo para cadastrar um novo mundo
	@Override
	public Mundo cadastrar(Mundo mundo) {
		return this.mundoRepository.save(mundo);
	}

	//metodo para atualizar um mundo existente
	@Override
	public Mundo atualizar(Mundo mundo) {
		return this.mundoRepository.save(mundo);
	}

	//metodo para apagar mundo existente pelo id
	@Override
	public void remover(String id) {
		this.mundoRepository.deleteById(id);

	}


}
