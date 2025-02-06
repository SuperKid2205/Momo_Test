#!/bin/bash
cd ..

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )/target/classes"
export CLASSPATH=$DIR

java org.momo.Main CASH_IN 1000
echo -----------------
java org.momo.Main CASH_IN 1000
echo -----------------
java org.momo.Main LIST_BILL
echo -----------------
java org.momo.Main PAY 2
echo -----------------
java org.momo.Main LIST_BILL
echo -----------------
java org.momo.Main EXIT
echo -----------------