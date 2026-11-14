#!/bin/bash
# Q1 - Build and run the MapReduce keyword search
set -e
KEYWORD="${1:-africa}"
cd "$(dirname "$0")"

if ! command -v javac >/dev/null || ! command -v mvn >/dev/null; then
  sudo apt update && sudo apt install -y openjdk-17-jdk maven wget
fi
if [ ! -d "$HOME/hadoop" ]; then
  wget -O /tmp/hadoop.tar.gz https://archive.apache.org/dist/hadoop/common/hadoop-3.3.6/hadoop-3.3.6.tar.gz
  tar -xzf /tmp/hadoop.tar.gz -C "$HOME" && mv "$HOME/hadoop-3.3.6" "$HOME/hadoop"
fi
export JAVA_HOME="$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")"
export HADOOP_HOME="$HOME/hadoop"
export PATH="$PATH:$HADOOP_HOME/bin"

(cd keyword-search && mvn -q clean package)
rm -rf output
hadoop jar keyword-search/target/keyword-search-1.0.jar za.eduvos.itbda.KeywordSearchDriver corpus output "$KEYWORD"
cat output/part-r-00000
