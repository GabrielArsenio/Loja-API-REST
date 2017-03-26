package br.com.gabrielarsenio.loja.model;

import com.google.gson.Gson;

import javax.persistence.*;

/**
 * Gabriel Arsenio 24/03/2017.
 */

@Entity
@Table(name = "categoria_produto")
public class CategoriaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    private String nome;
    private Boolean ativo;

    public CategoriaProduto() {
    }

    public CategoriaProduto(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
