package Main;
//have to do email test .. date 2019 1 18
import java.io.BufferedReader;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import hihi.GoogleAuthentication;
/**
 * Servlet implementation class NICEServlet
 */

@WebServlet("/NICEServlet")
@MultipartConfig(maxFileSize = -1, maxRequestSize = -1,location =Setup.FileSaveDirectory)
public class NICEServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String userDirString = "";
    private long end_t = 0;
    private long start_t = 0;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NICEServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher view = request.getRequestDispatcher("NICE.html");
		view.forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			
		String input_type_str = request.getParameter("tabss");
		String emailAddress = request.getParameter("emailAddress");
		String thr_num = request.getParameter("thr_num"); //set the number of thread
		
		
		start_t = System.currentTimeMillis();	// for calculate working time

		NICE nice = new NICE(emailAddress,request);
		nice.downloadInputData(request, input_type_str);
		
		
		//Feed Client the main page 
		RequestDispatcher view = request.getRequestDispatcher("index.html");
		view.forward(request, response);
		
		/** Running NICE **/
		nice.run(thr_num);
				
		sendResultMail(nice.email_dir);
	}
	private void sendResultMail(String emailAddress) {
		end_t = System.currentTimeMillis();
		System.out.println("work time is " + (end_t - start_t)/1000.0);
		//send e_mail
			File f_check = new File(userDirString + "/NICE.txt"); 
			try {
				java.util.Properties properties = System.getProperties();
				properties.put("mail.smtp.starttls.enable", "true");
				properties.put("mail.smtp.host", "smtp.gmail.com");
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.smtp.port", "587");
				
				Authenticator auth = new GoogleAuthentication();
				Session s = Session.getDefaultInstance(properties, auth);
				Message msg = new MimeMessage(s);
				Address sender_address = new InternetAddress("taegun89@gmail.com");
				Address receiver_address = new InternetAddress(emailAddress);
		
				msg.setHeader("content-type", "text/html;charset=euc-kr");
				
				msg.setFrom(sender_address);
				msg.addRecipient(Message.RecipientType.TO, receiver_address);
				File res = new File(emailAddress+"/NICE.txt");
				if(res.exists()) {
					msg.setSubject("Nice complete");
					msg.setContent("<a href = \"http://210.94.194.52:8080/download/"+emailAddress+"/NICE.txt"+"\">download_link</a>" 
							+"<br>Working time is " +(end_t - start_t)/1000.0 + "Second.", "text/html; charset=euc-kr");
				
				}
				else {
					msg.setSubject("Nice fail");
					msg.setContent("Please send us an e-mail for further explanations.", "text/html; charset=euc-kr");
							
				}
				// messageBodyPart.setText("test");
						  
				
				Transport.send(msg);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
	}
}
