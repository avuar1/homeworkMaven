package util;

import com.avuar1.util.HibernateUtil;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

@UtilityClass
public class HibernateTestUtil {

    // Здесь образ с докер хаба postgres и версия образа с докер хаб, мы поставили 13
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13");

    static {  // статический блок инициализации, которые один раз запускает постгрес
        postgres.start();
    }

    public static SessionFactory buildSessionFactory() {  // для тестов свой build SessionFactory а для HibernateUtil свой
        Configuration configuration = HibernateUtil.buildConfiguration(); // из Hibernate Util
        configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl()); // устанавливаем урл
        configuration.setProperty("hibernate.connection.username", postgres.getUsername());  // устанавливаем узернаме
        configuration.setProperty("hibernate.connection.password", postgres.getPassword());  // устанавливаем пароль
        configuration.configure();  //  и потом вызываем конфигуре

        return configuration.buildSessionFactory();
    }
}
