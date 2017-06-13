#!/usr/bin/env bash

set -o pipefail
set -x
set -eu

case $(uname) in
  'Linux')
    curl -OL https://github.com/google/protobuf/releases/download/v3.3.0/protoc-3.3.0-linux-x86_64.zip
    unzip protoc-3.3.0-linux-x86_64.zip -d protoc3
    sudo mv protoc3/bin/protoc /usr/bin/protoc
    rm -rf protoc3 protoc-3.3.0-linux-x86_64.zip
    sudo apt-get install thrift-compiler
    ;;
  'Darwin')
    brew install protobuf
    brew install thrift
    ;;
  *) ;;
esac

