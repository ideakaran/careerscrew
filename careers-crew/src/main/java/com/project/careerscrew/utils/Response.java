package com.project.careerscrew.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String message;
    private Object object;

    public ResponseEntity<?> success(String message, Object o) {
        Response response = new Response();
        response.setMessage(message);
        response.setObject(o);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<?> success(Object o) {
        Response response = new Response();
        response.setMessage("Success");
        response.setObject(o);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
