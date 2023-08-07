package com.avuar1.util;

import com.avuar1.entity.*;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();
        configuration.configure();

        return configuration.buildSessionFactory();
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();

        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(RentalTime.class);
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(CustomerData.class);
        configuration.addAnnotatedClass(CarCategory.class);
        configuration.addAnnotatedClass(Car.class);

        return configuration;
    }
}
