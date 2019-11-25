package com.service.impl;

import com.common.LocalConfig;
import com.common.constants.Constants.HanLPConfig;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.model.BaseRequest;
import com.model.Item;
import com.service.HanLPService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HanLPServiceImpl implements HanLPService {

    private Segment segment;
    private Set<String> ignoreNature;

    @Override
    public List<String> doAnalysis(BaseRequest request) {

        if (StringUtils.isBlank(request.getText())) {
            log.error("No text found");
            return new ArrayList<>();
        }

        List<String> items = doAnalysis(request.getText().trim());
        log.debug("Generated {} items", items.size());
        return items;
    }

    private List<String> doAnalysis(String text) {
        return segment.seg(text).stream()
                .filter(x -> ignoreNature.isEmpty() || !ignoreNature.contains(x.nature.toString()))
                .map(term -> new Item(term).toString()).collect(Collectors.toList());
    }

    @PostConstruct
    void init() {
        segment = HanLP.newSegment()
                .enableIndexMode((Boolean) LocalConfig.get(HanLPConfig.ENABLE_INDEX_MODE_KEY, Boolean.class, HanLPConfig.DEFAULT_ENABLE))
                .enableNameRecognize(LocalConfig.get(HanLPConfig.ENABLE_NAME_RECOGNIZE_KEY, Boolean.class, HanLPConfig.DEFAULT_ENABLE))
                .enablePlaceRecognize(LocalConfig.get(HanLPConfig.ENABLE_PLACE_RECOGNIZE_KEY, Boolean.class, HanLPConfig.DEFAULT_ENABLE))
                .enableCustomDictionary(LocalConfig.get(HanLPConfig.ENABLE_CUSTOM_DICTIONARY_KEY, Boolean.class, HanLPConfig.DEFAULT_ENABLE))
                .enablePartOfSpeechTagging(LocalConfig.get(HanLPConfig.ENABLE_PART_OF_SPEECH_TAGGING_KEY, Boolean.class, HanLPConfig.DEFAULT_ENABLE))
                .enableOrganizationRecognize(LocalConfig.get(HanLPConfig.ENABLE_ORGANIZATION_RECOGNIZE_KEY, Boolean.class, HanLPConfig.DEFAULT_ENABLE))
                .enableNumberQuantifierRecognize(LocalConfig.get(HanLPConfig.ENABLE_NUMBER_QUANTIFIER_RECOGNIZE_KEY, Boolean.class, HanLPConfig.DEFAULT_ENABLE))
                .enableMultithreading((Integer) LocalConfig.get(HanLPConfig.MULTI_THREAD_NUM_KEY, Integer.class, HanLPConfig.DEFAULT_MULTI_THREAD_NUM))
        ;

        List<String> ignoreConfig = LocalConfig.get(HanLPConfig.IGNORE_NATURE_KEY, List.class, new ArrayList<>());
        ignoreNature = 0 == ignoreConfig.size() ? HanLPConfig.DEFAULT_IGNORE_NATURE : new HashSet<>(ignoreConfig);

        log.info(doAnalysis("虎博科技——聚焦财经领域的智能搜索引擎。完整、准确、实时、智能，专业搜索行业、公司、报告等财经信息。虎博科技依托创始团队和核心成员在人工智能领域，特别是NLP自然语言处理领域的深厚技术积累，深入挖掘全球经济市场关键信息和数据，进行实时、全自动的获取、解析、理解和总结，帮助全世界关注资本市场的机构和个人用户，从繁琐重复的工作中解放出来，把精力投入到更富创造性的工作中。").toString());
    }
}
