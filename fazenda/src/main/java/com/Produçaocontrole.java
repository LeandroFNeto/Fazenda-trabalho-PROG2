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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import modelo.ProducaoLeite;
import modelo.Vaca;
import util.Dao;
/**
 *
 * @author LEANDRO
 */
public class Produçaocontrole {
    @FXML
    private void voltamenu() throws IOException{
        App.setRoot("menu");
    }
    
    
    @FXML
    private ComboBox<String> comboBrinco; // ComboBox para listar os brincos das vacas

    @FXML
    private DatePicker datePicker; // DatePicker para selecionar a data

    @FXML
    private TextField campoLitros; // TextField para registrar os litros de leite

    private Dao<Vaca> vacaDao; // DAO para buscar vacas
    private Dao<ProducaoLeite> producaoDao; // DAO para registrar a produção

    @FXML
    private void initialize() {
        vacaDao = new Dao<>(Vaca.class);
        producaoDao = new Dao<>(ProducaoLeite.class);
        carregarBrincos();
    }

    private void carregarBrincos() {
        try {
            List<Vaca> vacas = vacaDao.listarTodos();
            ObservableList<String> brincos = FXCollections.observableArrayList();
            for (Vaca vaca : vacas) {
                brincos.add(vaca.getBrinco());
            }
            comboBrinco.setItems(brincos);
        } catch (Exception e) {
            mostrarErro("Erro ao carregar brincos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void salvarProducao() {
        String brinco = comboBrinco.getValue();
        LocalDate data = datePicker.getValue();
        String litrosStr = campoLitros.getText();

        if (brinco == null || data == null || litrosStr.isBlank()) {
            mostrarErro("Todos os campos são obrigatórios.");
            return;
        }

        try {
            double litros = Double.parseDouble(litrosStr);
            if (litros <= 0) {
                mostrarErro("A quantidade de litros deve ser maior que zero.");
                return;
            }

            // Cria um registro de produção
            ProducaoLeite producao = new ProducaoLeite(brinco, data, litros);

            // Salva no banco de dados
            producaoDao.inserir(producao);

            mostrarSucesso("Produção registrada com sucesso!");
            limparCampos();
        } catch (NumberFormatException e) {
            mostrarErro("O campo de litros deve conter um valor numérico.");
        } catch (Exception e) {
            mostrarErro("Erro ao salvar produção: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        comboBrinco.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        campoLitros.clear();
    }

    private void mostrarSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.show();
    }
    
    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.show();
    }
}
