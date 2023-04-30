package by.stolybko.service;


import by.stolybko.database.dao.AirplaneDao;
import by.stolybko.database.entity.Airplane;
import lombok.NoArgsConstructor;
import java.util.List;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AirplaneService {

    private static final AirplaneService INSTANCE = new AirplaneService();
    private final AirplaneDao airplaneDao = AirplaneDao.getInstance();

    public List<Airplane> getAll() {
        return airplaneDao.getAll();
    }


    public static AirplaneService getInstance() {
        return INSTANCE;
    }

}
