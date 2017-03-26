package br.com.gabrielarsenio.loja.model;

import br.com.gabrielarsenio.loja.model.enums.TipoTransacaoEstoque;
import com.google.gson.Gson;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Gabriel Arsenio 25/03/2017.
 */
@Entity
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    @ManyToOne
    private Produto produto;
    private LocalDateTime data;
    @Enumerated(EnumType.ORDINAL)
    private TipoTransacaoEstoque tipoTransacao;
    private Double quantidadeTransacao;
    private Double quantidadeAtual;

    public Estoque() {
    }

    public Estoque(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public TipoTransacaoEstoque getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacaoEstoque tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public Double getQuantidadeTransacao() {
        return quantidadeTransacao;
    }

    public void setQuantidadeTransacao(Double quantidadeTransacao) {
        this.quantidadeTransacao = quantidadeTransacao;
    }

    public Double getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public void setQuantidadeAtual(Double quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
