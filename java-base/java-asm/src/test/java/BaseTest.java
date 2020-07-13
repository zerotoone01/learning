import com.huangxi.bytecode.Base;
import org.junit.Test;

/**
 * @author huang.luo.jun
 * @description TODO
 * @date 2020-07-13
 */
public class BaseTest {

    /**
     * 字节码增强测试
     */
    @Test
    public void baseTest(){
        Base base = new Base();
        base.process();
    }
}
