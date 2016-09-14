package com.rsqn.streams.server.comet.store;

import com.rsqn.streams.server.model.store.Item;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class FakeStore implements Store {
    private Map<Class, Map<String,Item>>  collections = new Hashtable<>();

    private Map<String,Item> getCollection(Class c) {
        Map<String,Item> ret = collections.get(c);
        if ( ret == null) {
            synchronized (collections) {
                ret = collections.get(c);
                if ( ret == null) {
                    ret = new Hashtable<>();
                    collections.put(c,ret);
                }
            }
        }
        return ret;
    }

    @Override
    public <T extends Item> T create(T i) {
        i.setUid(RandomStringUtils.randomAlphanumeric(24));
        getCollection(i.getClass()).put(i.getUid(),i);
        return i;
    }

    @Override
    public <T extends Item> T read(Class t, String uid) {
        return (T)getCollection(t).get(uid);
    }

    @Override
    public <T extends Item> T update(T i) {
        getCollection(i.getClass()).put(i.getUid(),i);
        return i;
    }

    @Override
    public <T extends Item> int delete(T i) {
        getCollection(i.getClass()).remove(i.getUid());
        return 0;
    }

    @Override
         public <T extends Item> List<T> list(Class c) {
        return new ArrayList(getCollection(c).values());
    }

    @Override
    public <T extends Item> T findOne(Class c, ItemMatcher<T> matcher) {
        List<T> l = list(c);

        for (T t : l) {
            if ( matcher.matches(t)) {
                return t;
            }
        }
        return null;
    }
}
