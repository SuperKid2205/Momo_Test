#!/bin/bash
cd ..

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )/target/classes"
export CLASSPATH=$DIR

java org.momo.Main LIST_BILL
echo -----------------
java org.momo.Main CREATE_BILL FOOD 123.12 01/12/2021 VIFON
echo -----------------
java org.momo.Main LIST_BILL
echo -----------------
java org.momo.Main DELETE_BILL 2
echo -----------------
java org.momo.Main LIST_BILL
echo -----------------

java org.momo.Main EXIT