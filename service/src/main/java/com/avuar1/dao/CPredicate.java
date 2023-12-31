package com.avuar1.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

public class CPredicate {

    private final List<Predicate> predicates = new ArrayList<>();

    private CPredicate() {
    }

    public static CPredicate builder(CriteriaBuilder cb) {
        return new CPredicate();
    }

    public <T> CPredicate add(T obj, Function<T, Predicate> function) {
        if (obj != null) {
            predicates.add(function.apply(obj));
        }
        return this;
    }

    public Predicate[] getPredicates() {
        return predicates.toArray(Predicate[]::new);
    }
}