package org.kybprototyping.consumer;

import java.io.Console;
import java.util.TimerTask;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SqsException;

public class MessageReceiverTimerTask extends TimerTask {
    private static final String QUEUE_URL = String.format("https://sqs.%s.amazonaws.com/%s/%s",
            System.getenv("AWS_REGION"),
            System.getenv("AWS_ACCOUNT_ID"), System.getenv("SQS_QUEUE_NAME"));

    private static ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
            .queueUrl(QUEUE_URL)
            .waitTimeSeconds(0) /* Short polling */
            .maxNumberOfMessages(1)
            .build();

    private SqsClient client; /* Note: It's a thread-safe client */
    private MessageReceivingCallback callback;
    private Console console;

    public MessageReceiverTimerTask(SqsClient client, MessageReceivingCallback callback, Console console) {
        this.client = client;
        this.callback = callback;
        this.console = console;
    }

    public void run() {
        try {
            console.printf("[%s] - Polling is starting with the thread: %d...%n", java.time.LocalTime.now(),
                    Thread.currentThread().getId());
            ReceiveMessageResponse receiveResponse = client.receiveMessage(receiveRequest);
            console.printf("[%s] - Polling completed with the status code %d!%n",
                    java.time.LocalTime.now(), receiveResponse.sdkHttpResponse().statusCode());
            callback.handleReceivedMessages(receiveResponse.messages());
        } catch (SqsException ex) {
            console.printf("Error occured: %s", ex.awsErrorDetails().errorMessage());
        }
    }
}
