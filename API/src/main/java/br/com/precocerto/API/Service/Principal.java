package br.com.precocerto.API.Service;

import br.com.precocerto.API.model.DadosMarcaOuModelo;
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
        var marcas = conversor.obterLista(json, DadosMarcaOuModelo.class);

        marcas.stream()
                .sorted(Comparator.comparing(DadosMarcaOuModelo::codigo))
                .forEach(System.out::println);
    }
}
