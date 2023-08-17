package br.com.turbi.model.entities;

public class Acessorio extends Carro {
    private String descricao;
    public Acessorio() {
    }
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
