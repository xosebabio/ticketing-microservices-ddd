package org.xbs.shared.messaging;

public class DefaultTopicResolver implements TopicResolver {
    @Override
    public String resolve(Class<?> eventClass) {
        return eventClass.getName().toLowerCase();
    }
}
