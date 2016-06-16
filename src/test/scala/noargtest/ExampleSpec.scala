package noargtest

import java.io.File

import org.scalatest.{Failed, FlatSpec}

class ExampleSpec extends FlatSpec {

  override def withFixture(test: NoArgTest) = {

    super.withFixture(test) match {
      case failed: Failed =>
        val currDir = new File(".")
        val fileNames = currDir.list()
        info("Dir snapshot: " + fileNames.mkString(", "))
        failed
      case other => other
    }
  }

  "This test" should "success" in {
    assert(1 + 1 === 2)
  }

  // Dir snapshot: .idea, build.sbt, project, src, target
  // it should "fail" in {
  //   assert(1 + 1 === 3)
  // }
}
