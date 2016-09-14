package com.rsqn.streams.server.comet.store;

@FunctionalInterface
public interface ItemMatcher<T> {
    boolean matches(T o);
}
