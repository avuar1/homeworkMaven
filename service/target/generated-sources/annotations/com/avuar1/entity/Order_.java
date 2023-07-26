package com.avuar1.entity;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, Car> car;
	public static volatile ListAttribute<Order, RentalTime> rentalTimes;
	public static volatile SingularAttribute<Order, OrderStatus> orderStatus;
	public static volatile SingularAttribute<Order, Integer> id;
	public static volatile SingularAttribute<Order, String> message;
	public static volatile SingularAttribute<Order, User> user;

	public static final String CAR = "car";
	public static final String RENTAL_TIMES = "rentalTimes";
	public static final String ORDER_STATUS = "orderStatus";
	public static final String ID = "id";
	public static final String MESSAGE = "message";
	public static final String USER = "user";

}

