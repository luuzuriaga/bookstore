@echo off
echo ========================================
echo   Ejecutando Todas las Pruebas
echo ========================================
echo.
call mvnw.cmd clean test verify
echo.
echo ========================================
echo   Todas las Pruebas Completadas
echo ========================================
pause