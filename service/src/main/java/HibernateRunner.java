import com.avuar1.entity.User;
import com.avuar1.util.HibernateUtil;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;

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
                    .setHint(GraphSemantic.FETCH.getJpaHintName(), graph)
                    .list();

            users.forEach(it -> System.out.println(it.getOrders()));
            users.forEach(it -> System.out.println(it.getCustomerData().getDriverLicenseExpiration()));

        }
    }
}
