@echo off

@REM rem definition du chemin
set TEMP_DIR=D:/JASON/programmes/JSP/proxy_configuration/temp
set WEB_DIR=D:/JASON/programmes/JSP/proxy_configuration/web
set LIB_DIR=D:/JASON/programmes/JSP/proxy_configuration/lib
set XML_FILE=D:/JASON/programmes/JSP/proxy_configuration/web.xml
set SRC_DIR=D:/JASON/programmes/JSP/proxy_configuration/src
set APPLICATION=proxy-configuration
set DEPLOYMENT_DIR=C:/Program Files/Apache Software Foundation/Tomcat 10.1/webapps/

@REM suppression du dossier temp
rmdir /s /q "%TEMP_DIR%"

@REM creation du dossier temp
mkdir "%TEMP_DIR%"
mkdir "%TEMP_DIR%/WEB-INF"
mkdir "%TEMP_DIR%/WEB-INF/classes"
mkdir "%TEMP_DIR%/WEB-INF/lib"

@REM copie du fichier haproxy.cfg
copy "haproxy.cfg" "%TEMP_DIR%/WEB-INF/classes/"

@REM copie des dossier
xcopy /E "%WEB_DIR%" "%TEMP_DIR%"  
copy "%XML_FILE%" "%TEMP_DIR%/WEB-INF/"
xcopy /E "%LIB_DIR%" "%TEMP_DIR%/WEB-INF/lib"

@REM compilation de java

FOR /R "%SRC_DIR%" %%F IN (*.java) DO (
    javac -sourcepath "%SRC_DIR%" -d "%TEMP_DIR%/WEB-INF/classes" -cp "%TEMP_DIR%/WEB-INF/lib/*" "%%F"
)

@REM transformation en .war
jar -cvf "%APPLICATION%.war" -C "%TEMP_DIR%" .

@REM deployemnt vers le site web
copy "%APPLICATION%.war"  "%DEPLOYMENT_DIR%"


rem Arrêt de Tomcat s'il est en cours d'exécution
@REM call "C:\Program Files\Apache Software Foundation\Tomcat 10.1\bin\shutdown.bat"

rem Attente de quelques secondes pour permettre à Tomcat de s'arrêter
@REM timeout /t 5 /nobreak > nul

rem Démarrage de Tomcat
@REM "C:\Program Files\Apache Software Foundation\Tomcat 10.1\bin\startup.bat"

echo Tomcat a été redémarré.
