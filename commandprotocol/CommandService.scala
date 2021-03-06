package commandprotocol

import sys.process._

class CommandService() {
	def run(command: String) = {
		command.!
	}

	def run(command: String, path: String) = {
		val repopath = new java.io.File( path )
		sys.process.Process(command, repopath).!
	}
}
