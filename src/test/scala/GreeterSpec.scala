package com.example.helloworld

import org.scalatest.{ FunSpec, Matchers }

class GreeterSpec extends FunSpec with Matchers {
  describe(""".phrase()""") {
    it("""ends with "world!"""") {
      Greeter.phrase() should endWith("world!")
    }
  }
}
