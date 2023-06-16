package by.stolybko.service;

import by.stolybko.config.ServiceConfig;
import by.stolybko.database.entity.AirplaneEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfig.class})
class AirplaneServiceTest {

  /*  @Autowired
    public AirplaneService airplaneService;

    @Test
    void findAll() {
        List<AirplaneEntity> airplanes = airplaneService.findAll();


    }*/
}