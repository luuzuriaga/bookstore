@echo off
echo ========================================
echo   Construyendo Aplicacion
echo ========================================
echo.
call mvnw.cmd clean package
echo.
echo ========================================
echo   Build Completado
echo   JAR generado en: target\
echo ========================================
pause