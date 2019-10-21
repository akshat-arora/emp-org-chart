package com.initializer.Employeedetails.ControlApi;

import com.initializer.Employeedetails.MessageUtil.MessageUtil;
import com.initializer.Employeedetails.Repositories.EmployeeRepo;
import com.initializer.Employeedetails.Repositories.RelationRepo;
import com.initializer.Employeedetails.Tables.Employee;
import com.initializer.Employeedetails.Tables.Info;
import com.initializer.Employeedetails.Tables.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/******************Service class for updating existing records******************/

@Service
public class Put {
    @Autowired
    EmployeeRepo repository;
    @Autowired
    RelationRepo repository1;
    @Autowired
    Get getOne;
    @Autowired
    MessageUtil messageUtil;
    public ResponseEntity put(@PathVariable("id") int id, @RequestBody Info user)
    {
        if(user==null)
        {
            return new ResponseEntity(messageUtil.getMessage("no_body"), HttpStatus.BAD_REQUEST);
        }
        Employee update = repository.findById(id);
        Employee updateReplace = new Employee();
        if (user.isReplace())
        {
            if(user.getJobTitle()==null)
            {
                return new ResponseEntity(messageUtil.getMessage("null_desi"),HttpStatus.BAD_REQUEST);
            }
            if(update == null)
            {
                return new ResponseEntity(messageUtil.getMessage("invalid_id"), HttpStatus.BAD_REQUEST);
            }

            if (update.getJobTitle().equals("Director"))
            {
                return new ResponseEntity(messageUtil.getMessage("update_dir"), HttpStatus.BAD_REQUEST);
            }
            if (user.getManagerId()== null) {
                Integer is = update.getManagerId();
                if(!user.getName().matches("^[ A-Za-z]+$"))
                {
                    return new ResponseEntity(messageUtil.getMessage("invalid_name"),HttpStatus.BAD_REQUEST);
                }
                else
                {
                    updateReplace.setName(user.getName());
                }
                updateReplace.setManagerId(is);
                Relation fkey = repository1.findByJobTitle(user.getJobTitle());
                if(fkey==null)
                {
                    return new ResponseEntity(messageUtil.getMessage("no_record"),HttpStatus.BAD_REQUEST);
                }
                updateReplace.setDesigId(fkey);
                repository.save(updateReplace);
                int gID = updateReplace.getId();
                List<Employee> listParent = repository.findAllByManagerId(id, Sort.by("desigId", "name").ascending());
                for (int i = 0; i < listParent.size(); i++) {
                    Employee newParent = listParent.get(i);
                    newParent.setManagerId(gID);
                }

                repository.deleteById(id);

                return getOne.get(updateReplace.getId());
            }
            if (user.getManagerId() <=0)
            {
                return new ResponseEntity(messageUtil.getMessage("invalid_mId"), HttpStatus.BAD_REQUEST);
            }
            int p=user.getManagerId();
            Employee em=repository.findById(p);
            if(em==null)
            {
                return new ResponseEntity(messageUtil.getMessage("mId_notexist"),HttpStatus.BAD_REQUEST);
            }
            else
            {
                update.setManagerId(user.getManagerId());
            }
            if(!user.getName().matches("^[ A-Za-z]+$"))
            {
                return new ResponseEntity(messageUtil.getMessage("invalid_name"),HttpStatus.BAD_REQUEST);
            }
            else
            {
                updateReplace.setName(user.getName());
            }
            updateReplace.setManagerId(user.getManagerId());
            Relation fkey = repository1.findByJobTitle(user.getJobTitle());
            if(fkey==null)
            {
                return new ResponseEntity(messageUtil.getMessage("no_record"),HttpStatus.BAD_REQUEST);
            }
            updateReplace.setDesigId(fkey);
            repository.save(updateReplace);
            int gID = updateReplace.getId();
            List<Employee> listParent = repository.findAllByManagerId(id, Sort.by("desigId", "name").ascending());

            for (int i = 0; i < listParent.size(); i++)
            {
                Employee newParent = listParent.get(i);
                newParent.setManagerId(gID);
            }

            repository.deleteById(id);

            return getOne.get(updateReplace.getId());

        }
        else
        {
            if(user==null)
            {
                return new ResponseEntity(messageUtil.getMessage("no_body"),HttpStatus.BAD_REQUEST);
            }
            if (update == null)
            {
                return new ResponseEntity(messageUtil.getMessage("invalid_id"), HttpStatus.BAD_REQUEST);
            }
            if(user.getManagerId()==null)
            {
                return new ResponseEntity(messageUtil.getMessage("invalid_mId"),HttpStatus.BAD_REQUEST);
            }
            if (update.getJobTitle().equals("Director")) {
                return new ResponseEntity(messageUtil.getMessage("update_dir"), HttpStatus.BAD_REQUEST);
            }

            if(user.getManagerId()<=0)
            {
                return new ResponseEntity(messageUtil.getMessage("invalid_mId"),HttpStatus.BAD_REQUEST);
            }
            int p=user.getManagerId();
            Employee em=repository.findById(p);
            if(em==null)
            {
                return new ResponseEntity(messageUtil.getMessage("mId_notexist"),HttpStatus.BAD_REQUEST);
            }
            else
            {
                Employee entry=repository.findById(user.getManagerId().intValue());
                float levelEntry=entry.getDesigId().getLid();
                Employee cuser=repository.findById(id);
                float curEntry=cuser.getDesigId().getLid();
                if(curEntry<levelEntry)
                {
                    return new ResponseEntity(messageUtil.getMessage("small_desi"),HttpStatus.BAD_REQUEST);
                }
                else
                {
                    update.setManagerId(user.getManagerId());
                }
            }
            Relation fkey = repository1.findByJobTitle(user.getJobTitle());
            if(fkey==null)
            {
                return new ResponseEntity(messageUtil.getMessage("no_record"),HttpStatus.BAD_REQUEST);
            }
            update.setDesigId(fkey);
            if(!user.getName().matches("^[ A-Za-z]+$"))
            {
                return new ResponseEntity(messageUtil.getMessage("invalid_name"),HttpStatus.BAD_REQUEST);
            }
            else
            {
                update.setName(user.getName());
            }
            repository.save(update);

            return getOne.get(update.getId());

        }
    }
}

