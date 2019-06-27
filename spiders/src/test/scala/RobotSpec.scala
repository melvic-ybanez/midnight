import com.github.melvic.midnight.robots.Node._
import com.github.melvic.midnight.robots.Parser._
import fastparse.Parsed.Success
import org.scalatest.{FlatSpec, Matchers}

class RobotSpec extends FlatSpec with Matchers {
  import RobotSpec._

  "A '*' User-Agent and empty Disallow" should "allow all bots access to all pages" in {
    val Success(result, _) = parseRobot(
        "User-Agent: * \n" +
        "Disallow:")
    result should be (Vector(AuthorizeAll))
  }

  it should "also be the case for an empty robot" in {
    val Success(result, _) = parseRobot("")
    result should be (Vector(AuthorizeAll))
  }

  it should "disallow all robots the specified pages" in {
    val input = makeString(
      "User-Agent: *",
      "Disallow: /cgi-bin/",
      "Disallow: /tmp/",
      "Disallow: /junk/")
    val Success(Vector(result), _) = parseRobot(input)
    result should be (Rule(
      AllBots,
      BlackList(Vector("/cgi-bin/", "/tmp/", "/junk/"))))
  }
}

object RobotSpec {
  def makeString(lines: String*) = lines.mkString("\n")
}
