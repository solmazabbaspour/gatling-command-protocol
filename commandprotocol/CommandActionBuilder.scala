package commandprotocol

import io.gatling.commons.stats.OK
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

class CommandRunActionBuilder(requestName: String) extends ActionBuilder {

	private def components(protocolComponentsRegistry: ProtocolComponentsRegistry) =
		protocolComponentsRegistry.components(CommandProtocol.CommandProtocolKey)

		override def build(ctx: ScenarioContext, next: Action): Action = {
			import ctx._
			val statsEngine = coreComponents.statsEngine
			val commandComponents = components(protocolComponentsRegistry)

			new CommandRun(commandComponents.commandProtocol, statsEngine, next, requestName)
		}
	}

class CommandRun(protocol: CommandProtocol, val statsEngine: StatsEngine, val next: Action, val requestName: String) extends ExitableAction with NameGen {
	override def name: String = requestName

	override def execute(session: Session) = {
		val k = new CommandService()
		val start = System.currentTimeMillis

		val path = session("path").as[String]
		val command = session("command").as[String]
		if(path == ""){
			k.run(command)
		}
		else{
			k.run(command,path)
		}

		val end = System.currentTimeMillis
		val timings = ResponseTimings(start, end)
		statsEngine.logResponse(session, name, timings, OK, None, None)
		next ! session
	}
}
