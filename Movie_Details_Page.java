import java.awt.*;
import java.net.URL;
import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.awt.event.*;

public class Movie_Details_Page extends JFrame{

		private static final long serialVersionUID = 1L;
		static JPanel[] panels = new JPanel[10]; 
		int count=0;
		static Integer movie_id=0;
		String city_n="";

	public Movie_Details_Page(String name_of_the_movie, String city_name, String name, int user_id){
	
		final String uname=name;
		final Integer uid=user_id;
		JPanel parentPane = new JPanel();
		parentPane.setBounds(450,120,800,600);
		parentPane.setBackground(Color.DARK_GRAY);
     		parentPane.setLayout(new GridLayout(0,3));
     		int both = JFrame.MAXIMIZED_BOTH;
		setExtendedState((getExtendedState() & both)!=both ? both : JFrame.NORMAL);
		city_n=city_name;
		add(parentPane);
		
		float[] hsb = Color.RGBtoHSB(1, 110, 178, null);
		getContentPane().setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));	
	 	
		ImageIcon icon_logo = new ImageIcon("/home/udaan/workspace/basic/images/book-my-show.png");
		JLabel lbl_book_my_show_icon = new JLabel();
		setLayout(null);
		lbl_book_my_show_icon.setBounds(0,0,310,50);		
	    lbl_book_my_show_icon.setIcon(icon_logo);
		add(lbl_book_my_show_icon);
		Image image1;
		ImageIcon imageIcon;
		setSize(1200, 800);	
		setVisible(true);
		String release_date = "",language="",length="",about="",director="";
		String[] castname= new String[100];
		Boolean adult=false;
		Blob trailer;
		Blob movie_image, cast_image;
		Image[] icon = new Image[20];
		Image[] icons = new Image[20];

		Font f = new Font(Font.SANS_SERIF, Font.BOLD,  18);
	
	//welcome user lable
		JLabel lbl_hello = new JLabel("Welcome "+uname, SwingConstants.CENTER);
		lbl_hello.setForeground(Color.white);
		hsb = Color.RGBtoHSB(193, 6, 6, null);
		lbl_hello.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
		lbl_hello.setOpaque(true);
	    lbl_hello.setBounds(450,0,860,50);	
	    lbl_hello.setFont(f);
		add(lbl_hello);
		
	//cast label
		JLabel lbl_cast = new JLabel("Cast", SwingConstants.CENTER);
		lbl_cast.setBounds(700,60,350, 50);
		lbl_cast.setFont(f);
		lbl_cast.setForeground(Color.white);
		add(lbl_cast);
		
	//button redirecting to the home page
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
		    	Movie_Details_Page.this.dispose();
		    	new HomePage(uname, uid);		    	
		    }
		});
		
		int i=0;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/book_my_show_db" ,"root" ,"Kalyani5*");
			Statement stmt_movie_details =con.createStatement();
			Statement stmt_cast_details =con.createStatement();
			ResultSet rs_movie_details = stmt_movie_details.executeQuery("select release_date, language, length, movie_image, movie_id, about, director, adult, trailer from movie_table where movie_name='"+name_of_the_movie+"'");
		
			if(rs_movie_details.next()){
				movie_id = rs_movie_details.getInt(5);

				release_date = rs_movie_details.getString(1);
				language = rs_movie_details.getString(2);
				length = rs_movie_details.getString(3);
				about = rs_movie_details.getString(6);
				director = rs_movie_details.getString(7);
				adult = rs_movie_details.getBoolean(8);
				trailer = rs_movie_details.getBlob(9);
		
				movie_image = rs_movie_details.getBlob(4);
				byte[] imageBytes = movie_image.getBytes(1, (int) movie_image.length());
				imageIcon = new ImageIcon(imageBytes);
				image1 = imageIcon.getImage();
				icon[0] = image1.getScaledInstance(200,300,Image.SCALE_SMOOTH);
			}
			ResultSet rs_cast_details = stmt_cast_details.executeQuery("select cast_name, cast_image from cast_table where movie_id='"+movie_id+"'");

				i=0;
				while(rs_cast_details.next()){
					
					castname[i] = rs_cast_details.getString(1);
				
					cast_image = rs_cast_details.getBlob(2);
					byte[] imageBytes = cast_image.getBytes(1, (int) cast_image.length());
					imageIcon = new ImageIcon(imageBytes);
					image1 = imageIcon.getImage();
					icons[i] = image1.getScaledInstance(200,300,Image.SCALE_SMOOTH);
					i=i+1;
				}
				
			final JLabel lbl1_panel = new JLabel(name_of_the_movie);
			lbl1_panel.setBounds(60,60,350, 50);
			lbl1_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lbl1_panel.setFont(f);
			lbl1_panel.setForeground(Color.white);

			add(lbl1_panel);

		//movie poster
			ImageIcon icon_logo1_panel = new ImageIcon(icon[0]);
			JLabel lbl2_panel = new JLabel();
			lbl2_panel.setBounds(60,100,350, 300);
			lbl2_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lbl2_panel.setVisible(true);
			lbl2_panel.setIcon(icon_logo1_panel);
			add(lbl2_panel);
			
		//movie length
			JLabel lbl4_panel = new JLabel("  "+length + " hrs   |");
			lbl4_panel.setBounds(50, 410,350,50);
			add(lbl4_panel);
			lbl4_panel.setHorizontalAlignment(SwingConstants.LEFT);
			lbl4_panel.setFont(f);
			lbl4_panel.setForeground(Color.white);

		//is the movie U or U/A
			String a;
			if(adult==false){
				a=" U/A ";
			}
			else{
				a=" U ";
			}
			
			JLabel lbl_ua = new JLabel(a+"  |");
			lbl_ua.setBounds(200, 410,350,50);
			lbl_ua.setFont(f);
			lbl_ua.setForeground(Color.white);
			add(lbl_ua);
			
		//language label
			JLabel lbl5_panel = new JLabel(language, SwingConstants.CENTER);
			lbl5_panel.setBounds(170,410,350, 50);
			add(lbl5_panel);
			lbl5_panel.setFont(f);
			lbl5_panel.setForeground(Color.white);

		//name of the director
			JLabel lbl7_panel = new JLabel("Directed by: "+director);
			lbl7_panel.setBounds(60, 440, 350, 50);
			lbl7_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lbl7_panel.setFont(f);
			lbl7_panel.setForeground(Color.white);
			add(lbl7_panel);
			
		//about movie
			JLabel lbl8_panel = new JLabel("<html>"+ about +"</html>");
			lbl8_panel.setBounds(60, 400, 360, 300);
			lbl8_panel.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl8_panel.setForeground(Color.white);
			add(lbl8_panel);
			
		//label to see theatres
			JLabel lbl9_panel = new JLabel("Click to Choose Theatre");
			lbl9_panel.setBounds(60, 520, 350, 300);
			lbl9_panel.setFont(f);
			lbl9_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lbl9_panel.setForeground(Color.yellow);
			add(lbl9_panel);
			
			lbl9_panel.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseClicked(MouseEvent e) 
			    {			
			    	Movie_Details_Page.this.dispose();
			    	new Theatre_Page(movie_id, city_n, uname, uid);
			    }
			});			
			setBackground(Color.DARK_GRAY);			
			con.close();

			}catch(Exception e){
				System.out.println(e);
			}			
		count=0;
		
		while(count<i){

		//setting panels for displaying cast of the movie
			panels[count]=new JPanel();
			panels[count].setSize(200, 200);
			panels[count].setLayout(new FlowLayout(FlowLayout.CENTER, 5,5));

		//cast name 
			JLabel lblc1_panel = new JLabel(castname[count]);
			lblc1_panel.setSize(100, 50);
			lblc1_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lblc1_panel.setFont(f);
			lblc1_panel.setForeground(Color.white);
			panels[count].add(lblc1_panel);

		//cast photo
			ImageIcon icon_logo1_panel = new ImageIcon(icons[count]);
			JLabel lblc2_panel = new JLabel();
			lblc2_panel.setSize(150, 200);
			lblc2_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lblc2_panel.setVisible(true);
			lblc2_panel.setIcon(icon_logo1_panel);
			panels[count].add(lblc2_panel);

		//adding panels to the parent panel
			panels[count].setBackground(Color.DARK_GRAY);
		    parentPane.add(panels[count]);
		    count=count+1;
	}
	}
}
