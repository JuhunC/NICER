package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Servlet implementation class Manhattan
 */
@WebServlet("/Manhattan")
@MultipartConfig(maxFileSize = -1, maxRequestSize = -1,location =Setup.FileSaveDirectory)
public class Manhattan extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private boolean median = false;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Manhattan() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher view = request.getRequestDispatcher("eQTL.html");
		view.forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailAddress = request.getParameter("emailAddress");
		
		// Get session info
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("loginId");
		String user_email = (String) session.getAttribute("user_email");
		
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
		//chmod 777 go	
		Process f_chm = null;
		try {
			if(f_chm == null) {
				f_chm = Runtime.getRuntime().exec("chmod 777 "+ userDir);
				
				f_chm.waitFor();
			}						
		}
		catch(Exception e) {
			e.printStackTrace();
		}  
		//
		
		
		// Upload p_value file
		Part part = request.getPart("P_file");
		File file = new File(userDirString+"/p_value.txt");
		try (InputStream inputStream= part.getInputStream()) { // save uploaded file
			Files.copy(inputStream, file.toPath());
		}
		
	
		Process a_0_1 = null;
		Process a_0_2 = null;
		try {
			
				if(a_0_1 == null ) { //line_number.txt make
				
				//for pipe cmd, use "/bin/sh", "-c"
				String[] command = {
						"/bin/sh",
						"-c",
						"wc -l " + userDirString + "/p_value.txt >> " + userDirString + "/line_num.txt"
				};
				a_0_1 = Runtime.getRuntime().exec(command);
						 
			    a_0_1.waitFor();
				
				}
			if(a_0_2 == null ) {
				
				int num1 = NICE.countXfile(file.getPath());
				int num2 = 0;
				BufferedReader br = new BufferedReader(new FileReader(new File(file.getPath())));
				String temp = br.readLine();
				num2 = temp.split(" ").length;
				br.close();
				File manhattan = new File(userDirString + "/man.R");
				FileWriter fw = new FileWriter(manhattan, true);
				System.out.println(num1+"\t"+num2);
				fw.write(//"install.packages(\"dplyr\")\n" + 
						"library(dplyr)\n" + 
						"\n" + 
						//"install.packages(\"data.table\")\n" + 
						"library(data.table)\n" + 
						"\n" + 
						"p_full <- fread(\""+file.getPath()+"\") %>% as.matrix\n" + 
						"sum(p_full == 0)\n" + 
						"\n" + 
						"\n" + 
						"log10p_full <- -log10(p_full)\n" + 
						"sum(log10p_full == Inf)\n" + 
						"\n" + 
						"\n" + 
						"log10p_full[log10p_full == Inf] <- max(log10p_full[log10p_full != Inf])\n" + 
						"\n" + 
						"sum(is.infinite(log10p_full))\n" + 
						"\n" + 
						"mean_p <- rowMeans(log10p_full)\n" + 
						"mean_p %>% length\n" + 
						"mean_p %>% head\n" + 
						"png(\""+userDirString +"/manhattan.png"+"\", width=2000, height=1000, pointsize=18)\n" + 
						"plot(mean_p)\n" + 
						"dev.off()\n");
				fw.close();
//				fw.write("library(qqman)\n" +
//						 "x = as.matrix(read.table(\"" + file.getPath() + "\"))\n" +
//						 "x1 = array(,c("+ num1 +"),)\n" + 
//						 "x2 = 1:"+ num1 +"\n" + 
//						 "x3 = array(,c("+ num1 +"),)\n" + 
//						 "x4 = array(,c("+ num1 +"),)\n" +
//						 "for(i in 1:"+ num1 +"){\n" + 
//						 "x1 = paste(c(\"rs\",i), collapse=\"\")\n" + 
//						 "x3[i] = 1\n");
//				if(median == true) {
//					fw.write("x4[i] = median(-log10(x[i,]))\n");			
//				}else {
//					fw.write("x4[i] = mean(-log10(x[i,]))\n");
//				}
//				fw.write("}\n" + 
//						 "data <-data.frame(SNP=x1,CHR=x2,BP=x3, P=x4)\n" + 
//						 "png(\""+ userDirString +"/manhattan.png"+"\", width=2000, height=1000, pointsize=18)\n" + 
//						 "manhattan(data)\n" + 
//						 "dev.off()\n");
//				fw.close();
				
				a_0_2 = Runtime.getRuntime().exec("R CMD BATCH " + manhattan.getPath());
				
				a_0_2.waitFor();	
				
				String _tmp = userDirString+"/manhattan.png";
				
				_tmp = _tmp.replace("/",Setup.urlencode);
				String address = "http://"+Setup.ipAddr+"/NICER/Download?file="+_tmp;
				
				
				
				NICEServlet.sendResultMail(Setup.FileSaveDirectory+emailAddress+"/"+strDate,
			   			"/manhattan.png",emailAddress, 0);
				
				response.setContentType("text/html; charset=euc-kr");
				StringBuffer tet = new StringBuffer();
				tet.append("<script language='javascript'>");
				tet.append("location.href = \""+address+"\"");
				tet.append("</script>");
				ServletOutputStream out = response.getOutputStream();
				out.println(tet.toString());
				out.flush();
				
				}
				
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//RequestDispatcher view = request.getRequestDispatcher("address");
		//view.forward(request, response);
	
	}

}
