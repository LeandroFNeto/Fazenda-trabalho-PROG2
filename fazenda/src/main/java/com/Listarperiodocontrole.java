/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.ProducaoLeite;
import util.Dao;

/**
 *
 * @author LEANDRO
 */
public class Listarperiodocontrole {
    
     @FXML
    private void voltamenu() throws IOException{
        App.setRoot("menu");
    }
    
    @FXML
    private DatePicker dateInicio; // DatePicker para selecionar a data inicial

    @FXML
    private DatePicker dateFim; // DatePicker para selecionar a data final

    @FXML
    private TableView<ProducaoLeite> tabelaProducao; // TableView para exibir as produções

    @FXML
    private TableColumn<ProducaoLeite, String> colunaData; // Coluna para exibir a data

    @FXML
    private TableColumn<ProducaoLeite, Double> colunaLitros; // Coluna para exibir os litros

    private Dao<ProducaoLeite> producaoDao;

    @FXML
    private void initialize() {
        producaoDao = new Dao<>(ProducaoLeite.class);
        configurarTabela();
    }

    private void configurarTabela() {
        // Configuração das colunas da tabela
        colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colunaLitros.setCellValueFactory(new PropertyValueFactory<>("litros"));
    }

    @FXML
    private void buscarPorPeriodo() {
        LocalDate inicio = dateInicio.getValue();
        LocalDate fim = dateFim.getValue();

        if (inicio == null || fim == null) {
            mostrarErro("Ambas as datas (início e fim) devem ser preenchidas.");
            return;
        }

        if (inicio.isAfter(fim)) {
            mostrarErro("A data de início não pode ser posterior à data final.");
            return;
        }

        try {
            // Busca todas as produções no banco de dados
            List<ProducaoLeite> producoes = producaoDao.listarTodos();
            ObservableList<ProducaoLeite> producoesFiltradas = FXCollections.observableArrayList();

            // Filtra as produções com base no período
            for (ProducaoLeite producao : producoes) {
                if (!producao.getData().isBefore(inicio) && !producao.getData().isAfter(fim)) {
                    producoesFiltradas.add(producao);
                }
            }

            // Atualiza a tabela com os dados filtrados
            tabelaProducao.setItems(producoesFiltradas);

        } catch (Exception e) {
            mostrarErro("Erro ao buscar as produções: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.show();
    }
}
