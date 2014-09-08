class DefaultHashCode(val a: Int) {
  def foo = a.toInt

  override def hashCode(): Int = super.hashCode()
}

object HashCodeRunner extends App {

  val default = new DefaultHashCode(42)
  println(s"hashCode(): ${default.hashCode()}")

//  println("guess my hash code".##)
  private val s = new String("guess my hash code")

  println(s.hashCode)

}
