/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import org.bson.codecs.pojo.annotations.BsonProperty;
import java.time.LocalDate;

/**
 *
 * @author LEANDRO
 */
public class ProducaoLeite {
        @BsonProperty(value = "brinco")
    private String brinco; // Identifica a vaca

    @BsonProperty(value = "data")
    private LocalDate data; // Data da produção

    @BsonProperty(value = "litros")
    private double litros; // Quantidade de leite produzida

    public ProducaoLeite() {}

    public ProducaoLeite(String brinco, LocalDate data, double litros) {
        this.brinco = brinco;
        this.data = data;
        this.litros = litros;
    }

    // Getters e Setters
    public String getBrinco() {
        return brinco;
    }

    public void setBrinco(String brinco) {
        this.brinco = brinco;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getLitros() {
        return litros;
    }

    public void setLitros(double litros) {
        this.litros = litros;
    }
    
}
