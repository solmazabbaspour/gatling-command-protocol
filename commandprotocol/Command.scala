package commandprotocol

class Command(requestName: String) {
  def run() = new CommandRunActionBuilder(requestName)
}
