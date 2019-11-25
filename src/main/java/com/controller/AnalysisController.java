package com.controller;

import com.model.BaseRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnalysisController extends BaseController {

    @RequestMapping(value = "/analyze")
    public List<String> doAnalysis(@RequestBody BaseRequest request) {
        return hanlpService.doAnalysis(request);
    }
}
