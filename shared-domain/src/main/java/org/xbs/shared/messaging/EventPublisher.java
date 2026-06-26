package org.xbs.shared.messaging;

public interface EventPublisher {
    void publish(String topic, Object event);
}
