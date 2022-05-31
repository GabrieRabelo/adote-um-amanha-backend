ECHO OFF

echo Build Stage
call mvn install -DskipTests
echo Build error level: %ERRORLEVEL%

if ["%ERRORLEVEL%"]==["1"] (
	GOTO :EOF
)

echo ~ 
echo Deploy stage
echo Establishing connection...
ssh -i "C:\Users\%USERNAME%\.ssh\adote.pem" ec2-user@adote-um-amanha.duckdns.org "bash ./scripts/%1/backup_backend.sh"  

sftp -b "deploy%1.sftp" -i "C:\Users\%USERNAME%\.ssh\adote.pem" -P 22 ec2-user@adote-um-amanha.duckdns.org

ssh -i "C:\Users\%USERNAME%\.ssh\adote.pem" ec2-user@adote-um-amanha.duckdns.org "bash ./scripts/%1/deploy_backend.sh"
