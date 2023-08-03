package com.avuar1.entity;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CustomerData.class)
public abstract class CustomerData_ {

	public static volatile SingularAttribute<CustomerData, LocalDate> driverLicenseExpiration;
	public static volatile SingularAttribute<CustomerData, String> driverLicenseNumber;
	public static volatile SingularAttribute<CustomerData, Integer> id;
	public static volatile SingularAttribute<CustomerData, Double> creditAmount;
	public static volatile SingularAttribute<CustomerData, User> user;

	public static final String DRIVER_LICENSE_EXPIRATION = "driverLicenseExpiration";
	public static final String DRIVER_LICENSE_NUMBER = "driverLicenseNumber";
	public static final String ID = "id";
	public static final String CREDIT_AMOUNT = "creditAmount";
	public static final String USER = "user";

}

