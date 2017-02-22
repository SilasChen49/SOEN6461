import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Created by Silas on 2017/2/20.
 */
public class main {

    public static void main(String[] args) throws IOException{
        FileReader fr = new FileReader("/Users/prashantsharma/Documents/workspace/Stock/src/Sampledata.csv");
        BufferedReader br1 = new BufferedReader(fr);
        String s = new String();
        int i =0;
        Stock[] s1 = new Stock[14000];
        s = br1.readLine();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        while((s = br1.readLine())!=null) {
            s1[i] = new Stock(s);
            dataset.setValue(s1[i].data[3], "Stock Price", s1[i].date);
            i++;
        }
        JFreeChart chart=ChartFactory.createLineChart("Stock Analysis", "Time", "Price", dataset);
        		chart.setBackgroundPaint(Color.white);
        		chart.getTitle().setPaint(Color.red);
        		CategoryPlot p = chart.getCategoryPlot();
        		p.setRangeMinorGridlinePaint(Color.blue);
        		ChartFrame frame = new ChartFrame("Line Chart",chart);
        		frame.setVisible(true);
        		frame.setSize(450,350);
    }
}
