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
import javafx.scene.control.TextField;
import modelo.Vaca;
import util.Dao;

/**
 *
 * @author LEANDRO
 */
public class Alterarvacacontrole {
    
   
    
    @FXML
    private ComboBox<String> comboBrinco; // ComboBox para listar os brincos

    @FXML
    private TextField campoNome; // Campo para mostrar e editar o nome

    @FXML
    private TextField campoRaca; // Campo para mostrar e editar a raça

    private Dao<Vaca> dao;

    @FXML
    private void initialize() {
        dao = new Dao<>(Vaca.class);
        carregarBrincos(); // Preenche a ComboBox com os brincos
    }

    private void carregarBrincos() {
        try {
            // Busca todas as vacas no banco de dados
            List<Vaca> vacas = dao.listarTodos();
            ObservableList<String> brincos = FXCollections.observableArrayList();

            // Adiciona os brincos na lista observável
            for (Vaca vaca : vacas) {
                brincos.add(vaca.getBrinco());
            }

            // Configura a ComboBox com a lista de brincos
            comboBrinco.setItems(brincos);

            // Adiciona um listener para detectar mudanças na seleção
            comboBrinco.setOnAction(event -> selecionarVaca());
        } catch (Exception e) {
            mostrarErro("Erro ao carregar os brincos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void selecionarVaca() {
        String brincoSelecionado = comboBrinco.getValue();

        if (brincoSelecionado != null) {
            try {
                // Busca os dados da vaca pelo brinco
                Vaca vaca = dao.buscarPorChave("brinco", brincoSelecionado);

                if (vaca != null) {
                    // Preenche os campos com os dados da vaca
                    campoNome.setText(vaca.getNome());
                    campoRaca.setText(vaca.getRaca());
                }
            } catch (Exception e) {
                mostrarErro("Erro ao buscar a vaca: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void salvarAlteracoes() {
        String brinco = comboBrinco.getValue();
        String nome = campoNome.getText();
        String raca = campoRaca.getText();

        if (brinco == null || nome.isBlank() || raca.isBlank()) {
            mostrarErro("Todos os campos são obrigatórios para salvar as alterações.");
            return;
        }

        try {
            // Cria o objeto Vaca atualizado
            Vaca vaca = new Vaca(brinco, nome, raca);

            // Atualiza a vaca no banco de dados
            dao.alterar("brinco", brinco, vaca);

            mostrarSucesso("Dados da vaca alterados com sucesso!");
            limparCampos();
            carregarBrincos(); // Atualiza a ComboBox após salvar
        } catch (Exception e) {
            mostrarErro("Erro ao salvar alterações: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void Menu() throws IOException {
        App.setRoot("menu");
    }

    private void limparCampos() {
        campoNome.clear();
        campoRaca.clear();
        comboBrinco.getSelectionModel().clearSelection();
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
    
    @FXML
private void excluirVaca() {
    String brincoSelecionado = comboBrinco.getValue();

    if (brincoSelecionado == null) {
        mostrarErro("Selecione uma vaca para excluir.");
        return;
    }

    try {
        // Confirmação de exclusão (opcional)
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Excluir Vaca");
        alert.setContentText("Tem certeza que deseja excluir a vaca com o brinco: " + brincoSelecionado + "?");

        if (alert.showAndWait().get().getButtonData().isCancelButton()) {
            return;
        }

        // Exclui a vaca do banco de dados
        dao.excluir("brinco", brincoSelecionado);
        mostrarSucesso("Vaca excluída com sucesso!");

        // Atualiza os campos e a lista de brincos
        limparCampos();
        carregarBrincos();
    } catch (Exception e) {
        mostrarErro("Erro ao excluir a vaca: " + e.getMessage());
        e.printStackTrace();
    }
}

}