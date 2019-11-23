package com.common.constants;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Constants {

    public static class SysConfig {
        public SysConfig() {}

        public static final String ENV_FLG_KEY = "spring.profiles.active";
        public static final String BASE_CONFIG = "application.yml";
        public static final String CONFIG_TEMPLATE = "application-%s.yml";

    }

    public static class HanLPConfig {
        public HanLPConfig() {}

        public static final String ENABLE_INDEX_MODE_KEY = "hanlp.enableIndex";
        public static final String ENABLE_NAME_RECOGNIZE_KEY = "hanlp.enableNameRecognize";
        public static final String ENABLE_PLACE_RECOGNIZE_KEY = "hanlp.enablePlaceRecognize";
        public static final String ENABLE_CUSTOM_DICTIONARY_KEY = "hanlp.enableCustomDictionary";
        public static final String ENABLE_PART_OF_SPEECH_TAGGING_KEY = "hanlp.enablePartOfSpeechTagging";
        public static final String ENABLE_ORGANIZATION_RECOGNIZE_KEY = "hanlp.enableOrganizationRecognize";
        public static final String ENABLE_NUMBER_QUANTIFIER_RECOGNIZE_KEY = "hanlp.enableNumberQuantifierRecognize";


        public static final String MULTI_THREAD_NUM_KEY = "hanlp.multiThreadNum";
        public static final String IGNORE_NATURE_KEY = "hanlp.ignoreNature";

        public static final Boolean DEFAULT_ENABLE = true;
        public static final int DEFAULT_MULTI_THREAD_NUM = Runtime.getRuntime().availableProcessors() * 2;
        public static final Set<String> DEFAULT_IGNORE_NATURE = Stream.of("c;cc;e;o;p;pba;pbei;u;ud;ude1;ude2;ude3;udeng;uj;ule;w;wb;wd;wf;wh;wj;wky;wkz;wm;wn;wp;ws;wt;ww;wyy;wyz".split(";")).collect(Collectors.toSet());
    }
}
