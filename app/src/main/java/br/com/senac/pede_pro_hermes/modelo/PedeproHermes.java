package br.com.senac.pede_pro_hermes.modelo;

public class PedeproHermes {
    private int id;
    private String cliente;
    private String modelo;
    private String marca;
    private String tamanho;
    private String valor;

    public PedeproHermes(int id, String cliente, String modelo, String marca, String tamanho, String valor) {
        this.id = id;
        this.cliente = cliente;
        this.modelo = modelo;
        this.marca = marca;
        this.tamanho = tamanho;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
