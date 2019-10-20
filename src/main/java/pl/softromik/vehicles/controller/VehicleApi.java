package pl.softromik.vehicles.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.softromik.vehicles.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehicles")
public class VehicleApi {

    private List<Vehicle> vehicleList;

    public VehicleApi() {
        this.vehicleList = new ArrayList<>();
        vehicleList.add(new Vehicle(1L, "Fiat", "Panda", "white"));
        vehicleList.add(new Vehicle(2L, "FatBike", "Full Haibike", "blue"));
        vehicleList.add(new Vehicle(3L, "Frugal/hulajnoga elektryczna", "Smart", "green"));
    }

    // metoda webowa do pobierania wszystkich pozycji pojazdow
    @GetMapping
    public ResponseEntity<List<Vehicle>> getVehicles() {
        return new ResponseEntity<>(vehicleList, HttpStatus.OK);
    }

    // metoda webowa do pobierania elementu po jego id
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable long id) {
        Optional<Vehicle> first = vehicleList.stream().filter(vehicle -> vehicle.getId() == id).findFirst();
        if (first.isPresent()) {
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // metoda webowa do pobierania elementow w okreslonym kolorze
    @GetMapping("/{color}")
    public ResponseEntity<List<Vehicle>> getAllVehiclesByColor(@PathVariable String color) {

        if (!vehicleList.isEmpty()) {
            return new ResponseEntity<>(vehicleList.stream()
                    .filter(vehicle -> vehicle.getColor().equals(color)).collect(Collectors.toList()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // metoda webowa do dodawania pozycji
    @PostMapping
    public ResponseEntity addVehicle(@RequestBody Vehicle vehicle) {
        boolean add = vehicleList.add(vehicle);
        if (add) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // metoda webowa do modyfikowania pozycji
    @PutMapping
    public ResponseEntity modVehicle(@RequestBody Vehicle newVehicle) {
        Optional<Vehicle> first = vehicleList.stream().filter(vehicle -> vehicle.getId() == newVehicle.getId()).findFirst();
        if (first.isPresent()) {
            vehicleList.remove(first.get());
            vehicleList.add(newVehicle);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // metoda webowa do usuwania jednej pozycji
    @DeleteMapping("/{id}")
    public ResponseEntity removeVehicle(@PathVariable long id) {
        Optional<Vehicle> first = vehicleList.stream().filter(vehicle -> vehicle.getId() == id).findFirst();
        if (first.isPresent()) {
            vehicleList.remove(first.get());
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping ("/{id}")
    public ResponseEntity<Vehicle> updateVehiclePartial(@RequestBody Vehicle newVehicle) {
        Optional<Vehicle> first = vehicleList.stream().filter(vehicle -> vehicle.getId() == newVehicle.getId()).findFirst();

        // ????????????????????????????????????????
        //
        if (first.isPresent()) {

            return new ResponseEntity(HttpStatus.OK);
    }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // ?????????????????????????????????????????????
}
