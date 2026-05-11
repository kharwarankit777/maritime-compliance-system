package com.maritime.controller;

import com.maritime.model.Ship;
import com.maritime.repository.ShipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ships")
@RequiredArgsConstructor
public class ShipController {
    
    private final ShipRepository shipRepository;
    
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
        Ship savedShip = shipRepository.save(ship);
        return ResponseEntity.ok(savedShip);
    }
    
    @GetMapping
    public ResponseEntity<List<Ship>> getAllShips() {
        List<Ship> ships = shipRepository.findAll();
        return ResponseEntity.ok(ships);
    }
}
