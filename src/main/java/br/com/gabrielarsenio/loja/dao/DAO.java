package br.com.gabrielarsenio.loja.dao;

import br.com.gabrielarsenio.loja.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Gabriel Arsenio 25/03/2017.
 */
public class DAO<T> {

    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Class classe;

    public DAO(Class classe) {
        this.classe = classe;
    }

    public T salvar(T entidade) {
        T retorno = null;
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            retorno = (T) session.merge(entidade);

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

        return retorno;
    }

    /**
     * @param entidade Não é necessário o modelo inteiro, basta setar apenas a PK
     */
    public Boolean excluir(T entidade) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.delete(entidade);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return true;
    }

    public T buscarPorId(Integer codigo) {
        T retorno = null;
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            retorno = (T) session.get(classe, codigo);

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

        return retorno;
    }

    public List<T> buscarTodos(Integer inicio, Integer quantidade) {
        List<T> retorno = null;
        Session session = null;
        Transaction transaction = null;
        Query query;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            query = session.createQuery("FROM " + classe.getSimpleName());

            if (inicio != null && inicio > 0) {
                query.setFirstResult(inicio);
            }

            if (quantidade != null && quantidade > 0) {
                query.setMaxResults(quantidade);
            }

            retorno = query.getResultList();

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

        return retorno;
    }

    public List<T> buscarTodos() {
        return buscarTodos(0, 0);
    }
}
