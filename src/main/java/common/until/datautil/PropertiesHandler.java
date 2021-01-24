package common.until.datautil;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * @author LuoPeng
 * @version 1.0
 * @date 2020/11/18 20:37
 */
public class PropertiesHandler {
    public static String paramenterPath = "src/test/resources";

    public static void loadPropertiesAndFile(File folder) throws IOException {
        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                loadPropertiesAndFile(file);
            }
        }
        if (folder.isFile()) {
            if (folder.getName().endsWith(".properties")) {
                Properties pors = new Properties();
                InputStream is = new FileInputStream(folder);
                BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(is, "UTF-8")));
                pors.load(bufferedReader);
                for (Map.Entry pro : pors.entrySet()) {
                    System.setProperty(pro.getKey().toString(), pro.getValue().toString());
                }
            } else {
                System.setProperty(folder.getName(), folder.getAbsolutePath());
            }
            return;
        }
    }

    public static void loadPropertiesByProjectEnv(String project, String env) throws IOException {
        String filePath = paramenterPath + "/" + project + "/" + env + "/";
        File folder = new File(filePath);
        loadPropertiesAndFile(folder);
    }

    public static void loadPropertiesByProjectEnv(String env) throws IOException {
        String filePath = paramenterPath + "/" + env + "/";
        File folder = new File(filePath);
        loadPropertiesAndFile(folder);
    }
}
