package me.ml3426.xomapper.introspection;

import org.apache.commons.lang3.tuple.Pair;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import me.ml3426.xomapper.ContextClassLoaderLocal;
import me.ml3426.xomapper.exception.IntropectionRuntimeException;
import me.ml3426.xomapper.util.LogUtil;

/**
 * Java自省相关工具类
 * 
 * @author ml3426 
 * @since 0.0.1
 */
public class IntrospectionUtils {
    
    private final Map<Class, BeanInfo> beanInfoCache = new WeakHashMap<>(16);
    
    private final Map<Class, Pair<List<PropertyDescriptor>, List<PropertyDescriptor>>> propertyCache
            = new WeakHashMap<>(16);

    private final static ContextClassLoaderLocal<IntrospectionUtils>
            UTILS_CLASSLOADER_LOCAL = ContextClassLoaderLocal.withInitial(IntrospectionUtils::new);
    
    private IntrospectionUtils() {
    }

    private static IntrospectionUtils getInstance() {
        return UTILS_CLASSLOADER_LOCAL.get();
    }
    
    private static List<PropertyDescriptor> getBeanGetters(final Class beanType) {
        
        final BeanInfo beanInfo = getInstance().beanInfoCache.computeIfAbsent(beanType, key -> {
            try {
                return Introspector.getBeanInfo(key, Object.class);
            } catch (IntrospectionException ex) {
                throw new IntropectionRuntimeException(ex);
            }
        });

        final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        final List<PropertyDescriptor> getters = new ArrayList<>(propertyDescriptors.length);
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            if (descriptor.getReadMethod() != null) {
                getters.add(descriptor);
            }
        }
        
        return getters;
    }
    
    private static List<PropertyDescriptor> getBeanSetters(final Class beanType) {
        final BeanInfo beanInfo = getInstance().beanInfoCache.computeIfAbsent(beanType, key -> {
            try {
                return Introspector.getBeanInfo(key, Object.class);
            } catch (IntrospectionException ex) {
                throw new IntropectionRuntimeException(ex);
            }
        });

        final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        final List<PropertyDescriptor> setters = new ArrayList<>(propertyDescriptors.length);
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            if (descriptor.getWriteMethod() != null) {
                setters.add(descriptor);
            } else {
                
            }
        }
    }
    
    private static Method findBuilderStyleSetter(final BeanInfo beanInfo, final PropertyDescriptor) 
}
