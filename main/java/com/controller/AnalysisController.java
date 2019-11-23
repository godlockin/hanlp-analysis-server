package com.controller;

import com.model.BaseRequest;
import com.model.BaseResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalysisController extends BaseController {

    @RequestMapping(value = "/analyze")
    public BaseResponse doAnalysis(@RequestBody BaseRequest request) {
        return hanlpService.doAnalysis(request);
    }
}
