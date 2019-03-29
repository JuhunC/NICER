package Main;
import java.io.File;

import METASOFT.*;;
public class NICE_Runnable implements Runnable {
	private int thr_num;
	private String email_dir;
	private int snp_cnt;
	private int pheno_cnt;
	private int ind_cnt;
	NICE_Runnable(int thr_num_, String email_dir_){
		thr_num = thr_num_;
		email_dir = email_dir_;
	}
	public void run() {
		NICE.transposeFile(email_dir+thr_num+"/X.txt", email_dir+thr_num+"/X_rightdim.txt");
		snp_cnt = NICE.countXfile(email_dir+thr_num+"/X.txt");
		pheno_cnt = NICE.countXfile(email_dir+"/Y.txt");
		ind_cnt = NICE.countXfile(email_dir+"/Y_rightdim.txt");
		
		runTTestStatic();
		runStage1();
		runStage2(); // MetaSoft
		runStage3();
	}
	private void runStage3() {
		try {
		Process proc = Runtime.getRuntime().exec(
				"R CMD BATCH --args "
		+"-snp="+ email_dir+thr_num +"/X_rightdim.txt "
		+"-pheno="+email_dir+"/Y_rightdim.txt -MvalueThreshold=0.5 "
		+"-Mvalue="+email_dir+thr_num+"/posterior.txt -minGeneNumber=10 "
		+"-Pdefault="+ email_dir+thr_num +"/p_ttest.txt "
		+"-out="+email_dir+thr_num+"/ "
//		+"-st_snp_num="+ num4 
		+" -NICE=./ -- "+Setup.NICEdir+"/NICE.R" + " " + email_dir+thr_num + "/NICE.Rout");		 
//		File n_check = new File(f_path + "/NICE.txt");

		proc.waitFor();	
		}catch(Exception e) {
			NICE.printERROR("Error for Thread "+thr_num+"\t While running Stage3!!!");
			e.printStackTrace();
		}

	}
	private void runStage2() {
		try {
			String args[] = {" -input ",Setup.NICEdir + "/inputMS.txt",
			"-mvalue", "-mvalue_method","mcmc",
			"-mcmc_sample","1000000","-seed","0","-mvalue_p_thres","1.0","-mvalue_prior_sigma","0.05",
			"-mvalue_prior_beta","1","5",
			"-pvalue_table",Setup.NICEdir+"/HanEskinPvalueTable.txt",
			"-output",email_dir+thr_num +"/posterior.txt"};
			Metasoft ms = new Metasoft(args);
		}catch(Exception e) {
			NICE.printERROR("Error for Thread "+thr_num+"\t While running Stage2!!!");
			e.printStackTrace();
		}
	}
	private void runStage1() {
		try {
			Process proc = Runtime.getRuntime().exec(
					"R CMD BATCH --args" 
					+" -snp=" + email_dir+thr_num + "/X_rightdim.txt" 
					+" -pheno="+email_dir +"/Y_rightdim.txt"
					+" -out=" + email_dir+thr_num+"/" 
					+" -- /home/ktg/NICE/inputMS.R "
					+ email_dir+thr_num + "/inputMS.Rout");
			proc.waitFor();
		}catch(Exception e) {
			NICE.printERROR("Error for Thread "+thr_num+"\t While running Stage1!!!");
			e.printStackTrace();
		}
	}
	private void runTTestStatic() {
		String phenos = pheno_cnt+" ";
		String snps = snp_cnt+" ";
		String indiv = ind_cnt+" ";
		try {
			Process ttest = Runtime.getRuntime().exec(Setup.NICEdir+"/t_test_static "
					+ phenos + snps + indiv + email_dir + "/Y.txt " + email_dir+thr_num+"/X.txt" + " "   
				     + email_dir+thr_num + "/p_ttest.txt");
			ttest.waitFor();
		} catch (Exception e) {
			NICE.printERROR("Error for Thread "+thr_num+"\t While running t_test_static!!!");
			e.printStackTrace();
		}
	}
}