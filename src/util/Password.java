package util;

import org.mindrot.jbcrypt.BCrypt;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class Password {

    public static final String saltBcrypt = "$2a$10$ehUG1EidWH9IircL1fZQJO";
    public static final String defaultPass = "P@ssword_1234";

    public String hashhPassword(String pass){
        return BCrypt.hashpw(pass, saltBcrypt);
    }
}