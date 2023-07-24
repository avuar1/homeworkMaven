package com.avuar1.dao;

import com.avuar1.entity.RentalTime;
import java.util.List;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

@NoArgsConstructor
public class RentalTimeDao {

    private static final RentalTimeDao INSTANCE = new RentalTimeDao();

    public List<RentalTime> findAll(Session session) {
        return session.createQuery("select rt from RentalTime rt", RentalTime.class)
                .list();
    }

    public static RentalTimeDao getInstance() {
        return INSTANCE;
    }
}
