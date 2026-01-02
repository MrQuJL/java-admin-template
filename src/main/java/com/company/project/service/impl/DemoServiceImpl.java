package com.company.project.service.impl;

import com.company.project.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String hello() {
        return "Hello World";
    }

}
