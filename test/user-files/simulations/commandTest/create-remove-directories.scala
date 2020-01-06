package commandTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import commandprotocol.Predef._


class TestSimulation extends Simulation {
	val commandProtocol = command
	.set()
	
	val scn = scenario("CommandSimulation")
		.exec((session: Session) => {
			session.set("path", "")
		})
    .pause(5)
		//STEP 1. Clone a random repo into ${clonePath}
		.exec((session: Session) => {
			val command = "mkdir /tmp/commandtest"
			session.set("command", command)
		})
		.exec(command("CreateDirectory").run())
		.pause(5)
		//STEP 1. Clone a random repo into ${clonePath}
		.exec((session: Session) => {
			val command = "rm -rf /tmp/commandtest"
			session.set("command", command)
		})
		.exec(command("RemoveDirectory").run())
		.pause(5)

	  setUp(
	    scn.inject(atOnceUsers(1))
	  ).protocols(commandProtocol)

	}
