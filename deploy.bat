ECHO OFF

ssh -i "C:\Users\%USERNAME%\.ssh\adote.pem" ec2-user@ec2-18-118-226-194.us-east-2.compute.amazonaws.com "bash ./scripts/backup_backend.sh"  
sftp -b "deploy.sftp" -i "C:\Users\%USERNAME%\.ssh\adote.pem" -P 22 ec2-user@ec2-18-118-226-194.us-east-2.compute.amazonaws.com
ssh -i "C:\Users\%USERNAME%\.ssh\adote.pem" ec2-user@ec2-18-118-226-194.us-east-2.compute.amazonaws.com "bash ./scripts/deploy_backend.sh"
