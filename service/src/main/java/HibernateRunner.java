import com.avuar1.entity.*;
import com.avuar1.util.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.*;
import org.hibernate.graph.*;
import org.hibernate.jpa.*;

public class HibernateRunner {
    public static void main(String[] args) {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            RootGraph<?> graph = session.getEntityGraph("WithUsersAndOrders");
            Map<String, Object> properties = Map.of(
                    GraphSemantic.LOAD.getJpaHintName(), graph
            );

            User user = session.find(User.class, 8, properties);
            
            System.out.println(user.getCustomerData().getDriverLicenseExpiration());
            System.out.println(user.getOrders().size());

            List<User> users = session.createQuery(
                    "select u from User u where 1=1", User.class)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(), graph)
                    .list();

            users.forEach(it -> System.out.println(it.getOrders()));
            users.forEach(it -> System.out.println(it.getCustomerData().getDriverLicenseExpiration()));

        }
    }
}
