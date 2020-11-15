import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import static com.codeborne.selenide.Selenide.open;

/**
 * @author peng_luo@yeah.net
 * @version 1.0
 * @date 2020/7/11 14:12
 */
public class Initialization {
    @BeforeSuite
    public void beforeSuite() {
//        System.setProperty("webdriver.chrome.driver", "E:\\gird\\chromedriver.exe");
//        Configuration.browser = "chrome";
        Configuration.savePageSource = false;
        Configuration.screenshots = false;
        Configuration.startMaximized = true;
        Configuration.headless = false;
        Configuration.timeout = 2000;
        //修改报告默认生成路径
//        Configuration.reportsFolder = "target/reports/test";
        System.out.println("这是一个测试套件");
    }

    @BeforeTest
    public void beforTest() {
        open("https://www.baidu.com/");
    }
}
