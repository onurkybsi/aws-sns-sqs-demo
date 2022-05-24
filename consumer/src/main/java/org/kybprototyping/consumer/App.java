package org.kybprototyping.consumer;

import java.io.Console;
import java.util.List;
import java.util.Timer;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.utils.CollectionUtils;

public class App {
    private static final String AWS_PROFILE = System.getenv("AWS_PROFILE");
    private static final String AWS_REGION = System.getenv("AWS_REGION");

    private static SqsClient client = buildSqsClient();
    private static Console console = System.console();

    public static void main(String[] args) {
        console.printf("Consumer is starting to receive the messages!%n");
        Timer timer = new Timer();
        MessageReceiverTimerTask messageReceiverTask = new MessageReceiverTimerTask(client,
                App::printReceivedMessages,
                console);
        // Note: With scheduleAtFixedRate executions are not delayed because of the previous execution
        //       If you use schedule and an execution takes 2 seconds, the next execution will be started in 12 seconds
        timer.scheduleAtFixedRate(messageReceiverTask, 0, 5000);
    }

    private static SqsClient buildSqsClient() {
        return SqsClient.builder()
                .region(Region.of(AWS_REGION))
                .credentialsProvider(ProfileCredentialsProvider.create(AWS_PROFILE))
                .build();
    }

    private static void printReceivedMessages(List<Message> messages) {
        if (!CollectionUtils.isNullOrEmpty(messages)) {
            console.writer().println("Messages are listing...");
            for (Message message : messages) {
                console.printf("Message: %s%n", message.body());
            }
        }
    }
}