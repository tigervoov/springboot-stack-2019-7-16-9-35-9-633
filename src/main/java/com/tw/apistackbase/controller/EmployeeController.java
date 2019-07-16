package com.tw.apistackbase.controller;

import com.tw.apistackbase.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private List<Company> employeesList;

    public EmployeeController() {
        this.employeesList = new ArrayList<>();
        initData();
    }

    private void initData() {
        employeeList.add(new Employee(4, "Mike", 21, "male", 7000));
        employeeList.add(new Employee(5, "John", 22, "male", 8000));
        employeeList.add(new Employee(6, "Feas", 23, "male", 9000));
        employeeList.add(new Employee(7, "Tiger", 24, "male", 10000));
        employeeList.add(new Employee(8, "Milon", 25, "male", 11000));
        employeeList.add(new Employee(9, "erice", 26, "male", 12000));
    }

    @GetMapping()
    public ResponseEntity getAllEmployees(@RequestParam(required = false) String page,
                                          @RequestParam(required = false) String pageSize,
                                          @RequestParam(required = false) String gender,) {
        if (gender != null) {
            List<Employee> result = employeesList.stream().filter(item -> item.getGender() == gender).collect(Collectors.toList());
            return ResponseEntity.ok().body(result);
        }
        if (page != null && pageSize != null) {
            List<Employee> list = new ArrayList<>();
            int pageNow = Integer.valueOf(page);
            int pageSizeNow = Integer.valueOf(pageSize);

            int maxItems = pageNow * pageSizeNow > employeesList.size() ? employeesList.size() : pageNow * pageSizeNow;

            for (int i = pageNow * pageSizeNow - pageSizeNow; i < maxItems; i++) {
                list.add(employeesList.get(i));
            }
            return ResponseEntity.ok().body(list);

        } else {
            return ResponseEntity.ok().body(employeesList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getSpecificEmployee(@PathVariable int id) {
        List<Employee> specificEmployee = employeesList.stream().filter(item -> item.getId() == id).collect(Collectors.toList());
        return ResponseEntity.ok().body(specificEmployee.get(0));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Employee employee) {
        List<Employee> specificEmployee = employeesList.stream().filter(item -> item.getId() == employee.getId()).collect(Collectors.toList());
        if (specificEmployee.size() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            this.employeesList.add(employee);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable int id, @RequestBody Employee employee) {
        List<Employee> specificEmployee = employeesList.stream().filter(item -> item.getId() == id).collect(Collectors.toList());
        if (specificEmployee.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        this.employeesList.remove(specificEmployee.get(0));
        this.employeesList.add(employee);
        return ResponseEntity.ok().body(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id){
        List<Employee> specificEmployee = employeesList.stream().filter(item -> item.getId() == id).collect(Collectors.toList());
        if(specificEmployee.size()==1){
            this.employeesList.remove(specificEmployee.get(0));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}