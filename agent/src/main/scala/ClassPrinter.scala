import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class ClassPrinter(writer: ClassWriter) extends ClassVisitor(Opcodes.ASM4, writer) {

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
    System.out.println(" " + name + desc)
    super.visitMethod(access, name, desc, signature, exceptions)
  }

  override def visitEnd() {
    System.out.println("}")
    super.visitEnd()
  }
}