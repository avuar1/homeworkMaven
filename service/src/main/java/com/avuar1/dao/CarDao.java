package com.avuar1.dao;

import com.avuar1.dto.CarFilter;
import com.avuar1.entity.Car;
import com.avuar1.entity.CarModel;
import com.avuar1.entity.CategoryLevel;
import static com.avuar1.entity.QCar.car;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;

public class CarDao {

    private static final CarDao INSTANCE = new CarDao();

    public List<Car> findAll(Session session) {
        return session.createQuery("select c from Car c", Car.class)
                .list();
    }

    public List<Car> findAllByCarCategory(Session session, CategoryLevel categoryLevel) {
        var carGraph = session.createEntityGraph(Car.class);
        carGraph.addAttributeNodes("carCategory");

        return new JPAQuery<Car>(session)
                .select(car)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), carGraph)
                .from(car)
                .where(car.carCategory.categoryLevel.eq(categoryLevel))
                .fetch();
    }

    public Optional<Double> findDayPriceByCarModel(Session session, CarModel carModel) {
        return Optional.ofNullable(new JPAQuery<Double>(session)
                .select(car.carCategory.dayPrice)
                .from(car)
                .where(car.carModel.eq(carModel))
                .fetchOne());

    }

    public List<Car> findAllByColourAndSeatsQuantity(Session session, CarFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getColour(), car.colour::eq)
                .add(filter.getSeatsQuantity(), car.seatsQuantity::eq)
                .buildAnd();

        return new JPAQuery<Car>(session)
                .select(car)
                .from(car)
                .where(predicate)
                .fetch();
    }

    public static CarDao getInstance() {
        return INSTANCE;
    }
}
