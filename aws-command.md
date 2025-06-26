# AWS CLI Commands

Example:
Crear una instancia EC2 con un script de usuario para instalar Apache y PHP.

Security Group:
```text
aws ec2 create-security-group \
  --endpoint-url http://localhost:4566 \
  --group-name my-sg \
  --description "My test SG"
```


🛠️ Opción usando AWS CLI estándar con endpoint
```text
aws ec2 run-instances \
  --endpoint-url http://localhost:4566 \
  --region us-east-1 \
  --image-id ami-df5de72bdb3b \
  --count 1 \
  --instance-type t3.nano \
  --key-name my-key \
  --security-group-ids <SECURITY_GROUP_ID> \
  --user-data file://./user_script.sh
```

## S3

#### Create a new S3 bucket
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 s3 mb s3://<bucket_name>
```

#### Upload a file to an S3 bucket
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 s3 cp <local_file_path> s3://<bucket_name>/<object_key>
```

#### Download a file from an S3 bucket
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 s3 cp s3://<bucket_name>/<object_key> <local_file_path>
```

#### List all objects in an S3 bucket
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 s3 ls s3://<bucket_name>
```

#### Delete an object from an S3 bucket
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 s3 rm s3://<bucket_name>/<object_key>
```

#### List all S3 buckets
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 s3 ls
```

## EC2

#### Launch a new EC2 instance
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ec2 run-instances \
    --image-id ami-12345678 \
    --count 1 \
    --instance-type t2.micro \
    --key-name <key_pair_name> \
    --security-group-ids <security_group_id> \
    --subnet-id <subnet_id>
```

#### Stop an EC2 instance
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ec2 stop-instances --instance-ids <instance_id>
```

#### Start an EC2 instance
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ec2 start-instances --instance-ids <instance_id>
```

#### Terminate an EC2 instance
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ec2 terminate-instances --instance-ids <instance_id>
```

#### List all EC2 instances
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ec2 describe-instances --query 'Reservations[*].Instances[*].[InstanceId,State.Name,InstanceType,PublicIpAddress]' --output table
```

## DynamoDB

#### Create a DynamoDB table
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 dynamodb create-table \
    --table-name <table_name> \
    --attribute-definitions AttributeName=<attribute_name>,AttributeType=S \
    --key-schema AttributeName=<attribute_name>,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
```

#### List all tables in DynamoDB
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 dynamodb list-tables
```

#### Describe a DynamoDB table
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 dynamodb describe-table --table-name <table_name>
```

#### Delete a DynamoDB table
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 dynamodb delete-table --table-name <table_name>
```

#### Put an item into a DynamoDB table
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 dynamodb put-item \
    --table-name <table_name> \
    --item '{"<attribute_name>": {"S": "<value>"}}'
```

#### Get an item from a DynamoDB table
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 dynamodb get-item \
    --table-name <table_name> \
    --key '{"<attribute_name>": {"S": "<value>"}}'
```

#### Update an item in a DynamoDB table
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 dynamodb update-item \
    --table-name <table_name> \
    --key '{"<attribute_name>": {"S": "<value>"}}' \
    --update-expression "SET <attribute_name> = :val" \
    --expression-attribute-values '{":val": {"S": "<new_value>"}}'
```

#### Delete an item from a DynamoDB table
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 dynamodb delete-item \
    --table-name <table_name> \
    --key '{"<attribute_name>": {"S": "<value>"}}'
```

## IAM

#### Create a new IAM user
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam create-user --user-name <user_name>
```

#### List all IAM users
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam list-users
```

#### Attach a policy to an IAM user
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam attach-user-policy \
    --user-name <user_name> \
    --policy-arn arn:aws:iam::aws:policy/<policy_name>
```

#### Detach a policy from an IAM user
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam detach-user-policy \
    --user-name <user_name> \
    --policy-arn arn:aws:iam::aws:policy/<policy_name>
```

#### Delete an IAM user
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam delete-user --user-name <user_name>
```

#### Create an IAM role
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam create-role \
    --role-name <role_name> \
    --assume-role-policy-document file://trust-policy.json
```

#### Attach a policy to an IAM role
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam attach-role-policy \
    --role-name <role_name> \
    --policy-arn arn:aws:iam::aws:policy/<policy_name>
```

#### Detach a policy from an IAM role
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam detach-role-policy \
    --role-name <role_name> \
    --policy-arn arn:aws:iam::aws:policy/<policy_name>
```

#### Delete an IAM role
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam delete-role --role-name <role_name>
```

#### List all IAM roles
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam list-roles
```

#### Get details of an IAM role
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam get-role --role-name <role_name>
```

#### Create an IAM policy
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam create-policy \
    --policy-name <policy_name> \
    --policy-document file://policy.json
```

#### List all IAM policies
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam list-policies
```

#### Get details of an IAM policy
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam get-policy --policy-arn arn:aws:iam::aws:policy/<policy_name>
```

#### Delete an IAM policy
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam delete-policy --policy-arn arn:aws:iam::aws:policy/<policy_name>
```

#### Create an IAM access key for a user
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam create-access-key --user-name <user_name>
```

#### List all access keys for a user
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam list-access-keys --user-name <user_name>
```

#### Delete an IAM access key
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam delete-access-key --user-name <user_name> --access-key-id <access_key_id>
```

#### Update an IAM user's password
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam update-login-profile \
    --user-name <user_name> \
    --password <new_password>
```

#### List IAM policies attached to a user
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam list-attached-user-policies --user-name <user_name>
```

#### List IAM policies attached to a role
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam list-attached-role-policies --role-name <role_name>
```

#### List IAM policies attached to a group
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam list-attached-group-policies --group-name <group_name>
```

#### Create an IAM group
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam create-group --group-name <group_name>
```

#### List all IAM groups
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam list-groups
```

#### Add a user to an IAM group
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam add-user-to-group \
    --group-name <group_name> \
    --user-name <user_name>
```

#### Remove a user from an IAM group
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam remove-user-from-group \
    --group-name <group_name> \
    --user-name <user_name>
```

#### Delete an IAM group
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam delete-group --group-name <group_name>
```

#### List all users in an IAM group
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam get-group --group-name <group_name>
```

#### List all policies attached to a group
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 iam list-attached-group-policies --group-name <group_name>
```

## CloudFormation

#### Create a CloudFormation stack
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 cloudformation create-stack \
    --stack-name <stack_name> \
    --template-body file://template.yaml \
    --parameters ParameterKey=<parameter_key>,ParameterValue=<parameter_value>
```

#### Update a CloudFormation stack
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 cloudformation update-stack \
    --stack-name <stack_name> \
    --template-body file://template.yaml \
    --parameters ParameterKey=<parameter_key>,ParameterValue=<parameter_value>
```

#### Delete a CloudFormation stack
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 cloudformation delete-stack --stack-name <stack_name>
```

#### List all CloudFormation stacks
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 cloudformation list-stacks --stack-status-filter CREATE_COMPLETE UPDATE_COMPLETE
```

#### Describe a CloudFormation stack
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 cloudformation describe-stacks --stack-name <stack_name>
```

#### Get CloudFormation stack events
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 cloudformation describe-stack-events --stack-name <stack_name>
```

#### Get CloudFormation stack resources
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 cloudformation describe-stack-resources --stack-name <stack_name>
```

## Lambda

#### Create a Lambda function
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 lambda create-function \
    --function-name <function_name> \
    --runtime nodejs14.x \
    --role arn:aws:iam::<account_id>:role/<role_name> \
    --handler index.handler \
    --zip-file fileb://function.zip
```

#### Update a Lambda function
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 lambda update-function-code \
    --function-name <function_name> \
    --zip-file fileb://function.zip
```

#### Invoke a Lambda function
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 lambda invoke \
    --function-name <function_name> \
    --payload '{"key": "value"}' \
    response.json
```

#### List all Lambda functions
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 lambda list-functions
```

#### Delete a Lambda function
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 lambda delete-function --function-name <function_name>
```

#### Get details of a Lambda function
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 lambda get-function --function-name <function_name>
```

#### Add a permission to a Lambda function
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 lambda add-permission \
    --function-name <function_name> \
    --principal <service_or_account> \
    --statement-id <statement_id> \
    --action lambda:InvokeFunction
```

#### Remove a permission from a Lambda function
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 lambda remove-permission \
    --function-name <function_name> \
    --statement-id <statement_id>
```

#### List permissions for a Lambda function
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 lambda get-policy --function-name <function_name>
```

#### Create an alias for a Lambda function
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 lambda create-alias \
    --function-name <function_name> \
    --name <alias_name> \
    --function-version <version>
```

#### Update a Lambda alias
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 lambda update-alias \
    --function-name <function_name> \
    --name <alias_name> \
    --function-version <version>
```

#### Delete a Lambda alias
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 lambda delete-alias \
    --function-name <function_name> \
    --name <alias_name>
```

## CloudWatch

#### Create a CloudWatch log group
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 logs create-log-group --log-group-name <log_group_name>
```

#### Create a CloudWatch log stream
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 logs create-log-stream \
    --log-group-name <log_group_name> \
    --log-stream-name <log_stream_name>
```

#### Put log events into a CloudWatch log stream
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 logs put-log-events \
    --log-group-name <log_group_name> \
    --log-stream-name <log_stream_name> \
    --log-events timestamp=$(date +%s%3N),message="Log message"
```

#### Get log events from a CloudWatch log stream
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 logs get-log-events \
    --log-group-name <log_group_name> \
    --log-stream-name <log_stream_name>
```

#### List all CloudWatch log groups
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 logs describe-log-groups
```

#### List all CloudWatch log streams in a log group
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 logs describe-log-streams --log-group-name <log_group_name>
```

#### Create a CloudWatch metric
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 cloudwatch put-metric-data \
    --namespace <namespace> \
    --metric-name <metric_name> \
    --value <metric_value> \
    --unit <unit>
```

#### List all CloudWatch metrics
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 cloudwatch list-metrics --namespace <namespace>
```

## Route 53

#### Create a Route 53 hosted zone
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 route53 create-hosted-zone \
    --name <domain_name> \
    --caller-reference <unique_string>
```

#### List all Route 53 hosted zones
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 route53 list-hosted-zones
```

#### Create a record set in a Route 53 hosted zone
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 route53 change-resource-record-sets \
    --hosted-zone-id <hosted_zone_id> \
    --change-batch '{
        "Changes": [{
            "Action": "CREATE",
            "ResourceRecordSet": {
                "Name": "<record_name>",
                "Type": "A",
                "TTL": 300,
                "ResourceRecords": [{"Value": "<ip_address>"}]
            }
        }]
    }'
```

#### Delete a record set in a Route 53 hosted zone
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 route53 change-resource-record-sets \
    --hosted-zone-id <hosted_zone_id> \
    --change-batch '{
        "Changes": [{
            "Action": "DELETE",
            "ResourceRecordSet": {
                "Name": "<record_name>",
                "Type": "A",
                "TTL": 300,
                "ResourceRecords": [{"Value": "<ip_address>"}]
            }
        }]
    }'
```

#### Get details of a Route 53 hosted zone
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 route53 get-hosted-zone --id <hosted_zone_id>
```

#### List all record sets in a Route 53 hosted zone
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 route53 list-resource-record-sets --hosted-zone-id <hosted_zone_id>
```

#### Delete a Route 53 hosted zone
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 route53 delete-hosted-zone --id <hosted_zone_id>
```

## SNS

#### Create an SNS topic
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sns create-topic --name <topic_name>
```

#### List all SNS topics
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sns list-topics
```

#### Publish a message to an SNS topic
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sns publish \
    --topic-arn arn:aws:sns:us-east-1:<account_id>:<topic_name> \
    --message "Hello, SNS!"
```

#### Subscribe an email endpoint to an SNS topic
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sns subscribe \
    --topic-arn arn:aws:sns:us-east-1:<account_id>:<topic_name> \
    --protocol email \
    --notification-endpoint <email_address>
```

#### Confirm an SNS subscription
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sns confirm-subscription \
    --topic-arn arn:aws:sns:us-east-1:<account_id>:<topic_name> \
    --token <subscription_token>
```

#### List all subscriptions for an SNS topic
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sns list-subscriptions-by-topic \
    --topic-arn arn:aws:sns:us-east-1:<account_id>:<topic_name>
```

#### Unsubscribe from an SNS topic
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sns unsubscribe --subscription-arn arn:aws:sns:us-east-1:<account_id>:<topic_name>:<subscription_id>
```

#### Delete an SNS topic
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sns delete-topic --topic-arn arn:aws:sns:us-east-1:<account_id>:<topic_name>
```

## SQS

#### Create an SQS queue
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sqs create-queue --queue-name <queue_name>
```

#### List all SQS queues
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sqs list-queues
```

#### Send a message to an SQS queue
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sqs send-message \
    --queue-url http://localhost:4566/000000000000/<queue_name> \
    --message-body "Hello, SQS!"
```

#### Receive messages from an SQS queue
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sqs receive-message \
    --queue-url http://localhost:4566/000000000000/<queue_name> \
    --max-number-of-messages 10
```

#### Delete a message from an SQS queue
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sqs delete-message \
    --queue-url http://localhost:4566/000000000000/<queue_name> \
    --receipt-handle <receipt_handle>
```

#### Get attributes of an SQS queue
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sqs get-queue-attributes \
    --queue-url http://localhost:4566/000000000000/<queue_name> \
    --attribute-names All
```

#### Set attributes of an SQS queue
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sqs set-queue-attributes \
    --queue-url http://localhost:4566/000000000000/<queue_name> \
    --attributes '{"VisibilityTimeout": "30"}'
```

#### Delete an SQS queue
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sqs delete-queue --queue-url http://localhost:4566/000000000000/<queue_name>
```

#### Purge an SQS queue
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sqs purge-queue --queue-url http://localhost:4566/000000000000/<queue_name>
```

#### Get the URL of an SQS queue
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sqs get-queue-url --queue-name <queue_name>
```

#### Create a dead-letter queue for an SQS queue
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sqs create-queue \
    --queue-name <dead_letter_queue_name> \
    --attributes '{"RedrivePolicy": "{\"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:<account_id>:<dead_letter_queue_name>\",\"maxReceiveCount\":\"5\"}"}'
```

#### Set a dead-letter queue for an SQS queue
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 sqs set-queue-attributes \
    --queue-url http://localhost:4566/000000000000/<queue_name> \
    --attributes '{"RedrivePolicy": "{\"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:<account_id>:<dead_letter_queue_name>\",\"maxReceiveCount\":\"5\"}"}'
```

## RDS

#### Create an RDS instance
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 rds create-db-instance \
    --db-instance-identifier <db_instance_identifier> \
    --db-instance-class db.t2.micro \
    --engine mysql \
    --master-username <master_username> \
    --master-user-password <master_password> \
    --allocated-storage 20
```

#### List all RDS instances
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 rds describe-db-instances
```

#### Delete an RDS instance
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 rds delete-db-instance \
    --db-instance-identifier <db_instance_identifier> \
    --skip-final-snapshot
```

#### Modify an RDS instance
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 rds modify-db-instance \
    --db-instance-identifier <db_instance_identifier> \
    --allocated-storage 30 \
    --apply-immediately
```

#### Describe an RDS instance
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 rds describe-db-instances --db-instance-identifier <db_instance_identifier>
```

#### Create an RDS snapshot
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 rds create-db-snapshot \
    --db-snapshot-identifier <snapshot_identifier> \
    --db-instance-identifier <db_instance_identifier>
```

#### List all RDS snapshots
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 rds describe-db-snapshots --db-instance-identifier <db_instance_identifier>
```

#### Restore an RDS instance from a snapshot
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 rds restore-db-instance-from-db-snapshot \
    --db-instance-identifier <new_db_instance_identifier> \
    --db-snapshot-identifier <snapshot_identifier>
```

## ECS

#### Create an ECS cluster
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecs create-cluster --cluster-name <cluster_name>
```

#### List all ECS clusters
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecs list-clusters
```

#### Create an ECS task definition
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecs register-task-definition \
    --family <task_family> \
    --network-mode bridge \
    --container-definitions '[{
        "name": "<container_name>",
        "image": "<image_name>",
        "memory": 512,
        "cpu": 256,
        "essential": true,
        "portMappings": [{
            "containerPort": 80,
            "hostPort": 80
        }]
    }]'
```

#### List all ECS task definitions
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecs list-task-definitions
```

#### Run an ECS task
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecs run-task \
    --cluster <cluster_name> \
    --task-definition <task_family> \
    --count 1 \
    --launch-type EC2
```

#### List all ECS tasks in a cluster
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecs list-tasks --cluster <cluster_name>
```

#### Describe an ECS task
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecs describe-tasks \
    --cluster <cluster_name> \
    --tasks <task_id>
```

#### Stop an ECS task
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecs stop-task \
    --cluster <cluster_name> \
    --task <task_id>
```

#### Create an ECS service
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecs create-service \
    --cluster <cluster_name> \
    --service-name <service_name> \
    --task-definition <task_family> \
    --desired-count 1 \
    --launch-type EC2
```

#### List all ECS services in a cluster
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecs list-services --cluster <cluster_name>
```

#### Describe an ECS service
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecs describe-services \
    --cluster <cluster_name> \
    --services <service_name>
```

#### Update an ECS service
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecs update-service \
    --cluster <cluster_name> \
    --service <service_name> \
    --desired-count 2
```

#### Delete an ECS service
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecs delete-service \
    --cluster <cluster_name> \
    --service <service_name> \
    --force
```

#### Delete an ECS cluster
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecs delete-cluster --cluster <cluster_name>
```

## ECR

#### Create an ECR repository
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecr create-repository --repository-name <repository_name>
```

#### List all ECR repositories
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecr describe-repositories
```

#### Push an image to an ECR repository
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecr get-login-password | docker login --username AWS --password-stdin <account_id>.dkr.ecr.us-east-1.amazonaws.com/<repository_name>
docker tag <local_image_name> <account_id>.dkr.ecr.us-east-1.amazonaws.com/<repository_name>:<tag>
docker push <account_id>.dkr.ecr.us-east-1.amazonaws.com/<repository_name>:<tag>
```

#### Pull an image from an ECR repository
```text
docker pull <account_id>.dkr.ecr.us-east-1.amazonaws.com/<repository_name>:<tag>
```

#### Delete an ECR repository
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecr delete-repository \
    --repository-name <repository_name> \
    --force
```

#### List images in an ECR repository
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecr list-images --repository-name <repository_name>
```

#### Delete an image from an ECR repository
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecr batch-delete-image \
    --repository-name <repository_name> \
    --image-ids imageTag=<tag>
```

#### Describe an ECR repository
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecr describe-repositories --repository-names <repository_name>
```

#### Get the URI of an ECR repository
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecr describe-repositories --repository-names <repository_name> --query 'repositories[0].repositoryUri' --output text
```

#### Get the ECR repository policy
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 ecr get-repository-policy --repository-name <repository_name>
```

## CloudFront

#### Create a CloudFront distribution
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 cloudfront create-distribution \
    --distribution-config '{
        "CallerReference": "<unique_string>",
        "Origins": {
            "Items": [{
                "Id": "<origin_id>",
                "DomainName": "<origin_domain_name>",
                "CustomOriginConfig": {
                    "HTTPPort": 80,
                    "HTTPSPort": 443,
                    "OriginProtocolPolicy": "http-only"
                }
            }],
            "Quantity": 1
        },
        "DefaultCacheBehavior": {
            "TargetOriginId": "<origin_id>",
            "ViewerProtocolPolicy": "allow-all",
            "ForwardedValues": {
                "QueryString": false,
                "Cookies": {
                    "Forward": "none"
                }
            },
            "TrustedSigners": {
                "Enabled": false,
                "Quantity": 0
            },
            "MinTTL": 0
        },
        "Enabled": true
    }'
```

#### List all CloudFront distributions
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 cloudfront list-distributions
```

#### Get details of a CloudFront distribution
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 cloudfront get-distribution --id <distribution_id>
```

#### Update a CloudFront distribution
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 cloudfront update-distribution \
    --id <distribution_id> \
    --distribution-config '{
        "CallerReference": "<unique_string>",
        "Origins": {
            "Items": [{
                "Id": "<origin_id>",
                "DomainName": "<origin_domain_name>",
                "CustomOriginConfig": {
                    "HTTPPort": 80,
                    "HTTPSPort": 443,
                    "OriginProtocolPolicy": "http-only"
                }
            }],
            "Quantity": 1
        },
        "DefaultCacheBehavior": {
            "TargetOriginId": "<origin_id>",
            "ViewerProtocolPolicy": "allow-all",
            "ForwardedValues": {
                "QueryString": false,
                "Cookies": {
                    "Forward": "none"
                }
            },
            "TrustedSigners": {
                "Enabled": false,
                "Quantity": 0
            },
            "MinTTL": 0
        },
        "Enabled": true
    }'
```

#### Delete a CloudFront distribution
```text
aws --endpoint-url http://localhost:4566 --region us-east-1 cloudfront delete-distribution --id <distribution_id>
```



