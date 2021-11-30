#!/bin/bash
echo -n "Please input MK address:"
read mkAddress
echo "MK address is: $mkAddress"

java -jar lib/unit-test.jar -Dmk.address=$mkAddress


#  使用read命令达到类似bat中的pause命令效果
echo 按任意键完成
read -n 1
echo 结束