package br.com.turbi.model.entities;

public class Fabricante extends ModeloCarro {
    private String nomeFabricante;

    public Fabricante() {
    }
    public String getNomeFabricante() {
        return nomeFabricante;
    }

    public void setNomeFabricante(String nomeFabricante) {
        this.nomeFabricante = nomeFabricante;
    }
}
