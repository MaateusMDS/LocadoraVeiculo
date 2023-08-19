package br.com.turbi.dominio.veiculo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Categoria {
    HATCH_COMPACTO("Hatch Compacto"),
    HATCH_MEDIO("Hatch Médio"),
    SEDAN_COMPACTO("Sedan Compacto"),
    SEDAN_MEDIO("Sedan Médio"),
    SEDAN_GRANDE("Sedan Grande"),
    MINIVAN("Minivan"),
    ESPORTIVO("Esportivo"),
    UTILITARIO_COMERCIAL("Utilitário Comercial");

    private String nome;

    Categoria(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Categoria setNome(String nome) {
        this.nome = nome;
        return this;
    }

    @JsonCreator
    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.name().equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        return null;
    }

}
