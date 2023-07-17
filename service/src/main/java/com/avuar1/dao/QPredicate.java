package com.avuar1.dao;

import com.querydsl.core.types.*;
import com.querydsl.core.types.Predicate;
import java.util.*;
import java.util.function.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QPredicate {

    private final List<Predicate> predicates = new ArrayList<>();

    public static QPredicate builder() {
        return new QPredicate();
    }

    public <T> QPredicate add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    public Predicate buildAnd() {
        return ExpressionUtils.allOf(predicates);
    }

    public Predicate buildOr() {
        return ExpressionUtils.anyOf(predicates);
    }
}
