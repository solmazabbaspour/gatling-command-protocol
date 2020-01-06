# Gatling Command Protocol
[Gatling](https://github.com/gatling/gatling) protocol that allows running load test using commandline commands.

![Status](https://github.com/solmazabbaspour/gatling-command-protocol/workflows/CI/badge.svg)

**Gatling version** 3.3.0


## Usage
- Copy the contents of the [commandprotocol](commandprotocol) under your gatling simulations folder.
- Import the protocol in your scenario using `import commandprotocol.Predef._`
- Create a new command protocol as below:
```
val commandProtocol = script
.set()
```
- Use the script protocol in your scenario
```
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
  ````

## Test
A simple test has been created to showcase the usage of this protocol. This scenario will create a directory under `/tmp/commandtest` and then will remove it in the next command. Under [test/bin](test/bin) Run `./gatling.sh -s commandTest.TestSimulation`


### Gatling Report
![Gatling report](https://user-images.githubusercontent.com/5240896/71823028-a3d38f00-3096-11ea-8b37-8ae60032b13e.png)

Please note that this version marks all responses as `OK`.
