package br.com.gabrielarsenio.loja.dao;

import br.com.gabrielarsenio.loja.model.Produto;

/**
 * Gabriel Arsenio 25/03/2017.
 */
public class ProdutoDAO extends DAO<Produto> {

    public ProdutoDAO() {
        super(Produto.class);
    }

    public Boolean excluir(Integer codigo) {
        return super.excluir(new Produto(codigo));
    }
}
