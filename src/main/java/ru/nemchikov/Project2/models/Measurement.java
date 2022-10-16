package ru.nemchikov.Project2.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "value")
    @NotNull
    @Min(value = -100)
    @Max(value = 100)
    private double value;
    @NotNull
    @Column(name = "raining")
    private boolean raining;
    @NotNull
    @Column(name = "adding_time")
    private LocalDateTime addingTime;

    @ManyToOne()
    @JoinColumn(name = "sensor_name", referencedColumnName = "name")
    private Sensor sensor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getAddingTime() {
        return addingTime;
    }

    public void setAddingTime(LocalDateTime addingTime) {
        this.addingTime = addingTime;
    }

    @Override
    public String toString() {
        return "Measurment{" +
                "id=" + id +
                ", value=" + value +
                ", isRaining=" + raining +
                ", sensor=" + sensor +
                '}';
    }
}
