package commandprotocol

import io.gatling.commons.stats.{ KO, OK, Status }
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.action.{Action, ExitableAction}
import io.gatling.core.protocol.ProtocolComponentsRegistry
import io.gatling.core.stats.StatsEngine
import io.gatling.core.stats.message.ResponseTimings
import io.gatling.core.structure.ScenarioContext
import io.gatling.core.util.NameGen
import io.gatling.core.session.Expression
import io.gatling.commons.validation.Validation
import io.gatling.core.session.Session
import io.gatling.commons.util.Clock
import io.gatling.commons.util.DefaultClock

class CommandRunActionBuilder(requestName: String) extends ActionBuilder {

	private def components(protocolComponentsRegistry: ProtocolComponentsRegistry) =
		protocolComponentsRegistry.components(CommandProtocol.CommandProtocolKey)

		override def build(ctx: ScenarioContext, next: Action): Action = {
			import ctx._
			val statsEngine = coreComponents.statsEngine
			val commandComponents = components(protocolComponentsRegistry)
			val clock = new DefaultClock

			new CommandRun(commandComponents.commandProtocol, statsEngine, next, requestName, clock )
		}
	}

	class CommandRun(protocol: CommandProtocol, val statsEngine: StatsEngine, val next: Action, val requestName: String, val clock: Clock) extends ExitableAction with NameGen {
		override def name: String = requestName

		override def execute(session: Session) = {
			val k = new CommandService()
			val start = clock.nowMillis
			var message = 0

			val path = session("path").as[String]
			var command = session("command").as[String]

			println("Running command: "  + command )
			if(path == ""){
				message = k.run(command)
			}
			else{
			 message = k.run(command,path)
			}
			val end = clock.nowMillis

			if (message != 0) {
				statsEngine.logResponse(session, requestName, start, end, KO, None, None)
			}
			else{
				statsEngine.logResponse(session, requestName, start, end, OK, None, None)
			}
			next ! session
		}
	}
