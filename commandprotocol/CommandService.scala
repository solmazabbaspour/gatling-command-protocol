package commandprotocol

import sys.process._

class CommandService() {
	def run(command: String) = {
		println("Running command: "  + command )
		command.!
	}
}
