import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

/**
 * Created by Silas on 2017/2/20.
 */
public class main extends JFrame{

    public static main mainFrame;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mainFrame = new main();
            }
        });
    }

        public main() {
            super("Stock Analysis");
            setSize(800,600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            add(new StockSystem());
            setVisible(true);
        }
        StockSystem view = new StockSystem();

}
