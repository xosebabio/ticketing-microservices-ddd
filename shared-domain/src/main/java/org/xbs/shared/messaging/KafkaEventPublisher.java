package org.xbs.shared.messaging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(KafkaTemplate.class)
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TopicResolver topicResolver;

    public KafkaEventPublisher(KafkaTemplate<String, Object> kafkaTemplate, TopicResolver topicResolver) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicResolver = topicResolver;
    }

    @Override
    public void publish(Object event) {
        String topic = topicResolver.resolve(event.getClass());
        kafkaTemplate.send(topic, event);
    }
}
