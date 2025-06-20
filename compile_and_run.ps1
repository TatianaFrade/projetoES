# Create target directories if they don't exist
New-Item -ItemType Directory -Force -Path "target\classes"

# Set classpath for compilation
$CLASSPATH = "libs\gson-2.10.1.jar;."

# Clean target directory
Remove-Item -Path "target\classes\*" -Recurse -Force -ErrorAction SilentlyContinue

# Compile all Java files
Write-Host "Compiling Java files..." -ForegroundColor Green
javac -encoding UTF-8 -d "target\classes" -cp $CLASSPATH "src\main\java\pt\ipleiria\estg\ei\dei\esoft\Main.java" $(Get-ChildItem -Path "src\main\java\pt\ipleiria\estg\ei\dei\esoft\" -Filter "*.java" -Recurse | ForEach-Object { $_.FullName })

# Check if compilation was successful
if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation successful!" -ForegroundColor Green
    
    # Run the application
    Write-Host "Running the application..." -ForegroundColor Green
    java -cp "target\classes;libs\gson-2.10.1.jar" pt.ipleiria.estg.ei.dei.esoft.Main
} else {
    Write-Host "Compilation failed!" -ForegroundColor Red
}
