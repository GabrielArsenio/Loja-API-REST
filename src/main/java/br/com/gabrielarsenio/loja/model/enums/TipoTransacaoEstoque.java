package br.com.gabrielarsenio.loja.model.enums;

/**
 * Gabriel Arsenio 25/03/2017.
 */
public enum TipoTransacaoEstoque {
    ENTRADA(0, "Entrada manual"),
    SAIDA(1, "Saída manual"),
    ENTRADA_PEDIDO_COMPRA(3, "Entrada via pedido de compra"),
    SAIDA_VENDA(4, "Venda");

    private final Integer index;
    private final String descricao;

    TipoTransacaoEstoque(Integer index, String descricao) {
        this.index = index;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getIndex() {
        return index;
    }
}
