package org.kybprototyping.consumer;

import java.io.Console;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SqsException;
import software.amazon.awssdk.utils.CollectionUtils;

public class App {
    private static final String QUEUE_URL = System.getenv("QUEUE_URL");
    private static final String AWS_PROFILE = System.getenv("AWS_PROFILE");

    private static SqsClient client = buildSqsClient();
    private static Console console = System.console();

    public static void main(String[] args) {
        console.printf("Consumer is starting to receive the messages!%n");
        while (true) {
            receiveMessage();
        }
    }

    private static SqsClient buildSqsClient() {
        return SqsClient.builder()
                .region(Region.EU_CENTRAL_1)
                .credentialsProvider(ProfileCredentialsProvider.create(AWS_PROFILE))
                .build();
    }

    private static void receiveMessage() {
        try {
            ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                    .queueUrl(QUEUE_URL)
                    .waitTimeSeconds(20)
                    .build();

            ReceiveMessageResponse receiveResponse = client.receiveMessage(receiveRequest);
            console.printf("Polling completed with the status code %d !%n",
                    receiveResponse.sdkHttpResponse().statusCode());
            printReceivedMessages(receiveResponse);
        } catch (SqsException ex) {
            console.printf("Error occured: %s%Consumer is terminating...", ex.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    private static void printReceivedMessages(ReceiveMessageResponse receiveResponse) {
        if (receiveResponse != null && !CollectionUtils.isNullOrEmpty(receiveResponse.messages())) {
            console.writer().println("Messages are listing...");
            int i = 1;
            for (Message message : receiveResponse.messages()) {
                console.printf("Message %d: %s%n", i, message.body());
                i++;
            }
        }
    }
}