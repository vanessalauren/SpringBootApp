set -x
set -e

mkdir ./elasticbeanstalk/
mv config.yml ./elasticbeanstalk/config.yml
cd ./elasticbeanstalk
ls
eb init -f -I "$AWS_ACCESS_KEY_ID" -S "$AWS_SECRET_ACCESS_KEY" -a "sloos" -e "timeinc-docker-dev" --region us-east-1 -s "64bit Amazon Linux running Docker 1.7"