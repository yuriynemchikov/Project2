package ru.nemchikov.Project2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nemchikov.Project2.models.Measurement;


@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

}
