package com;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import modelo.Usuario;
import util.Dao;

public class Incluirusuariocontrole {

    @FXML
    private AnchorPane menu;
    @FXML
    private TextField login; // Campo de login
    @FXML
    private TextField nome;  // Campo de nome
    @FXML
    private PasswordField senha; // Campo de senha

    private Dao<Usuario> usuarioDao;

    public Incluirusuariocontrole() {
        try {
            usuarioDao = new Dao<>(Usuario.class);
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao conectar ao banco de dados.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSalvar(ActionEvent event) {
        String login = this.login.getText();
        String nome = this.nome.getText();
        String senha = this.senha.getText();

        if (login.isEmpty() || nome.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Erro", "Todos os campos são obrigatórios.", Alert.AlertType.WARNING);
            return;
        }

        if (senha.length() < 6) {
            mostrarAlerta("Erro", "A senha deve ter pelo menos 6 caracteres.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Usuario existente = usuarioDao.buscarPorChave("login", login);
            if (existente != null) {
                mostrarAlerta("Erro", "Este login já está em uso.", Alert.AlertType.ERROR);
                return;
            }

            Usuario novoUsuario = new Usuario(login, nome, senha);
            usuarioDao.inserir(novoUsuario);

            mostrarAlerta("Sucesso", "Usuário registrado com sucesso!", Alert.AlertType.INFORMATION);

            limparCampos();
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao salvar usuário: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        login.clear();
        nome.clear();
        senha.clear();
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void voltarMenu(ActionEvent event) throws IOException {
        App.setRoot("menu"); // Método de navegação
    }
}
