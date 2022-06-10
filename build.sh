#!/usr/bin/env bash

SCRIPT_PATH="$0"
PROJECT_DIR=$(dirname "$(readlink -m "${SCRIPT_PATH}")")
MVNW="${PROJECT_DIR}/mvnw"
BIN_DIR="${PROJECT_DIR}/bin"

JAVA_HOME=${JAVA_HOME:-}

if [[ ! -d "${JAVA_HOME}" ]]; then
    echo "# JAVA_HOME is not provided. Exit." 1>&2
    exit 1
fi

if [[ ! -x "${JAVA_HOME}/bin/java" ]]; then
    echo "# JAVA_HOME is not java home directory. Exit." 1>&2
    exit 1
fi

echo "# Using JAVA_HOME=${JAVA_HOME}"
JAVA_HOME=${JAVA_HOME} ${MVNW} clean compile package

if [[ ! "$?" == "0" ]]; then
    echo "An error occured when build project" 1>&2
    exit 1
fi

echo "# Build successful"
