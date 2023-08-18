package br.com.turbi.dominio.cliente.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

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

    @JsonCreator
    public static Sexo fromString(String value) {
        if ("Masculino".equalsIgnoreCase(value) || "M".equalsIgnoreCase(value)) {
            return MASCULINO;
        } else if ("Feminino".equalsIgnoreCase(value) || "F".equalsIgnoreCase(value)) {
            return FEMININO;
        }
        throw new IllegalArgumentException("Valor de sexo inválido: " + value);
    }

    public String toString(){
        return sigla.toUpperCase();
    }

}
