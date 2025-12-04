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

---

#### AWS SSO Configure

```text
aws configure sso
aws configure list-profiles
aws sso login --profile XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

#### Find a Lambda by name with Profile
```text
aws --region us-east-1 lambda list-functions \
    --profile XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX \
    --query "Functions[?FunctionName=='xxx-xxx-params-xxx']"
```
