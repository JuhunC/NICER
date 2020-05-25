package Main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
/**
 * Servlet implementation class eQTL
 */
@WebServlet("/eQTL")
@MultipartConfig(maxFileSize = -1, maxRequestSize = -1,location =Setup.FileSaveDirectory)
public class eQTL extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public eQTL() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher view = request.getRequestDispatcher("eQTL.jsp");
		view.forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String emailAddress = request.getParameter("emailAddress");
		System.out.println(emailAddress);
		
		// Get Current Time
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");//dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		
		// Make Directory
		String userDirString = Setup.FileSaveDirectory+emailAddress+"/";
		
		File userDir=new File(userDirString);    
		System.out.println(userDirString);
		if(userDir.mkdir()) {
			userDirString += strDate;
			userDir = new File(userDirString);
			if(userDir.mkdir())
				System.out.println("Directory Made");
		}else{
			userDirString += strDate;
			userDir = new File(userDirString);
			if(userDir.mkdir())
				System.out.println("Directory Made");
		}
		
		// Upload SNP file
		Part part = request.getPart("SNP_pos");
		File file = new File(userDirString+"/x_pos.txt");
		try (InputStream inputStream= part.getInputStream()) { // save uploaded file
			Files.copy(inputStream, file.toPath());
		}
		
		// Upload Phenotype file
		Part part1 = request.getPart("Phenotype_pos");
		File file1 = new File(userDirString+"/y_pos.txt");
		try (InputStream inputStream= part1.getInputStream()) { // save uploaded file
			Files.copy(inputStream, file1.toPath());
		}
		
		// Upload Phenotype file
		Part part2 = request.getPart("P-Value");
		File file2 = new File(userDirString+"/p_value.txt");
		try (InputStream inputStream= part2.getInputStream()) { // save uploaded file
			Files.copy(inputStream, file2.toPath());
		}
		
		/** ���⿡ NICE Instruction�� �����ϴ� �������� �����ϸ� �˴ϴ�. **/
		
	/*	RequestDispatcher view = request.getRequestDispatcher("index.html");
		view.forward(request, response); */
	
		try {
			
			
				Process a_0 = Runtime.getRuntime().exec(Setup.NICEdir+"/eQTLmap4 -g "
						+ userDirString + "/y_pos.txt -s " + userDirString + "/x_pos.txt -p " 
					     + userDirString + "/p_value.txt -m 2.5 -M 5 -o " + userDirString + "/eQTL.png -z 2");
				
				a_0.waitFor();

			   	String _tmp = userDirString+"/eQTL.png";//Setup.FileSaveDirectory+emailAddress+"/"+strDate+"/eQTL.png";
			   	// send email
			   	NICEServlet.sendResultMail(Setup.FileSaveDirectory+emailAddress+"/"+strDate,
			   			"/eQTL.png",emailAddress, 0);
			   	
			   	_tmp = _tmp.replace("/", Setup.urlencode);
				String address = "http://"+Setup.ipAddr+"/NICER/Download?file="+_tmp;
				System.out.println(address + "123456");				
				response.setContentType("text/html; charset=euc-kr");
				StringBuffer tet = new StringBuffer();
				tet.append("<script language='javascript'>");
				tet.append("location.href = \""+address+"\"");
				tet.append("</script>");
				ServletOutputStream out = response.getOutputStream();
				out.println(tet.toString());
				out.flush();
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
