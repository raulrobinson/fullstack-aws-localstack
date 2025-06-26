export const Resources = [
  {
    key: 'dynamodb',
    name: 'DynamoDB',
    description: 'Tablas NoSQL locales',
    icon: 'assets/icons/Arch_Amazon-DynamoDB_64.svg',
    route: '/dynamodb'
  },
  {
    key: 's3',
    name: 'S3',
    description: 'Almacenamiento S3',
    icon: 'assets/icons/Arch_Amazon-S3-on-Outposts_Storage_64.svg',
    route: '/s3'
  },
  {
    key: 'sqs',
    name: 'SQS',
    description: 'Colas SQS',
    icon: 'assets/icons/Arch_Amazon-Simple-Queue-Service_64.svg',
    route: '/sqs'
  },
  {
    key: 'sns',
    name: 'SNS',
    description: 'Notificaciones SNS',
    icon: 'assets/icons/Arch_Amazon-Simple-Notification-Service_64.svg',
    route: '/sns'
  },
  {
    key: 'lambda',
    name: 'Lambda',
    description: 'Funciones Lambda',
    icon: 'assets/icons/Arch_AWS-Lambda_64.svg',
    route: '/lambda'
  },
  {
    key: 'iam',
    name: 'IAM',
    description: 'Gestión de usuarios y permisos',
    icon: 'assets/icons/Arch_AWS-Identity-and-Access-Management_64.svg',
    route: '/iam'
  },
  {
    key: 'logs',
    name: 'LOGS',
    description: 'CloudWatch Logs',
    icon: 'assets/icons/Arch_Amazon-CloudWatch_64.svg',
    route: '/logs'
  },
];

export const ServiceRouteMap: Record<string, string> = {
  dynamodb: '/dynamodb',
  s3: '/s3',
  sqs: '/sqs',
  sns: '/sns',
  lambda: '/lambda',
  iam: '/iam',
  logs: '/logs',
};
