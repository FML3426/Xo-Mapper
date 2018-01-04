package me.ml3426.xomapper.mapper;

/**
 * 基础Mapper类型，作为Mapper的基类型提供map接口
 * 
 * @author ml3426 
 * @since 0.0.1
 */
public interface Mapper {

    /**
     * 类型转换的实际函数，将<code>origin</code>的属性复制到<code>destination中</code>
     * 
     * @param origin 转换源实例
     * @param destination 转换目的实例
     */
    void map(final Object origin, final Object destination);
}
