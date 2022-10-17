package ru.nemchikov.Project2.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.nemchikov.Project2.dto.MeasurementDTO;
import ru.nemchikov.Project2.dto.MeasurementResponse;
import ru.nemchikov.Project2.models.Measurement;
import ru.nemchikov.Project2.services.MeasurementService;
import ru.nemchikov.Project2.util.MeasurementErrorResponse;
import ru.nemchikov.Project2.util.MeasurementNotCreatedException;
import ru.nemchikov.Project2.util.MeasurementNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public MeasurementResponse getMeasurements() {
        return new MeasurementResponse(measurementService.findAll().stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public long getRainyDaysCount(){
        return measurementService.findAll().stream()
                .filter(Measurement::isRaining).count();
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult){
        Measurement measurementToAdd = convertToMeasurement(measurementDTO);
        if (bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                sb.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MeasurementNotCreatedException(sb.toString());
        }

        measurementService.save(measurementToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotFoundException e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                "Row with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public Measurement convertToMeasurement(MeasurementDTO dto){
        return modelMapper.map(dto, Measurement.class);
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement mer){
        return modelMapper.map(mer, MeasurementDTO.class);
    }

}
