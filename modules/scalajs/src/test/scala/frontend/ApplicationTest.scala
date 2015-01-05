package frontend

import utest._
import utest.framework.TestSuite

object ApplicationTest extends TestSuite {
  def tests = TestSuite {
    'HelloWorld {
      assert(1 == 1)
    }
  }
}
