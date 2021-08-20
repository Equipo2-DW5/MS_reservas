package com.example.MS_reservas.controllers;

import com.example.MS_reservas.exceptions.ReservaDateNotValidException;
import com.example.MS_reservas.exceptions.ReservaNotFoundException;
import com.example.MS_reservas.models.Reserva;
import com.example.MS_reservas.repositories.ReservaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
public class ReservaController {
    private final ReservaRepository reservaRepository;

    public ReservaController(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;

        Date original = new Date();
        int duracion = 5;
        Date later = addHoursToJavaUtilDate(original, duracion);
        //Seed
        Reserva reserva01 = new Reserva("descripcion",original,later,String.valueOf(duracion),"userId","lab05");

        this.reservaRepository.save(reserva01);
    }

    public Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    @GetMapping("/reservas")
    List<Reserva> getReservas(){
        List<Reserva> reservas = reservaRepository.findAll();
        if(reservas.size() == 0) {
            throw new ReservaNotFoundException("No hay reservas");
        }
        System.out.println(reservaRepository.findAll());
    return reservaRepository.findAll();
    }

    @GetMapping("/reservas/{reservaId}")
    Reserva reservasById(@PathVariable String reservaId){
        return reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ReservaNotFoundException("No se encontro una reserva con id" + reservaId));
    }

    @GetMapping("/reservas/user/{userId}")
    List<Reserva> reservasByUser(@PathVariable String userId){
        List<Reserva> reservasByUser = reservaRepository.findByidUsuario(userId);
        if(reservasByUser.size() == 0) {
            throw new ReservaNotFoundException("No hay reservas para el usuario con id " + userId );
        }
        return reservasByUser;
    }

    @PostMapping("/reservas")
    ResponseEntity<Reserva> createReserva(@Valid @RequestBody Reserva _reserva){
        Date fechaSolicitud = _reserva.getFechaSolicitud();
        Date fechaFin = _reserva.getFechaFin();
        int duracion = Integer.parseInt(_reserva.getDuracion());
        long duracionCalculo = TimeUnit.MILLISECONDS.toHours(fechaFin.getTime() - fechaSolicitud.getTime());

        if (fechaSolicitud.after(fechaFin)) {
            throw new ReservaDateNotValidException("La fecha final no puede ser inferior a la fecha de solicitud " );
        }

        if(duracionCalculo != duracion) {
            throw new ReservaDateNotValidException("El periodo de duracion no coincide con las fechas" );
        }

        Reserva reserva = new Reserva(_reserva.getDescripcion(), _reserva.getFechaSolicitud(),_reserva.getFechaFin(),_reserva.getDuracion(),_reserva.getIdUsuario(),_reserva.getIdLaboratorio());

        Reserva reservaCreated = reservaRepository.save(reserva);

        return new ResponseEntity<>(reservaCreated, HttpStatus.CREATED);
    }
}
