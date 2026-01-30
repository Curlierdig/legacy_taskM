@echo off
setlocal enabledelayedexpansion
echo Setting JAVA_HOME for this session...
echo.

REM Try known Java locations first (most reliable)
if exist "C:\Program Files\Java\jdk-21\bin\java.exe" (
    set "JAVA_HOME=C:\Program Files\Java\jdk-21"
    goto :found
)
if exist "C:\Program Files\Java\jdk-17\bin\java.exe" (
    set "JAVA_HOME=C:\Program Files\Java\jdk-17"
    goto :found
)

set "JAVA_PATH="
for /f "tokens=2 delims==" %%i in ('java -XshowSettings:properties -version 2^>^&1 ^| findstr "java.home"') do set "JAVA_PATH=%%i"

if not defined JAVA_PATH goto :try_common

REM Remove quotes and spaces
set "JAVA_PATH=!JAVA_PATH:"=!"
set "JAVA_PATH=!JAVA_PATH: =!"

REM java.home is usually JDK root (e.g. C:\Program Files\Java\jdk-21)
REM If it ends with \jre, we need parent dir; otherwise use as-is
echo !JAVA_PATH! | findstr /i "\\jre$" >nul
if !errorlevel! equ 0 (
    for %%i in ("!JAVA_PATH!\.") do set "JAVA_HOME=%%~dpi"
    set "JAVA_HOME=!JAVA_HOME:~0,-1!"
) else (
    set "JAVA_HOME=!JAVA_PATH!"
)
goto :found

:try_common
echo Could not detect Java path. Trying common locations...
if exist "C:\Program Files\Java\jdk-21" (
    set "JAVA_HOME=C:\Program Files\Java\jdk-21"
    goto :found
)
if exist "C:\Program Files\Java\jdk-17" (
    set "JAVA_HOME=C:\Program Files\Java\jdk-17"
    goto :found
)
echo ERROR: Java not found. Please set JAVA_HOME manually. See FIX_JAVA_HOME.md
pause
exit /b 1

:found
echo Found Java: !JAVA_HOME!
echo.

REM Export to caller: endlocal passes our JAVA_HOME out
for /f "delims=" %%v in ("!JAVA_HOME!") do endlocal & set "JAVA_HOME=%%v" & set "PATH=%%v\bin;%PATH%"

echo JAVA_HOME is now set for this session.
echo To set permanently, see FIX_JAVA_HOME.md
echo Now run: .\mvnw.cmd spring-boot:run
echo.
