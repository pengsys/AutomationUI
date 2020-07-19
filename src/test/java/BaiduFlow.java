import baidu.projectObject.BaiDuPage;
import org.testng.annotations.*;

/**
 * @author peng_luo@yeah.net
 * @version 1.0
 * @date 2020/7/2 22:37
 */
public class BaiduFlow extends Initialization {
    @BeforeTest
    public void beforefoTest() {
        System.out.println("this is before class");
    }

    @Test
    public BaiDuPage baiDuTest() {
        BaiDuPage baiDuPage = new BaiDuPage();
        baiDuPage.BaiDuClick("python");
        return baiDuPage;
    }

}
