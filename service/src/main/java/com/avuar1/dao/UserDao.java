package com.avuar1.dao;

import com.avuar1.dto.*;
import com.avuar1.entity.*;
import com.avuar1.entity.User_;
import com.querydsl.jpa.impl.*;
import java.util.*;
import javax.persistence.criteria.*;
import lombok.*;
import org.hibernate.*;

import static com.avuar1.entity.QUser.user;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    public List<User> findAll(Session session) {
        return session.createQuery("select u from User u", User.class)
                .list();
    }

    //Criteria
    public List<User> findAllCriteria(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> user = criteria.from(User.class);// определяем что к чему будет относится

        //сам запрос
        criteria.select(user);

        return session.createQuery(criteria)
                .list();
    }

        public Optional<User> findByEmailAndPasswordWithCriteriaAPI(Session session, String email, String password){
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<User> criteria = cb.createQuery(User.class);

        Root<User> user = criteria.from(User.class);

        criteria.select(user).where(
                cb.equal(user.get(User_.EMAIL), email),
                cb.equal(user.get(User_.PASSWORD), password)
        );
        return session.createQuery(criteria)
                .uniqueResultOptional();

    }

    public List<User> findAllByFirstName(Session session, String firstName){
        return session.createQuery("select u from User u where u.firstName = :firstName", User.class)
                .setParameter("firstName", firstName)
                .list();
    }

    // Возвращает User упорядоченных по email
    public List<User> findLimitedUsersOrderByEmail(Session session, int limit){
        return session.createQuery("select u from User u order by u.email", User.class)
                .setMaxResults(limit)
                .list();
    }

    public Optional<User> findByEmailAndPasswordWithQuerydsl(Session session, String email, String password) {

        return Optional.ofNullable(new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(user.email.eq(email), user.password.eq(password))
                .fetchOne());
    }

    public List<User> findByFirstNameAndLastNameWithCriteriaAPI(Session session, String firstName, String lastName) {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery criteria = cb.createQuery(User.class);
        Root<User> user = criteria.from(User.class);

        List<Predicate> predicates = new ArrayList<>();
        if (firstName != null) {
            predicates.add(cb.equal(user.get(User_.firstName), firstName));
        }
        if (lastName != null) {
            predicates.add(cb.equal(user.get(User_.lastName), lastName));
        }

        criteria.select(user).where(
                predicates.toArray(Predicate[]::new)
        );

        return session.createQuery(criteria)
                .list();
    }

    public List<User> findByFirstNameAndLastNameWithQuerydsl(Session session, UserFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getFirstName(), user.firstName::eq)
                .add(filter.getLastName(), user.lastName::eq)
                .buildAnd();

        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }

    public static UserDao getInstance(){
        return INSTANCE;
    }
}
