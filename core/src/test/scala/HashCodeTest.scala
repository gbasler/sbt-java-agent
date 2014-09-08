import org.specs2.mutable.Specification

class HashCodeTest extends Specification {

  class DefaultHashCode(val a: Int)

  "hashCode" in {
    val default = new DefaultHashCode(42)
    default.## === 0
  }
}
