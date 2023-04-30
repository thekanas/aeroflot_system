package by.stolybko.database.dao;


import by.stolybko.database.connection.ConnectionPool;
import by.stolybko.database.dto.PersonFilter;
import by.stolybko.database.entity.Person;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class PersonDao {

    private static final PersonDao INSTANCE = new PersonDao();
    private static final String SELECT_ALL = "SELECT person_id, fullName, position, birth_day, description FROM person";
    private static final String SELECT_ALL_POSITION = "SELECT DISTINCT position FROM person ORDER BY position";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE person_id = ?";
    private static final String INSERT = "INSERT INTO person (fullName, position, birth_day, description) VALUES(?,?,?,?)";
    private static final String DELETE_BY_ID = "DELETE FROM person WHERE person_id =?";
    private static final String UPDATE = "UPDATE person SET fullName = ?, position = ?, birth_day = ?, description = ? WHERE person_id = ?";
    private static final String SELECT_BY = "SELECT * FROM person WHERE fullName LIKE ? AND position LIKE ? AND birth_day BETWEEN ? AND ? LIMIT ? OFFSET ?";
    private static final String COUNT_ALL_BY_FILTR = "SELECT COUNT(*) FROM person WHERE fullName LIKE ? AND position LIKE ? AND birth_day BETWEEN ? AND ?";

    public static PersonDao getInstance() {
        return INSTANCE;
    }

    public List<Person> getAll() {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = ConnectionPool.get();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                persons.add(Person.builder()
                        .id(resultSet.getLong("person_id"))
                        .fullName(resultSet.getString("fullName"))
                        .position(resultSet.getString("position"))
                        .birthDay(LocalDate.parse(resultSet.getString("birth_day"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .description(resultSet.getString("description"))
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    public Optional<Person> getById(Long id) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ? Optional.of(Person.builder()
                    .id(resultSet.getLong("person_id"))
                    .fullName(resultSet.getString("fullName"))
                    .position(resultSet.getString("position"))
                    .birthDay(LocalDate.parse(resultSet.getString("birth_day"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .description(resultSet.getString("description"))
                    .build())
                    : Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Person> getByFilter(PersonFilter filter, Integer page) {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY)) {
            preparedStatement.setString(1, "%" + filter.getFullName() + "%");
            preparedStatement.setString(2, "%" + filter.getPosition() + "%");
            preparedStatement.setDate(3, Date.valueOf(filter.getBirthDayFrom()));
            preparedStatement.setDate(4, Date.valueOf(filter.getBirthDayTO()));
            preparedStatement.setInt(5, Integer.parseInt(filter.getLimit()));
            preparedStatement.setInt(6, Integer.parseInt(filter.getLimit()) * (page - 1));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                persons.add(Person.builder()
                        .id(resultSet.getLong("person_id"))
                        .fullName(resultSet.getString("fullName"))
                        .position(resultSet.getString("position"))
                        .birthDay(LocalDate.parse(resultSet.getString("birth_day"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .description(resultSet.getString("description"))
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return persons;
    }

    public Optional<Person> create(Person person) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, person.getFullName());
            preparedStatement.setString(2, person.getPosition());
            preparedStatement.setObject(3, person.getBirthDay());
            preparedStatement.setString(4, person.getDescription());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                person.setId(generatedKeys.getLong("person_id"));
            }

            return Optional.of(person);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean delete(Long id) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Person> update(Person person) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, person.getFullName());
            preparedStatement.setString(2, person.getPosition());
            preparedStatement.setObject(3, person.getBirthDay());
            preparedStatement.setString(4, person.getDescription());
            preparedStatement.setLong(5, person.getId());
            preparedStatement.executeUpdate();

            return Optional.of(person);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public int countAllRecordsByFilter(PersonFilter filter) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ALL_BY_FILTR)) {
            preparedStatement.setString(1, "%" + filter.getFullName() + "%");
            preparedStatement.setString(2, "%" + filter.getPosition() + "%");
            preparedStatement.setDate(3, Date.valueOf(filter.getBirthDayFrom()));
            preparedStatement.setDate(4, Date.valueOf(filter.getBirthDayTO()));

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getInt("count");

        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
    }

    public List<String> getAllPosition() {
        List<String> positions = new ArrayList<>();
        try (Connection connection = ConnectionPool.get();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SELECT_ALL_POSITION);
            while (resultSet.next()) {
                positions.add(resultSet.getString("position"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }
}
