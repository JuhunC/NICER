/*Copyright (c) 2016 ZarLab

     This program is free software: you can redistribute it and/or modify
     it under the terms of the GNU Affero General Public License as published by     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package generateC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class generateCovBand {
	private BufferedReader in;
	private BufferedWriter out;
	private String str;
	private StringTokenizer st;
	private int snpCnt;
	
	public generateCovBand(int windowSize, String corrMatrix, String output){
		try{
			in  = new BufferedReader(new FileReader(corrMatrix));
			out = new BufferedWriter(new FileWriter(output));
			in.readLine();
			snpCnt = 0;
			while((str=in.readLine()) != null){
				snpCnt++;
				st = new StringTokenizer(str);
				for(int i=0; i<(windowSize-snpCnt); i++) 	out.write("0\t");
				for(int i=0; i<(snpCnt-windowSize); i++) 	st.nextToken();
				if(snpCnt <= windowSize)	
					for(int i=0; i<snpCnt; i++) out.write(st.nextToken()+"\t");
				else
					for(int i=0; i<windowSize; i++) out.write(st.nextToken()+"\t");
				out.write("\n");
			}
			System.out.println(snpCnt + " number of lines read");
			in.close(); out.close();
		}catch(IOException ex){ex.printStackTrace();}
	}
}
