import { Routes } from '@angular/router';
import { DashboardComponent } from "./components/dashboard/dashboard.component";
import { LoginComponent } from "./pages/auth/login.component";
import { authGuard } from "@shared/guards/auth.guard";
import { DynamodbComponent } from "./aws/dynamodb/dynamodb.component";
import { ResourceBrowserComponent } from "./components/resource-browser/resource-browser.component";
import { OverviewComponent } from "./components/overview/overview.component";
import { StatusComponent } from "./components/status/status.component";
import { S3Component } from "./aws/s3/s3.component";
import { SqsComponent } from "./aws/sqs/sqs/sqs";
import { SnsComponent } from "./aws/sns/sns.component";
import { LambdaComponent } from "./aws/lambda/lambda.component";

export const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    canActivate: [authGuard],
    children: [
      { path: 'overview', component: OverviewComponent },
      { path: 'status', component: StatusComponent },
      { path: 'browser', component: ResourceBrowserComponent },
      { path: 'dynamodb', component: DynamodbComponent },
      { path: 's3', component: S3Component },
      { path: 'sqs', component: SqsComponent },
      { path: 'sns', component: SnsComponent },
      { path: 'lambda', component: LambdaComponent },
      { path: 'iam', loadComponent: () => import('./aws/iam/iam/iam').then(m => m.Iam) },
      { path: 'logs', loadComponent: () => import('./aws/logs/logs/logs').then(m => m.Logs) },
      { path: '', redirectTo: 'overview', pathMatch: 'full' },
    ],
  },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: '' },
];

