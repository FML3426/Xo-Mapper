package me.ml3426.xomapper.gen;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/**
 * 方法字节码生成器
 */
class MethodGen {
    
    private final MethodVisitor methodVisitor;

    private MethodGen(MethodVisitor methodVisitor) {
        this.methodVisitor = methodVisitor;
    }
    
    public static MethodGen gen(final ClassWriter cw, final int access,
                                final String methodName, final String methodDescriptor) {
        final MethodVisitor mv = cw.visitMethod(access, methodName, methodDescriptor, null, null);
        return new MethodGen(mv);
    }
    
    
}
