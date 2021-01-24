package common.action;

import org.openqa.selenium.By;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;

/**
 * @author LuoPeng
 * @version 1.0
 * @date 2020/11/18 20:55
 */
public class MyActions {

    /**
     * 上传图片
     */
    public static void uploadImageFile(By uoloadElementPath, String filePath){
        File excelFile = new File(filePath);
        $(uoloadElementPath).uploadFile(excelFile);
        sleep(2000);
    }
}
