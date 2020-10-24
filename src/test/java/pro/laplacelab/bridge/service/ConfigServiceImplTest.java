package pro.laplacelab.bridge.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pro.laplacelab.bridge.model.Config;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class ConfigServiceImplTest {

    @Autowired
    ConfigService configService;

    @Test
    public void whenRegisterSuccessThenReturnConfigUUID() {
        assertNotNull(configService.add("key1=val1\nkey2=val2"));
    }

    @Test(expected = RuntimeException.class)
    public void whenRegisterFailThenThrowException() {
        assertNotNull(configService.add("key"));
    }

    @Test
    public void whenRegisterSuccessThenConfigSaved() {
        UUID uuid = configService.add("key1=val1\nkey2=val2");
        Config config = configService.get(uuid);
        assertEquals("val1", config.findByName("key1"));
        assertEquals("val2", config.findByName("key2"));
    }

}