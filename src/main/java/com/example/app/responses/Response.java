package com.example.app.responses;

import java.util.List;

public class Response<T> {
	
	private T data;
	private List<String> erros;
	
	//construtor que recebe qualquer dado
	public Response(T data) {
		this.data = data;
}
	
	//construtor que recebe lista de erros
	
	public Response(List<String> erros) {
		this.erros = erros;
	}

	//getters e setters

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}
}
	
	
