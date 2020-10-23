package pro.laplacelab.bridge.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigTest {

    @Test
    public void whenConfigBuildSuccessfully() {
        Config config = new Config("key1=val1\nkey2=val2");
        assertEquals("val1", config.findByName("key1"));
        assertEquals("val2", config.findByName("key2"));
    }


    @Test(expected = RuntimeException.class)
    public void whenConfigBuildFail() {
        new Config("key1=\nkey2=val2");
    }

}