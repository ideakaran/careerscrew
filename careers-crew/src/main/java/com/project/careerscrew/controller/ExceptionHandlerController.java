package com.project.careerscrew.controller;

import com.project.careerscrew.dto.GenericResponseDTO;
import com.project.careerscrew.utils.exceptions.CustomAppException;
import com.project.careerscrew.utils.exceptions.ForbiddenAccessException;
import com.project.careerscrew.utils.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(final ResourceNotFoundException ex,
                                                       final HttpServletRequest request) {

        log.error("DataNotFoundException handled {} ", ex.getMessage());
        GenericResponseDTO<String> genericResponseDTO = new GenericResponseDTO<>(ex.getMessage(), null);
        return new ResponseEntity<>(genericResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<?> forbiddenAccessException(final ForbiddenAccessException ex,
                                                      final HttpServletRequest request) {

        log.error("ForbiddenAccessException handled {} ", ex.getMessage());
        GenericResponseDTO<String> genericResponseDTO = new GenericResponseDTO<>(ex.getMessage(), null);
        return new ResponseEntity<>(genericResponseDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomAppException.class)
    public ResponseEntity<?> globalAppException(final CustomAppException ex,
                                                final HttpServletRequest request) {

        log.error("CustomAppException handled {}", ex.getMessage());
        GenericResponseDTO<String> genericResponseDTO = new GenericResponseDTO<>(ex.getMessage(), null);
        return new ResponseEntity<>(genericResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> globalAppException(final RuntimeException ex,
                                                final HttpServletRequest request) {

        log.error("Runtime Exception occurred {} ", ex.getMessage());
        ex.printStackTrace();
        GenericResponseDTO<String> genericResponseDTO = new GenericResponseDTO<>(ex.getMessage(), null);
        return new ResponseEntity<>(genericResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
