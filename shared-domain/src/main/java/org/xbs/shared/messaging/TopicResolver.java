package org.xbs.shared.messaging;

@FunctionalInterface
public interface TopicResolver {
    String resolve(Class<?> eventClass);
}
