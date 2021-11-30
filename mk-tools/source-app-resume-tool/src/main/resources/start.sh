#!/bin/bash
echo -n "Please input MK [address]: "
read mkAddress
echo -n "Please input MK [x-service-name]: "
read mkXServiceName

echo "MK address is: $mkAddress"
echo "MK x-service-name is: $mkXServiceName"

java -jar -Dmk.address=$mkAddress -Dmk.xServiceName=$mkXServiceName lib/unit-test.jar

#  使用read命令达到类似bat中的pause命令效果
echo 按任意键完成
read -n 1
echo 结束