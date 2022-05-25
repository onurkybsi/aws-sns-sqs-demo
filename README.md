# aws-sns-sqs-demo

This project is a demo project which demonstrates how to use [AWS SNS](https://docs.aws.amazon.com/sns/latest/dg/welcome.html) and [AWS SQS](https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/welcome.html) for integration of services.

## System Requirements
* [Node](https://nodejs.org/en/)
* [AWS CDK](https://docs.aws.amazon.com/cdk/v2/guide/getting_started.html)
* [Bash](https://www.gnu.org/software/bash/)
* [Maven](https://maven.apache.org/)
* [JDK/JRE 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

## infrastructure

An AWS CDK app which creates the AWS SNS topic for the _producer_ and the AWS SQS queue for the _consumer_.

### Installation
1. Configure your `.aws/config` and `.aws/credentials`. For more information. [see](https://docs.aws.amazon.com/sdkref/latest/guide/file-format.html).

2. Fill the values in the `.env` file

3. Run the command below in the infrastructure directory
```bash
bash .sh deploy
```

## producer

The producer app demostrates a message publisher for an _AWS SNS_ topic.

### Installation
1. Configure your `.aws/config` and `.aws/credentials`. For more information. [see](https://docs.aws.amazon.com/sdkref/latest/guide/file-format.html).

2. Fill the values in the `.env` file

3. Run the command below in the infrastructure directory to deploy the AWS SNS topic
```bash
bash .sh deploy
```

4. Run the command below in the producer directory

```bash
bash .sh build run
```

## consumer

The consumer app demostrates a message consumer from an _AWS SQS_ queue.

### Installation
1. Configure your `.aws/config` and `.aws/credentials`. For more information. [see](https://docs.aws.amazon.com/sdkref/latest/guide/file-format.html).

2. Fill the values in the `.env` file

3. Run the command below in the infrastructure directory to deploy the AWS SQS queue
```bash
bash .sh deploy
```

3. Run the command below in the consumer directory

```bash
bash .sh build run
```