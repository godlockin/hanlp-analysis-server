package com.controller;

import com.service.HanLPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

    @Autowired
    protected HanLPService hanlpService;
}
