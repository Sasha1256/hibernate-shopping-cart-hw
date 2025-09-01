package mate.academy.dao.impl;

import mate.academy.dao.TicketDao;
import mate.academy.model.Ticket;
import mate.academy.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TicketDaoImpl implements TicketDao {
    @Override
    public Ticket add(Ticket ticket) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(ticket);
            transaction.commit();
            return ticket;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new HibernateException("Cannot save ticket", exception);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
