## AWS Toolkit with IntelliJ IDEA (Ultimate Edition)

### pre-required plugin:
- AWS Core: This plugin is required to use the Amazon Q or AWS Toolkit plugins. It will be automatically installed if you install either plugin.
- AWS Toolkit: View, modify, and deploy AWS resources
  - Authentication - Connect to AWS using static credentials, credential process, or AWS identity center
  - Resource Explorer - View and manage AWS resources
  - Run/Debug Local Lambda Functions - Locally test and step-through debug functions in a Lambda-like execution environment provided by the AWS SAM CLI. Supports Java, Python, Node.js, and .NET.
  - Deploy SAM-based Applications - Package, deploy track SAM-based applications
  - CloudWatch Logs - View and search CloudWatch log streams
  - S3 Explorer - Manage S3 buckets, and upload to/download from S3 buckets

C:\Users\<user>\.aws

- config (file)
```text
[default]
region=us-east-1
output = json
```

- credentials (file)
```text
[default]
aws_access_key_id = <your_access_key_id>
aws_secret_access_key = <your_secret_access_key>
```

