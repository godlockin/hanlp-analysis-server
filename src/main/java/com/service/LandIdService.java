package com.service;

import com.model.BaseRequest;
import com.service.langid.DetectedLanguage;

public interface LandIdService {
    DetectedLanguage doAnalysis(BaseRequest request);
}
