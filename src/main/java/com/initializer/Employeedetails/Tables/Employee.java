package com.initializer.Employeedetails.Tables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**************************Creating table employee with required columns***********************/

@Entity
@Table(name="employee")

public class Employee {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonIgnore
    @Column(name = "manager",nullable = true)
    private int managerId;

    @Transient
    @JsonProperty("jobTitle")
    private String jobTitle;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "designation")
    @JsonIgnore
    private Relation desigId;

    public Employee(){}

    //Generating getters and setters

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getManagerId()
    {
        return managerId;
    }

    public void setManagerId(int managerId)
    {
        this.managerId = managerId;
    }

    public Relation getDesigId()
    {
        return desigId;
    }

    public void setDesigId(Relation desigId)
    {
        this.desigId = desigId;
    }

    public String getJobTitle()
    {
        return desigId.getJobTitle();
    }

    public void setJobTitle(String jobTitle)
    {
        this.jobTitle = jobTitle;
    }

}

