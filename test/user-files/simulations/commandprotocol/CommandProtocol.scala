package commandprotocol

import akka.actor.ActorSystem
import io.gatling.core
import io.gatling.core.CoreComponents
import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.protocol.{Protocol, ProtocolComponents, ProtocolKey}
import io.gatling.commons.validation.Validation
import io.gatling.core.session.Session

class CommandProtocol() extends Protocol {
	type Components = CommandComponents
}

object CommandProtocol {
	def apply() = new CommandProtocol()

	val CommandProtocolKey = new ProtocolKey {

		type Protocol = CommandProtocol
		type Components = CommandComponents

		override def protocolClass: Class[core.protocol.Protocol] = classOf[CommandProtocol].asInstanceOf[Class[io.gatling.core.protocol.Protocol]]
		override def defaultProtocolValue(configuration: GatlingConfiguration): CommandProtocol = throw new IllegalStateException("Can't provide a default value for CommandProtocol")
		override def newComponents(system: ActorSystem, coreComponents: CoreComponents): CommandProtocol => CommandComponents = {
			commandProtocol => CommandComponents(commandProtocol)
		}
	}
}

case class CommandComponents(commandProtocol: CommandProtocol) extends ProtocolComponents {
	def onStart: Option[Session => Session] = None
	def onExit: Option[Session => Unit] = None
}

case class CommandProtocolBuilder() {
	def build() = CommandProtocol()
}

object CommandProtocolBuilder {
	def set() = CommandProtocolBuilder()
}
