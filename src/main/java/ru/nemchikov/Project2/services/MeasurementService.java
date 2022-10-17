package ru.nemchikov.Project2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nemchikov.Project2.models.Measurement;
import ru.nemchikov.Project2.repositories.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class MeasurementService {

    private final SensorService sensorService;
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(SensorService sensorService, MeasurementRepository measurementRepository) {
        this.sensorService = sensorService;
        this.measurementRepository = measurementRepository;
    }

    public List<Measurement> findAll(){
        return measurementRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement){
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    public void enrichMeasurement(Measurement measurement){
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
        measurement.setAddingTime(LocalDateTime.now());
    }
}
