import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

public class HomePage extends JFrame implements ActionListener, MouseListener, AdjustmentListener{
	 String uname;
	 int uid;
	
	 public HomePage(String name, Integer uid2){
		uname=name;
	 	uid=uid2;
		this.btn_click();

	 }
	 
	 	final JFrame frame = new JFrame();    
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("rawtypes")
		String city_name="";
		private static final JComboBox cmb_cities = new JComboBox();
		static String language_selected="Hindi";
		static String[] movie_names = new String[100];
		static String[] release_date = new String[100];
		static String[] language = new String[100];
		static String[] length = new String[100];
		static Boolean[] adult = new Boolean[100];
		static Integer[] movie_id = new Integer[100];
		int city_id, j=0;;
		Integer[] movie_id_array=new Integer[100];
		static Blob[] movie_image = new Blob[100];
		static Image image1;
		static Image[] icon = new Image[20];
		static ImageIcon imageIcon;
        static JPanel[] panel = new JPanel[10]; 
		static int count=0;
		static JPanel parentPanel = new JPanel();
		Font f = new Font(Font.SANS_SERIF, Font.BOLD,  18);
	
	@SuppressWarnings({ "null", "unchecked" })
	public void btn_click() 
	{	
		String[] city_names = new String[20];
		int both = JFrame.MAXIMIZED_BOTH;
		int i=0;
		
	//setting frame
		frame.setLayout(null);
		frame.setSize(1300,720);
		frame.setExtendedState((frame.getExtendedState() & both)!=both ? both : JFrame.NORMAL);
		
	//setting panel
		parentPanel.setBounds(10,80,1300,1300);
		parentPanel.setBackground(Color.DARK_GRAY);	     
     	parentPanel.setLayout(new GridLayout(0,4));
		
    //hindi button
		JButton btn_hindi = new JButton("Hindi");
	    btn_hindi.setBounds(165,0,200,50);		
	    btn_hindi.setFont(f);
	    btn_hindi.setOpaque(true);
        btn_hindi.setBackground(Color.DARK_GRAY);
	    btn_hindi.setForeground(Color.white);
	   	frame.add(btn_hindi);
	    
	//marathi button
	    JButton btn_marathi = new JButton("Marathi");
	    btn_marathi.setBounds(365,0,200,50);		
	    btn_marathi.setFont(f);
	    btn_marathi.setOpaque(true);
        btn_marathi.setBackground(Color.DARK_GRAY);
	    btn_marathi.setForeground(Color.white);
	    frame.add(btn_marathi);
	    
    //english button
	    JButton btn_english = new JButton("English");
	    btn_english.setBounds(565,0,200,50);		
	    btn_english.setFont(f);
	    btn_english.setOpaque(true);
        btn_english.setBackground(Color.DARK_GRAY);
	    btn_english.setForeground(Color.white);
	    frame.add(btn_english);

	    btn_hindi.addActionListener(this);
	    btn_english.addActionListener(this);
	    btn_marathi.addActionListener(this);
	    
    //selectin movies based on language and city selected
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/book_my_show_db" ,"root" ,"Kalyani5*");
			Statement stmt_cities=con.createStatement();
			Statement stmt_movies=con.createStatement();

			ResultSet rs_cities = stmt_cities.executeQuery("select city_name from city_table");
			ResultSet rs_movies = stmt_movies.executeQuery("select movie_name, release_date, language, length, movie_image, movie_id, adult from movie_table where language='"+language_selected+"'");
		
			i=0;
	
			while(rs_cities.next()){
				city_names[i]=rs_cities.getString(1);
				i=i+1;
			}
			i=0;
			while(rs_movies.next()){
				movie_names[i]=rs_movies.getString(1);
				release_date[i]=rs_movies.getString(2);
				language[i]=rs_movies.getString(3);
				movie_id[i]=rs_movies.getInt(6);
				adult[i]=rs_movies.getBoolean(7);
				length[i]=rs_movies.getString(4);
				
				movie_image[i] = rs_movies.getBlob(5);
				byte[] imageBytes = movie_image[i].getBytes(1, (int) movie_image[i].length());
				imageIcon = new ImageIcon(imageBytes);
				image1 = imageIcon.getImage();
				icon[i] = image1.getScaledInstance(200,300,Image.SCALE_SMOOTH);

				i=i+1;
			}
			con.close();

			}catch(Exception e){
				System.out.println(e);
			}

	//setting background to frame
		float[] hsb = Color.RGBtoHSB(1, 110, 178, null);
		frame.getContentPane().setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
	
	//adding book-my-show logo
	 	ImageIcon icon_logo = new ImageIcon("/home/udaan/workspace/basic/images/book-my-show.png");
		JLabel lbl_book_my_show_icon = new JLabel();
	    lbl_book_my_show_icon.setBounds(0,0,310,50);		
	    lbl_book_my_show_icon.setIcon(icon_logo);
		frame.add(lbl_book_my_show_icon);
	
	//welcome user label
		JLabel lbl_hello = new JLabel("Welcome "+uname, SwingConstants.CENTER);
		lbl_hello.setForeground(Color.white);
		lbl_hello.setBackground(Color.GRAY);
		lbl_hello.setOpaque(true);
	    lbl_hello.setBounds(760,0,340,50);	
	    lbl_hello.setFont(f);
	    hsb = Color.RGBtoHSB(193, 6, 6, null);
		lbl_hello.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
		frame.add(lbl_hello);

	//adding cities from table city_table in combo box
		int k=0;
		while(city_names[k]!=null){
			cmb_cities.addItem(city_names[k]);
			k=k+1;
		}
	    cmb_cities.setBounds(1100, 0, 200, 50);    
	    cmb_cities.setFont(f);
	    frame.add(cmb_cities);   
	    cmb_cities.setSelectedItem("Pune");
		city_name= (String) (cmb_cities.getItemAt(cmb_cities.getSelectedIndex()));  
		cmb_cities.addActionListener(this);  

	//creating panels dynamically according to the movies selected from movie_table
		while(count<i){
	
		//setting up panel
			panel[count]=new JPanel();
			panel[count].setSize(300, 700);
			panel[count].setLayout(new FlowLayout(FlowLayout.CENTER, 50,10));

		//movie name label
			final JLabel lbl1_panel = new JLabel(movie_names[count]);
			lbl1_panel.setSize(300, 50);
			lbl1_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lbl1_panel.setFont(f);
			lbl1_panel.setForeground(Color.white);
			panel[count].add(lbl1_panel);

		//movie poster 
			ImageIcon icon_logo1_panel = new ImageIcon(icon[count]);
			JLabel lbl2_panel = new JLabel();
			lbl2_panel.setSize(300, 300);
			lbl2_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lbl2_panel.setVisible(true);
			lbl2_panel.setIcon(icon_logo1_panel);
			panel[count].add(lbl2_panel);
		
		//after clicking movie page, we go to movie details page
			lbl2_panel.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseClicked(MouseEvent e) 
			    {			
			    	HomePage.this.dispose();
			    	new Movie_Details_Page(lbl1_panel.getText(), city_name, uname, uid);		
			    }
			});

		//length of the movie
			JLabel lbl4_panel = new JLabel("  "+length[count] + " hrs");
			lbl4_panel.setSize(150,50);
			panel[count].add(lbl4_panel);
			lbl4_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lbl4_panel.setFont(f);
			lbl4_panel.setForeground(Color.white);

		//is the film A or U/A 
			String a;
			if(adult[count]==false){
				a="U/A";
			}
			else{
				a="U";
			}
			
			JLabel lbl_ua = new JLabel(a);
			lbl_ua.setBounds(200, 410,350,50);
			lbl_ua.setFont(f);
			lbl_ua.setForeground(Color.white);
			panel[count].add(lbl_ua);
		
		//language of the movie
			JLabel lbl5_panel = new JLabel(language[count]);
			lbl5_panel.setSize(300, 50);
			panel[count].add(lbl5_panel);
			lbl5_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lbl5_panel.setFont(f);
			lbl5_panel.setForeground(Color.white);

		//release date of the movie
			JLabel lbl6_panel = new JLabel(release_date[count]);
			lbl6_panel.setSize(300, 50);
			lbl6_panel.setHorizontalAlignment(SwingConstants.CENTER);
			panel[count].add(lbl6_panel);
			panel[count].setBackground(Color.lightGray);
			lbl6_panel.setFont(f);
			lbl6_panel.setForeground(Color.white);

		//adding panels to the parent panel
			panel[count].setBackground(Color.DARK_GRAY);
		    parentPanel.add(panel[count]);
		    count=count+1;
	}
		frame.add(parentPanel);		
		frame.setVisible(true);
	}
	@Override
	//selecting language
		public void actionPerformed(ActionEvent e) {
			j=0;
			String str=e.getActionCommand();
			if(str.equals("Hindi")){
				language_selected = "Hindi";
			}
			else if(str.equals("Marathi")){
				language_selected = "Marathi";
			}
			else if(str.equals("English")){
				language_selected = "English";
			}
	
	//selecting city 
		city_name= (String) (cmb_cities.getItemAt(cmb_cities.getSelectedIndex()));  
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con1 = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/book_my_show_db" ,"root" ,"Kalyani5*");
			
			Statement stmt_city=con1.createStatement();
			ResultSet rs_city = stmt_city.executeQuery("select city_id from city_table where city_name='"+cmb_cities.getItemAt(cmb_cities.getSelectedIndex())+"'");
			
			while(rs_city.next()){
				city_id=rs_city.getInt(1);
			}
		
			Statement stmt_movies1=con1.createStatement();
			ResultSet rs_movies1 = stmt_movies1.executeQuery("select movie_name, release_date, language, length, movie_image, movie_id, adult from movie_table where language='"+language_selected+"'");//+"and movie_id in "+movie_id_array+"");		
					
			while(rs_movies1.next()){
				movie_names[j]=rs_movies1.getString(1);
				release_date[j]=rs_movies1.getString(2);
				language[j]=rs_movies1.getString(3);
				movie_id[j]=rs_movies1.getInt(6);
				adult[j]=rs_movies1.getBoolean(7);
				length[j]=rs_movies1.getString(4);
				movie_image[j] = rs_movies1.getBlob(5);
				byte[] imageBytes = movie_image[j].getBytes(1, (int) movie_image[j].length());
				imageIcon = new ImageIcon(imageBytes);
				image1 = imageIcon.getImage();
				icon[j] = image1.getScaledInstance(200,300,Image.SCALE_SMOOTH);

				j=j+1;
			}
			con1.close();
			}catch(Exception e1){
				System.out.println(e1);
			}
	
	//on changing the movie language, the parent panel is updated
		parentPanel.removeAll();
		parentPanel.revalidate();
		parentPanel.repaint();
		count=0;
		
		while(count<j){
			
		//creating panels
			panel[count]=new JPanel();
			panel[count].setSize(300, 700);
			panel[count].setLayout(new FlowLayout(FlowLayout.CENTER, 50,10));
			
		//movie name
			final JLabel lbl1_panel = new JLabel(movie_names[count]);
			lbl1_panel.setSize(300, 50);
			lbl1_panel.setFont(f);
			lbl1_panel.setForeground(Color.white);
			lbl1_panel.setHorizontalAlignment(SwingConstants.CENTER);
			panel[count].add(lbl1_panel);

		//movie poster
			ImageIcon icon_logo1_panel = new ImageIcon(icon[count]);
			JLabel lbl2_panel = new JLabel();
			lbl2_panel.setSize(300, 300);	
			lbl2_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lbl2_panel.setVisible(true);
			lbl2_panel.setIcon(icon_logo1_panel);
			panel[count].add(lbl2_panel);
		
		//going to movie details page after clicking the movie poster
			lbl2_panel.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseClicked(MouseEvent e) 
			    {		
			    	HomePage.this.dispose();
			    	new Movie_Details_Page(lbl1_panel.getText(), city_name, uname, uid);
			    }
			});
		
		//movie length
			JLabel lbl4_panel = new JLabel("  "+length[count] + " hrs");
			lbl4_panel.setSize(150,50);
			panel[count].add(lbl4_panel);
			lbl4_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lbl4_panel.setFont(f);
			lbl4_panel.setForeground(Color.white);
			
		//is the film A or U/A 
			String a;
			if(adult[count]==false){
				a="U/A";
			}
			else{
				a="U";
			}
			
			JLabel lbl_ua = new JLabel(a);
			lbl_ua.setBounds(200, 410,350,50);
			lbl_ua.setFont(f);
			lbl_ua.setForeground(Color.white);
		
		//language of the movie
			panel[count].add(lbl_ua);
			JLabel lbl5_panel = new JLabel(language[count]);
			lbl5_panel.setSize(300, 50);
			panel[count].add(lbl5_panel);
			lbl5_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lbl5_panel.setFont(f);
			lbl5_panel.setForeground(Color.white);
			
		//movie release date
			JLabel lbl6_panel = new JLabel(release_date[count]);
			lbl6_panel.setSize(100, 50);
			lbl6_panel.setHorizontalAlignment(SwingConstants.CENTER);
			lbl6_panel.setFont(f);	
			panel[count].add(lbl6_panel);
			panel[count].setBackground(Color.lightGray);
			lbl6_panel.setForeground(Color.white);
			
		//adding panels to the parent panel
			panel[count].setBackground(Color.DARK_GRAY);
		    parentPanel.add(panel[count]);
		    count=count+1;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("");
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		// TODO Auto-generated method stub		
	}
}
