#!/bin/bash

echo "Getting latest MongoDB driver BOM version..."
latest_bom_version=$(curl -s https://repo1.maven.org/maven2/org/mongodb/mongodb-driver-bom/maven-metadata.xml | grep -oPm1 "(?<=<latest>)[^<]+")

if [ -z "$latest_bom_version" ]; then 
    latest_bom_version="5.4.0"
    echo "Failed to fetch latest version, using fallback: $latest_bom_version"
else
    echo "Using BOM version: $latest_bom_version"
fi

export BOM_VERSION=$latest_bom_version
echo "BOM_VERSION=$latest_bom_version" >> $GITHUB_ENV
