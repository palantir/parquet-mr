#!/usr/bin/env bash

set -o pipefail
set -x
set -eu

case $(uname) in
  'Linux')
    # protoc is already available on circle nodes. Need to ensure version didn't change
    sudo apt-get install thrift-compiler
    ;;
  'Darwin')
    brew tap homebrew/versions
    brew install protobuf250
    brew install thrift
    ;;
  *) ;;
esac

