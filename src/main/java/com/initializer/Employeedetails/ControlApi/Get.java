package com.initializer.Employeedetails.ControlApi;

import com.initializer.Employeedetails.MessageUtil.MessageUtil;
import com.initializer.Employeedetails.Repositories.EmployeeRepo;
import com.initializer.Employeedetails.Repositories.RelationRepo;
import com.initializer.Employeedetails.Tables.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/******************Service class for retrieving records through ID******************/

@Service
public class Get
{   @Autowired
    EmployeeRepo repository;
    @Autowired
    RelationRepo repository1;
    @Autowired
    MessageUtil messageUtil;

    public ResponseEntity get(@PathVariable("id") int id)
    {
        if(id<=0)
        {
            return new ResponseEntity(messageUtil.getMessage("invalid_id"), HttpStatus.BAD_REQUEST);
        }

        Map<String,Object> mp=new LinkedHashMap<>();

        //fetch details of row with entered ID
        Employee user=repository.findById(id);

        if(user==null)
        {
            return new ResponseEntity(messageUtil.getMessage("no_record"),HttpStatus.NOT_FOUND);
        }

        mp.put("employee",user);

        int  parentID=user.getManagerId();

        //Fetching details of Manager of ID entered
        Employee parent=repository.findById(parentID);
        if(parent!=null)
        {
            mp.put("manager",parent);
        }

        List<Employee> allColleagues=repository.findAllByManagerId(parentID, Sort.by("desigId","name").ascending());
        if(!allColleagues.isEmpty())
        {
            //Fetching details of colleagues of entered ID
            List<Employee> colleague = coll(allColleagues, id);
            mp.put("colleagues", colleague);
        }

        //Fetching details of subordinates of id entered

        List<Employee> child=repository.findAllByManagerId(id,Sort.by("desigId","name").ascending());
        if(!child.isEmpty())
        {
            mp.put("subordinates",child);
        }
        return new ResponseEntity(mp,HttpStatus.OK);
    }

    //Method to fetch colleagues(this method removes the details of row itself and returns only colleague's info)

    private List<Employee> coll(List<Employee> allColleagues, int id)
    {
        List<Employee> listColleague=new ArrayList<>();
        for(int i=0;i<allColleagues.size();i++)
        {
            Employee addColleague = allColleagues.get(i);

            if(addColleague.getId()!=id)
            {
                listColleague.add(addColleague);
            }
        }

        return listColleague;

    }
}
