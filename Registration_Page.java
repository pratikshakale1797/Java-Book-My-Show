import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.*;

public class Registration_Page extends JFrame implements ActionListener {

	int i=0, j=0, already_present=0;
	
	public Registration_Page(){
	
	//setting the frame
		final JFrame frame = new JFrame();    
		frame.setLayout(null);
		frame.setSize(1300,720);
		int both = JFrame.MAXIMIZED_BOTH;
		frame.setExtendedState((frame.getExtendedState() & both)!=both ? both : JFrame.NORMAL);
		Font f = new Font(Font.SANS_SERIF, Font.BOLD,  18);
		final String[] usernames = new String[200];
		frame.setVisible(true);
		setLayout(null);	
		
	//book my show logo
	 	ImageIcon icon_logo = new ImageIcon("/home/udaan/workspace/basic/images/book-my-show.png");
		JLabel lbl_book_my_show_icon = new JLabel();
	  	lbl_book_my_show_icon.setBounds(0,0,310,50);		
	    	lbl_book_my_show_icon.setIcon(icon_logo);
		lbl_book_my_show_icon.setForeground(Color.white);
		frame.add(lbl_book_my_show_icon);

	//label of registration
		JLabel lbl_hello = new JLabel("Register yourself with Book My Show!", SwingConstants.CENTER);
		lbl_hello.setForeground(Color.white);
		float[] hsb = Color.RGBtoHSB(1, 110, 178, null);
		frame.getContentPane().setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
		hsb = Color.RGBtoHSB(193, 6, 6, null);
		lbl_hello.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
		lbl_hello.setOpaque(true);
	    	lbl_hello.setBounds(160,0,940,50);	
	    	lbl_hello.setFont(f);
	    	frame.add(lbl_hello);
	    
    //enter name label
	    JLabel lbl_name = new JLabel("Enter Name: ");
		lbl_name.setBounds(350,100,300,50);
		frame.add(lbl_name);
		lbl_name.setVisible(true);
		lbl_name.setFont(f);
		lbl_name.setForeground(Color.white);
		
	//name text box
		final JTextArea txt_name = new JTextArea();
		txt_name.setText(null);
		txt_name.setBounds(580,110,300,30);
		txt_name.setAlignmentX(BOTTOM_ALIGNMENT);
		frame.add(txt_name);
		txt_name.setVisible(true);
		txt_name.setFont(f);
		
	//email text box
		JLabel lbl_email = new JLabel("Email ID: ");
		lbl_email.setBounds(350,160,300,80);
		frame.add(lbl_email);
		lbl_email.setVisible(true);
		lbl_email.setFont(f);
		lbl_email.setForeground(Color.white);
		
	//email text box
		final JTextArea txt_email = new JTextArea();
		txt_email.setText(null);
		txt_email.setBounds(580,180,300,30);
		frame.add(txt_email);
		txt_email.setVisible(true);
		txt_email.setFont(f);
		
	//mobile number label
		JLabel lbl_mobno = new JLabel("Mobile Number: ");
		lbl_mobno.setBounds(350,230,300,80);
		frame.add(lbl_mobno);
		lbl_mobno.setVisible(true);
		lbl_mobno.setFont(f);
		lbl_mobno.setForeground(Color.white);
		
	//mobile number text box
		final JTextArea txt_mobno = new JTextArea();
		txt_mobno.setText(null);
		txt_mobno.setBounds(580,250,300,30);
		frame.add(txt_mobno);
		txt_mobno.setVisible(true);
		txt_mobno.setFont(f);
		
	//enter password label
		JLabel lbl_password1 = new JLabel("Enter Password: ");
		lbl_password1.setBounds(350,300,300,80);
		frame.add(lbl_password1);
		lbl_password1.setVisible(true);
		lbl_password1.setFont(f);
		lbl_password1.setForeground(Color.white);
		
	//confirm password label
		JLabel lbl_password2 = new JLabel("Confirm Password: ");
		lbl_password2.setBounds(350,360,300,80);
		frame.add(lbl_password2);
		lbl_password2.setForeground(Color.white);
		lbl_password2.setVisible(true);
		lbl_password2.setFont(f);
		
	//password1 text box
		final JPasswordField txt_password1 = new JPasswordField();
		txt_password1.setBounds(580,320,300,30);
		frame.add(txt_password1);
		txt_password1.setVisible(true);
		txt_password1.setFont(f);

	//password 2 text box
		final JPasswordField txt_password2 = new JPasswordField();
		txt_password2.setBounds(580,390,300,30);
		frame.add(txt_password2);
		txt_mobno.setVisible(true);
		txt_password2.setFont(f);
		
	//register button
		JButton btnreg = new JButton("Register");
		btnreg.setBounds(520,500,200,50);
		frame.add(btnreg);
		btnreg.setVisible(true);
		btnreg.setFont(f);
		
	//login button
		JButton btnlogin = new JButton("Login");
		btnlogin.setBounds(1100, 0, 200, 50);
		frame.add(btnlogin);
		btnlogin.setFont(f);

		try
   		{
   			Class.forName("com.mysql.jdbc.Driver");
   			Connection con = DriverManager.getConnection
   					("jdbc:mysql://localhost:3306/book_my_show_db" ,"root" ,"Kalyani5*");
   			Statement stmt_get_user_name =con.createStatement();
   			ResultSet rs_user_name = stmt_get_user_name.executeQuery("select user_name from user_table");
   		
   			while(rs_user_name.next()){
					usernames[i]=rs_user_name.getString(1);		        	
					i=i+1;
   			}
		
   			con.close();

   		}catch(Exception e1){
   			System.out.println(e1);
   		}

		btnlogin.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				new Login_Page();
		    	Registration_Page.this.dispose();
		    	frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		
	//code for registration
		btnreg.addActionListener(new ActionListener(){  
		    @SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e){  
	
		    	String name=txt_name.getText();
		    	String email=txt_email.getText();
		    	String number=txt_mobno.getText();
		    	String password1 = (txt_password1.getText());
		        String password2 = (txt_password2.getText());

		        int k=0;
		        while(k<i){
		        	if(name.equals(usernames[k])){
		        		already_present=1;
		        		System.out.println(usernames[k]);
		        	}
		        	k=k+1;
		        }
		           
		        String regex1 = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"; 
		        Pattern pattern1 = Pattern.compile(regex1);
		        Matcher matcher1 = pattern1.matcher(email);		       
		        
		        String regex2 = "\\d{10}"; 
		        Pattern pattern2 = Pattern.compile(regex2);
				Matcher matcher2 = pattern2.matcher(number);	
		        
				String regex3 = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,15}$";
				Pattern pattern3 = Pattern.compile(regex3);
				Matcher matcher3 = pattern3.matcher(password1);
				
		    	if(txt_name.getText().isEmpty()==true || txt_email.getText().isEmpty()==true ||
		    			txt_mobno.getText().isEmpty()==true || 
		    			txt_password1.getPassword().length==0 
		    			|| txt_password2.getPassword().length==0){
	   	            	JOptionPane.showMessageDialog(null, "All fields must be complete to submit.", "Woops", JOptionPane.ERROR_MESSAGE);

		    	}
		    	else if(already_present==1){
	                   JOptionPane.showMessageDialog(null, "Username already exists!", "Woops", JOptionPane.ERROR_MESSAGE);
	                   already_present=0;
		    	}
		    	else if(matcher1.matches()==false){
	                   JOptionPane.showMessageDialog(null, "Invalid Email ID!", "Woops", JOptionPane.ERROR_MESSAGE);
		    	}
		    	else if(matcher2.matches()==false){
	                   JOptionPane.showMessageDialog(null, "Invalid Contact Number!", "Woops", JOptionPane.ERROR_MESSAGE);
		    	}	
		    	else if(matcher3.matches()==false){
	                   JOptionPane.showMessageDialog(null, "Password is not compatible with the guideline-> \nIt must contain one digit from 0-9 \nIt must contain one lowercase and one uppercase character \nIt must contain one special symbol \nIt's length must be at least 8 characters and maximum of 15", "Woops", JOptionPane.ERROR_MESSAGE);
		    	}
		    	else if(password1.equals(password2)==false)
		         {
		               JOptionPane.showMessageDialog(null, "Passwords do not match.", "Woops", JOptionPane.ERROR_MESSAGE);
		               txt_password1.setText(null);
		               txt_password2.setText(null);
		         }
		    	else{
		    		try
		    		{
		    			Class.forName("com.mysql.jdbc.Driver");
		    			Connection con = DriverManager.getConnection
		    					("jdbc:mysql://localhost:3306/book_my_show_db" ,"root" ,"Kalyani5*");
		    			  String query = " insert into user_table (user_name, mobile_number, email_id, user_password)"
		    				        + " values (?, ?, ?, ?)";
		    			  PreparedStatement preparedStmt = con.prepareStatement(query);
		    		      preparedStmt.setString (1, name);
		    		      preparedStmt.setString (2, number);
		    		      preparedStmt.setString(3,email );
		    		      preparedStmt.setString (4, password1);
		    		      preparedStmt.execute();
		    			  JOptionPane.showMessageDialog(null,"Registration Successful!. Please login to continue.");  
		    			  Registration_Page.this.dispose();
		    			  new Login_Page();
		       
		    		      con.close();
		    		      
		    	}catch(Exception e1){
					System.out.println(e1);
				}
		    }  
		  }}); 
	}
	
	public static void main(String[] args){
		  SwingUtilities.invokeLater(new Runnable(){ 
	            public void run()
	            {	         	
	               new Registration_Page().setVisible(false);	                              
	            }
	        });
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub		
	}
}

