import java.io.*;

//class and methods must be called from within a try block
//
public class CSVParse {
    private String path = "";
    BufferedReader br = null;

    private CSVParse() {}

    public CSVParse(String file_path) throws Exception {
	path=file_path;
	br= new BufferedReader(new FileReader(path));
    }

    public double parse() throws Exception {
	String line= "",line2= "";
	double sum=0.0;
	int count=0;

	while ((line=br.readLine()) != null) {
	    line2=br.readLine();
	    String[] touching=line.split(",");
	    String[] waves=line2.split(",");

	    for(int i=2;i<6;i++) {
		if(touching[i].indexOf("1")>=0) {
		    sum+=Double.parseDouble(waves[i]);
		    count++;
		}
	    }
	}
	if (count==0) throw new RuntimeException("No data in file");

	return (sum/count);	
    }
 

    public static void main(String[] args) {
	try {
	    CSVParse obj=new CSVParse("/home/cammat13/Projects/data.csv");
	    System.out.println(obj.parse());
	}
	catch (Exception e) {
	    System.out.println(e);
	}
    }
}