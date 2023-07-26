package com.avuar1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRentalTime is a Querydsl query type for RentalTime
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRentalTime extends EntityPathBase<RentalTime> {

    private static final long serialVersionUID = -480499269L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRentalTime rentalTime = new QRentalTime("rentalTime");

    public final QCar car;

    public final DateTimePath<java.time.LocalDateTime> endRentalTime = createDateTime("endRentalTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QOrder order;

    public final DateTimePath<java.time.LocalDateTime> startRentalTime = createDateTime("startRentalTime", java.time.LocalDateTime.class);

    public QRentalTime(String variable) {
        this(RentalTime.class, forVariable(variable), INITS);
    }

    public QRentalTime(Path<? extends RentalTime> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRentalTime(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRentalTime(PathMetadata metadata, PathInits inits) {
        this(RentalTime.class, metadata, inits);
    }

    public QRentalTime(Class<? extends RentalTime> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.car = inits.isInitialized("car") ? new QCar(forProperty("car"), inits.get("car")) : null;
        this.order = inits.isInitialized("order") ? new QOrder(forProperty("order"), inits.get("order")) : null;
    }

}

