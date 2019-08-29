package hihi;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class GoogleAuthentication extends Authenticator{
	PasswordAuthentication passAuth;
	
	public GoogleAuthentication() {
		passAuth = new PasswordAuthentication("taegun89@gmail.com", "fxbagyrimuybqipt");
	}
	
	public PasswordAuthentication getPasswordAuthentication() {
		return passAuth;
	}
}
