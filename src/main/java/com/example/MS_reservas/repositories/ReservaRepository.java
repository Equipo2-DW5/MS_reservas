package com.example.MS_reservas.repositories;

import com.example.MS_reservas.models.Reserva;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReservaRepository extends MongoRepository<Reserva, String> {
    List<Reserva> findByidUsuario (String idUsuario);
}
