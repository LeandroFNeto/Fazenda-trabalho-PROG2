package com;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import modelo.Usuario;
import util.Dao;

/**
 * Classe de controle para alterar informações de usuários.
 *
 * @author LEANDRO
 */
public class Alterarusuariocontrole {

    @FXML
    private ComboBox<String> comboUsuarios; // ComboBox para listar os logins

    @FXML
    private TextField campoNome; // Campo para mostrar e editar o nome

    @FXML
    private TextField campoLogin; // Campo para mostrar e editar o login

    @FXML
    private PasswordField campoSenha; // Campo para mostrar e editar a senha

    private Dao<Usuario> dao;

    @FXML
    private void initialize() {
        dao = new Dao<>(Usuario.class);
        carregarUsuarios(); // Preenche a ComboBox com os logins
    }

    private void carregarUsuarios() {
        try {
            // Busca todos os usuários no banco de dados
            List<Usuario> usuarios = dao.listarTodos();
            ObservableList<String> logins = FXCollections.observableArrayList();

            // Adiciona os logins na lista observável
            for (Usuario usuario : usuarios) {
                logins.add(usuario.getLogin());
            }

            // Configura a ComboBox com a lista de logins
            comboUsuarios.setItems(logins);

            // Adiciona um listener para detectar mudanças na seleção
            comboUsuarios.setOnAction(event -> selecionarUsuario());
        } catch (Exception e) {
            mostrarErro("Erro ao carregar os usuários: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void selecionarUsuario() {
        String loginSelecionado = comboUsuarios.getValue();

        if (loginSelecionado != null) {
            try {
                // Busca os dados do usuário pelo login
                Usuario usuario = dao.buscarPorChave("login", loginSelecionado);

                if (usuario != null) {
                    // Preenche os campos com os dados do usuário
                    campoNome.setText(usuario.getNome());
                    campoLogin.setText(usuario.getLogin());
                    campoSenha.setText(usuario.getSenha());
                }
            } catch (Exception e) {
                mostrarErro("Erro ao buscar o usuário: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void salvarAlteracoes() {
        String login = campoLogin.getText();
        String nome = campoNome.getText();
        String senha = campoSenha.getText();

        if (login.isBlank() || nome.isBlank() || senha.isBlank()) {
            mostrarErro("Todos os campos são obrigatórios para salvar as alterações.");
            return;
        }

        try {
            // Cria o objeto Usuario atualizado
            Usuario usuario = new Usuario(login, nome, senha);

            // Atualiza o usuário no banco de dados
            dao.alterar("login", login, usuario);

            mostrarSucesso("Dados do usuário alterados com sucesso!");
            limparCampos();
            carregarUsuarios(); // Atualiza a ComboBox após salvar
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
        campoLogin.clear();
        campoSenha.clear();
        comboUsuarios.getSelectionModel().clearSelection();
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

