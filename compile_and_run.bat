@echo off
echo Creating target directories...
mkdir "target\classes" 2>nul

echo Cleaning target directory...
del /s /q "target\classes\*.*" 2>nul

echo Finding all Java files...
dir /s /b src\main\java\pt\ipleiria\estg\ei\dei\esoft\*.java > java_files.txt
echo Java files found:
type java_files.txt

echo Compiling Java files...
javac -encoding UTF-8 -d "target\classes" -cp "libs\gson-2.10.1.jar;." @java_files.txt

if %errorlevel% == 0 (
    echo Compilation successful!
    echo Running the application...
    java -cp "target\classes;libs\gson-2.10.1.jar" pt.ipleiria.estg.ei.dei.esoft.Main
) else (
    echo Compilation failed!
    pause
)
