package com.avuar1.repository;

import com.avuar1.dao.QPredicate;
import com.avuar1.dto.CarFilter;
import com.avuar1.entity.Car;
import com.avuar1.entity.CarModel;
import com.avuar1.entity.CategoryLevel;
import static com.avuar1.entity.QCar.car;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.hibernate.graph.GraphSemantic;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class CarRepository extends RepositoryBase<Integer, Car> {

    public CarRepository(EntityManager entityManager) {
        super(Car.class, entityManager);
    }

    public List<Car> findAllByCarCategory(String carCategory) {
        var carGraph = getEntityManager().createEntityGraph(Car.class);
        carGraph.addAttributeNodes("carCategory");

        return new JPAQuery<Car>(getEntityManager())
                .select(car)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), carGraph)
                .from(car)
                .where(car.carCategory.categoryLevel.eq(CategoryLevel.valueOf(carCategory)))
                .fetch();
    }

    public Optional<Double> findDayPriceByCarModel(String carModel) {
        return Optional.ofNullable(new JPAQuery<Double>(getEntityManager())
                .select(car.carCategory.dayPrice)
                .from(car)
                .where(car.carModel.eq(CarModel.valueOf(carModel)))
                .fetchOne());

    }

    public List<Car> findAllByColourAndSeatsQuantity(CarFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getColour(), car.colour::eq)
                .add(filter.getSeatsQuantity(), car.seatsQuantity::eq)
                .buildAnd();

        return new JPAQuery<Car>(getEntityManager())
                .select(car)
                .from(car)
                .where(predicate)
                .fetch();
    }

}
