import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Silas on 2017/2/20.
 */
public class main {

    public static void main(String[] args) throws IOException{
        FileReader fr = new FileReader("E:\\workspace\\readCSV\\src\\stockdata.csv");
        BufferedReader br1 = new BufferedReader(fr);
        String s = new String();
        int i =0;
        Stock[] s1 = new Stock[14000];
        s = br1.readLine();
        while((s = br1.readLine())!=null) {
            s1[i] = new Stock(s);
            i++;
        }
    }
}
