package com.controller;

import com.service.HanLPService;
import com.service.LandIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

    @Autowired
    protected HanLPService hanlpService;

    @Autowired
    protected LandIdService landIdService;
}
