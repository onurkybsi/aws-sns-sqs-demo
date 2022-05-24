package org.kybprototyping.producer;

import java.io.Console;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

public class App {
    private static final String TOPIC_ARN = System.getenv("TOPIC_ARN");
    private static final String AWS_REGION = System.getenv("AWS_REGION");
    private static final String AWS_PROFILE = System.getenv("AWS_PROFILE");

    private static Console console = System.console();
    private static SnsClient client = buildSnsClient();

    public static void main(String[] args) {
        console.printf("Producer is waiting to publish message!%n");
        while (true) {
            String message = console.readLine("Enter your message: ");
            publishMessage(message);
        }
    }

    private static SnsClient buildSnsClient() {
        return SnsClient.builder()
                .region(Region.of(AWS_REGION))
                .credentialsProvider(ProfileCredentialsProvider.create(AWS_PROFILE))
                .build();
    }

    private static void publishMessage(String message) {
        try {
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .topicArn(TOPIC_ARN)
                    .build();

            PublishResponse result = client.publish(request);
            console.printf("Message %s sent with the status code %d !%n",
                    result.messageId(),
                    result.sdkHttpResponse().statusCode());
        } catch (SnsException ex) {
            console.printf("Error occured: %s%nProducer is terminating...", ex.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}