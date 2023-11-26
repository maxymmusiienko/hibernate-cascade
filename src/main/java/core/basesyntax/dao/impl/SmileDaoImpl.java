package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            session.persist(entity);

            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while adding smile");

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Smile get(Long id) {
        Session session = null;

        try {
            session = factory.openSession();
            return session.get(Smile.class, id);

        } catch (Exception e) {
            throw new RuntimeException("Error while getting smile");

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Smile> getAll() {
        Session session = null;

        try {
            session = factory.openSession();
            Query<Smile> query = session.createQuery("FROM Smile", Smile.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting all smiles", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
