#!/bin/bash
cd ..

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )/target/classes"
export CLASSPATH=$DIR

java org.momo.Main LIST_BILL
echo -----------------
java org.momo.Main UPDATE_BILL 1  ELECTRIC1 200 31/03/2021 EVN
echo -----------------
java org.momo.Main LIST_BILL
echo -----------------
java org.momo.Main VIEW_BILL 1
echo -----------------

java org.momo.Main EXIT