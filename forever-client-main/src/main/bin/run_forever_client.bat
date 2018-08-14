@echo on

echo "forever-client-main on...."



set "CURRENT_DIR=%cd%"
echo "%CURRENT_DIR%"
echo "%cd%"

set tempPath=./lib
set "PATH=%PATH%;%tempPath%"
echo "%PATH%"

set MAINCLASS=com.gaoan.forever.App

java -jar forever-client-main-1.0-SNAPSHOT.jar

pause


:end