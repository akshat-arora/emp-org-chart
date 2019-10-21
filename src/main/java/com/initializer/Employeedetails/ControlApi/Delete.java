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

import java.util.List;

/******************Service class for deleting records through ID******************/

@Service
public class Delete {
    @Autowired
    EmployeeRepo repository;
    @Autowired
    RelationRepo repository1;
    @Autowired
    MessageUtil messageUtil;

    public ResponseEntity delete(@PathVariable("id") int id)
    {
        if(id<=0)
        {
            return new ResponseEntity(messageUtil.getMessage("invalid_id"),HttpStatus.BAD_REQUEST);
        }

        //Getting row information of id that is to be deleted

        Employee user=repository.findById(id);
        if(user==null)
        {
            return new ResponseEntity(messageUtil.getMessage("no_record"),HttpStatus.NOT_FOUND);
        }

        if(user.getJobTitle().equals("Director"))
        {
            List<Employee> mlist=repository.findAllByManagerId(1,Sort.by("name"));
            if(!mlist.isEmpty())
            {return new ResponseEntity(messageUtil.getMessage("delete_dir"),HttpStatus.BAD_REQUEST);}
            else
            {
                return new ResponseEntity(messageUtil.getMessage("delete_director"),HttpStatus.NOT_FOUND);
            }
        }

        int parent=user.getManagerId();

        //Finding children of row to be deleted
        List<Employee> listParent=repository.findAllByManagerId(id,Sort.by("desigId","name").ascending());

        //Setting Parent id of each child to parent id of row to be deleted
        for(int i=0;i<listParent.size();i++)
        {
            Employee newParent=listParent.get(i);
            newParent.setManagerId(parent);
        }

        repository.deleteById(id);

        return new ResponseEntity(messageUtil.getMessage("deleted"),HttpStatus.NO_CONTENT);
    }

}
