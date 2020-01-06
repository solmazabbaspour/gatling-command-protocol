package commandprotocol

import io.gatling.commons.model.Credentials
import io.gatling.core.CoreComponents
import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.protocol.{ Protocol, ProtocolKey }



object CommandProtocol {
		val CommandProtocolKey: ProtocolKey[CommandProtocol, CommandComponents] = new ProtocolKey[CommandProtocol, CommandComponents] {
     def protocolClass: Class[io.gatling.core.protocol.Protocol] = classOf[CommandProtocol].asInstanceOf[Class[io.gatling.core.protocol.Protocol]]
     def defaultProtocolValue(configuration: GatlingConfiguration): CommandProtocol = throw new IllegalStateException("Can't provide a default value for CommandProtocol")
     def newComponents(coreComponents: CoreComponents): CommandProtocol => CommandComponents = {
       commandProtocol => CommandComponents(commandProtocol)
     }
   }
}


case class CommandProtocol() extends Protocol {
	type Components = CommandComponents
}

case class CommandProtocolBuilder() {
	def build() = CommandProtocol()
}

object CommandProtocolBuilder {
	def set() = CommandProtocolBuilder()
}
