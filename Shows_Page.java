import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

public class Shows_Page extends JFrame{

	public Shows_Page(final String mname, final String cname, final String cid, final Integer mid, final float rate, final String name, Integer user_id){
	
		String[] st = new String[100];
		String[] sn = new String[100];
		Integer[] sid = new Integer[100];
		
	//setting frame
		int both = JFrame.MAXIMIZED_BOTH;
		setExtendedState((getExtendedState() & both)!=both ? both : JFrame.NORMAL);
		final Integer uid=user_id;
		final String uname=name;
	    JPanel[] spanels = new JPanel[10]; 
		setLayout(null);
		setSize(1200, 800);	
		setVisible(true);
		
	//setting parent panel
		JPanel CinemaHallPane = new JPanel();
		CinemaHallPane.setBounds(200,120,800,600);
		add(CinemaHallPane);
		CinemaHallPane.setVisible(true);
		CinemaHallPane.setLayout(new GridLayout(10,0,20,20));
		float[] hsb = Color.RGBtoHSB(1, 110, 178, null);
		getContentPane().setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
		CinemaHallPane.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
		Font f = new Font(Font.SANS_SERIF, Font.BOLD,  18);
		
	//book my show label
	 	ImageIcon icon_logo = new ImageIcon("/home/udaan/workspace/basic/images/book-my-show.png");
		JLabel lbl_book_my_show_icon = new JLabel();
	    lbl_book_my_show_icon.setBounds(0,0,310,50);		
	    lbl_book_my_show_icon.setIcon(icon_logo);
		add(lbl_book_my_show_icon);

	//welcome user label
		JLabel lbl_hello = new JLabel("Welcome "+name, SwingConstants.CENTER);
		lbl_hello.setForeground(Color.white);
		hsb = Color.RGBtoHSB(193, 6, 6, null);
		lbl_hello.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
		lbl_hello.setOpaque(true);
	    lbl_hello.setBounds(450,0,860,50);	
	    lbl_hello.setFont(f);
		add(lbl_hello);
		    
	//button redirecting to home page
		JLabel lbl_home = new JLabel("Go to Home Page", SwingConstants.CENTER);
		lbl_home.setBounds(160,0,310,50);
		lbl_home.setFont(f);
		lbl_home.setForeground(Color.white);
		lbl_home.setOpaque(true);
		lbl_home.setBackground(Color.DARK_GRAY);
		add(lbl_home);
		lbl_home.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) 
		    {			
		    	Shows_Page.this.dispose();
		    	new HomePage(name, uid);		    	
		    }
		});
		
	//displaying show details
		int k=0;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/book_my_show_db" ,"root" ,"Kalyani5*");
			Statement stmt_sh =con.createStatement();
			ResultSet rs_sh = stmt_sh.executeQuery("select show_time, screen_no, show_id from show_table where movie_id='"+mid+"'"+"and cinema_hall_id='"+cid+"'");
			
			while(rs_sh.next()){
				
				st[k]=rs_sh.getString(1);
				sn[k]=rs_sh.getString(2);
				sid[k]=rs_sh.getInt(3);
				k=k+1;
			}
			
			int count=0;
			while(count<k){
				
			//setting up panels for shows
				spanels[count]=new JPanel();
				spanels[count].setLayout(new FlowLayout(FlowLayout.CENTER, 5,5));
				
			//movie name
				final JLabel lbl1_panel = new JLabel(mname);
				lbl1_panel.setBounds(420,10,350, 50);
				lbl1_panel.setHorizontalAlignment(SwingConstants.CENTER);
				lbl1_panel.setFont(f);
				add(lbl1_panel);
				
			//cinema hall name
				final JLabel lbl2_panel = new JLabel(cname);
				lbl2_panel.setBounds(420,70,350, 50);
				lbl2_panel.setHorizontalAlignment(SwingConstants.CENTER);
				lbl2_panel.setFont(f);
				lbl2_panel.setForeground(Color.white);
				add(lbl2_panel);
				
			//show time
				JLabel lbls1 = new JLabel(" Show Time: "+st[count]+" | ");
				lbls1.setSize(100, 50);
				lbls1.setHorizontalAlignment(SwingConstants.CENTER);
				lbls1.setFont(f);
				lbls1.setForeground(Color.white);
				spanels[count].add(lbls1);

			//screen number
				JLabel lbls2 = new JLabel(" Screen No.: "+sn[count]+" | ");
				lbls2.setSize(100, 50);
				lbls2.setHorizontalAlignment(SwingConstants.CENTER);
				lbls2.setFont(f);
				lbls2.setForeground(Color.white);
				spanels[count].add(lbls2);

			//rate
				JLabel lbls3 = new JLabel(" Rate: "+rate+" | ");
				lbls3.setSize(100, 50);
				lbls3.setHorizontalAlignment(SwingConstants.CENTER);
				lbls3.setFont(f);
				lbls3.setForeground(Color.white);
				spanels[count].add(lbls3);
				
			//book button
				JLabel lbls4 = new JLabel("Book");
				lbls4.setSize(100, 50);
				lbls4.setHorizontalAlignment(SwingConstants.CENTER);
				lbls4.setFont(f);
				lbls4.setForeground(Color.yellow);
				lbls4.setOpaque(true);
				lbls4.setBackground(Color.black);

				final JLabel lbls5 = new JLabel(""+sid[count]);				
				
				spanels[count].add(lbls4);
				
			//adding panels to the parent panel
				spanels[count].setBackground(Color.DARK_GRAY);
			    CinemaHallPane.add(spanels[count]);
			    count=count+1;
			    
			    lbls4.addMouseListener(new MouseAdapter() {
				    @Override
				    public void mouseClicked(MouseEvent e) 
				    {			
				    	new Booking_Page(mname, cname, cid, mid, rate, uid,uname, lbls5.getText());
				    	Shows_Page.this.dispose();
				    }
				});		
		}
	}catch(Exception e){
		System.out.println(e);
	}}
}

