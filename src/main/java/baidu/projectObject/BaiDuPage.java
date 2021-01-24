package baidu.projectObject;

import baidu.projectElement.BaiDuEle;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

/**
 * @author peng_luo@yeah.net
 * @version 1.0
 * @date 2020/7/2 22:31
 */
public class BaiDuPage extends BaiDuEle {

    @Step("在搜索框输入需要查询的数据")
    public void text_box_input(String text){
        $(text_box).should(Condition.exist).clear();
        $(text_box).sendKeys(text);
    }

    @Step("点击百度一下按钮")
    public void bau_du_click(){
        $(input_submit).should(Condition.exist).click();
    }

    public BaiDuPage BaiDuClick(String text){
        text_box_input(text);
        bau_du_click();
        return this;
    }
}
