package br.com.precocerto.API.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface IConversor {

    <T> T obterDados(String json, Class<T> tClass) throws JsonProcessingException;

    <T> List<T> obterLista(String json, Class<T> tClass);
}
