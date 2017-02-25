import com.sun.beans.editors.FontEditor;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by mo on 2017-02-23.
 */
public class View extends JPanel {

    public JLabel dateLabel, periodLabel;
    public JButton displayButton;
    public JTextField dateTextField1, dateTextField2, dateTextField3, dateTextField4, dateTextField5, dateTextField6;
    public JComboBox periodComboBox1, periodComboBox2;
    public Stock[] stock;
    int stockNum = 0;
    String date1, month1, year1, date2, month2, year2;
    int period1, period2;
    int today = 0, flag = 0;
    String startDate, endDate;
    XYSeries xySeries1, xySeries2, xySeries3;

    public View() {
        super();
        setVisible(true);
        setLayout(null);
        setSize(800, 600);

        dateLabel = new JLabel("Date:");
        dateLabel.setBounds(150, 50, 100, 50);
        dateLabel.setFont(new Font("dialog", 0, 20));
        dateTextField1 = new JTextField();
        dateTextField1.setBounds(250, 63, 40, 30);
        add(dateTextField1);
        dateTextField2 = new JTextField();
        dateTextField2.setBounds(300, 63, 40, 30);
        add(dateTextField2);
        dateTextField3 = new JTextField();
        dateTextField3.setBounds(350, 63, 40, 30);
        add(dateTextField3);
        dateTextField4 = new JTextField();
        dateTextField4.setBounds(450, 63, 40, 30);
        add(dateTextField4);
        dateTextField5 = new JTextField();
        dateTextField5.setBounds(500, 63, 40, 30);
        add(dateTextField5);
        dateTextField6 = new JTextField();
        dateTextField6.setBounds(550, 63, 40, 30);
        add(dateTextField6);
        addLabel(290);
        addLabel(340);
        addLabel(490);
        addLabel(540);
        JLabel egLabel = new JLabel("(dd-mm-yy)");
        egLabel.setBounds(140, 70, 100, 50);
        add(egLabel);
        periodLabel = new JLabel("Period:");
        periodLabel.setBounds(150, 150, 100, 50);
        periodLabel.setFont(new Font("dialog", 0, 20));
        displayButton = new JButton("Display");
        displayButton.setBounds(500, 250, 100, 50);
        displayButton.addActionListener(new Display());
        String[] period = {"25", "50", "75"};
        periodComboBox1 = new JComboBox(period);
        periodComboBox2 = new JComboBox(period);
        periodComboBox1.setBounds(300, 150, 100, 50);
        periodComboBox2.setBounds(500, 150, 100, 50);
        add(dateLabel);
        add(periodLabel);
        add(displayButton);
        add(periodComboBox1);
        add(periodComboBox2);
        stock = new Stock[14000];
        xySeries1 = new XYSeries("Stock");
        xySeries2 = new XYSeries("MA1");
        xySeries3 = new XYSeries("MA2");
        try {
            FileReader fr = new FileReader("Sampledata.csv");
            BufferedReader br1 = new BufferedReader(fr);
            String s = new String();
            int i = 0;
            s = br1.readLine();

            while ((s = br1.readLine()) != null) {
                stock[i] = new Stock(s);
                i++;
            }
            stockNum = i - 1;
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        ;
        calculateMAs();
    }

    public void addLabel(int x) {
        JLabel jLabel = new JLabel("-");
        jLabel.setBounds(x, 65, 20, 20);
        add(jLabel);
    }

    class Display implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            date1 = dateTextField1.getText();
            month1 = dateTextField2.getText();
            year1 = dateTextField3.getText();
            date2 = dateTextField4.getText();
            month2 = dateTextField5.getText();
            year2 = dateTextField6.getText();
            startDate = "20" + year1 + "-" + month1 + "-" + date1;
            endDate = "20" + year2 + "-" + month2 + "-" + date2;
            period1 = periodComboBox1.getSelectedIndex();
            period1 = period1 + 6;
            period2 = periodComboBox2.getSelectedIndex();
            period2 = period2 + 6;
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();;
            for (int i = stockNum - 1; i >= 0; i--) {
                if (stock[i].date.compareTo(startDate) >= 0 && stock[i].date.compareTo(endDate) <= 0) {
                    dataset.setValue(stock[i].data[3], "Stock", stock[i].date);
                    dataset.setValue(stock[i].data[period1], "MA1", stock[i].date);
                    dataset.setValue(stock[i].data[period2], "MA2", stock[i].date);
                }
                if (stock[i].date.compareTo(endDate) == 0)
                    today = i;
            }
            flag = decide(period1, period2, today);
            for (int i=375; i<425; i++)
            {
                System.out.print(stock[i].date+": "+decide(period1,period2,i));
                System.out.println();
            }
            JFreeChart chart = ChartFactory.createLineChart("Stock Analysis", "Time", "Price", dataset);
            chart.setBackgroundPaint(Color.white);
            chart.getTitle().setPaint(Color.red);
            CategoryPlot p = chart.getCategoryPlot();
            p.setRangeMinorGridlinePaint(Color.blue);
            ChartFrame frame = new ChartFrame("Line Chart", chart);
            frame.setLayout(null);
            JLabel jLabel = new JLabel("No Buy/Sell", JLabel.CENTER);
            jLabel.setFont(new Font("dialog", 0, 20));
            jLabel.setBounds(580, 530, 150, 50);
            jLabel.setBackground(Color.red);
            jLabel.setOpaque(true);
            if (flag ==1)
                jLabel.setText("Buy");
            else if (flag ==2)
                jLabel.setText("Sell");
            frame.add(jLabel);
            frame.setVisible(true);
            frame.setSize(800, 600);
            today = 0;
            flag = 0;
            startDate="";
            endDate="";
        }
    }

    //0 means no buy/sell, 1 means buy, 2 means sell
    public int decide(int period1, int period2, int today) {
        double d1, d2;
        d1 = stock[today - 1].data[period1 + 0] - stock[today - 1].data[period2 + 0];
        d2 = stock[today].data[period1 + 0] - stock[today].data[period2 + 0];
        if (period2 > period1) {
            if (d1 > 0 && d2 < 0)
                return 2;
            else if (d1 < 0 && d2 > 0)
                return 1;
            else return 0;
        }
        if (period2 < period1){
            if (d1<0 && d2 >0)
                return 2;
            else if (d1>0 && d1 <0)
                return 1;
            else return 0;
        }
        return 0;
    }

    public void calculateMAs() {
        for (int i = 0; i < stockNum; i++) {
            stock[i].data[6] = calculateMA(25, i);
            stock[i].data[7] = calculateMA(50, i);
            stock[i].data[8] = calculateMA(75, i);
        }
    }

    public double calculateMA(int period, int x) {
        double sum = 0;
        if (x < period) {
            for (int i = 0; i < x; i++)
                sum = sum + stock[i].data[3];
            return (sum / x);
        } else {
            for (int i = (x - period); i < x; i++)
                sum = sum + stock[i].data[3];
            return (sum / period);
        }
    }
}
