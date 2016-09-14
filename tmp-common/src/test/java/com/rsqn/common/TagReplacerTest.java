package com.rsqn.common;

import com.rsqn.common.util.TagReplacer;
import junit.framework.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TagReplacerTest {

    @Test
    public void shouldReplaceTags() throws Exception {
        String tpl = "path/{sessionId}";
        Map<String,String> tags = new HashMap<>();
        tags.put("sessionId","fred");

        String s = TagReplacer.replaceTags(tpl, tags);

        Assert.assertEquals("path/fred", s);

    }
}
