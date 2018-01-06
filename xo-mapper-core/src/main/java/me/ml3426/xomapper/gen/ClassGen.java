package me.ml3426.xomapper.gen;

import com.sun.beans.introspect.ClassInfo;

import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.beans.MethodDescriptor;
import java.util.List;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

/**
 * 类字节码生成器
 * 
 * @author ml3426 
 * @since 0.0.1
 */
class ClassGen {
    
    private final ClassWriter classWriter;
    
    private ClassInfo classInfo;

    private ClassGen(final ClassWriter cw) {
        this.classWriter = cw;
    }
    
    public static ClassGen gen() {
        return new ClassGen(new ClassWriter(ClassWriter.COMPUTE_FRAMES));
    }
    
    public void genClass(final Type superType, final String className, final List<Type> interfaces, 
                         final String source, boolean withNullConstrutor) {
        final Type classType = Type.getType("L" + className.replace('.', '/') + ";");
        
        classWriter.visit(
                Opcodes.V1_8,
                Opcodes.ACC_PUBLIC,
                classType.getInternalName(),
                null,
                superType.getInternalName(),
                (String[]) interfaces.stream().map(Type::getInternalName).toArray()
        );
        
        if (StringUtils.isNotBlank(source)) {
            classWriter.visitSource(source, null);
        }
        
        if (withNullConstrutor) {
            genNullConstructor();
        }
    }
    
    public void genNullConstructor() {
        final MethodVisitor initVisitor =
                classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);

        initVisitor.visitCode();
        initVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        initVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(Object.class),
                "<init>", "()V", false);
        initVisitor.visitInsn(Opcodes.RETURN);
        initVisitor.visitMaxs(0, 0);
        initVisitor.visitEnd();
    }
    
    public MethodGen genMethod(final int access, final String methodName, final String methodDescriptor) {
        return MethodGen.gen(classWriter, access, methodName, methodDescriptor);
    }
}
