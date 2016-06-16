package selenium

import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.selenium._
import org.scalatest.concurrent.Eventually._
import org.scalatest.time._
import scala.concurrent.duration._

class BlogSpec extends FlatSpec with Matchers with WebBrowser {

  implicit val webDriver: WebDriver = new HtmlUnitDriver

  val host = "http://www.scalatest.org/user_guide/using_selenium"

  "The blog app home page" should "have the correct title" in {
    go to (host)
    pageTitle should be ("ScalaTest")
  }

  "google" should "have the correct title" in {
    go to "http://www.google.com"
    click on "q"
    textField("q").value = "hoge"
    submit()
    // Google's search is rendered dynamically with JavaScript.
    eventually { pageTitle should be ("hoge - Google 検索") }

    click on linkText("click here!")
    textField("q").value = "Cheese!"
    textField("q").value should be ("Cheese!")
    textArea("body").value = "I saw something cool today!"
    textArea("body").value should be ("I saw something cool today!")
    radioButtonGroup("group1").value = "Option 2"
    radioButtonGroup("group1").value should be ("Option 2")
    radioButtonGroup("group1").selection should be (Some("Option 2"))
    checkbox("cbx1").select()
    checkbox("cbx1").clear()
    checkbox("cbx1").isSelected should be (true)
    singleSel("select1").value = "option2"
    singleSel("select1").value should be ("option2")
    singleSel("select1").selection should be (Some("option2"))
    multiSel("select2").values += "option5"
    multiSel("select2").values += "option6"
    multiSel("select2").clear("option5")
    multiSel("select2").clearAll()
    multiSel("select2").values should have size 2
    multiSel("select2").values(0) should be ("option5")
    multiSel("select2").values(1) should be ("option6")
    switch to frame(0)
    switch to frame("name")
    switch to window(windowHandle)
    switch to activeElement
    switch to defaultContent
    goBack()
    goForward()
    reloadPage()
    add cookie ("cookie_name", "cookie_value")
    cookie("cookie_name").value should be ("cookie_value")
    // cookie(name: String, value: String, domain: String, path: String, expiry: Date, secure: Boolean)
    cookie("cookie_name").value  // Read cookie's value
    cookie("cookie_name").path   // Read cookie's path
    cookie("cookie_name").expiry // Read cookie's expiry
    cookie("cookie_name").domain // Read cookie's domain
    cookie("cookie_name").secure // Read cookie's isSecure flag
    delete cookie "cookie_name"
    delete all cookies
    implicitlyWait(Span(10, Seconds))
    pageSource
    currentUrl
    val file = capture
    capture to "MyScreenShot.png"
    capture to "MyScreenShot"
    setCaptureDir("/home/your_name/screenshots")
    withScreenshot {
      assert("Gold" == "Silver", "Expected gold, but got silver")
    }

    class HomePage extends org.scalatest.selenium.Page {
      val url = "localhost:9000/index.html"
    }

    val homePage = new HomePage
    go to homePage
    val result1 = executeScript("return document.title;")
    result1 should be ("Test title")
    val result2 = executeScript("return 'Hello ' + arguments[0]", "ScalaTest")
    result2 should be ("Hello ScalaTest")

    val script =
      """
        |var callback = arguments[arguments.length - 1];
        |window.setTimeout(function() {callback('Hello ScalaTest')}, 500);
      """.stripMargin
    setScriptTimeout(1 second)
    val result = executeAsyncScript(script)
    result should be ("Hello ScalaTest")

    val ele: Option[Element] = find("q")

    val eles: IndexedSeq[Element] = findAll(className("small")).toIndexedSeq
    for (e <- eles; if e.tagName != "input")
      e should be ('displayed)
    val textFields = eles filter { _.isInstanceOf[TextField] }

    close()
    quit()
  }
}

