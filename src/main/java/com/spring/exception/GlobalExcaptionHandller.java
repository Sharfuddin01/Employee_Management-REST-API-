package com.spring.exception;

import com.spring.dto.APIResponse;
import com.spring.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExcaptionHandller {


    @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorDto> handleException(Exception e , WebRequest request){
          ErrorDto error=new ErrorDto();
          error.setMessage(e.getMessage());
          error.setUrl(request.getDescription(false));
        return new ResponseEntity<>(error , HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RecordNotFoundException.class)
  public ResponseEntity<ErrorDto> customeException(RuntimeException e, WebRequest request){
        ErrorDto error=new ErrorDto();
        error.setMessage(e.getMessage());
        error.setUrl(request.getDescription(false));

        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
