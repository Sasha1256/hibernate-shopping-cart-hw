package mate.academy.dao.impl;

import mate.academy.dao.ShoppingCartDao;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Optional;

public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(shoppingCart);
            transaction.commit();
            return shoppingCart;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new HibernateException("Can't save ShoppingCart", exception);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<ShoppingCart> getByUser(User user) {
        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {
            Query<ShoppingCart> shoppingCartQuery= session.createQuery("from ShoppingCart where user = :user"
                    , ShoppingCart.class);
            shoppingCartQuery.setParameter("user", user);
            if (shoppingCartQuery.getResultList().isEmpty()) {
                return Optional.empty();
            }
            return Optional.ofNullable(shoppingCartQuery.getSingleResult());
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(shoppingCart);
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new HibernateException("Can`t update Shopping cart", exception);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
