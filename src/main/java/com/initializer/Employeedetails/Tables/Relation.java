package com.initializer.Employeedetails.Tables;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**************************Creating table relation with required columns***********************/

@Entity
@Table(name = "Relation")

public class Relation implements Serializable {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JoinColumn
    @JsonIgnore
    private Integer desigId;

    private String jobTitle;

    @JsonIgnore
    private float lid;

    //Generating their getters and setters

    public Integer getDesigId()
    {
        return desigId;
    }

    public void setDesigId(Integer j_id)
    {
        this.desigId = desigId;
    }

    public String getJobTitle()
    {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle)
    {
        this.jobTitle = jobTitle;
    }

    public float getLid()
    {
        return lid;
    }

    public void setLid(float lid)
    {
        this.lid = lid;
    }

}
