package com.service.impl;

import com.model.BaseRequest;
import com.service.LandIdService;
import com.service.langid.DetectedLanguage;
import com.service.langid.LangIdV3;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class LandIdServiceImpl implements LandIdService {

    private LangIdV3 model;

    @PostConstruct
    void init() {
        model = new LangIdV3();
    }

    @Override
    public DetectedLanguage doAnalysis(BaseRequest request) {

        if (StringUtils.isBlank(request.getText())) {
            log.error("No text found");
            return new DetectedLanguage("NA", 0);
        }

        return model.classify(request.getText(), true);
    }
}
