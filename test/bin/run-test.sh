#!/bin/sh
if [[ -d "test/user-files/simulations/commandprotocol" ]]; then
  rm -rf "test/user-files/simulations/commandprotocol"
fi
echo -e "\n\nCopy commandprotocol to simulations\n\n"
cp -r "commandprotocol" "test/user-files/simulations"

./test/bin/gatling.sh -s commandTest.TestSimulation
