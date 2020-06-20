package Main;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import javax.servlet.http.Part;

@MultipartConfig(maxFileSize = -1, maxRequestSize = -1, location = Setup.FileSaveDirectory_Multitrans)
public class MultiTrans {
	private File x_file; // SNP file
	private File y_file; // Pheno file
	private File threshold_file; // Threshold file
	public String email_dir;
	private int snp_num;
	private int window_size;
	private int s_num;
	private Thread thr;
	private int tl_snp_cnt = 0;
	private final int FORCE_THREAD = 0; // Force Thread Number(not to force <= 0)

	MultiTrans(String emailaddr, HttpServletRequest request) {
		// create directory
		email_dir = Setup.FileSaveDirectory_Multitrans + emailaddr + "/";
		File userDir = new File(email_dir);
		if (!userDir.exists() && userDir.mkdir()) { // this part need Optimize
			Process f_chm = null;
			try {
				if (f_chm == null) {
					f_chm = Runtime.getRuntime().exec("chmod 777 " + email_dir);

					f_chm.waitFor();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		email_dir += getCurrentStrDate() + "/";// append date within email address
//		email_dir += "2019-04-07_13-17-53/";
		userDir = new File(email_dir);
		userDir.mkdir();
	}

	public void run(String snp_num, String window_size, String s_num) {
		createThreadDir();
		createNrunThread(snp_num, window_size, s_num);
		waitThread();

		combineResults();

		return; // end of running MultiTrans
	}

	private void createThreadDir() {

		File userDir = new File(email_dir);
		userDir.mkdirs();
		Process f_chm = null;
		try {
			if (f_chm == null) {
				f_chm = Runtime.getRuntime().exec("chmod 777 " + userDir);
				f_chm.waitFor();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void combineResults() {
		try {
			FileWriter fw = new FileWriter(email_dir + "/MultiTrans.output");
			BufferedWriter bw = new BufferedWriter(fw);
			FileReader fr = new FileReader(email_dir + "/MultiTrans.output");
			
			BufferedReader br = new BufferedReader(fr);
			String tmp = br.readLine();
			while (tmp != null) {
				bw.write(tmp + "\n");
				tmp = br.readLine();
			}
			br.close();
			fr.close();

			bw.close();
			fw.close();
		} catch (Exception e) {
			printERROR("Error while combining MultiTrans.txt...!!");
			e.printStackTrace();
		}
	}

	private void waitThread() {
		try {
			thr.join();
		} catch (InterruptedException e) {
			printERROR("Error while joining thread!!");
			e.printStackTrace();
		}

	}

	private void createNrunThread(String snp_num, String window_size, String s_num) {
		thr = new Thread(new MultiTrans_Runnable(email_dir, snp_num, window_size, s_num));
		thr.start();

	}

	/*
	 * private void divideXfile(int ln_cnt) { try { int ttl_ln =
	 * countXfile(x_file.getAbsolutePath()); FileReader fr = new
	 * FileReader(x_file.getAbsoluteFile()); BufferedReader br = new
	 * BufferedReader(fr); String tmp; int thr_cnt = 1;
	 * 
	 * BufferedWriter bw = new BufferedWriter(new FileWriter(email_dir + "/" +
	 * thr_cnt + "/X.txt")); for (int i = 0; i < ttl_ln; i++) { tmp = br.readLine();
	 * if (i != 0 && i % ln_cnt == 0) { System.out.println(i); bw.close();
	 * thr_cnt++;
	 * 
	 * bw = new BufferedWriter(new FileWriter(email_dir + "/" + thr_cnt +
	 * "/X.txt")); } bw.write(tmp + "\n"); } bw.close(); // int thr_cnt = 1; //
	 * FileReader fr = new FileReader(x_file.getAbsoluteFile()); // BufferedReader
	 * br = new BufferedReader(fr); // String ln = br.readLine(); // // FileWriter
	 * fw = new FileWriter(email_dir+"/"+thr_cnt+"/"+"X.txt"); // BufferedWriter bw
	 * = new BufferedWriter(fw); // // int cnt =0; // while(ln != null) { // cnt++;
	 * // ln+="\n"; // bw.write(ln.toCharArray()); // ln = br.readLine(); // //
	 * if(cnt == ln_cnt) { // bw.close();fw.close(); // thr_cnt++; cnt = 1; // fw =
	 * new FileWriter(email_dir+"/"+thr_cnt+"/"+"X.txt"); // bw = new
	 * BufferedWriter(fw); // } // } // bw.close(); fw.close(); // br.close();
	 * fr.close(); } catch (Exception e) {
	 * printERROR("Error while dividing X file!!"); e.printStackTrace(); } }
	 */

	/**
	 * Count Number of Lines in X.txt
	 * 
	 * @return
	 */
	public static int countXfile(String str) {
		File x_file = new File(str);
		int ln_cnt = 0;
		try {
			FileReader fr = new FileReader(x_file.getAbsolutePath());
			BufferedReader br = new BufferedReader(fr);
			String tmp = br.readLine();
			while (tmp != null) {
				ln_cnt++;
				tmp = br.readLine();
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			printERROR("Error while reading " + str + " file to count lines!!");
			e.printStackTrace();
		}
		return ln_cnt;
	}

	/**
	 * Create Folder for each Threads
	 * 
	 * @param thr_num_str
	 */

	public void downloadInputData(HttpServletRequest request) {
		try {
			Part part = request.getPart("SNPfile");
			File x = new File(email_dir + "/X.txt");
			try (InputStream inputStream = part.getInputStream()){ // save uploaded file
				Files.copy(inputStream, x.toPath());
			}
			Part part1 = request.getPart("Phenotypefile");
			File y = new File(email_dir + "/Y.txt");
			try (InputStream inputStream = part1.getInputStream()) { // save uploaded file
				Files.copy(inputStream, y.toPath());
			}
			Part part2 = request.getPart("Thresholdfile");
			File x_rightdim = new File(email_dir + "/X_rightdim.txt");
			try (InputStream inputStream = part2.getInputStream()) { // save uploaded file
				Files.copy(inputStream, x_rightdim.toPath());
			}
			Part part3 = request.getPart("Thresholdfile");
			File r_file = new File(email_dir + "/r.txt");
			try (InputStream inputStream = part3.getInputStream()) { // save uploaded file
				Files.copy(inputStream, r_file.toPath());
			}
			Part part4 = request.getPart("Sortedfile");
			File sorted_file = new File(email_dir + "/sorted");
			try (InputStream inputStream = part4.getInputStream()) { // save uploaded file
				Files.copy(inputStream, sorted_file.toPath());
			}
		} catch (Exception e) {
			printERROR("Error Occured while uploading transposed XY data!!");
			e.printStackTrace();
		}
	}

	
	/**
	 * Error Printing Screen
	 * 
	 * @param err_str error message
	 */
	public static void printERROR(String err_msg) {
		System.err.print(err_msg + "\n");
	}

	/**
	 * Get String formatted Current Date
	 * 
	 * @return
	 */
	public String getCurrentStrDate() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");// dd/MM/yyyy
		Date now = new Date();
		return sdfDate.format(now);
	}

}
