package com.avuar1.entity;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Car.class)
public abstract class Car_ {

	public static volatile SingularAttribute<Car, String> colour;
	public static volatile SingularAttribute<Car, Integer> seatsQuantity;
	public static volatile SingularAttribute<Car, CarCategory> carCategory;
	public static volatile SingularAttribute<Car, Integer> id;
	public static volatile SingularAttribute<Car, CarModel> carModel;

	public static final String COLOUR = "colour";
	public static final String SEATS_QUANTITY = "seatsQuantity";
	public static final String CAR_CATEGORY = "carCategory";
	public static final String ID = "id";
	public static final String CAR_MODEL = "carModel";

}

