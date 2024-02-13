package br.com.precocerto.API.Service;

import br.com.precocerto.API.model.Dados;
import br.com.precocerto.API.model.Modelos;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Comparator;
import java.util.Scanner;

public class Principal {

    ConsumoApi consumo = new ConsumoApi();
    Scanner reader = new Scanner(System.in);
    ConverteDados conversor = new ConverteDados();
    String url = "https://parallelum.com.br/fipe/api/v1/";

    public void Menu() throws JsonProcessingException {
        System.out.println("""
                *************************
                O que você deseja buscar?
                Carro
                Caminhão
                Moto
                **************************
                """);
        var escolhaDoVeiculo = reader.nextLine();
        String endereco;

        if (escolhaDoVeiculo.toLowerCase().contains("car")) {
            endereco = url + "carros/marcas";
        } else if (escolhaDoVeiculo.toLowerCase().contains("m")) {
            endereco = url + "motos/marcas";
        } else {
            endereco = url + "caminhoes/marcas";
        }

        var json = consumo.ObeterDados(endereco);
        var marcas = conversor.obterLista(json, Dados.class);

        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\n Digite o codigo do modelo desejado");
        var modelo = reader.nextInt();

        endereco = endereco + "/" + modelo  + "/modelos";

        json = consumo.ObeterDados(endereco);

        var modeloLista = conversor.obterDados(json, Modelos.class);

        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

    }
}
