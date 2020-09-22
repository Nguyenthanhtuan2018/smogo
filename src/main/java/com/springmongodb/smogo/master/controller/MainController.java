package com.springmongodb.smogo.master.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springmongodb.smogo.master.entity.Employee;
import com.springmongodb.smogo.master.service.EmployeeRepository;
import com.springmongodb.smogo.master.service.EmployeeRepositoryCustom;

@RestController
public class MainController {

    private static final String[] NAMES = { "Tom", "Jerry", "Donald" };

    @Autowired
    private EmployeeRepositoryCustom employeeRepositoryCustom;

    @Autowired
    private EmployeeRepository employeeRepository;

    @ResponseBody
    @RequestMapping("/")
    public String home() {
        String html = "";
        html += "<ul>";
        html += " <li><a href='/testInsert'>Test Insert</a></li>";
        html += " <li><a href='/showAllEmployee'>Show All Employee</a></li>";
        html += " <li><a href='/showFullNameLikeTom'>Show All 'Tom'</a></li>";
        html += " <li><a href='/deleteAllEmployee'>Delete All Employee</a></li>";
        html += "</ul>";
        return html;
    }

    @ResponseBody
    @PostMapping("/testInsert")
    public Employee testInsert(@RequestBody Employee request) {
        Employee employee = new Employee();

        long id = employeeRepositoryCustom.getMaxEmpId() + 1;
        int idx = (int) (id % NAMES.length);
        String name = NAMES[idx] + " " + id;

        employee.setId(id);
        employee.setEmployeeNo("E" + id);
        employee.setName(request.getName());
        employee.setHireDate(new Date());
        employeeRepository.insert(employee);

        return employee;
    }

    @ResponseBody
    @RequestMapping("/showAllEmployee")
    public String showAllEmployee() {

        List<Employee> employees = employeeRepository.findAll();

        String html = "";
        for (Employee emp : employees) {
            html += emp + "<br>";
        }

        return html;
    }

    @ResponseBody
    @RequestMapping("/showFullNameLikeTom")
    public String showFullNameLikeTom() {

        List<Employee> employees = employeeRepository.findByNameLike("Tom");

        String html = "";
        for (Employee emp : employees) {
            html += emp + "<br>";
        }

        return html;
    }

    @ResponseBody
    @RequestMapping("/deleteAllEmployee")
    public String deleteAllEmployee() {

        employeeRepository.deleteAll();
        return "Deleted!";
    }

}
