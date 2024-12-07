package com;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 *
 * @author Jose
 */
public class MenuControle {
    
     @FXML
    private void incluirusuario() throws IOException{
        App.setRoot("Incluirusuario");
    }
    
    
    @FXML
    private void alterarusuario() throws IOException{
        App.setRoot("Alterarusuario");
    }
    
    @FXML
    private void producaoIncluir() throws IOException{
        App.setRoot("producaoIncluir");
    }
    
    @FXML
    private void producaoListarPorVaca() throws IOException{
        App.setRoot("Listarvaca");
    }
    
    @FXML
    private void sair(){
        Platform.exit();
        System.exit(0);
    }
    
    @FXML
    private void vacaAlterar() throws IOException{
        App.setRoot("Alterarvaca");
    }
    
    @FXML
    private void vacaIncluir() throws IOException{
        App.setRoot("vacaIncluir");
    }
    
    @FXML
    private void RegistrarProduçao() throws IOException{
        App.setRoot("Registrarproduçao");
        
    }
    @FXML
    private void Listarperiodo() throws IOException{
        App.setRoot("Listarperiodo");
        
    }
    
}
