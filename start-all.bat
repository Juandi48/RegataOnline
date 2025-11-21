@echo off
REM Ir a la carpeta donde está este archivo
cd /d "%~dp0"

REM --- Levantar BACKEND (Spring Boot) en una ventana nueva ---
start "backend-regata" cmd /k mvn spring-boot:run

REM --- Levantar FRONTEND (Angular) en otra ventana nueva ---
cd frontend\regataonline-frontend
start "frontend-regata" cmd /k npm start
