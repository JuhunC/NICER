package Main;

import METASOFT.*;
public class MultiTrans_Runnable implements Runnable {
	private int thr_num;
	private int tl_thr_num;
	private String email_dir;
	private int snp_cnt;
	private int pheno_cnt;
	private int ind_cnt;
	private int snp_num;
	private int window_size;
	MultiTrans_Runnable(String email_dir_, int snp_num, int window_size){
		this.tl_thr_num = tl_thr_num;
		this.snp_num = snp_num;
		email_dir = email_dir_;
		this.window_size = window_size;
	}
	public void run() {
//		MultiTrans.transposeFile(email_dir+thr_num+"/X.txt", email_dir+thr_num+"/X_rightdim.txt");
		snp_cnt = MultiTrans.countXfile(email_dir+thr_num+"/X.txt");
		pheno_cnt = MultiTrans.countXfile(email_dir+"/Y.txt");
		ind_cnt = MultiTrans.countXfile(email_dir+"/Y_rightdim.txt");
		
//		runTTestStatic();
		runStage1();
		runStage2(); // MetaSoft
		runStage3();
	}
	//Generate correlation matrix r in the rotate space
	private void runStage3() {
		try {

			String cmd = 
					"R CMD BATCH --args "
					+ "-Xpath=" + email_dir+ "/X_rightdim.txt "
					+ "-Kpath=" + email_dir+ "/K.txt "
					+ "-VCpath=" + email_dir + "/VC.txt "
					+ "-outputPath=" + email_dir + " -- " + "./generateR.R" 
					+ email_dir + "/generateR.log";		
			Process proc = Runtime.getRuntime().exec(cmd);		 

			proc.waitFor();	
			System.out.println("Finished Stage 3");
		}catch(Exception e) {
			MultiTrans.printERROR("Error while running Stage3!!!");
			e.printStackTrace();
		}
	}
	private void runStage2() {
		try {
			Process ps = Runtime.getRuntime().exec(
					"\r\n" + 
					"python ./Pylmm_MultiTrans/pylmmGWAS_multiPhHeri.py -v "
					+ " --emmaSNP=" + email_dir + "X.txt" 
					+ "--kfile=" + email_dir +"K.txt"
					+ " --emmaPHENO=" + email_dir +"Y.txt " + email_dir + "VC.txt");
			ps.waitFor();

			System.out.println("Finished Stage 2");
		}catch(Exception e) {
			MultiTrans.printERROR("Error while running Stage2!!!");
			e.printStackTrace();
		}
	}
	private void runStage1() {
		try {
			Process proc = Runtime.getRuntime().exec(
					"python ./Pylmm_MultiTrans/pylmmKinship.py -v" 
					+ " --emmaSNP=" + email_dir +"X.txt"
					+ " --emmaNumSNPs=" + window_size + " " 
					+ email_dir +"K.txt");
			proc.waitFor();
			System.out.println("Finished Stage 1");
		}catch(Exception e) {
			MultiTrans.printERROR("Error while running Stage1!!!");
			e.printStackTrace();
		}
	}
	private void runTTestStatic() {
		String phenos = pheno_cnt+" ";
		String snps = snp_cnt+" ";
		String indiv = ind_cnt+" ";
		try {
			Process ttest = Runtime.getRuntime().exec(Setup.NICEdir+"t_test_static "
					+ phenos + snps + indiv + email_dir + "/Y.txt " + email_dir+thr_num+"/X.txt" + " "   
				     + email_dir+thr_num + "/p_ttest.txt");
			ttest.waitFor();
		} catch (Exception e) {
			MultiTrans.printERROR("Error for Thread "+thr_num+"\t While running t_test_static!!!");
			e.printStackTrace();
		}
	}
}
