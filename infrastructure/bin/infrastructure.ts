#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { Environment } from 'aws-cdk-lib';
import { SqsStack } from '../lib/sqs-stack';
import { ITopicSubscription } from 'aws-cdk-lib/aws-sns';
import { SqsSubscription } from 'aws-cdk-lib/aws-sns-subscriptions';
import { SnsStack } from '../lib/sns-stack';

const app = new cdk.App();

const env: Environment = {
  account: process.env.AWS_ACCOUNT_ID, region: process.env.AWS_REGION
};

const sqsStack = new SqsStack(app, "SqsStack", { env: env });

const subscriptions: ITopicSubscription[] = [];
sqsStack.queues.forEach((queue) => {
  subscriptions.push(new SqsSubscription(queue));
});
const snsStack = new SnsStack(app, "SnsStack", { env: env, subscriptions: subscriptions });