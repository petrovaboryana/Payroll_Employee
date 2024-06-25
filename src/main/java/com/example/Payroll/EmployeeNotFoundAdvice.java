package com.example.Payroll;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice//Указва, че този клас ще предоставя глобални съвети за всички контролери в приложението.
class EmployeeNotFoundAdvice {

    @ExceptionHandler(EmployeeNotFoundException.class)//Указва, че този метод ще обработва изключения от тип
    @ResponseStatus(HttpStatus.NOT_FOUND)//Указва, че когато се хвърли грешката, HTTP отговорът ще бъде с статус код 404 Not Found.
    String employeeNotFoundHandler(EmployeeNotFoundException ex) {
        return ex.getMessage();//връща съобщението за грешка
    }
}