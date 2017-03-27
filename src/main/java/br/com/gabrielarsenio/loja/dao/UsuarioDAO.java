package br.com.gabrielarsenio.loja.dao;

import br.com.gabrielarsenio.loja.model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Gabriel Arsenio 26/03/2017.
 */
public class UsuarioDAO extends DAO {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Usuario buscarUsuario(String usuario, String senha) {
        Usuario registro = null;
        Session session = null;
        Transaction transaction = null;
        Query query;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            query = session.createQuery("FROM Usuario u WHERE u.usuario = :usuario AND u.senha = :senha");
            query.setParameter("usuario", usuario);
            query.setParameter("senha", senha);
            query.setMaxResults(1);

            registro = (Usuario) query.uniqueResult();

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
