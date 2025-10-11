@echo off
echo ========================================
echo   Ejecutando Pruebas Unitarias
echo ========================================
echo.
call mvnw.cmd clean test -Dtest="*ServiceTest"
echo.
echo ========================================
echo   Pruebas Unitarias Completadas
echo ========================================
pause