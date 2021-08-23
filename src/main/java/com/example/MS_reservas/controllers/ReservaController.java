package com.example.MS_reservas.controllers;

import com.example.MS_reservas.exceptions.ReservaDateNotValidException;
import com.example.MS_reservas.exceptions.ReservaNotFoundException;
import com.example.MS_reservas.models.Reserva;
import com.example.MS_reservas.models.ResponseAPI;
import com.example.MS_reservas.repositories.ReservaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
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
    ResponseEntity<ResponseAPI> getReservas(){
        List<Reserva> reservas = reservaRepository.findAll();
        if(reservas.size() == 0) {
            throw new ReservaNotFoundException("No hay reservas");
        }

        ResponseAPI res = new ResponseAPI();

        res.setData((ArrayList) reservas);
        res.setMessage("Reservas consultadas exitosamente");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/reservas/{reservaId}")
    ResponseEntity<ResponseAPI>  reservasById(@PathVariable String reservaId){
        Reserva reservasById = reservaRepository.findById(reservaId).orElse(null);
        if(reservasById == null) {
            throw new ReservaNotFoundException("No se encontro una reserva con id" + reservaId );
        }
        ResponseAPI res = new ResponseAPI();
        ArrayList<Reserva> responseList = new ArrayList<Reserva>();
        responseList.add(reservasById);
        res.setData(responseList);
        res.setMessage("Reserva consultada exitosamente");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/reservas/user/{userId}")
    ResponseEntity<ResponseAPI> reservasByUser(@PathVariable String userId){
        List<Reserva> reservasByUser = reservaRepository.findByidUsuario(userId);
        if(reservasByUser.size() == 0) {
            throw new ReservaNotFoundException("No hay reservas para el usuario con id " + userId );
        }
        ResponseAPI res = new ResponseAPI();

        res.setData((ArrayList) reservasByUser);
        res.setMessage("Reserva consultada exitosamente");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/reservas")
    ResponseEntity<ResponseAPI> createReserva(@Valid @RequestBody Reserva _reserva){
        Date fechaSolicitud = _reserva.getFechaSolicitud();
        Date fechaFin = _reserva.getFechaFin();
        int duracion = Integer.parseInt(_reserva.getDuracion());

        checkDates(fechaFin,fechaSolicitud,duracion);

        Reserva reserva = new Reserva(_reserva.getDescripcion(), _reserva.getFechaSolicitud(),_reserva.getFechaFin(),_reserva.getDuracion(),_reserva.getIdUsuario(),_reserva.getIdLaboratorio());

        Reserva reservaCreated = reservaRepository.save(reserva);
        ResponseAPI res = new ResponseAPI();
        ArrayList<Reserva> responseList = new ArrayList<Reserva>();
        responseList.add(reservaCreated);
        res.setData(responseList);
        res.setMessage("Reserva creada exitosamente");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/reservas")
    ResponseEntity<ResponseAPI> updateReserva(@Valid @RequestBody Reserva _reserva){
        Reserva reservaExists = reservaRepository.findById(_reserva.getIdReserva()).orElse(null);
        if (reservaExists == null) {
            throw new ReservaNotFoundException("Esta reserva no existe");
        }
        Date fechaSolicitud = _reserva.getFechaSolicitud();
        Date fechaFin = _reserva.getFechaFin();
        int duracion = Integer.parseInt(_reserva.getDuracion());
        checkDates(fechaFin,fechaSolicitud,duracion);

        Reserva reserva = new Reserva(_reserva.getDescripcion(), _reserva.getFechaSolicitud(),_reserva.getFechaFin(),_reserva.getDuracion(),_reserva.getIdUsuario(),_reserva.getIdLaboratorio());

        Reserva reservaUpdated = reservaRepository.save(reserva);
        ResponseAPI res = new ResponseAPI();
        ArrayList<Reserva> responseList = new ArrayList<Reserva>();
        responseList.add(reservaUpdated);
        res.setData(responseList);
        res.setMessage("Reserva actualizada exitosamente");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public void checkDates(Date fechaFin, Date fechaSolicitud, long duracion){
        long duracionCalculo = TimeUnit.MILLISECONDS.toHours(fechaFin.getTime() - fechaSolicitud.getTime());

        if (fechaSolicitud.after(fechaFin)) {
            throw new ReservaDateNotValidException("La fecha final no puede ser inferior a la fecha de solicitud " );
        }

        if(duracionCalculo != duracion) {
            throw new ReservaDateNotValidException("El periodo de duracion no coincide con las fechas" );
        }
    }
}
