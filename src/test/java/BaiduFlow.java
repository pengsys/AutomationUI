import baidu.projectObject.BaiDuPage;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

/**
 * @author peng_luo@yeah.net
 * @version 1.0
 * @date 2020/7/2 22:37
 */
public class BaiduFlow extends Initialization {
    public static String SELECT_TEXT= "selenide";
    @BeforeTest
    public void beforefoTest() {
        System.out.println("this is before class");
    }

    @Test
    public void baiDuTest() {
        BaiDuPage baiDuTest = page(BaiDuPage.class)
                .BaiDuClick(SELECT_TEXT);
    }


    public static void main(String[] args) {
        open("http://www.baidu.com");
    }

}
