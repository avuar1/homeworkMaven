package com.avuar1.entity;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CarCategory.class)
public abstract class CarCategory_ {

	public static volatile ListAttribute<CarCategory, Car> cars;
	public static volatile SingularAttribute<CarCategory, Double> dayPrice;
	public static volatile SingularAttribute<CarCategory, CategoryLevel> categoryLevel;
	public static volatile SingularAttribute<CarCategory, Integer> id;

	public static final String CARS = "cars";
	public static final String DAY_PRICE = "dayPrice";
	public static final String CATEGORY_LEVEL = "categoryLevel";
	public static final String ID = "id";

}

