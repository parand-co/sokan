package util;

import javax.faces.context.FacesContext;
import java.io.*;
import java.util.Properties;

/**
 * Created by nedaja on 15/02/2020.
 */
public class PropertiesLoader {

    public static String read(String key){
        try(InputStream inputStream= new FileInputStream(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources")+"\\config.properties")){
            Properties prop = new Properties();
            prop.load(inputStream);
            return prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean write(String key,String value){
        try{
            InputStream inputStream= new FileInputStream(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources")+"\\config.properties");
            Properties prop = new Properties();
            prop.load(inputStream);
            inputStream.close();

            OutputStream outputStream= new FileOutputStream(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources")+"\\config.properties");
            prop.setProperty(key,value);
            prop.store(outputStream,null);
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
