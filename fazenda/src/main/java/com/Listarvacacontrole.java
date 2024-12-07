/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.ProducaoLeite;
import modelo.Vaca;
import util.Dao;

/**
 *
 * @author LEANDRO
 */
public class Listarvacacontrole {
    
     @FXML
    private void voltamenu() throws IOException{
        App.setRoot("menu");
    }
    
     @FXML
    private ComboBox<String> comboBrinco; // ComboBox para selecionar o brinco da vaca

    @FXML
    private TableView<ProducaoLeite> tabelaProducao; // TableView para exibir produções

    @FXML
    private TableColumn<ProducaoLeite, String> colunaData; // Coluna para exibir a data

    @FXML
    private TableColumn<ProducaoLeite, Double> colunaLitros; // Coluna para exibir os litros

    private Dao<Vaca> vacaDao; // DAO para vacas
    private Dao<ProducaoLeite> producaoDao; // DAO para produções

    @FXML
    private void initialize() {
        vacaDao = new Dao<>(Vaca.class);
        producaoDao = new Dao<>(ProducaoLeite.class);

        configurarTabela();
        carregarBrincos();
    }

    private void configurarTabela() {
        // Configuração das colunas da tabela
        colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colunaLitros.setCellValueFactory(new PropertyValueFactory<>("litros"));
    }

    private void carregarBrincos() {
        try {
            List<Vaca> vacas = vacaDao.listarTodos();
            ObservableList<String> brincos = FXCollections.observableArrayList();
            for (Vaca vaca : vacas) {
                brincos.add(vaca.getBrinco());
            }
            comboBrinco.setItems(brincos);

            // Listener para carregar a produção ao selecionar o brinco
            comboBrinco.setOnAction(event -> carregarProducao());
        } catch (Exception e) {
            mostrarErro("Erro ao carregar brincos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void carregarProducao() {
        String brincoSelecionado = comboBrinco.getValue();

        if (brincoSelecionado == null) {
            mostrarErro("Selecione uma vaca para exibir a produção.");
            return;
        }

        try {
            // Busca todas as produções da vaca selecionada
            List<ProducaoLeite> producoes = producaoDao.listarTodos();
            ObservableList<ProducaoLeite> producoesFiltradas = FXCollections.observableArrayList();

            // Filtra as produções pelo brinco
            for (ProducaoLeite producao : producoes) {
                if (producao.getBrinco().equals(brincoSelecionado)) {
                    producoesFiltradas.add(producao);
                }
            }

            // Atualiza a tabela com os dados filtrados
            tabelaProducao.setItems(producoesFiltradas);

        } catch (Exception e) {
            mostrarErro("Erro ao carregar a produção: " + e.getMessage());
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
