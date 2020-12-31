import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

public class Theatre_Page  extends JFrame{
	static Integer movie_cid;
	static Integer city_id;
    	static JPanel[] panels = new JPanel[10]; 
    	static Integer cinemahid;
	String movname="";
	float mrate;
	static String city="";
	
	public Theatre_Page(int mov_id, String city_name, String name, Integer user_id){
	
		Font f = new Font(Font.SANS_SERIF, Font.BOLD,  18);
		movie_cid=mov_id;
		city=city_name;
		final String uname=name;
		final Integer uid = user_id;

	//setting frame
		int both = JFrame.MAXIMIZED_BOTH;
		setExtendedState((getExtendedState() & both)!=both ? both : JFrame.NORMAL);
		float[] hsb = Color.RGBtoHSB(1, 110, 178, null);
		getContentPane().setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));	
		
	//setting parent panel
		JPanel parentPane = new JPanel();
		parentPane.setBounds(200,80,800,600);
		parentPane.setLayout(new GridLayout(5,1,20,20));
		add(parentPane);
		parentPane.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));

	//book my show logo
		ImageIcon icon_logo = new ImageIcon("/home/udaan/workspace/basic/images/book-my-show.png");
		JLabel lbl_book_my_show_icon = new JLabel();
		lbl_book_my_show_icon.setBounds(0,0,310,50);		
		lbl_book_my_show_icon.setIcon(icon_logo);
		add(lbl_book_my_show_icon);
		setLayout(null);
		setSize(1200, 800);	
		setVisible(true);
		String[] cinname= new String[100];
		String[] cinadd= new String[100];
		String[] cincn= new String[100];
		String[] cinid= new String[100];

	//welcome user label
		JLabel lbl_hello = new JLabel("Welcome "+uname, SwingConstants.CENTER);
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
	    	Theatre_Page.this.dispose();
	    	new HomePage(uname, uid);		    	
	    }
	});
	
	//getting details from tables
	int i=0;
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection
				("jdbc:mysql://localhost:3306/book_my_show_db" ,"root" ,"Kalyani5*");
		Statement stmt_cinema_details =con.createStatement();
		ResultSet rs_cinema_details = stmt_cinema_details.executeQuery("select distinct cinema_hall_name, address, contact_number, cinema_hall_table.cinema_hall_id from cinema_hall_table, show_table where cinema_hall_table.cinema_hall_id=show_table.cinema_hall_id and city_id='"+city_id+"'"+"and movie_id='"+movie_cid+"'");
		Statement stmt_city_id =con.createStatement();
		ResultSet rs_city_id = stmt_city_id.executeQuery("select city_id from city_table where city_name='"+city+"'");
		Statement stmt_mname =con.createStatement();
		ResultSet rs_mname = stmt_mname.executeQuery("select movie_name, rate from movie_table where movie_id='"+movie_cid+"'");

		while(rs_mname.next()){
			movname=rs_mname.getString(1);
			mrate=rs_mname.getFloat(2);
		}

		while(rs_city_id.next()){
			city_id=rs_city_id.getInt(1);
		}

		i=0;
		while(rs_cinema_details.next()){
			cinname[i] = rs_cinema_details.getString(1);
			cinadd[i] = rs_cinema_details.getString(2);
			cincn[i] = rs_cinema_details.getString(3);
			cinid[i] = rs_cinema_details.getString(4);
			i=i+1;
		}
	
	//movie name
		final JLabel lbl1_panel = new JLabel(movname);
		lbl1_panel.setBounds(420,10,350, 50);
		lbl1_panel.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1_panel.setFont(f);
		lbl1_panel.setForeground(Color.white);
		add(lbl1_panel);

		setBackground(Color.DARK_GRAY);			
		con.close();

		}catch(Exception e){
			System.out.println(e);
		}
		
		int count = 0, b=count;
		b=b+1;
	
		while(count<i){

		//setting panels
			panels[count]=new JPanel();
			panels[count].setLayout(new FlowLayout(FlowLayout.CENTER));
			
		//cinema hall name
			final JLabel lblc1_panel = new JLabel(cinname[count]);
			lblc1_panel.setSize(400, 50);
			lblc1_panel.setFont(f);
			lblc1_panel.setForeground(Color.white);
			panels[count].add(lblc1_panel);
			
		//address of the cinema hall
			JLabel lblc2_panel = new JLabel("<html>"+ "Address: " + cinadd[count] +"</html>");
			lblc2_panel.setBounds(430,(b*120),400, 50);
			lblc2_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lblc2_panel.setForeground(Color.white);
			lblc2_panel.setOpaque(true);
			lblc2_panel.setBackground(Color.DARK_GRAY);
			add(lblc2_panel);
				
		//cinema hall contact number
			JLabel lblc3_panel = new JLabel("("+cincn[count]+")");
			lblc3_panel.setSize(400, 50);
			lblc3_panel.setFont(f);
			lblc3_panel.setForeground(Color.white);
			panels[count].add(lblc3_panel);		

		//shows label redirecting to the shows page
			JLabel lbl9_panel = new JLabel("Shows");
			lbl9_panel.setSize(350, 50);
			lbl9_panel.setFont(f);
			lbl9_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lbl9_panel.setForeground(Color.yellow);
			panels[count].add(lbl9_panel);
		
			final JLabel lbls = new JLabel(cinid[count]);

			lbl9_panel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) 
				{			
					new Shows_Page(movname, lblc1_panel.getText(),lbls.getText(), movie_cid, mrate, uname, uid);
					Theatre_Page.this.dispose();
				}
			});			
		
		//adding panels to the parent panel
			panels[count].setBackground(Color.DARK_GRAY);
			parentPane.add(panels[count]);
			count=count+1;
			b=b+1;
		}
	}
}


