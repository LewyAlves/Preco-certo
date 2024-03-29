package br.com.precocerto.API.Service;

import br.com.precocerto.API.model.Dados;
import br.com.precocerto.API.model.Modelos;
import br.com.precocerto.API.model.Veiculo;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    ConsumoApi consumo = new ConsumoApi();
    Scanner reader = new Scanner(System.in);
    ConverteDados conversor = new ConverteDados();
    String url = "https://parallelum.com.br/fipe/api/v1/";

    public void Menu() throws JsonProcessingException {
        System.out.println("""
                *************************
                        Bem vindo
                *************************
                Digite o tipo de veiculo
                que deseja consultar.
                
                Carro
                Caminhão
                Moto
                
                **************************
                       Tabela FIPE
                **************************
                """);
        var escolhaDoVeiculo = reader.nextLine();
        String endereco;

        if (escolhaDoVeiculo.toLowerCase().contains("car")) {
            endereco = url + "carros/marcas";
        } else if (escolhaDoVeiculo.toLowerCase().contains("mo")) {
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
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);
        reader.nextLine();

        System.out.println("\n Digite um trecho do modelo selecionado");
        String modeloSelecionado = reader.nextLine();

        List<Dados> filtroModelo= modeloLista.modelos().stream()
                        .filter(m -> m.nome().toLowerCase().contains(modeloSelecionado.toLowerCase()))
                                .collect(Collectors.toList());

        System.out.println("\n filtro: ");
        filtroModelo.forEach(System.out::println);

        System.out.println("\n Digite o codigo para visualizar as versões de anos do modelo desejado");
        var versao = reader.nextInt();

        endereco = endereco + "/" + versao + "/anos";

        json = consumo.ObeterDados(endereco);

        List<Dados> anos = conversor.obterLista(json, Dados.class);

        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.ObeterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }
        veiculos.stream().filter(v -> v.ano() <= 2024)
                .forEach(System.out::println);
    }
}
