import { Stack, aws_sqs as sqs, App, StackProps } from 'aws-cdk-lib';

export class SqsStack extends Stack {
    public readonly queues: sqs.Queue[] = [];

    constructor(scope: App, id: string, props?: StackProps) {
        super(scope, id, props);

        const testingQueue = new sqs.Queue(this, `${process.env.SQS_QUEUE_NAME}-queue`, {
            queueName: process.env.SQS_QUEUE_NAME
        });

        this.queues.push(testingQueue);
    }
}