package org.kybprototyping.consumer;

import java.util.List;

import software.amazon.awssdk.services.sqs.model.Message;

@FunctionalInterface
public interface MessageReceivingCallback {
    public abstract void handleReceivedMessages(List<Message> message);
}
