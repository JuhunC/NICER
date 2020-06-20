package Main;

import METASOFT.*;
public class MultiTrans_Runnable implements Runnable {
	private String email_dir;
	private String snp_num;
	private String window_size;
	private String s_num;
	MultiTrans_Runnable(String email_dir_, String snp_num, String window_size, String s_num){
		this.snp_num = snp_num;
		email_dir = email_dir_;
		this.window_size = window_size;
		this.s_num = s_num;
	}
	public void run() {
		runStage1();
		runStage2();
		runStage3();
		runStage4();
		
		runSlide();
	}
	
	private void runSlide() {
		
		try {
			//slide_1prep
			String cmd = Setup.mainDir+ "/slide.1.0/slide_1prep -C "
					+  email_dir+ "c.txt " + window_size + " "
					+ email_dir+ "prep";
			// slide_2run
			String cmd2 =  Setup.mainDir + "/slide.1.0/slide_2run "
					+ email_dir+ "prep " + email_dir+ "maxstat " 
					+ snp_num+ " " + s_num;
			// slide_3sort
			String cmd3 =  Setup.mainDir + "/slide.1.0/slide_3sort "
					+ email_dir+ "sorted " + email_dir+ "maxstat";
			// slide_4correct
			String cmd4 =  Setup.mainDir + "/slide.1.0/slide_4correct -p "
					+ email_dir+ "sorted " + Setup.MULTITRANSdir + "/threshold.txt "
					+ email_dir+ "MultiTrans.output";
			Process proc = Runtime.getRuntime().exec(cmd);		 
			proc.waitFor();
			Process proc2 = Runtime.getRuntime().exec(cmd2);		 
			proc2.waitFor();
			Process proc3 = Runtime.getRuntime().exec(cmd3);		 
			proc3.waitFor();
			Process proc4 = Runtime.getRuntime().exec(cmd4);		 
			proc4.waitFor();
			System.out.println("Finished running Slide");
		}catch(Exception e) {
			MultiTrans.printERROR("Error while running Slide!!!");
			e.printStackTrace();
		}
		
	}
	
	//Generate correlation band matrix c
	private void runStage4() {
		try {

			String cmd = 
					"java -jar " +  Setup.MULTITRANSdir+ "/generateC/jgenerateC.jar"
					+ window_size + " " + email_dir+ "/r.txt "
					+ email_dir + "/c.txt ";
			Process proc = Runtime.getRuntime().exec(cmd);		 

			proc.waitFor();	
			System.out.println("Finished Stage 4");
		}catch(Exception e) {
			MultiTrans.printERROR("Error while running Stage4!!!");
			e.printStackTrace();
		}
	}
	
	//Generate correlation matrix r in the rotate space
	private void runStage3() {
		try {
			String cmd = 
					"R CMD BATCH --args "
					+ "-Xpath=" + email_dir+ "X_rightdim.txt "
					+ "-Kpath=" + email_dir+ "K.txt "
					+ "-VCpath=" + email_dir + "VC.txt "
					+ "-outputPath=" + email_dir + " -- " 
					+ Setup.MULTITRANSdir+ "/generateR.R" 
					+ email_dir + "generateR.log";		
			Process proc = Runtime.getRuntime().exec(cmd);		 

			proc.waitFor();	
			System.out.println("Finished Stage 3");
		}catch(Exception e) {
			MultiTrans.printERROR("Error while running Stage3!!!");
			e.printStackTrace();
		}
	}
	
	// Estimate heritability
	private void runStage2() {
		try {
			Process ps = Runtime.getRuntime().exec(
					"\r\n" + 
					"python " + Setup.MULTITRANSdir+ "/Pylmm_MultiTrans/pylmmGWAS_multiPhHeri.py -v "
					+ " --emmaSNP=" + email_dir + "X.txt" 
					+ "--kfile=" + email_dir +"K.txt"
					+ " --emmaPHENO=" + email_dir +"Y.txt " 
					+ email_dir + "VC.txt");
			ps.waitFor();

			System.out.println("Finished Stage 2");
		}catch(Exception e) {
			MultiTrans.printERROR("Error while running Stage2!!!");
			e.printStackTrace();
		}
	}
	
	// Generate a Kinship matrix
	private void runStage1() {
		try {
			Process proc = Runtime.getRuntime().exec(
					"python " + Setup.MULTITRANSdir+ "/Pylmm_MultiTrans/pylmmKinship.py -v" 
					+ " --emmaSNP=" + email_dir +"X.txt"
					+ " --emmaNumSNPs=" + snp_num + " " 
					+ email_dir +"K.txt");
			proc.waitFor();
			System.out.println("Finished Stage 1");
		}catch(Exception e) {
			MultiTrans.printERROR("Error while running Stage1!!!");
			e.printStackTrace();
		}
	}
}
