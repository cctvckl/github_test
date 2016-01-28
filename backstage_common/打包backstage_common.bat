cmd /c title %~nx0

cd /d %~dp0

set jarname=backstage_common.jar

if not exist build mkdir build
del build\%jarname%

for /r bin %%a in (.) do @if exist "%%a\.svn" rd /s /q "%%a\.svn"
jar cvf build/%jarname% -C bin com -C bin org

pause
