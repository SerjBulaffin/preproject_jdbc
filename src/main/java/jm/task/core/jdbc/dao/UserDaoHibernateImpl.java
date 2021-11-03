package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = new Util().getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {

        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            String SQL = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT not NULL AUTO_INCREMENT, " +
                    " name VARCHAR (20) not NULL, " +
                    " lastname VARCHAR (30) not NULL, " +
                    " age TINYINT not NULL, " +
                    " PRIMARY KEY (id))";

            session.createSQLQuery(SQL);
            session.getTransaction().commit();
        } catch (Exception e) {
        }
    }

    @Override
    public void dropUsersTable() {

        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            String SQL = "DROP TABLE IF EXISTS users ";

            session.createSQLQuery(SQL);
            session.getTransaction().commit();
        } catch (Exception e) {
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {

        }
    }

    @Override
    public void removeUserById(long id) {

        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            User user = (User) session.get(User.class, id);

            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            users = session.createQuery("From User").list();
            session.getTransaction().commit();
        } catch (Exception e) {
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {

        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            session.delete("From User");

//            List<User> users = session.createQuery("From User").list();
//            for (User user : users) {
//                session.delete(user);
//            }

            session.getTransaction().commit();
        } catch (Exception e) {
        }
    }

}
