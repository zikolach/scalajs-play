package shared

import utest._
import utest.framework.TestSuite

object CommonTest extends TestSuite {
  def tests = TestSuite {
    'TestCommon {
      assert(true)
    }
    'SqrtTest {
      assert(Common.sqrt(1) == 1)
      assert(Common.sqrt(3) == 8)
    }
  }
}
