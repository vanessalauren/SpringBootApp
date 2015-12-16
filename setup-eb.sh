set -x
set -e

mkdir /home/ubuntu/.aws
touch /home/ubuntu/.aws/config
chmod 600 /home/ubuntu/.aws/config
echo "[profile eb-cli]" > /home/ubuntu/.aws/config
echo "aws_access_key_id=$AWS_ACCESS_KEY_ID" >> /home/ubuntu/.aws/config
echo "aws_secret_access_key=$AWS_SECRET_ACCESS_KEY" >> /home/ubuntu/.aws/config
mkdir ./elasticbeanstalk/
mv config.yml ./elasticbeanstalk/config.yml
cd ./elasticbeanstalk
ls
eb init sloos --profile default -r us-east-1 -p Docker -k ti-use1-bdprod-docker -i -v