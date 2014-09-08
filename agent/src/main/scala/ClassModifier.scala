import com.sun.org.apache.bcel.internal.generic.GETSTATIC
import org.objectweb.asm.{MethodVisitor, ClassVisitor, ClassWriter, Opcodes}

class ClassModifier(writer: ClassWriter) extends ClassVisitor(Opcodes.ASM5, writer) {

  override def visit(version: Int,
                     access: Int,
                     name: String,
                     signature: String,
                     superName: String,
                     interfaces: Array[String]) {
    System.out.println(name + " extends " + superName + " {")
    super.visit(version, access, name, signature, superName, interfaces)
  }

  override def visitMethod(access: Int, name: String, desc: String, signature: String, exceptions: Array[String]): MethodVisitor = {
    System.out.println(s"looking at method " + name + desc)

    new MethodModifier(super.visitMethod(access, name, desc, signature, exceptions))
  }

  override def visitEnd() {
    System.out.println("visitEnd")
    super.visitEnd()
  }
}

class MethodModifier(delegateVisitor: MethodVisitor) extends MethodVisitor(Opcodes.ASM5 , delegateVisitor) {


  override def visitInsn(opcode: Int) = {
    if (opcode == Opcodes.IRETURN) {
      println(s"modifiying return...")

      //      mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
      //      mv.visitLdcInsn("I live!")
      //      mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream",
      //        "println", "(Ljava/lang/String;)V", false)

      mv.visitInsn(Opcodes.POP)
      mv.visitIntInsn(Opcodes.BIPUSH, 42)
    }
    super.visitInsn(opcode)
  }

  override def visitMaxs(maxStack: Int, maxLocals: Int) = {
    super.visitMaxs(maxStack + 2, maxLocals)
  }
}