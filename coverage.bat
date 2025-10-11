@echo off
echo ========================================
echo   Generando Reporte de Cobertura
echo ========================================
echo.
call mvnw.cmd clean verify
echo.
echo ========================================
echo   Reporte de Cobertura Generado
echo   Abrir: target\site\jacoco\index.html
echo ========================================
pause