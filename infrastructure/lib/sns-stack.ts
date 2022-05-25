import { StackProps, App, aws_sns as sns, Stack } from 'aws-cdk-lib';
import { ITopicSubscription } from 'aws-cdk-lib/aws-sns';

interface SnsStackProps extends StackProps {
    subscriptions?: ITopicSubscription[]
}

export class SnsStack extends Stack {
    constructor(scope: App, id: string, props?: SnsStackProps) {
        super(scope, id, props);

        const topic = new sns.Topic(this, `${process.env.SNS_TOPIC_NAME}-topic`, {
            topicName: process.env.SNS_TOPIC_NAME,
            fifo: false
        });

        if (props?.subscriptions) {
            props.subscriptions.forEach((subscription) => {
                if (!subscription)
                    return;
                topic.addSubscription(subscription);
            })
        }
    }
}