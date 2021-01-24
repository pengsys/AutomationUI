package baidu;

import baidu.projectObject.BaiDuPage;
import io.qameta.allure.Description;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.page;

/**
 * @author peng_luo@yeah.net
 * @version 1.0
 * @date 2020/7/2 22:37
 */
public class BaiDuFlow extends Initialization {
//
//    @BeforeTest
//    public void beforeTest() {
//        System.out.println("this is before class");
//    }

    @Test
    @Description("打开百度")
    public void baiDuTest() {
        BaiDuPage baiDuPage = page(BaiDuPage.class)
                .BaiDuClick("selenium");
    }
}
