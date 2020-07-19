import com.codeborne.selenide.WebDriverRunner;
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
    public void beforeSuite(){
        System.out.println("这是一个测试套件");
    }

    @BeforeTest
    public void beforTest() {
        open("https://www.baidu.com/");
    }
}
