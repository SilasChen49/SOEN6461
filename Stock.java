/**
 * Created by Silas on 2017/2/20.
 */
public class Stock {

    public String date;
    public double[] data;

    Stock(String s){
        data = new double[6];
        String[] s1 = new String[7];
        s1 = s.split("[,]");
        date = s1[0];
        for (int i=0; i<6; i++){
            data[i]  = Double.parseDouble(s1[i+1]);
        }

    }
}
