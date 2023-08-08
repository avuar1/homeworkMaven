package com.avuar1.repository;

import com.avuar1.dao.CPredicate;
import com.avuar1.dao.QPredicate;
import com.avuar1.dto.UserFilter;
import com.avuar1.entity.User;
import com.avuar1.entity.User_;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import org.hibernate.Session;

import static com.avuar1.entity.QUser.user;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserRepository extends RepositoryBase<Integer, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    public Optional<User> findByEmailAndPasswordWithCriteriaAPI(String email, String password) {
        var cb = getEntityManager().getCriteriaBuilder();

        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);

        criteria.select(user).where(
                cb.equal(user.get(User_.EMAIL), email),
                cb.equal(user.get(User_.PASSWORD), password)
        );

        return Optional.ofNullable(getEntityManager().createQuery(criteria)
                .getSingleResult());
    }

    public Optional<User> findByEmailAndPasswordWithQuerydsl(String email, String password) {
        return Optional.ofNullable(new JPAQuery<User>(getEntityManager())
                .select(user)
                .from(user)
                .where(user.email.eq(email), user.password.eq(password))
                .fetchOne());
    }

    public List<User> findByFirstNameAndLastNameWithCriteriaAPI(UserFilter filter) {
        var cb = getEntityManager().getCriteriaBuilder();

        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);

        Predicate[] predicate = CPredicate.builder(cb)
                .add(filter.getFirstName(), firstName -> cb.equal(user.get(User_.firstName), firstName))
                .add(filter.getLastName(), lastName -> cb.equal(user.get(User_.lastName), lastName))
                .getPredicates();

        criteria.select(user).where(predicate);

        return getEntityManager().createQuery(criteria)
                .getResultList();
    }

    public List<User> findByFirstNameAndLastNameWithQuerydsl(UserFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getFirstName(), user.firstName::eq)
                .add(filter.getLastName(), user.lastName::eq)
                .buildAnd();

        return new JPAQuery<User>(getEntityManager())
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }
}



