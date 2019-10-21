package com.initializer.Employeedetails.ControllerPackage;

import com.initializer.Employeedetails.Tables.Employee;
import com.initializer.Employeedetails.Tables.Relation;
import com.initializer.Employeedetails.ControlApi.*;
import com.initializer.Employeedetails.Tables.Info;
import com.initializer.Employeedetails.Repositories.EmployeeRepo;
import com.initializer.Employeedetails.Repositories.RelationRepo;
import com.sun.imageio.plugins.png.PNGImageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.*;
import java.util.*;

/***********Controller class which dictates the control of the application*************/

@RestController
public class Controller
{
    @Autowired
    public EmployeeRepo repository;
    @Autowired
    public RelationRepo repository1;
    @Autowired
    public Post postDetails;
    @Autowired
    public Put putDetails;
    @Autowired
    public Get getDetails;
    @Autowired
    public Delete deleteDetails;

    //Method to fetch complete details of a row by entering ID
    @GetMapping(value = "/employees/{id}")
    @ResponseBody
    public ResponseEntity getOne(@PathVariable("id") int id)
    {
        return getDetails.get(id);
    }

    //Method to fetch detail of every entry in the table

    @RequestMapping(value = "/employees",method = RequestMethod.GET)
    public ResponseEntity findAll()
    {
        Iterable<Employee> list= repository.findAll(Sort.by("desigId","name"));
        return new ResponseEntity(list,HttpStatus.OK);
    }

    //Method to post new entry in the table

    @PostMapping(path = "/employees")
    public ResponseEntity postDetail(@RequestBody Info user)
    {
       return postDetails.post(user);
    }

    //Method to delete a particular record from table using ID
    @DeleteMapping(value = "/employees/{id}")
    public ResponseEntity deleteOne(@PathVariable("id") int id)
    {
        return deleteDetails.delete(id);
    }

    //Method to update/replace record in the table
    @PutMapping(value = "/employees/{id}")
    public ResponseEntity updateOne(@PathVariable("id") int id,@RequestBody Info user)
    {
        return putDetails.put(id,user);
    }
}