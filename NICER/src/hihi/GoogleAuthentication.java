package hihi;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class GoogleAuthentication extends Authenticator{
	PasswordAuthentication passAuth;
	
	public GoogleAuthentication() {
		passAuth = new PasswordAuthentication("taegun89@gmail.com", "vrbeimnesqvsgbev");
	}
	
	public PasswordAuthentication getPasswordAuthentication() {
		return passAuth;
	}
}
