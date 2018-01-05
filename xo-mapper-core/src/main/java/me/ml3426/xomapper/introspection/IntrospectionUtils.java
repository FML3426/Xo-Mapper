package me.ml3426.xomapper.introspection;

import com.sun.xml.internal.ws.util.StringUtils;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import me.ml3426.xomapper.ContextClassLoaderLocal;
import me.ml3426.xomapper.exception.IntropectionRuntimeException;
import me.ml3426.xomapper.util.LogUtil;
import org.apache.commons.lang3.tuple.MutablePair;

/**
 * Java自省相关工具类
 * 
 * @author ml3426 
 * @since 0.0.1
 */
public class IntrospectionUtils {
    
    private final Map<Class, BeanInfo> beanInfoCache = new WeakHashMap<>(16);

    private final static String SETTER_PREFIX = "set";
    
    private final Map<Class, MutablePair<List<PropertyDescriptor>, List<PropertyDescriptor>>>
            propertyCache = new WeakHashMap<>(16);

    private final static ContextClassLoaderLocal<IntrospectionUtils>
            UTILS_CLASSLOADER_LOCAL = ContextClassLoaderLocal.withInitial(IntrospectionUtils::new);
    
    private IntrospectionUtils() {
    }

    private static IntrospectionUtils getInstance() {
        return UTILS_CLASSLOADER_LOCAL.get();
    }

    public static List<PropertyDescriptor> getBeanGetters(final Class beanType) {

        final MutablePair<List<PropertyDescriptor>, List<PropertyDescriptor>> properties =
                getInstance().propertyCache.computeIfAbsent(beanType, key -> MutablePair.of(null, null));

        final List<PropertyDescriptor> cacheGetters = properties.getLeft();
        if (cacheGetters == null) {
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
            properties.setLeft(getters);
            return getters;
        }

        return cacheGetters;
    }
    
    public static List<PropertyDescriptor> getBeanSetters(final Class beanType) {

        final MutablePair<List<PropertyDescriptor>, List<PropertyDescriptor>> properties =
                getInstance().propertyCache.computeIfAbsent(beanType, key -> MutablePair.of(null, null));

        final List<PropertyDescriptor> cacheSetters = properties.getRight();
        if (cacheSetters == null) {
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
                    try {
                        descriptor.setWriteMethod(findBuilderStyleSetter(beanInfo, descriptor));
                    } catch (IntrospectionException ex) {
                        LogUtil.error(() -> "Builder模式setter查询失败", ex);
                    }
                }
            }
            properties.setRight(setters);
            return setters;
        }

        return cacheSetters;
    }

    private static Method findBuilderStyleSetter(final BeanInfo beanInfo, final PropertyDescriptor descriptor) {
        final MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();

        final String methodName = SETTER_PREFIX + StringUtils.capitalize(descriptor.getName());
        for (MethodDescriptor methodDescriptor : methodDescriptors) {
            final Method currentMethod = methodDescriptor.getMethod();

            if (methodDescriptor.getName().equals(methodName)
                    && currentMethod.getReturnType().equals(beanInfo.getBeanDescriptor().getBeanClass())
                    && currentMethod.getParameterCount() == 1
                    && currentMethod.getParameterTypes()[0].equals(descriptor.getPropertyType())) {
                return currentMethod;
            }
        }
        return null;
    }
}
