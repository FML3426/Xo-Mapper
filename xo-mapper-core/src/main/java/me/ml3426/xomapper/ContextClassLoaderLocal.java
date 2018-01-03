package me.ml3426.xomapper;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.WeakHashMap;

import me.ml3426.xomapper.util.LogUtil;

/**
 * 线程上下文级别的ClassLoader的Local，与{@link ThreadLocal}的工作原理类似，但是保存的是
 * 类加载器级别的实例。
 * 
 * 用来对不同的ClassLoader之间的实例做隔离，不同ClassLoader看不到对方生成的实例。
 * 
 * @param <T> 此Local对应储存的实例的类型
 * @author ml3426
 * @since 0.0.1
 */
public class ContextClassLoaderLocal<T> {
    
    private final Map<ClassLoader, T> valueCache = new WeakHashMap<>(16);
    
    private static ClassLoader getContextClassLoader() {
        return AccessController.doPrivileged((PrivilegedAction<ClassLoader>) () -> {
            try {
                return Thread.currentThread().getContextClassLoader();
            } catch (SecurityException ex) {
                LogUtil.warn(() -> "没有权限获取对应类的ContextClassLoader.", ex);
                return null;
            }
        });
    }
}
