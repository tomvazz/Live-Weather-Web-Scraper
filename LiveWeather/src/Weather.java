import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Weather {
	
	public String[] d = new String[7];
	public String[] dd = new String[7];
	public String[] h = new String[7];
	public String[] l = new String[7];
	
	public JLabel[] datelbl = new JLabel[6];
	public JLabel[] daylbl = new JLabel[6];
	public JLabel[] highlbl = new JLabel[6];
	public JLabel[] lowlbl = new JLabel[6];
	public JLabel livetemplbl;
	
	public int livetempnum;
	public String label;
	public String labelnum;
	
	public static JLabel background;
	public static ImageIcon backdrop;
	
	//design timer
	public double f1 = -100;
	public double f1vel = 0.2;
	public int direction = 0;
	Timer motion = new Timer();
	TimerTask motiontask = new TimerTask() {
		public void run() {
			if (direction == 0) {
				f1 = f1 + f1vel;
				if (f1 >= 0) {
					direction = 1;
				}
			}
			if (direction == 1) {
				f1 = f1 - f1vel;
				if (f1 <= -100) {
					direction = 0;
				}
			}
			background.setBounds((int)f1, -20, 550, 800);
		}
	};
	
	// live temp count effect
	public int countup = 0;
	public boolean runtimer = true;
	Timer tcount = new Timer();
	TimerTask livecount = new TimerTask() {
		public void run() {
			if (runtimer == true) {
				countup++;
				livetemplbl.setText(String.valueOf(countup) + "°F");
				if (countup == livetempnum) {
					runtimer = false;
				}
			}
		}
	};
	

	public void execute(JFrame f) throws IOException {
		coppelllive();
		
		JLabel city = new JLabel("COPPELL");
		city.setForeground(Color.WHITE);
		city.setFont(new Font("Futura", Font.PLAIN, 40));
		city.setHorizontalAlignment(SwingConstants.LEFT);
		city.setBounds(15, 6, 431, 80);
		f.getContentPane().add(city);
		
		JLabel today = new JLabel("weather  |  today");
		today.setForeground(Color.GRAY);
		today.setFont(new Font("Avenir", Font.PLAIN, 18));
		today.setHorizontalAlignment(SwingConstants.LEFT);
		today.setBounds(19, 55, 200, 50);
		f.getContentPane().add(today);
		
		JLabel labellbl = new JLabel(label + " " + labelnum);
		labellbl.setForeground(Color.LIGHT_GRAY);
		labellbl.setFont(new Font("Avenir", Font.PLAIN, 16));
		labellbl.setHorizontalAlignment(SwingConstants.RIGHT);
		labellbl.setBounds(7, 210, 250, 30);
		f.getContentPane().add(labellbl);

		JLabel drop = new JLabel("S");
		drop.setHorizontalAlignment(SwingConstants.CENTER);
		drop.setForeground(new Color(135, 206, 250));
		drop.setFont(new Font("Wingdings", Font.PLAIN, 28));
		drop.setBounds(263, 198, 30, 50);
		f.getContentPane().add(drop);
		
		JLabel uplbl = new JLabel("#");
		uplbl.setForeground(new Color(0, 255, 0));
		uplbl.setFont(new Font("Wingdings 3", Font.PLAIN, 25));
		uplbl.setBounds(150, 339, 16, 50);
		f.getContentPane().add(uplbl);
		
		JLabel downlbl = new JLabel("$");
		downlbl.setForeground(Color.RED);
		downlbl.setFont(new Font("Wingdings 3", Font.PLAIN, 25));
		downlbl.setBounds(230, 339, 16, 50);
		f.getContentPane().add(downlbl);
		
		JLabel hightodaylbl = new JLabel(h[0]);
		hightodaylbl.setForeground(Color.LIGHT_GRAY);
		hightodaylbl.setFont(new Font("Avenir", Font.PLAIN, 20));
		hightodaylbl.setHorizontalAlignment(SwingConstants.LEFT);
		hightodaylbl.setBounds(170, 350, 70, 30);
		f.getContentPane().add(hightodaylbl);
		
		JLabel lowtodaylbl = new JLabel(l[0]);
		lowtodaylbl.setForeground(Color.LIGHT_GRAY);
		lowtodaylbl.setFont(new Font("Avenir", Font.PLAIN, 20));
		lowtodaylbl.setHorizontalAlignment(SwingConstants.LEFT);
		lowtodaylbl.setBounds(250, 350, 70, 30);
		f.getContentPane().add(lowtodaylbl);
		
		livetemplbl = new JLabel(String.valueOf(countup) + "°F");
		livetemplbl.setForeground(Color.WHITE);
		livetemplbl.setFont(new Font("Avenir", Font.PLAIN, 99));
		livetemplbl.setHorizontalAlignment(SwingConstants.CENTER);
		livetemplbl.setBounds(0, 200, 450, 200);
		f.getContentPane().add(livetemplbl);
		
		for (int a = 0; a < 6; a++) {
			datelbl[a] = new JLabel(dd[a]);
			datelbl[a].setFont(new Font("Avenir", Font.PLAIN, 18));
			datelbl[a].setForeground(Color.WHITE);
			datelbl[a].setHorizontalAlignment(SwingConstants.CENTER);
			datelbl[a].setBounds(a*75, 677, 75, 30);
			f.getContentPane().add(datelbl[a]);
		}
		for (int a = 0; a < 6; a++) {
			daylbl[a] = new JLabel(d[a]);
			daylbl[a].setFont(new Font("Avenir", Font.PLAIN, 10));
			daylbl[a].setForeground(new Color(176, 196, 222));
			daylbl[a].setHorizontalAlignment(SwingConstants.CENTER);
			daylbl[a].setBounds(a*75, 697, 75, 30);
			f.getContentPane().add(daylbl[a]);
		}
		for (int a = 0; a < 6; a++) {
			highlbl[a] = new JLabel(h[a]);
			highlbl[a].setFont(new Font("Avenir", Font.PLAIN, 24));
			highlbl[a].setForeground(new Color(127, 255, 212));
			highlbl[a].setHorizontalAlignment(SwingConstants.CENTER);
			highlbl[a].setBounds(a*75, 722, 75, 30);
			f.getContentPane().add(highlbl[a]);
		}
		for (int a = 0; a < 6; a++) {
			lowlbl[a] = new JLabel(l[a]);
			lowlbl[a].setFont(new Font("Avenir", Font.PLAIN, 15));
			lowlbl[a].setForeground(new Color(221, 160, 221));
			lowlbl[a].setHorizontalAlignment(SwingConstants.CENTER);
			lowlbl[a].setBounds(a*75, 742, 75, 30);
			f.getContentPane().add(lowlbl[a]);
		}
		
		ImageIcon slits = new ImageIcon(this.getClass().getResource("/slits1.png"));
		JLabel lines = new JLabel(slits);
		lines.setBounds(0, 580, 450, 200);
		f.getContentPane().add(lines);
		
		backdrop = new ImageIcon(this.getClass().getResource("/backdrop1.png"));
		background = new JLabel(backdrop);
		background.setBounds((int)f1, -20, 550, 800);
		f.getContentPane().add(background);
		
		//timers start
		motion.schedule(motiontask, 0, 10);
		tcount.schedule(livecount, 850, 18);
		
	}
	
	public void coppelllive() throws IOException {
		
		String url = "https://www.weatherforyou.com/reports/index.php?pands=coppell%2Ctexas";
		Document document = Jsoup.connect(url).userAgent("WebScraper").get();
		
		int count = 0;
		for (Element row : document.select(".cal_colwrap.cal_dayiconhi")) { // day, date, hightemp
			String day = row.select(".cal_day").text();
			String date = row.select(".cal_date").text(); 
			String hightemp = row.select(".cal_hi").text(); 
			d[count] = day;
			dd[count] = date;
			h[count] = hightemp;
			count++;
		}
		
		count = 0;
		for (Element row : document.select(".cal_colwrap.cal_low")) { //lowtemp
			String lowtemp = row.select(".cal_low").text();
			l[count] = lowtemp;
			count++;
		}
		
		for (Element row : document.select("span.Temp")) { //livetemp
			String livetemp = row.select(".Temp").text();
			for (int a = 0; a < livetemp.length(); a++) {
				if (livetemp.substring(a, a+1).equals("°")) {
					livetemp = livetemp.substring(0,a);
				}
			}
			livetempnum = Integer.valueOf(livetemp);
			System.out.println("live temp -> " + livetempnum + "°F");
		}
		
		for (Element row : document.select("span.Label")) { //label
			label = row.select(".Label").text();
			System.out.println("label -> " + label);
			break;
		}
		
		for (Element row : document.select("span.Value")) { //labelnum
			labelnum = row.select(".Value").text();
			System.out.println("num -> " + labelnum);
			break;
		}
		
		for (int a = 0; a < 7; a++) {
			System.out.println(d[a] + " " + dd[a] + " -> " + h[a] + " / " + l[a]);
		}
		
	}
}
