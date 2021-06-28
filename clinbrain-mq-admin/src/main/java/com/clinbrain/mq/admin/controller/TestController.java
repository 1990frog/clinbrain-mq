package com.clinbrain.mq.admin.controller;

import com.clinbrain.mq.admin.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestDao testDao;

    @GetMapping("getVersion")
    public String getVersion(){
        String version = testDao.getVersion();
        return version;
    }

}
