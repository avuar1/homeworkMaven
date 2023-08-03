package com.avuar1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarCategory is a Querydsl query type for CarCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarCategory extends EntityPathBase<CarCategory> {

    private static final long serialVersionUID = 2066377352L;

    public static final QCarCategory carCategory = new QCarCategory("carCategory");

    public final ListPath<Car, QCar> cars = this.<Car, QCar>createList("cars", Car.class, QCar.class, PathInits.DIRECT2);

    public final EnumPath<CategoryLevel> categoryLevel = createEnum("categoryLevel", CategoryLevel.class);

    public final NumberPath<Double> dayPrice = createNumber("dayPrice", Double.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QCarCategory(String variable) {
        super(CarCategory.class, forVariable(variable));
    }

    public QCarCategory(Path<? extends CarCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCarCategory(PathMetadata metadata) {
        super(CarCategory.class, metadata);
    }

}

