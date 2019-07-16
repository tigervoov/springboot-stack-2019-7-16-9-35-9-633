package com.tw.apistackbase.entity;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String companyName;
    private int companyId;
    private int employeesNumber;
    private List<Employee> employeeList;


    public Company(){

    }
    public Company(String companyName, int companyId,List<Employee> employeeList) {
        this.companyName = companyName;
        this.companyId = companyId;
        this.employeeList=employeeList;
        this.employeesNumber=employeeList.size();
    }


    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
