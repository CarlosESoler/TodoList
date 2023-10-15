package com.carlos.todolistrocketseat.Utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class UtilsProp {

    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullProperties(src));
    }

    public static String[] getNullProperties(Object source) {
        final BeanWrapper SOURCE = new BeanWrapperImpl(source);

        PropertyDescriptor[] propertyDescriptors = SOURCE.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            Object srcValue = SOURCE.getPropertyValue(propertyDescriptor.getName());
            if (srcValue == null) emptyNames.add(propertyDescriptor.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
