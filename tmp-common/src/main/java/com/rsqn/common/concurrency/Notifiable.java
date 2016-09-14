package com.rsqn.common.concurrency;

@FunctionalInterface
public interface Notifiable<T> {
    void onNotify(T arg);
}
