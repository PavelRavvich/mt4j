package pro.laplacelab.mt4j;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DisplayName("Bootstrap test")
public class ApplicationTest {

    @Test
    @DisplayName("When main call then no one error throw")
    public void whenMainCallThenNoOneErrorThrow() {
        Application.main(new String[] {});
    }

}