package by.stolybko.database.dao;

import by.stolybko.database.entity.AirportEntity;

public final class AirportDao extends Dao<Long, AirportEntity> {

    private static final AirportDao INSTANCE = new AirportDao();

    private AirportDao() {
        super(AirportEntity.class);
    }

    public static AirportDao getInstance() {
        return INSTANCE;
    }
}
