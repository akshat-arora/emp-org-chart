package com.initializer.Employeedetails.Repositories;

import com.initializer.Employeedetails.Tables.Relation;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

/*****Interface for Relation class*****/

public interface RelationRepo extends CrudRepository<Relation,Integer> {

    Relation findByJobTitle(String desi);
    Iterable<Relation> findAllByJobTitle(String desi);
    Relation findByDesigId(int id, Sort sort);

}
