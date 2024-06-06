package repo;

import domain.User;

import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.example.iss.HibernateUtil;
import java.util.List;

public class UserDBRepository extends MemoryRepository<User> {
    public List<User> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void add(User user) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            data.add(user);
        } catch (Exception e) {
            throw new RepositoryException("Cannot duplicate repository objects!");
        }
    }

    public void update(int id, User newUser) {
        int index = poz(id);
        if (index != -1) {
            super.update(id, newUser);
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();
                session.update(newUser);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(int id) {
        int index = poz(id);
        if (index != -1) {
            super.delete(id);
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();
                User user = session.get(User.class, id);
                session.delete(user);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public User authenticate(String nume, String parola) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User WHERE nume = :nume AND parola = :parola", User.class)
                    .setParameter("nume", nume)
                    .setParameter("parola", parola)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
