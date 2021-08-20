package com.example.MS_reservas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ControllerAdvice
@ResponseBody
public class ReservaDateNotValidAdvice {
    @ResponseBody
    @ExceptionHandler(ReservaDateNotValidException.class)
    ResponseEntity<Object> EntityNotFoundAdvice(ReservaDateNotValidException ex){
        Map<String, List<String>> body = new HashMap<>();
        body.put("errors", Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
