import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
import java.util.ArrayList;
import java.util.Date;  


public class Booking_Page extends JFrame implements ActionListener, ItemListener{
	
	@SuppressWarnings("deprecation")
	String date_selected;
	int k=0, n=0, jm=0, count=1,r=0, c=0, p=0, act=0;
	JComboBox<String> cmb_date = new JComboBox();
   	JPanel[] seatPane = new JPanel[200];
  	String[] seat_booked_earlier=new String[200];
    	ArrayList clicked=new ArrayList();
    	ArrayList removed=new ArrayList();
    	String[] seat_booked_now=new String[200];
    	float[] hsb;
    
    public Booking_Page(String mname, String cname, String cid, int mid, float rate, Integer user_id,String uname, final String sid){
    	
    	int both = JFrame.MAXIMIZED_BOTH;
    	final String user_name=uname;
		final String uid=user_id+"";
		final Integer user_uid=user_id;
		String usid=sid, end_date="";;
		Date rdate, ddate = null;	
		int movie_id=mid, h=1;
		
	//setting frame	
		setLayout(null);
		setSize(1200, 800);	
		setVisible(true);
    	setExtendedState((getExtendedState() & both)!=both ? both : JFrame.NORMAL);

	//panel for displaying seat numbers	
		JPanel parentPane = new JPanel();
		parentPane.setBounds(270,100,700,400);
		parentPane.setBackground(Color.DARK_GRAY);
		parentPane.setVisible(true);
		parentPane.setLayout(new GridLayout(10,10));
		add(parentPane);

    //book my show logo
		ImageIcon icon_logo = new ImageIcon("/home/udaan/workspace/basic/images/book-my-show.png");
		JLabel lbl_book_my_show_icon = new JLabel();
	    lbl_book_my_show_icon.setBounds(0,0,310,50);		
	    lbl_book_my_show_icon.setIcon(icon_logo);
		add(lbl_book_my_show_icon);
		hsb = Color.RGBtoHSB(1, 110, 178, null);
		getContentPane().setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
	 
	//welcome user label
		JLabel lbl_hello = new JLabel("Welcome "+user_name, SwingConstants.CENTER);
		lbl_hello.setForeground(Color.white);
		Font f = new Font(Font.SANS_SERIF, Font.BOLD,  18);
		hsb = Color.RGBtoHSB(193, 6, 6, null);
		lbl_hello.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
		lbl_hello.setOpaque(true);
	    lbl_hello.setBounds(450,0,860,50);	
	    lbl_hello.setFont(f);
		add(lbl_hello);
		   
	//label to redirect to home page	
		JLabel lbl_home = new JLabel("Go to Home Page", SwingConstants.CENTER);
		lbl_home.setBounds(160,0,310,50);
		lbl_home.setFont(f);
		lbl_home.setForeground(Color.white);
		lbl_home.setOpaque(true);
		lbl_home.setBackground(Color.DARK_GRAY);
		add(lbl_home);

	//get movie due date to ensure correct date goes into the combo box for selecting date	
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/book_my_show_db" ,"root" ,"Kalyani5*");
			Statement stmt_get_end_date =con.createStatement();
			ResultSet rs_get_end_date = stmt_get_end_date.executeQuery("select due_date from movie_table where movie_id='"+ movie_id +"'");
		
			while(rs_get_end_date.next()){
					end_date=rs_get_end_date.getString(1);		        	
			}
			con.close();
	
		}catch(Exception e1){
			System.out.println(e1);
		}

	//listener for label directing to home page	
		lbl_home.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) 
		    {			
		    	Booking_Page.this.dispose();
		    	new HomePage(user_name, user_uid);		    	
		    }
		});
	
	//getting seats booked earlier	
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/book_my_show_db" ,"root" ,"Kalyani5*");
			
			Statement stmt_s =con.createStatement();
			ResultSet rs_s = stmt_s.executeQuery("select seat_booked from seat_table where show_id='"+sid+"'");
			
			while(rs_s.next()){
				seat_booked_earlier[k]=rs_s.getString(1);
				k=k+1;
			}
			
	//adding dates in combo box	
       final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
       final LocalDateTime now = LocalDateTime.now();  
       
       cmb_date.addItem(dtf.format(now));
       while(h<5){
    	   if(end_date==dtf.format(now.plusDays(h))){
    		   break;
    	   }
    	   cmb_date.addItem(dtf.format(now.plusDays(h)));
    	   h=h+1;
       }
		       
       cmb_date.setBounds(300,550,150,50);
       add(cmb_date);   		    
	   date_selected= (String) (cmb_date.getItemAt(cmb_date.getSelectedIndex()));  	  
		
	//adding seats using checkbox 
		while(count<=100){
			seatPane[count]=new JPanel();
			final JCheckBox lbls = new JCheckBox(""+count);
			lbls.setHorizontalAlignment(SwingConstants.CENTER);
			lbls.setForeground(Color.white);
			lbls.setBackground(Color.DARK_GRAY);
			lbls.setOpaque(true);

			int b=0;

			while(b<k){
				if(lbls.getText().equals(seat_booked_earlier[b]))
					lbls.setEnabled(false);
					lbls.setBackground(Color.black);
					b=b+1;
			}				    	
			
			seatPane[count].add(lbls);
		
	//code for selecting seat	
		lbls.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(lbls.isSelected()==true){
					clicked.add(c,lbls.getText());
				    c=c+1;
				}
				if(lbls.isSelected()==false){
					removed.add(r,lbls.getText());
					clicked.removeAll(removed);
					removed.removeAll(removed);
					c=c-1;
				}
			}
		});
	
		JButton btnbook = new JButton("Book");
		btnbook.setBounds(790,550,150,50);
		add(btnbook);	
		
	//adding details of booking to seat_table and booking_table	
		btnbook.addActionListener(new ActionListener(){  
		    @SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e){  		    	
		    	int q=0;
				try
		   		{
		   			Class.forName("com.mysql.jdbc.Driver");
		   			Connection con = DriverManager.getConnection
		   					("jdbc:mysql://localhost:3306/book_my_show_db" ,"root" ,"Kalyani5*");
		   		
		   			String query = " insert into booking_table (show_id, number_of_tickets_booked, date_of_booking, booked_for_date, user_id)"
		   				        + " values (?, ?, ?, ?, ?)";
		  			PreparedStatement preparedStmt = con.prepareStatement(query);
		   		    preparedStmt.setString (1, sid);
		    		preparedStmt.setInt (2, clicked.toArray().length);
		    		preparedStmt.setString(3, dtf.format(now));
		    		preparedStmt.setString (4,date_selected);
		    		preparedStmt.setString (5,uid);
		    		      
		    		preparedStmt.execute();
		    			
		    		while(q<act){
		    		  String query1 = " insert into seat_table (user_id, show_id, seat_booked)"
		    			        + " values (?, ?, ?)";
		    		  PreparedStatement preparedStmt1 = con.prepareStatement(query1);
		    		  preparedStmt1.setString (1, uid);
		    		  preparedStmt1.setString (2, sid);
		    		  preparedStmt1.setString(3, (String) clicked.get(q));
		    		      
		    		  preparedStmt1.execute();
		    		  q=q+1;
		    		}
		    		
		    if(clicked.isEmpty()){
	  		      JOptionPane.showMessageDialog(null,"Please select seat before booking!");  

		    }	      
		    else{
		    	 JOptionPane.showMessageDialog(null,"Thank you for booking. Do visit again!");  
		    	 Booking_Page.this.dispose();
   		      	 new HomePage(user_name, user_uid);	    	
		    }
		    
		    con.close();
		    }catch(Exception e1){
		    	System.out.println(e1);
			}
			    	
		}});  
		
			seatPane[count].setBackground(Color.DARK_GRAY);
		    parentPane.add(seatPane[count]);
		    count=count+1;			
	    }
	    	       
		}catch(Exception e){
			System.out.println(e);
		}}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}	
}

