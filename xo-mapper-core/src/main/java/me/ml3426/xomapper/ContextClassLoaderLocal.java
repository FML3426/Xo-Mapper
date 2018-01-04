package me.ml3426.xomapper;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.WeakHashMap;

import java.util.function.Supplier;
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
 * @see Thread#getContextClassLoader()
 */
public class ContextClassLoaderLocal<T> {
    
    private final Map<ClassLoader, T> valueCache = new WeakHashMap<>(16);

    private T globalValue;

    private boolean globalValueInited = false;

    /**
     * 创建一个空的ContextClassLoaderLocal
     * @see #withInitial(Supplier)
     */
    public ContextClassLoaderLocal() {
    }

    public static <S> ContextClassLoaderLocal<S> withInitial(final Supplier<S> initSupplier) {
        return new SuppliedContextClassLoaderLocal<>(initSupplier);
    }

    /**
     *
     *
     * @return
     */
    protected T initialValue() {
        return null;
    }

    /**
     *
     *
     * @return
     */
    public synchronized T get() {
        final ClassLoader ccl = getContextClassLoader();

        if (ccl != null) {
            return valueCache.computeIfAbsent(ccl, loader -> initialValue());
        } else {
            if (!globalValueInited) {
                globalValueInited = true;
                return globalValue = initialValue();
            }

            return globalValue;
        }
    }

    /**
     *
     *
     * @param value
     */
    public synchronized void set(T value) {
        final ClassLoader ccl = getContextClassLoader();

        if (ccl != null) {
            valueCache.put(ccl, value);
        }

        if (!globalValueInited) {
            globalValueInited = true;
            globalValue = value;
        }
    }
    
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

    /**
     * 通过指定的Supplier保存线程上下文本地对象的保存器，通过制定的
     *
     * @param <T>
     */
    private final static class SuppliedContextClassLoaderLocal<T> extends ContextClassLoaderLocal<T> {

        private final Supplier<T> valueSupplier;

        public SuppliedContextClassLoaderLocal(Supplier<T> valueSupplier) {
            this.valueSupplier = valueSupplier;
        }

        @Override
        protected T initialValue() {
            return valueSupplier.get();
        }
    }
}
