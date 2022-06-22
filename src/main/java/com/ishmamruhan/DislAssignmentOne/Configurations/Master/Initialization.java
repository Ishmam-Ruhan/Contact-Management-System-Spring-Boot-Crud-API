package com.ishmamruhan.DislAssignmentOne.Configurations.Master;

import com.ishmamruhan.DislAssignmentOne.ExternalAPI.ZipInfoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class Initialization {

    @Autowired
    ApplicationContext applicationContext;


    @PostConstruct
    public  void init(){
    }
}
