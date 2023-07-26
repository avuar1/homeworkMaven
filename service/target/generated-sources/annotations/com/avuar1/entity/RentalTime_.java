package com.avuar1.entity;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RentalTime.class)
public abstract class RentalTime_ {

	public static volatile SingularAttribute<RentalTime, Car> car;
	public static volatile SingularAttribute<RentalTime, LocalDateTime> endRentalTime;
	public static volatile SingularAttribute<RentalTime, Integer> id;
	public static volatile SingularAttribute<RentalTime, LocalDateTime> startRentalTime;
	public static volatile SingularAttribute<RentalTime, Order> order;

	public static final String CAR = "car";
	public static final String END_RENTAL_TIME = "endRentalTime";
	public static final String ID = "id";
	public static final String START_RENTAL_TIME = "startRentalTime";
	public static final String ORDER = "order";

}

