package com.service;

import com.model.BaseRequest;

import java.util.List;

public interface HanLPService {
    List<String> doAnalysis(BaseRequest request);
}
