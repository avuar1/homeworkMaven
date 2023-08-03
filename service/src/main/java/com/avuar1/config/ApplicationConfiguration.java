package com.avuar1.config;

import com.avuar1.repository.UserRepository;
import com.avuar1.util.HibernateUtil;
import java.lang.reflect.Proxy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.avuar1")
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

    @Bean
    public Session session(){
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        return (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                ((proxy, method, args1) -> method.invoke(sessionFactory.openSession(), args1)));
    }

    @Bean
    public UserRepository userRepository(){
        return new UserRepository(session());
    }

}
