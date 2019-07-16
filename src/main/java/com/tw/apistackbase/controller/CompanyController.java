package com.tw.apistackbase.controller;


import com.tw.apistackbase.entity.Company;
import com.tw.apistackbase.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private List<Company> companyList;

    public CompanyController() {
        this.companyList = new ArrayList<>();
        initData();
    }

    @GetMapping()
    public ResponseEntity getAllCompany(@RequestParam(required = false) String page,
                                        @RequestParam(required = false) String pageSize) {
        if (page != null && pageSize != null) {
            List<Company> list = new ArrayList<>();
            int pageNow = Integer.valueOf(page);
            int pageSizeNow = Integer.valueOf(pageSize);

            int maxItems = pageNow * pageSizeNow > companyList.size() ? companyList.size() : pageNow * pageSizeNow;

            for (int i = pageNow * pageSizeNow - pageSizeNow; i < maxItems; i++) {
                list.add(companyList.get(i));
            }
            return ResponseEntity.ok().body(list);

        } else {
            return ResponseEntity.ok().body(companyList);
        }
    }

    @GetMapping("/{companyId}")
    public ResponseEntity getSpecificCompany(@PathVariable int companyId) {
        List<Company> specificCompany = companyList.stream().filter(item -> item.getCompanyId() == companyId).collect(Collectors.toList());
        return ResponseEntity.ok().body(specificCompany);
    }

    @GetMapping("/{companyId}/employees")
    public ResponseEntity getAllEmployeesByCompanyId(@PathVariable int companyId) {
        List<Company> specificCompany = this.companyList.stream().filter(item -> item.getCompanyId() == companyId).collect(Collectors.toList());
        return ResponseEntity.ok().body(specificCompany.get(0).getEmployeeList());
    }


    @PostMapping()
    public ResponseEntity creatCompany(@RequestBody Company company) {
        companyList.add(company);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{companyId}")
    public ResponseEntity updateCompany(@PathVariable int companyId @RequestBody Company company){
        List<Company> specificCompany = this.companyList.stream().filter(item -> item.getCompanyId() == companyId).collect(Collectors.toList());
        companyList.remove(specificCompany.get(0));
        companyList.add(company);
        return ResponseEntity.ok().body(company);
    }
    @DeleteMapping("/{companyId}")
    public ResponseEntity delete(@PathVariable int companyId){
        List<Company> specificCompany = this.companyList.stream().filter(item -> item.getCompanyId() == companyId).collect(Collectors.toList());
        if(specificCompany.size()>0){
            companyList.remove(specificCompany.get(0))
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public void initData() {
        addCompany();
    }

    public void addCompany() {

//        Company company1 = new Company("oocl", 1, initEmployeesList());
//        Company company2 = new Company("cosco", 2, initEmployeesList());

        this.companyList.add(new Company("oocl", 1, initEmployeesList()));
        this.companyList.add(new Company("cosco", 2, initEmployeesList()));
        this.companyList.add(new Company("apple", 3, initEmployeesList()));
        this.companyList.add(new Company("alibaba", 4, initEmployeesList()));
        this.companyList.add(new Company("tencent", 5, initEmployeesList()));
        this.companyList.add(new Company("thoughtworks", 6, initEmployeesList()));
    }

    public List<Employee> initEmployeesList() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(4, "Mike", 21, "male", 7000));
        employeeList.add(new Employee(5, "John", 22, "male", 8000));
        employeeList.add(new Employee(6, "Feas", 23, "male", 9000));
        employeeList.add(new Employee(7, "Tiger", 24, "male", 10000));
        employeeList.add(new Employee(8, "Milon", 25, "male", 11000));
        employeeList.add(new Employee(9, "erice", 26, "male", 12000));
        return employeeList;
    }
}
