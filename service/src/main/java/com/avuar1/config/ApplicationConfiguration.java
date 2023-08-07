package com.avuar1.config;

import com.avuar1.repository.UserRepository;
import com.avuar1.util.HibernateUtil;
import java.lang.reflect.Proxy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.avuar1")
public class ApplicationConfiguration {

    @Bean
    public Session session() {
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        return (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                ((proxy, method, args1) -> method.invoke(sessionFactory.openSession(), args1)));
    }

}
