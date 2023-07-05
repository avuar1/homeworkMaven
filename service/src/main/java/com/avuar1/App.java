package com.avuar1;


import com.avuar1.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class App {
    public static void main(String[] args) {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();


            session.getTransaction().commit();
        }
    }

}
