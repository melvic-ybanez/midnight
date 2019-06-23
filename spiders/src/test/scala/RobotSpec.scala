import com.github.melvic.midnight.robots.Node._
import com.github.melvic.midnight.robots.Parser._
import fastparse.Parsed.Success
import org.scalatest.{FlatSpec, Matchers}

class RobotSpec extends FlatSpec with Matchers {
  "A '*' User-Agent and empty Disallow" should "allow all bots access to all pages" in {
    val Success(result, _) = parseRobot("User-Agent: *\nDisallow:")
    result should be (Vector(AuthorizeAll))
  }

  it should "also be the case for an empty robot" in {
    val Success(result, _) = parseRobot("")
    result should be (Vector(AuthorizeAll))
  }
}
