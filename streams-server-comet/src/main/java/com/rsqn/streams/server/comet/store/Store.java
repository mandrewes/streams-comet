package com.rsqn.streams.server.comet.store;


import com.rsqn.streams.server.model.store.Item;

import java.util.List;

public interface Store {

    <T extends Item> T create(T i);

    <T extends Item> T read(Class t, String uid);

    <T extends Item> T update(T i);

    <T extends Item> int delete(T i);

    <T extends Item> List<T> list(Class c);

    <T extends Item> T findOne(Class c, ItemMatcher<T> matcher);

}
