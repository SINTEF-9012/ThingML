#!/bin/bash

rm cliStdo.log &> /dev/null

timeout -s SIGINT 6 mvn -q -f ServerEmptyCfg/pom.xml exec:java > srvStdo.log 2> srvStdr.log&
sleep 2
timeout -s SIGINT 5 ./ClientEmptyCfg/ClientEmptyCfg > cliStdo.log 2> cliStdr.log&

sleep 6

#printf "Cli stdo:\n\n"
cat cliStdo.log
#printf "\nCli stdr:\n\n"
>&2 cat cliStdr.log

#printf "\n\nSrv stdo:\n\n"
>&2 cat srvStdo.log
#printf "\nSrv stdr:\n\n"
>&2 cat srvStdr.log