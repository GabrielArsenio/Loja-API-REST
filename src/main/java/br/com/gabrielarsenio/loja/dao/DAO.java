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
public class DAO {

    protected SessionFactory sessionFactory;
    protected final Class entidade;

    public DAO(Class entidade) {
        this.entidade = entidade;
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public DAO(Class entidade, SessionFactory sessionFactory) {
        this.entidade = entidade;
        this.sessionFactory = sessionFactory;
    }

    public Object salvar(Object registro) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.saveOrUpdate(registro);

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

    public Boolean excluir(Integer codigo) {
        Object registro;
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            registro = session.get(entidade, codigo);
            session.delete(registro);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return true;
    }

    public Object buscarPorId(Integer codigo) {
        Object registro = null;
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            registro = session.get(entidade, codigo);

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

    public List<Object> buscarTodos(Integer inicio, Integer quantidade) {
        List<Object> registros = null;
        Session session = null;
        Transaction transaction = null;
        Query query;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            query = session.createQuery("FROM " + entidade.getSimpleName());

            if (inicio != null && inicio > 0) {
                query.setFirstResult(inicio);
            }

            if (quantidade != null && quantidade > 0) {
                query.setMaxResults(quantidade);
            }

            registros = query.getResultList();

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

        return registros;
    }
}
