package com.rsqn.common.concurrency;

@FunctionalInterface
public interface QueueListener<T> {
    void onItem(T arg);
}
