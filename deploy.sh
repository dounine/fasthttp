#!/bin/sh
password=$1
if [ $# -gt 0 ]; then
echo "project deploying..." 
mvn clean deploy -Prelease -Dmaven.test.skip=true -Dgpg.passphrase=${password}
else
echo "not null OSS password"
fi
