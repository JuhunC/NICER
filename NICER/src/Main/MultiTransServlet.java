//package Main;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.nio.file.Files;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.activation.DataHandler;
//import javax.activation.FileDataSource;
//import javax.mail.Address;
//import javax.mail.Authenticator;
//import javax.mail.BodyPart;
//import javax.mail.Message;
//import javax.mail.Multipart;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Part;
//
//import hihi.GoogleAuthentication;
//
///**
// * Servlet implementation class MultiTransServlet
// */
//
//
//
//@WebServlet("/MultiTransServlet")
//@MultipartConfig(maxFileSize = -1, maxRequestSize = -1,location =Setup.FileSaveDirectory)
//public class MultiTransServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public MultiTransServlet() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		RequestDispatcher view = request.getRequestDispatcher("MultiTrans.html");
//		view.forward(request, response);
//		//response.getWriter().append("Served at: ").append(request.getContextPath());
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		String emailAddress = request.getParameter("emailAddress");
//		String NumSNPs = request.getParameter("NumSNPs");
//		System.out.println(emailAddress);
//		System.out.println(NumSNPs);
//		
//		// Get Current Time
//		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");//dd/MM/yyyy
//		Date now = new Date();
//		String strDate = sdfDate.format(now);
//		
//		// Make Directory
//		String userDirString = Setup.FileSaveDirectory+emailAddress+"/";
//		
//		File userDir=new File(userDirString);    
//		System.out.println(userDirString);
//		if(userDir.mkdir()) {
//			userDirString += strDate;
//			userDir = new File(userDirString);
//			if(userDir.mkdir())
//				System.out.println("Directory Made");
//		}else{
//			userDirString += strDate;
//			userDir = new File(userDirString);
//			if(userDir.mkdir())
//				System.out.println("Directory Made");
//		}
//		
//		// Upload SNP file
//		Part part = request.getPart("SNPfile");
//		File file = new File(userDirString+"/X.txt");
//		try (InputStream inputStream= part.getInputStream()) { // save uploaded file
//			Files.copy(inputStream, file.toPath());
//		}
//		
//		// Upload Phenotype file
//		Part part1 = request.getPart("Phenotypefile");
//		File file1 = new File(userDirString+"/Y.txt");
//		try (InputStream inputStream= part1.getInputStream()) { // save uploaded file
//			Files.copy(inputStream, file1.toPath());
//		}
//		
//		/** 占쏙옙占썩에 MultiTrans Instruction占쏙옙 占쏙옙占쏙옙占싹댐옙 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占싹몌옙 占싯니댐옙. **/
//		RequestDispatcher view = request.getRequestDispatcher("index.html");
//		view.forward(request, response);
//		//doGet(request, response);
//		
//		Process a_1 = null;Process a_2 = null;Process a_3 = null;Process a_4 = null;Process a_5 = null;
//		try {
//			
//			if(a_1 == null)
//			{
//				//change the path later
//				System.out.println(file.getPath());
//				a_1 = Runtime.getRuntime().exec("python /home/ktg/MultiTrans/Pylmm_MultiTrans/pylmmKinship.py -v --emmaSNP="+ file.getPath() +" --emmaNumSNPs="+ NumSNPs + " " + userDirString +"/K.txt");
//				InputStream stderr2 = a_1.getInputStream();
//				InputStreamReader isr2 = new InputStreamReader(stderr2);
//				BufferedReader br2 = new BufferedReader(isr2);
//
//				java.io.OutputStream os = a_1.getOutputStream();
//				PrintWriter pw = new PrintWriter(os);
//			
//				os.flush();
//		
//				streamout abc = new streamout(a_1.getErrorStream(), "abc");
//				streamout abc2 = new streamout(a_1.getInputStream(), "abc");
//				
//				InputStream stderr = a_1.getErrorStream();
//				InputStreamReader isr = new InputStreamReader(stderr);
//				BufferedReader br = new BufferedReader(isr);
//		
//				
//				abc.start();
//				abc2.start();
//				 
//			    a_1.waitFor();
//				abc.interrupt();
//				abc2.interrupt();	
//			}
//			if(a_2 == null)
//			{
//				a_2 = Runtime.getRuntime().exec("python /home/ktg/MultiTrans/Pylmm_MultiTrans/pylmmGWAS_multiPhHeri.py -v --emmaSNP=" + file.getPath() + " --kfile=" + userDirString +"/K.txt" + " --emmaPHENO=" + file1.getPath() + " " + userDirString +"/VC.txt");
//				
//				InputStream stderr2 = a_2.getInputStream();
//				InputStreamReader isr2 = new InputStreamReader(stderr2);
//				BufferedReader br2 = new BufferedReader(isr2);
//
//				java.io.OutputStream os = a_2.getOutputStream();
//				PrintWriter pw = new PrintWriter(os);
//			
//				os.flush();
//		
//				streamout abc = new streamout(a_2.getErrorStream(), "abc");
//				streamout abc2 = new streamout(a_2.getInputStream(), "abc");
//				
//				InputStream stderr = a_2.getErrorStream();
//				InputStreamReader isr = new InputStreamReader(stderr);
//				BufferedReader br = new BufferedReader(isr);
//		
//				
//				abc.start();
//				abc2.start();
//				 
//			    a_2.waitFor();
//				abc.interrupt();
//				abc2.interrupt();		
//			}
//			if(a_3 == null)
//			{
//				File rightdim = new File(userDirString + "/test.R");
//				FileWriter fw = new FileWriter(rightdim, true);
//				fw.write("X = as.matrix(read.table(\"" + file.getPath() + "\"))\n" + 
//						"write.table(t(X), \"" + userDirString +"/X_rightdim.txt\"," + "row.names = F, col.names = F, quote = F)");
//				System.out.println(rightdim.getPath());
//				a_3 = Runtime.getRuntime().exec("R CMD BATCH " + rightdim.getPath());
//				
//				fw.flush();
//				fw.close();
//		
//				InputStream stderr2 = a_3.getInputStream();
//				InputStreamReader isr2 = new InputStreamReader(stderr2);
//				BufferedReader br2 = new BufferedReader(isr2);
//
//				java.io.OutputStream os = a_3.getOutputStream();
//				PrintWriter pw = new PrintWriter(os);
//	
//				streamout abc = new streamout(a_3.getErrorStream(), "abc");
//				streamout abc2 = new streamout(a_3.getInputStream(), "abc");
//				
//				InputStream stderr = a_3.getErrorStream();
//				InputStreamReader isr = new InputStreamReader(stderr);
//				BufferedReader br = new BufferedReader(isr);
//		
//				
//				abc.start();
//				abc2.start();
//				 
//			    a_3.waitFor();
//				abc.interrupt();
//				abc2.interrupt();
//				
//			}
//			if(a_4 == null)
//			{
//				a_4 = Runtime.getRuntime().exec("R CMD BATCH --args -Xpath=" + userDirString + "/X_rightdim.txt " + "-Kpath="+userDirString+"/K.txt -VCpath="+userDirString+"/VC.txt -outputPath="+ userDirString +" -- /home/ktg/MultiTrans/generateR.R "+userDirString+"/generateR.log");
//				
//				InputStream stderr2 = a_4.getInputStream();
//				InputStreamReader isr2 = new InputStreamReader(stderr2);
//				BufferedReader br2 = new BufferedReader(isr2);
//
//				java.io.OutputStream os = a_4.getOutputStream();
//				PrintWriter pw = new PrintWriter(os);
//			
//				os.flush();
//		
//				streamout abc = new streamout(a_4.getErrorStream(), "abc");
//				streamout abc2 = new streamout(a_4.getInputStream(), "abc");
//				
//				InputStream stderr = a_4.getErrorStream();
//				InputStreamReader isr = new InputStreamReader(stderr);
//				BufferedReader br = new BufferedReader(isr);
//		
//				
//				abc.start();
//				abc2.start();
//				 
//			    a_4.waitFor();
//				abc.interrupt();
//				abc2.interrupt();		
//			}
//			if(a_5 == null)
//			{
//				a_5 = Runtime.getRuntime().exec("java -jar /home/ktg/MultiTrans/generateC/generateC.jar 1000 "+ userDirString +"/r.txt "+ userDirString + "/ccc.txt");
//				
//				InputStream stderr2 = a_5.getInputStream();
//				InputStreamReader isr2 = new InputStreamReader(stderr2);
//				BufferedReader br2 = new BufferedReader(isr2);
//
//				java.io.OutputStream os = a_5.getOutputStream();
//				PrintWriter pw = new PrintWriter(os);
//			
//				os.flush();
//		
//				streamout abc = new streamout(a_5.getErrorStream(), "abc");
//				streamout abc2 = new streamout(a_5.getInputStream(), "abc");
//				
//				InputStream stderr = a_5.getErrorStream();
//				InputStreamReader isr = new InputStreamReader(stderr);
//				BufferedReader br = new BufferedReader(isr);
//		
//				abc.start();
//				abc2.start();
//			    a_5.waitFor();
//				abc.interrupt();
//				abc2.interrupt();		
//			}
//			File f_check = new File(userDirString + "/ccc.txt"); 
//				try {
//					java.util.Properties properties = System.getProperties();
//					properties.put("mail.smtp.starttls.enable", "true");
//					properties.put("mail.smtp.host", "smtp.gmail.com");
//					properties.put("mail.smtp.auth", "true");
//					properties.put("mail.smtp.port", "587");
//					
//					Authenticator auth = new GoogleAuthentication();
//					Session s = Session.getDefaultInstance(properties, auth);
//					Message msg = new MimeMessage(s);
//					Address sender_address = new InternetAddress("taegun89@gmail.com");
//					Address receiver_address = new InternetAddress(emailAddress);
//			
//					msg.setHeader("content-type", "text/html;charset=UTF-8");
//					msg.setFrom(sender_address);
//					msg.addRecipient(Message.RecipientType.TO, receiver_address);
//					msg.setSubject("test");
//					
//					if(file.exists()) {
//					// Create the message part
//					   BodyPart messageBodyPart = new MimeBodyPart();
//
//					   // Fill the message
//					   messageBodyPart.setText("�뀒�뒪�듃�슜 硫붿씪�쓽 �궡�슜�엯�땲�떎.");
//					   Multipart multipart = new MimeMultipart();
//					   multipart.addBodyPart(messageBodyPart);
//					   
//					   // 泥ル쾲吏� �뙆�씪�쓣 諛붾뵒�뙆�듃�뿉 �꽕�젙�븳�떎.
//					   messageBodyPart = new MimeBodyPart();
//					   FileDataSource fds = new FileDataSource(f_check);
//					   messageBodyPart.setDataHandler(new DataHandler(fds));
//					   messageBodyPart.setFileName(fds.getName());
//					   multipart.addBodyPart(messageBodyPart);
//				
//					   // Put parts in message
//					   msg.setContent(multipart);
//					}
//					else {
//						// Create the message part
//						   BodyPart messageBodyPart = new MimeBodyPart();
//
//						   // Fill the message
//						   messageBodyPart.setText("error");
//						   Multipart multipart = new MimeMultipart();
//						   multipart.addBodyPart(messageBodyPart);
//						   
//						   msg.setContent(multipart);
//					}
//					Transport.send(msg);
//				}
//				catch(Exception e) {
//					e.printStackTrace();
//				}
//			
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
//
//}
