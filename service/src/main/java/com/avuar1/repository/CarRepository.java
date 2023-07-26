package com.avuar1.repository;

import com.avuar1.entity.Car;
import com.avuar1.entity.CarModel;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;

import static com.avuar1.entity.QCar.car;

public class CarRepository extends RepositoryBase<Integer, Car>{

    public CarRepository(EntityManager entityManager) {
        super(Car.class, entityManager);
    }

    public List<Car> findAllByCarCategory(Session session, String carCategory) {
        var carGraph = session.createEntityGraph(Car.class);
        carGraph.addAttributeNodes("carCategory");

        return new JPAQuery<Car>(session)
                .select(car)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), carGraph)
                .from(car)
                .where(car.carCategory.category.eq(carCategory))
                .fetch();
    }
    public Optional<Double> findDayPriceByCarModel(Session session, String carModel) {
        return Optional.ofNullable(new JPAQuery<Double>(session)
                .select(car.carCategory.dayPrice)
                .from(car)
                .where(car.carModel.eq(CarModel.OPEL))
                .fetchOne());

    }

}
