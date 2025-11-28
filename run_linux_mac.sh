#!/bin/bash
echo "📧 Building and running..."
mvn clean package exec:java -Dexec.mainClass=MainUI