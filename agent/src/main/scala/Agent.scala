import java.lang.instrument.{ClassDefinition, Instrumentation, ClassFileTransformer}
import java.security.ProtectionDomain
import javassist.{CtMethod, CtClass, ClassPool}

import org.objectweb.asm.{ClassWriter, ClassReader}

object Agent {
  def premain(agentArgs: String, inst: Instrumentation) {

    println("Java agent installed.")

    if (false) {
      // Javassist
      val classPool: ClassPool = new ClassPool(true)
      val stringClass: CtClass = classPool.get("java.lang.String")
      val hashCodeMethod: CtMethod = stringClass.getDeclaredMethod("hashCode", null)
      hashCodeMethod.setBody("{return 0;}")
      val bytes = stringClass.toBytecode
      val classDefinitions = Seq(new ClassDefinition(classOf[String], bytes))
      inst.redefineClasses(classDefinitions: _*)
    }

    // ASM
    inst.addTransformer(new ClassFileTransformer {
      def transform(loader: ClassLoader,
                    className: String,
                    classBeingRedefined: Class[_],
                    protectionDomain: ProtectionDomain,
                    classfileBuffer: Array[Byte]): Array[Byte] = {

//        println(s"modifying $className...")

        if (className == "DefaultHashCode") {
          println("***")
          // ASM Code
          val reader = new ClassReader(classfileBuffer)
          val writer = new ClassWriter(reader, 0)
//          val visitor = new ClassPrinter(writer)
          val visitor = new ClassModifier(writer)
          reader.accept(visitor, 0)
          writer.toByteArray
          null
        } else {
          null
        }
      }
    })
  }
}
