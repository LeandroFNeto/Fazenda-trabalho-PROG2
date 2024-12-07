/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import util.Dao;
import modelo.Usuario;

/**
 *
 * @author LEANDRO
 */
public class Logincontrole {
    
  
     @FXML
    private TextField usuario; // Campo de texto para o login

    @FXML
    private PasswordField senha; // Campo de texto para a senha

    private Dao<Usuario> usuarioDao;

    public Logincontrole() {
        try {
            usuarioDao = new Dao<>(Usuario.class);
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao conectar ao banco de dados.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String login = usuario.getText();
        String senhaInput = senha.getText();

        if (login.isEmpty() || senhaInput.isEmpty()) {
            mostrarAlerta("Erro", "Usuário ou senha não podem estar vazios.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Usuario usuarioEncontrado = usuarioDao.buscarPorChave("login", login);

            if (usuarioEncontrado != null && usuarioEncontrado.getSenha().equals(senhaInput)) {
                App.setRoot("menu");
                
                // Substitua pela lógica para trocar a cena ou prosseguir no sistema
            } else {
                mostrarAlerta("Erro", "Usuário ou senha inválidos.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao verificar usuário: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}

