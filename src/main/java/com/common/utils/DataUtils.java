package com.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

@Slf4j
public class DataUtils {

    public static final String DATE_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = DATE_YYYYMMDD_HHMMSS;

    public static <T> T getNotNullValue(Map base, String key, Class<T> clazz, Object defaultValue) {
        return handleNullValue(base.get(key), clazz, defaultValue);
    }

    public static <T> T handleNullValue(Object base, Class<T> clazz, Object defaultValue) {
        return clazz.cast(Optional.ofNullable(base).orElse(defaultValue));
    }

    public static String fullWidth2halfWidth(String fullWidthStr) {
        if (StringUtils.isBlank(fullWidthStr)) {
            return "";
        }

        char[] charArray = fullWidthStr.toCharArray();
        //对全角字符转换的char数组遍历
        for (int i = 0; i < charArray.length; ++i) {
            int charIntValue = (int) charArray[i];
            //如果符合转换关系,将对应下标之间减掉偏移量65248;如果是空格的话,直接做转换
            if (charIntValue >= 65281 && charIntValue <= 65374) {
                charArray[i] = (char) (charIntValue - 65248);
            } else if (charIntValue == 12288) {
                charArray[i] = (char) 32;
            }
        }
        return new String(charArray);
    }

    public static <E> void forEach(Integer maxIndex, Iterable<? extends E> elements, BiConsumer<Integer, ? super E> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);
        int index = 0;
        for (E element : elements) {
            action.accept(index++, element);
            if (maxIndex > 0 && maxIndex < index) {
                break;
            }
        }
    }
}
