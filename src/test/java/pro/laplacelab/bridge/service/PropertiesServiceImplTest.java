package pro.laplacelab.bridge.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class PropertiesServiceImplTest {

//    @Autowired
//    AdvisorConfigService advisorConfigService;
//
//    @Test
//    public void whenRegisterSuccessThenReturnConfigUUID() {
//        assertNotNull(advisorConfigService.add("key1=val1\nkey2=val2"));
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void whenRegisterFailThenThrowException() {
//        assertNotNull(advisorConfigService.add("key"));
//    }
//
//    @Test
//    public void whenRegisterSuccessThenConfigSaved() {
//        UUID uuid = advisorConfigService.add("key1=val1\nkey2=val2");
//        Properties config = advisorConfigService.get(uuid);
//        assertEquals("val1", config.findByName("key1"));
//        assertEquals("val2", config.findByName("key2"));
//    }

}