package by.stolybko.database.dao;


import by.stolybko.database.connection.ConnectionPool;
import by.stolybko.database.entity.Airplane;
import lombok.NoArgsConstructor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AirplaneDao {
    private static final AirplaneDao INSTANCE = new AirplaneDao();
    private static final String SELECT_ALL = "SELECT * FROM airplane";

    public List<Airplane> getAll() {
        List<Airplane> airplanes = new ArrayList<>();
        try (Connection connection = ConnectionPool.get();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                airplanes.add(Airplane.builder()
                        .id(resultSet.getLong("airplane_id"))
                        .make(resultSet.getString("make"))
                        .model(resultSet.getString("model"))
                        .flightRangeKm(resultSet.getInt("flight_range_km"))
                        .passengerCapacity(resultSet.getInt("passenger_capacity"))
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airplanes;
    }


    public static AirplaneDao getInstance() {
        return INSTANCE;
    }
}
