package com.service;

import com.model.BaseRequest;
import com.model.BaseResponse;

public interface HanLPService {
    BaseResponse doAnalysis(BaseRequest request);
}
