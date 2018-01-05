package me.ml3426.xomapper.introspection;

import org.junit.jupiter.api.Test;

import java.beans.PropertyDescriptor;
import java.util.List;

import me.ml3426.xomapper.base.SimpleBeanA;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Java自省相关工具类-测试类
 * 
 * @author ml3426 
 * @since 0.0.1
 */
class IntrospectionUtilsTest {
    
    @Test
    void testClassLoaderInsulateAndEx() {
        final List<PropertyDescriptor> getters = IntrospectionUtils.getBeanGetters(SimpleBeanA.class);
        final ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(new ClassLoader() {});
        final List<PropertyDescriptor> anotherGetters = IntrospectionUtils.getBeanGetters(SimpleBeanA.class);
        Thread.currentThread().setContextClassLoader(ccl);
        final List<PropertyDescriptor> oneAnotherGetters = IntrospectionUtils.getBeanGetters(SimpleBeanA.class);
        assertAll("ClassLoader隔离测试",
                () -> assertFalse(getters == anotherGetters),
                () -> assertTrue(getters == oneAnotherGetters));

        // TODO: 2018/1/6 等到PowerMock支持JUnit5之后测试类似Introspection.getBeanInfo等静态方法抛出异常的情况 
    }

    @Test
    public void testGetter() {
        final List<PropertyDescriptor> getters = IntrospectionUtils.getBeanGetters(SimpleBeanA.class);
        for (PropertyDescriptor getter : getters) {
            System.out.println(getter.getDisplayName() + ":" + getter.getReadMethod());
        }
        assertAll("Getters测试", 
                () -> assertTrue(getters.size() == 8),
                () -> assertTrue(getters.get(getters.size() - 1).getDisplayName().equals("property8")));
    }
    
    @Test void testSetter() {
        final List<PropertyDescriptor> setters = IntrospectionUtils.getBeanSetters(SimpleBeanA.class);
        for (PropertyDescriptor setter : setters) {
            System.out.println(setter.getDisplayName() + ":" + setter.getWriteMethod());
        }
        assertAll("Setters测试",
                () -> assertTrue(setters.size() == 8),
                () -> assertTrue(setters.get(setters.size() - 1).getDisplayName().equals("property8")),
                () -> assertTrue(setters.get(6).getWriteMethod() != null));
    }
}