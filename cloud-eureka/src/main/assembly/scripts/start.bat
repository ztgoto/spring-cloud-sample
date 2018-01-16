@echo off & setlocal enabledelayedexpansion

cd %~dp0

java -Xms1g -Xmx1024m -XX:MaxPermSize=2g -jar ../lib/${project.build.finalName}.${project.packaging} --spring.profiles.active=${spring.profiles.active}
