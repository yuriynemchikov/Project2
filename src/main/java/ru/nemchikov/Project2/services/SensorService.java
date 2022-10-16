package ru.nemchikov.Project2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nemchikov.Project2.models.Sensor;
import ru.nemchikov.Project2.repositories.SensorRepository;

import java.time.LocalDateTime;

@Service
@Transactional
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Sensor findByName(String name) {
         return sensorRepository.findByName(name).orElse(null);
    }
    @Transactional
    public void save(Sensor sensor){
        enrichSensor(sensor);
        sensorRepository.save(sensor);
    }

    public void enrichSensor(Sensor sensor){
        sensor.setCreatedAt(LocalDateTime.now());
    }

}
