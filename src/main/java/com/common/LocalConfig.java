package com.common;

import com.common.constants.Constants.SysConfig;
import com.common.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class LocalConfig {

    private static ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();

    public static Object put(String k, Object v) { return cache.put(k, v); }

    public static void putAll(Map<String, Object> config) { cache.putAll(config); }

    public static <T> T get(String k, Class clazz, Object defaultValue) {
        if (cache.isEmpty()) {
            synchronized (LocalConfig.class) {
                if (cache.isEmpty()) {
                    sysInit();
                }
            }
        }
        return (T) DataUtils.getNotNullValue(cache, k, clazz, defaultValue);
    }

    public static Map get() {
        return new HashMap(cache);
    }

    @PostConstruct
    public static void sysInit() {
        // cache base config
        putAll(loadYamlConfig(SysConfig.BASE_CONFIG));

        // load config for env
        String envFlg = get(SysConfig.ENV_FLG_KEY, String.class, "");
        if (StringUtils.isNotBlank(envFlg)) {
            putAll(loadYamlConfig(String.format(SysConfig.CONFIG_TEMPLATE, envFlg)));
        }

        initIpAddr();
        log.info("Init {} config done", cache.size());
    }

    private static void initIpAddr() {
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            log.error(e.getMessage(),e);
        }
        String ip = localHost.getHostAddress();
        String hostName = localHost.getHostName();
        cache.put("local_ip", ip);
        cache.put("local_host_name", hostName);
    }

    private static Map<String, Object> loadYamlConfig(String fileName) {

        Map trgt = new HashMap();
        try (InputStream fis = LocalConfig.class.getClassLoader().getResourceAsStream(fileName)) {
            Map base = new Yaml().load(fis);
            flattenMap("", base, trgt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trgt;
    }

    private static void flattenMap(String prefixKey, Map base, Map trgt) {

        if (CollectionUtils.isEmpty(base)) {
            return;
        }

        String key = StringUtils.isNotBlank(prefixKey) ? prefixKey + "." : "";
        base.forEach((k, v) -> {
            String kStr = key + k;
            if (StringUtils.isBlank(kStr) || null == v) {
                return;
            }

            if (v instanceof Map) {
                flattenMap(kStr, (Map) v, trgt);
            } else {
                trgt.put(kStr, v);
            }
        });
    }
}
