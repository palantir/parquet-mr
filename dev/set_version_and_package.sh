#!/usr/bin/env bash

set -euo pipefail

FWDIR="$(cd "`dirname "${BASH_SOURCE[0]}"`"; pwd)"

get_version() {
  git describe --tags --first-parent
}

set_version_and_package() {
  version=$(get_version)
  ./build/mvn versions:set -DnewVersion="$version"
  ./build/mvn -T1C -DskipTests package
}

set_version_and_package
