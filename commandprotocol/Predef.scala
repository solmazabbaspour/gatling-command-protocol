package commandprotocol

object Predef {
  val command = CommandProtocolBuilder
  implicit def commandBuilderToProtocol(builder: CommandProtocolBuilder): CommandProtocol = builder.build()
  def command(name: String) = new Command(name)
}
