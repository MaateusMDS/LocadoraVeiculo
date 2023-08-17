package br.com.turbi.model.entities;

public class ModeloCarro extends Carro {
    private String descricao;

    public ModeloCarro() {
    }

    public ModeloCarro(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
