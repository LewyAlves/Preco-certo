package br.com.precocerto.API.Service;

import br.com.precocerto.API.model.DadosMarca;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    ConsumoApi api = new ConsumoApi();
    Scanner reader = new Scanner(System.in);
    String address = "https://parallelum.com.br/fipe/api/v1/";

    public void Menu() throws JsonProcessingException {
        System.out.println("""
                O que você deseja buscar?
                carros
                caminhoes
                motos
                """);
        var escolhaDoVeiculo = reader.nextLine();
        var json = api.ObeterDados(address + escolhaDoVeiculo + "/marcas");

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<DadosMarca> marcas = mapper.readValue(json, new TypeReference<List<DadosMarca>>() {
            });
            for (DadosMarca marca: marcas){
                System.out.println("Codigo: " + marca.codigo() + " Nome: " + marca.nome());
            }
        } catch (JsonProcessingException e){
            throw new RuntimeException("Deu erro");
        }
    }
}
