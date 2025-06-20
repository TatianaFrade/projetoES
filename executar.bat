@echo off
echo Criando diretório lib caso não exista...
if not exist lib mkdir lib

echo Baixando a biblioteca Gson...
powershell -Command "& { Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar' -OutFile 'lib\gson-2.10.1.jar' }"

echo Compilando o projeto...
javac -encoding UTF-8 -cp "lib\gson-2.10.1.jar;." -d "target\classes" src\main\java\pt\ipleiria\estg\ei\dei\esoft\*.java

echo Executando a aplicacao...
java -cp "target\classes;lib\gson-2.10.1.jar" pt.ipleiria.estg.ei.dei.esoft.Main

pause
