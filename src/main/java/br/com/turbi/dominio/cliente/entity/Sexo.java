package br.com.turbi.dominio.cliente.entity;

public enum Sexo {
    MASCULINO("M", "Masculino"),
    FEMININO("F", "Feminino");

    private String sigla;
    private String sexo;

    Sexo(String sigla, String sexo) {
        this.sigla = sigla;
        this.sexo = sexo;
    }

    public String getSigla() {
        return sigla;
    }

    public String getSexo() {
        return sexo;
    }

    public String toString(){
        return sigla.toUpperCase();
    }

}
