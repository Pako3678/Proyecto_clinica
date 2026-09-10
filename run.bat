@echo off
title Clinica API - Spring Boot
echo ========================================================
echo   Iniciando Clinica (Spring Boot + Frontend)...
echo ========================================================

powershell -ExecutionPolicy Bypass -Command ^
  "$jars = (Get-ChildItem -Path \"$HOME\.m2\repository\" -Recurse -Filter \"*.jar\" | Where-Object { $_.FullName -notmatch '1\.7\.36' -and $_.FullName -notmatch 'test' } | Select-Object -ExpandProperty FullName) -join ';'; ^
   $sources = (Get-ChildItem -Path 'src\main\java' -Recurse -Filter '*.java' | Select-Object -ExpandProperty FullName); ^
   javac --release 21 -parameters -proc:none -cp \"target\classes;$jars\" -d target\classes $sources; ^
   if (Test-Path 'src\main\resources\static') { Copy-Item -Path 'src\main\resources\static\*' -Destination 'target\classes\static\' -Recurse -Force -ErrorAction SilentlyContinue }; ^
   $cp = \"target\classes;$jars\"; ^
   java -cp $cp com.clinica.api.ClinicaApiApplication"

pause
