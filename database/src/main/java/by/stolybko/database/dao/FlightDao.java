package by.stolybko.database.dao;

import by.stolybko.database.entity.FlightEntity;

public final class FlightDao extends Dao<Long, FlightEntity> {
    private static final FlightDao INSTANCE = new FlightDao();

    private FlightDao() {
        super(FlightEntity.class);
    }

    public static FlightDao getInstance() {
        return INSTANCE;
    }
}
