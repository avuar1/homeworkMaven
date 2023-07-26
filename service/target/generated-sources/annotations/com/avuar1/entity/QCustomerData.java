package com.avuar1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerData is a Querydsl query type for CustomerData
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerData extends EntityPathBase<CustomerData> {

    private static final long serialVersionUID = 502241746L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerData customerData = new QCustomerData("customerData");

    public final NumberPath<Double> creditAmount = createNumber("creditAmount", Double.class);

    public final DatePath<java.time.LocalDate> driverLicenseExpiration = createDate("driverLicenseExpiration", java.time.LocalDate.class);

    public final StringPath driverLicenseNumber = createString("driverLicenseNumber");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QUser user;

    public QCustomerData(String variable) {
        this(CustomerData.class, forVariable(variable), INITS);
    }

    public QCustomerData(Path<? extends CustomerData> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerData(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerData(PathMetadata metadata, PathInits inits) {
        this(CustomerData.class, metadata, inits);
    }

    public QCustomerData(Class<? extends CustomerData> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

