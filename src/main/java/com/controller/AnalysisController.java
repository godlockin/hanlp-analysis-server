package com.controller;

import com.model.BaseRequest;
import com.service.langid.DetectedLanguage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnalysisController extends BaseController {

    @RequestMapping(value = "/langId", method = RequestMethod.POST)
    public DetectedLanguage doLangId(@RequestBody BaseRequest request) {
        return landIdService.doAnalysis(request);
    }

    @RequestMapping(value = "/analyze", method = RequestMethod.POST)
    public List<String> doAnalysis(@RequestBody BaseRequest request) {
        return hanlpService.doAnalysis(request);
    }
}
