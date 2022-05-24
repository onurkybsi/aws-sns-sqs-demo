# aws-sns-sqs-demo

This project is a demo project which demonstrates how to use [AWS SNS](https://docs.aws.amazon.com/sns/latest/dg/welcome.html) and [AWS SQS](https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/welcome.html) for integration of services.

## System Requirements
* [Bash](https://www.gnu.org/software/bash/)
* [Maven](https://maven.apache.org/)
* [JDK/JRE 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

You should also create an _AWS SNS_ topic and _AWS SQS_. Then, you should subscribe your _AWS SQS_ queue to your _AWS SNS_ topic. For more information, [see](https://docs.aws.amazon.com/sns/latest/dg/subscribe-sqs-queue-to-sns-topic.html).

## producer

The producer app demostrates a message publisher for an _AWS SNS_ topic.

### Installation
1. Configure your `.aws/config` and `.aws/credentials`. For more information. [see](https://docs.aws.amazon.com/sdkref/latest/guide/file-format.html).
2. Create a `.env` file in the producer directory like below:

```bash
TOPIC_ARN=arn:aws:sns:eu-central-1:1234:testing
AWS_REGION=eu-central-1
AWS_PROFILE=onurkybsi
```

3. Run the command below in the prodcuer directory

```bash
bash .sh build run
```

## consumer

The consumer app demostrates a message consumer from an _AWS SQS_ queue.

### Installation
1. Configure your `.aws/config` and `.aws/credentials`. For more information. [see](https://docs.aws.amazon.com/sdkref/latest/guide/file-format.html).
2. Create a `.env` file in the consumer directory like below:

```bash
QUEUE_URL=https://sqs.eu-central-1.amazonaws.com/1234/testing
AWS_REGION=eu-central-1
AWS_PROFILE=onurkybsi
```

3. Run the command below in the prodcuer directory

```bash
bash .sh build run
```