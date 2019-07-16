package com.tw.apistackbase.controller;


import com.tw.apistackbase.entity.Company;
import com.tw.apistackbase.entity.Employee;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void should_Return_Companies_List_When_Get_Companies() throws Exception {
        final MvcResult mvcResult = this.mockMvc.perform(get("/companies")).andExpect(status().isOk()).andReturn();
        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());

        assertEquals("oocl", jsonArray.getJSONObject(0).getString("companyName"));
        assertEquals("cosco", jsonArray.getJSONObject(1).getString("companyName"));
    }

    @Test
    public void should_Return_Company1_When_Get_Companies1() throws Exception {
        final MvcResult mvcResult = this.mockMvc.perform(get("/companies/2")).andExpect(status().isOk()).andReturn();
        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());

        assertEquals("cosco", jsonArray.getJSONObject(0).getString("companyName"));
    }
//    {
//        employeeList.add(new Employee(4,"Mike",21,"male",7000));
//        employeeList.add(new Employee(5,"John",22,"male",8000));
//        employeeList.add(new Employee(6,"Feas",23,"male",9000));
//        employeeList.add(new Employee(7,"Tiger",24,"male",10000));
//        employeeList.add(new Employee(8,"Milon",25,"male",11000));
//        employeeList.add(new Employee(9,"erice",26,"male",12000));
//    }

    @Test
    public void should_Return_AllEmployees_When_Get_A_Company_emplpyees() throws Exception {
        final MvcResult mvcResult = this.mockMvc.perform(get("/companies/2/employees")).andExpect(status().isOk()).andReturn();
        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());

        assertEquals("Mike", jsonArray.getJSONObject(0).getString("name"));
        assertEquals("John", jsonArray.getJSONObject(1).getString("name"));
        assertEquals("Feas", jsonArray.getJSONObject(2).getString("name"));
        assertEquals("Tiger", jsonArray.getJSONObject(3).getString("name"));
        assertEquals("Milon", jsonArray.getJSONObject(4).getString("name"));
        assertEquals("erice", jsonArray.getJSONObject(5).getString("name"));
    }

    @Test
    public void should_Return_Employees_When_Get_Companies_with_page_and_pageSize() throws Exception {
        final MvcResult mvcResult = this.mockMvc.perform(get("/companies?page=2&pageSize=5")).andExpect(status().isOk()).andReturn();
        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());

        //assertEquals("oocl", jsonArray.getJSONObject(0).getString("companyName"));
        assertEquals("thoughtworks", jsonArray.getJSONObject(0).getString("companyName"));
    }

    @Test
    public void should_Return_Company1_When_Post_Companies() throws Exception {
        List<Employee> employeeList = new ArrayList<>();
        Employee employee1 = new Employee(5, "tikor", 30, "male", 7100);
        employeeList.add(employee1);

        Company company = new Company("nike", 3, employeeList);

        JSONObject jsonObject = new JSONObject(company);

        this.mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString())).andExpect(status().isCreated());
    }

    @Test
    public void should_Return_new_company_when_update_a_company() throws Exception {

    }
}
