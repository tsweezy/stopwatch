// GUI Calendar Refrence https://www.santhoshreddymandadi.com/java/java-calendar-using-swings.html

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

public class pickDay extends JFrame implements ItemListener{

    JPanel p1, p2;
    JComboBox month;
    JComboBox year;
    JButton today;
    int extradays[]={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    String day[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    String months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public pickDay(String title){

        super();
        setTitle(title);
        p1 = new JPanel();
        month = new JComboBox();
        for(int i=0;i< months.length;i++){
            month.addItem(months[i]);
        }

        month.addItemListener(this);
        year = new JComboBox();
        for(int i=1980;i<=2099;i++){
            year.addItem(i);
        }

        today = new JButton("Jump to Today");
        today.addItemListener(this);

        year.addItemListener(this);
        p1.add(month);
        p1.add(year);
        p1.add(today);
        p2 = new JPanel();
        p2.setLayout(new GridLayout(0,7,5,5));
        Date date = new Date();
        drawCalendar(months[date.getMonth()], (1900+date.getYear()));
        year.setSelectedItem((1900+date.getYear()));
        month.setSelectedItem(months[date.getMonth()]);
        Container c=getContentPane();
        c.setLayout(new FlowLayout());
        add(p1);
        add(p2);
        setVisible(true);
        setSize(390,300);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String args[]){
        pickDay frame = new pickDay("Calendar");
    }

    @Override
    public void itemStateChanged(ItemEvent e){
        if(e.getStateChange() == ItemEvent.SELECTED){
            drawCalendar((String)month.getSelectedItem(), (Integer)year.getSelectedItem());
        }
    }

    public void drawCalendar(String inputMonth, int inputYear){
        p2.removeAll();
        for(int i=0;i< day.length;i++){
            JLabel Label = new JLabel(day[i]);
            Label.setHorizontalAlignment(SwingConstants.RIGHT);
            p2.add(Label);
        }

        Date date = new Date("01-"+inputMonth+"-"+inputYear);
        int daysThisMonth = extradays[date.getMonth()];
        if(date.getYear()%4==0 && date.getMonth()==1){
            daysThisMonth = 29;
        }

        for(int i=1, day=1;day<=daysThisMonth;i++){
            for(int j=0;j<7;j++){
                if(day==1){
                    if(j==date.getDay()){
                        JButton Button = new JButton(String.valueOf(day));
                        Button.setHorizontalAlignment(SwingConstants.RIGHT);
                        p2.add(Button);
                        day++;
                    }

                    else{
                        JLabel label = new JLabel("");
                        p2.add(label);
                    }
                }
                else{
                    JButton Button = new JButton(String.valueOf(day));
                    Button.setHorizontalAlignment(SwingConstants.RIGHT);
                    p2.add(Button);
                    day++;
                }

                if(day>daysThisMonth){
                    break;
                }
            }
        }
        p2.validate();
        setTitle(inputMonth+", "+inputYear);
    }
}