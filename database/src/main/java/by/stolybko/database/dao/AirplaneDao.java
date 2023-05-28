package by.stolybko.database.dao;

import by.stolybko.database.entity.AirplaneEntity;

public final class AirplaneDao extends Dao<Long, AirplaneEntity> {
    private static final AirplaneDao INSTANCE = new AirplaneDao();

    private AirplaneDao() {
        super(AirplaneEntity.class);
    }

    public static AirplaneDao getInstance() {
        return INSTANCE;
    }
}
