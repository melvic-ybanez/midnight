import com.github.melvic.midnight.robots.Node.{AllBots, DisallowNone}
import org.scalatest.{FlatSpec, Matchers}
import com.github.melvic.midnight.robots.Parser._
import fastparse.Parsed.Success

import scala.collection.mutable.ArrayBuffer

class RobotSpec extends FlatSpec with Matchers {
  "A '*' User-Agent and empty Disallow" should "allow all bots access to all pages" in {
    val Success(Seq((bots, disallowed)), _) = parseRobot("User-Agent: *\nDisallow:")
    bots should be (Seq(AllBots))
    disallowed should be (Seq(DisallowNone))
  }
}
