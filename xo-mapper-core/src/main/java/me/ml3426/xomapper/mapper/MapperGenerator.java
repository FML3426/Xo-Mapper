package me.ml3426.xomapper.mapper;

/**
 * Mapper类型的生成器
 * 
 * @author ml3426 
 * @since 0.0.1
 */
public class MapperGenerator {
    
    private Class sourceClass;
    
    private Class targetClass;

    public MapperGenerator(Class sourceClass, Class targetClass) {
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }
    
    
}
