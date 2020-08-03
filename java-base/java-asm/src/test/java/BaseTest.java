import com.huangxi.bytecode.Base;
import com.huangxi.bytecode.BaseLogger;
import org.junit.Test;

/**
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

    @Test
    public void BaseLoggerTest() throws Exception {
        BaseLogger baseLogger = new BaseLogger();
        baseLogger.baseLog();
    }
}
