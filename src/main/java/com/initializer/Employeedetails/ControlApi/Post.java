package com.initializer.Employeedetails.ControlApi;

import com.initializer.Employeedetails.MessageUtil.MessageUtil;
import com.initializer.Employeedetails.Tables.Employee;
import com.initializer.Employeedetails.Tables.Info;
import com.initializer.Employeedetails.Tables.Relation;
import com.initializer.Employeedetails.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/******************Service class for creating new records******************/

@Service
public class Post {

    @Autowired
    RelationRepo repository1;
    @Autowired
    EmployeeRepo repository;
    @Autowired
    MessageUtil messageUtil;
    public ResponseEntity post(@RequestBody Info user) {
         if (user.getName() == null || (user.getJobTitle() == null) || user.getManagerId() == null) {
            return new ResponseEntity(messageUtil.getMessage("null_values"), HttpStatus.BAD_REQUEST);
        }

        if (!user.getName().matches("^[ A-Za-z]+$")) {
            return new ResponseEntity(messageUtil.getMessage("invalid_name"), HttpStatus.BAD_REQUEST);
        }

        Employee tDetails = new Employee();
        Relation fkey = repository1.findByJobTitle(user.getJobTitle());
        List<Employee> empList = repository.findAll(Sort.by("desigId", "name"));
        if (empList.isEmpty()) {
            if (!user.getJobTitle().equals("Director")) {
                return new ResponseEntity(messageUtil.getMessage("director_first"), HttpStatus.BAD_REQUEST);
            } else {
                tDetails.setManagerId(-1);
                tDetails.setName(user.getName());
                Relation relation = repository1.findByDesigId(1, Sort.by("jobTitle"));
                tDetails.setDesigId(relation);
                repository.save(tDetails);
                return new ResponseEntity(messageUtil.getMessage("saved"),HttpStatus.CREATED);
            }
        }

        //Set details that are entered by user in new entry
        if (!empList.isEmpty()) {
            if (user.getJobTitle().equals("Director")) {
                return new ResponseEntity(messageUtil.getMessage("one_director"), HttpStatus.BAD_REQUEST);
            }
        }

        if (user.getManagerId() <= 0) {
            return new ResponseEntity(messageUtil.getMessage("invalid_mId"), HttpStatus.BAD_REQUEST);
        } else {
            int p = user.getManagerId();
            Employee em = repository.findById(p);
            if (em == null) {
                return new ResponseEntity(messageUtil.getMessage("mId_notexist"), HttpStatus.BAD_REQUEST);
            } else {
                tDetails.setManagerId(user.getManagerId());
            }
        }

        tDetails.setName(user.getName());
        tDetails.setManagerId(user.getManagerId());

        if (fkey == null) {
            return new ResponseEntity(messageUtil.getMessage("invalid_desi"), HttpStatus.BAD_REQUEST);
        }

        Employee tPidDetails = repository.findById(tDetails.getManagerId());
        int tabledesId = fkey.getDesigId();
        int userdesId = tPidDetails.getDesigId().getDesigId();
        if (tabledesId <= userdesId) {
            return new ResponseEntity(messageUtil.getMessage("small_desi"), HttpStatus.BAD_REQUEST);
        } else {
            tDetails.setDesigId(fkey);
        }

        repository.save(tDetails);
        return new ResponseEntity(tDetails, HttpStatus.CREATED);
    }
}