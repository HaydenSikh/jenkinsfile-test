#!/bin/bash

FLAG=${1:-}

if [ "$1" == "--remove-old-launcher" ]; then
  RM_OLD_BIN=1
  shift
fi

root=$(
  cd $(dirname $(readlink $0 || echo $0))/..
  /bin/pwd
)

sbtver=0.13.9
sbtjar=sbt-launch.jar
sbtmd5=767d963ed266459aa8bf32184599786d

download () {
  ALREADY_TRIED=${1:-0}

  if [ ! -f $sbtjar ]; then
    echo 'downloading '$sbtjar 1>&2
    curl -L -O https://dl.bintray.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch/$sbtver/$sbtjar
  fi

  test -f $sbtjar || exit 1
}

download


test -f ~/.sbtconfig && . ~/.sbtconfig
java -ea                          \
  $SBT_OPTS                       \
  $JAVA_OPTS                      \
  -Djava.net.preferIPv4Stack=true \
  -Dfile.encoding=UTF-8           \
  -XX:+AggressiveOpts             \
  -XX:+UseParNewGC                \
  -XX:+UseConcMarkSweepGC         \
  -XX:+CMSParallelRemarkEnabled   \
  -XX:+CMSClassUnloadingEnabled   \
  -XX:MaxPermSize=1024m           \
  -XX:SurvivorRatio=128           \
  -XX:MaxTenuringThreshold=0      \
  -Xss8M                          \
  -Xms512M                        \
  -Xmx1G                          \
  -server                         \
  -jar $sbtjar "$@"
