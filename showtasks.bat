call .\runcrud
if "%ERRORLEVEL%" == "0" goto openbrowser
echo.
echo Runcrud has errors - breaking work
goto fail

:openbrowser
start http://localhost:8080/crud/v1/tasks/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo.
echo Problems with opening browser
goto fail

:fail
echo.
echo There were errors
