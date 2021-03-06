package br.com.gabrielarsenio.loja.dao;

import br.com.gabrielarsenio.loja.model.Estoque;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Gabriel Arsenio 25/03/2017.
 */
public class EstoqueDAO extends DAO {

    public EstoqueDAO() {
        super(Estoque.class);
    }

    public Object buscarUltimoEstoqueProduto(Integer codigoProduto) {
        Object registro = null;
        Session session = null;
        Transaction transaction = null;
        Query query;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            query = session.createQuery("FROM Estoque e WHERE e.produto.codigo = :codigoProduto ORDER BY e.codigo DESC");
            query.setParameter("codigoProduto", codigoProduto);
            query.setMaxResults(1);

            registro = query.uniqueResult();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return registro;
    }
}
