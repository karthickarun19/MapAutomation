package Utilities;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class PropertyReader {
	 private Properties prop = null;
	 
	  public void MyPropAllKeys(){
	         
	       InputStream is = null;
	        try {
	            this.prop = new Properties();
	            is = this.getClass().getResourceAsStream("C:\\Users\\Karthick\\eclipse-workspace\\"
	            		+ "com.subramanian.karthick\\Properties\\address.properties");
	            prop.load(is);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	     
	  public Set<Object> getAllKeys(){
	        Set<Object> keys = prop.keySet();
	        return keys;
	    }
	     
	    public String getPropertyValue(String key){
	        return this.prop.getProperty(key);
	    }
}
