#!/bin/bash

echo "Building with BOM version: $BOM_VERSION"
./gradlew build "-Pbom_version=$BOM_VERSION" --no-daemon
