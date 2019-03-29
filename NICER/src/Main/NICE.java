package Main;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.*;

public class NICE {
	private File x_file; // SNP file
	private File y_file; // Pheno file
	public String email_dir;
	private int thr_num; // total number of threads(1,2,...)
	private Thread[] thr;
	
	NICE(String emailaddr, HttpServletRequest request){
		// create directory
		email_dir = Setup.FileSaveDirectory + emailaddr+"/";
		File userDir = new File(email_dir);
		if(userDir.mkdir()) {			//this part need Optimize
			Process f_chm = null;
			try {
				if(f_chm == null) {
					f_chm = Runtime.getRuntime().exec("chmod 777 "+ email_dir);
					
					f_chm.waitFor();
				}						
			}
			catch(Exception e) {
				e.printStackTrace();
			} 
			email_dir += getCurrentStrDate();// append date within email address 
			userDir = new File(email_dir);
			userDir.mkdir();
		}
	}
	public void run(String thr_num_str) {
		createThreadDir(thr_num_str);
		
		// count lines for each thr
		int x_cnt = countXfile(x_file.getAbsolutePath());
		int thr_ln_x = x_cnt / thr_num +1;
		System.out.println("thr_ln_x"+thr_ln_x);
		
		divideXfile(thr_ln_x);
		
		createNrunThread();
		waitThread();
		
		combineResults();
		
		return; // end of running NICE
	}
	private void combineResults() {
		try {
			FileWriter fw = new FileWriter(email_dir+"/NICE.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i =0;i<thr_num;i++) {
				FileReader fr = new FileReader(email_dir+thr_num+"/NICE.txt");
				BufferedReader br = new BufferedReader(fr);
				String tmp = br.readLine();
				while(tmp!=null) {
					tmp+= "\n";
					bw.write(tmp.toCharArray());
					tmp = br.readLine();
				}
				br.close();
				fr.close();
			}
			bw.close();
			fw.close();
		}catch(Exception e) {
			printERROR("Error while combining NICE.txt...!!");
			e.printStackTrace();
		}
	}
	private void waitThread() {
		for(int i = 0; i < thr_num; i++) {
			try {
				thr[i].join();
			}catch(InterruptedException e) {
				printERROR("Error while joining thread"+i+"!!");
				e.printStackTrace();
			}
		}
	}
	private void createNrunThread() {
		thr = new Thread[thr_num];
		for(int i =0;i<thr_num;i++) {
			thr[i] = new Thread(new NICE_Runnable(i+1, email_dir));
			thr[i].start();
		}
	}
	private void divideXfile(int ln_cnt) {
		try {
			int thr_cnt = 1;
			FileReader fr = new FileReader(x_file.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);
			String ln = br.readLine();
			
			FileWriter fw = new FileWriter(email_dir+"/"+thr_cnt+"/"+"X.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			
			int cnt =0;
			while(ln != null) {
				cnt++;
				bw.write(ln.toCharArray());
				ln = br.readLine();
				
				if(cnt == ln_cnt) {
					bw.close();fw.close();
					thr_cnt++; cnt = 0;
					fw = new FileWriter(email_dir+"/"+thr_cnt+"/"+"X.txt");
					bw = new BufferedWriter(fw);
				}
			}
			bw.close(); fw.close();
		}catch(Exception e) {
			printERROR("Error while dividing X file!!");
			e.printStackTrace();
		}
	}
	/**
	 * Count Number of Lines in X.txt
	 * @return
	 */
	public static int countXfile(String str) {
		File x_file = new File(str);
		int ln_cnt = 0;
		try {
			FileReader fr = new FileReader(x_file.getAbsolutePath());
			BufferedReader br = new BufferedReader(fr);
			String tmp = br.readLine();
			while(tmp!=null) {
				ln_cnt++;
				tmp=br.readLine();
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			printERROR("Error while reading X file to count lines!!");
			e.printStackTrace();
		}
		return ln_cnt;
	}
	/**
	 * Create Folder for each Threads
	 * @param thr_num_str
	 */
	private void createThreadDir(String thr_num_str) {
		if(thr_num_str.equals("5set")) {
			thr_num = 5;
		}else{ //default
			thr_num = 10;
		}
		File[] userDir_ = new File[thr_num];
		for(int i = 0; i < thr_num; i++) {
			userDir_[i] = new File(email_dir+"/" + String.valueOf(i+1));
			userDir_[i].mkdirs();
			Process f_chm = null;
			try {
				if(f_chm == null) {
					f_chm = Runtime.getRuntime().exec("chmod 777 "+ userDir_[i]);
					f_chm.waitFor();
				}						
			}
			catch(Exception e) {
				e.printStackTrace();
			}     
		
		}
	}
	public void downloadInputData(HttpServletRequest request, String input_type_str) {
		 if(input_type_str.equals("type1")) {
			 downloadPlink(request);
		 }else if(input_type_str.equals("type2")) {
			 downloadXY(request);
		 }else if(input_type_str.equals("type3")) {
			 downloadTransposedXY(request);
		 }
	}
	private void downloadTransposedXY(HttpServletRequest request) {
		// Upload SNP file	
		try {
			Part part = request.getPart("SNPfile2");
			x_file = new File(email_dir+"/X_rightdim.txt");
			try (InputStream inputStream= part.getInputStream()) { // save uploaded file
				Files.copy(inputStream, x_file.toPath());
			}		
			// Upload Phenotype file
			Part part1 = request.getPart("Phenotypefile2");
			y_file = new File(email_dir+"/Y_rightdim.txt");
			try (InputStream inputStream= part1.getInputStream()) { // save uploaded file
				Files.copy(inputStream, y_file.toPath());
			}
		}catch(Exception e) {
			printERROR("Error Occured while uploading transposed XY data!!");
			e.printStackTrace();
		}
		transposeFile(email_dir+"/X_rightdim.txt",email_dir+"/X.txt");
		transposeFile(email_dir+"/Y_rightdim.txt",email_dir+"/Y.txt");
	}
	private void downloadXY(HttpServletRequest request) {
		try {
			// Upload SNP file	
			Part part = request.getPart("SNPfile");
			x_file = new File(email_dir+"/X.txt");
			try (InputStream inputStream= part.getInputStream()) { // save uploaded file
				Files.copy(inputStream, x_file.toPath());
			}		
			// Upload Phenotype file
			Part part1 = request.getPart("Phenotypefile");
			y_file = new File(email_dir+"/Y.txt");
			try (InputStream inputStream= part1.getInputStream()) { // save uploaded file
				Files.copy(inputStream, y_file.toPath());
			}
			transposeFile(x_file.getPath(),email_dir+"/Y_rightdim.txt");
		}catch(Exception e) {
			printERROR("Error Occurred while uploading XY data!!");
			e.printStackTrace();
		}
	}
	private void downloadPlink(HttpServletRequest request) {
		try {
			Part part_00 = request.getPart("BIM_file");
			Part part_01 = request.getPart("BED_file");
			Part part_02 = request.getPart("FAM_file");
			Part part_03 = request.getPart("EX_file");
			
			File file_0 = new File(email_dir+"/input_f.bim");
			File file_1 = new File(email_dir+"/input_f.bed");
			File file_2 = new File(email_dir+"/input_f.fam");
			File file_3 = new File(email_dir+"/input_y.txt");
			
			try (InputStream inputStream= part_00.getInputStream()) { // save uploaded file
				Files.copy(inputStream, file_0.toPath());
			}
			try (InputStream inputStream= part_01.getInputStream()) { // save uploaded file
				Files.copy(inputStream, file_1.toPath());
			}	
			try (InputStream inputStream= part_02.getInputStream()) { // save uploaded file
				Files.copy(inputStream, file_2.toPath());
			}
			try (InputStream inputStream= part_03.getInputStream()) { // save uploaded file
				Files.copy(inputStream, file_3.toPath());
			}	
			Process plink_data = null;
			plink_data = Runtime.getRuntime().exec("/home/ktg/plink/plink --bfile " + email_dir + "/input_f --recodeA --noweb --maf 0.3 --out "+email_dir+"/input_x");
			plink_data.waitFor();
			
			Process pl_to_input = null; //x data
			pl_to_input = Runtime.getRuntime().exec(Setup.NICEdir+"/pl_to_input " + email_dir);
			pl_to_input.waitFor();
			
			Process pl_to_input_y = null; // y data
			pl_to_input_y = Runtime.getRuntime().exec(Setup.NICEdir+"pl_to_input_y " + email_dir);
			pl_to_input_y.waitFor();
			
			// X_rightdim -> X.txt Y_rightdim -> Y.txt
			transposeFile(email_dir+"/X_rightdim.txt",email_dir+"/X.txt");
			transposeFile(email_dir+"/Y_rightdim.txt",email_dir+"/Y.txt");			
		}
		catch(Exception e) {
			printERROR("Error Occurred while uploading// converting PLINK data!!");
			e.printStackTrace();
		}
	}
	/**
	 * Error Printing Screen
	 * @param err_str error message
	 */
	public static void printERROR(String err_msg) {
		System.err.print(err_msg+"\n");
	}
	/**
	 * Get String formatted Current Date
	 * @return
	 */
	public String getCurrentStrDate() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");//dd/MM/yyyy
		Date now = new Date();
		return sdfDate.format(now);
	}
	public static void transposeFile(String inputFile, String outputFile) {
		try {
			Process pro;
			File rightdim = new File(Setup.NICEdir + "/test.R");
			FileWriter fw = new FileWriter(rightdim, true);
			fw.write("X = as.matrix(read.table(\""+inputFile+"X.txt\"))\n" + 
					"write.table(t(X), \"" + outputFile +"\"," + "row.names = F, col.names = F, quote = F)\n");
			pro = Runtime.getRuntime().exec("R CMD BATCH " + rightdim.getPath());
			
			fw.flush();
			fw.close();
			 
			pro.waitFor();
		}catch(Exception e){
			printERROR("Error while transposing "+inputFile+"!!!");
			e.printStackTrace();
		}
	}
}