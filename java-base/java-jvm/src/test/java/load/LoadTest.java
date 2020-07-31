package load;


import com.huangxi.spi.load.SingleTon;
import org.junit.Test;

/**
 * @description TODO
 * @date 2020-07-15
 */
public class LoadTest {
    @Test
    public void loadTest() {
//        SingleTon singleTon = SingleTon.getInstance();
//        System.out.println("count1=" + singleTon.count1);
//        System.out.println("count2=" + singleTon.count2);

        System.out.println("count1=" + SingleTon.count1);
        System.out.println("count2=" + SingleTon.count2);
        //相当于两次
        // result>>>>>>>> count1=1 count2=0
    }

}
