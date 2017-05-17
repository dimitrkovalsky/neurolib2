package com.liberty.common;

import com.liberty.dto.TwoColumnDto;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;

/**
 * User: Dimitr
 * Date: 17.05.2017
 * Time: 8:58
 */
public class CollectionDivider {
    public static <T> TwoColumnDto<T> divide(Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return empty();
        }
        List<T> left = new ArrayList<>();
        List<T> right = new ArrayList<>();
        int i = 0;
        for (T element : collection) {
            if ((i + 1) % 2 == 0)
                right.add(element);
            else
                left.add(element);
            i++;
        }
        return new TwoColumnDto<T>(left, right);
    }

    private static <T> TwoColumnDto empty() {
        return new TwoColumnDto<>(emptyList(), emptyList());
    }
}
