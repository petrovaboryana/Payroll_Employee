package com.example.Payroll;

class EmployeeNotFoundException extends RuntimeException {//може да бъде хвърлено по време на изпълнение на програмата.
    public EmployeeNotFoundException(Long id) {
        super("Could not find employee " + id);//описание на грешката
    }
}
