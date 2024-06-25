package com.example.Payroll;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")//Get заявка за всички записи
    List<Employee> all(){
        return repository.findAll();
    }

    @PostMapping("/employees")//Post заявка за създаване на нов запис
    Employee newEmployee(@RequestBody Employee newEmployee){
        return repository.save(newEmployee);
    }


    @GetMapping("/employees/{id}")//един служител
    Employee oneEmployee(@PathVariable Long id ){
        return repository.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException(id));//изключение за ненамерен служител
    }

    @PutMapping("/employees/{id}")//PUT заявка за ъпдейт на данните
    Employee replaceEmployee(@RequestBody Employee newEmployee , @PathVariable Long id ){
        return repository.findById(id)
                .map(employee -> {//ако съществува данните се обновяват и запазват
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(()->{//Ако не бъде намерен, новият служител се създава с даденото id.
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")//DELETE заявка за изтриване на запис
    void deleteEmployee(@PathVariable Long id ){
        repository.deleteById(id);
    }

}
