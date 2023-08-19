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

    public Sexo setSigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public String getSexo() {
        return sexo;
    }

    public Sexo setSexo(String sexo) {
        this.sexo = sexo;
        return this;
    }

    @JsonCreator
    public static Sexo fromString(String param) {
        for (Sexo sexo : Sexo.values()) {
            if (sexo.name().equalsIgnoreCase(param)) {
                return sexo;
            }
        }
        return null;
    }
    public String toString(){
        return sigla.toUpperCase();
    }

}
