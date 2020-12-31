import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

public class Login_Page extends JFrame implements ActionListener{
	int i=0;
	String[] uname= new String[100];
	String[] upassword= new String[100];
	Integer[] uid= new Integer[100];
	int user_id;

	public Login_Page(){
	
	//setting frame
		final JFrame frame = new JFrame();    
		frame.setLayout(null);
		frame.setSize(1300,720);
		frame.setVisible(true);
		setLayout(null);	
		Font f = new Font(Font.SANS_SERIF, Font.BOLD,  18);
		int both = JFrame.MAXIMIZED_BOTH;
		frame.setExtendedState((frame.getExtendedState() & both)!=both ? both : JFrame.NORMAL);
		float[] hsb = Color.RGBtoHSB(1, 110, 178, null);
		frame.getContentPane().setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));

	//label - login to book my show
		JLabel lbl_hello = new JLabel("Login to Book My Show!", SwingConstants.CENTER);
		lbl_hello.setBounds(160,0,1200,50);	
		frame.add(lbl_hello);
		lbl_hello.setFont(f);
		lbl_hello.setForeground(Color.white);
		hsb = Color.RGBtoHSB(193, 6, 6, null);
		lbl_hello.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
		lbl_hello.setOpaque(true);
		JLabel lbl_name = new JLabel("Enter Name: ");
		lbl_name.setBounds(350,100,200,50);
		frame.add(lbl_name);
		lbl_name.setFont(f);
		lbl_name.setForeground(Color.white);
		lbl_name.setVisible(true);

	//adding book-my-show logo
		ImageIcon icon_logo = new ImageIcon("/home/udaan/workspace/basic/images/book-my-show.png");
		JLabel lbl_book_my_show_icon = new JLabel();
		lbl_book_my_show_icon.setBounds(0,0,310,50);		
		lbl_book_my_show_icon.setIcon(icon_logo);
		frame.add(lbl_book_my_show_icon);
	    
	//name textbox
		final JTextArea txt_name = new JTextArea();
		txt_name.setText(null);
		txt_name.setBounds(580,110,300,30);
		txt_name.setAlignmentX(BOTTOM_ALIGNMENT);
		txt_name.setFont(f);
		frame.add(txt_name);
		txt_name.setVisible(true);
	
	//enter password label
		JLabel lbl_password1 = new JLabel("Enter Password: ");
		lbl_password1.setBounds(350,180,200,80);
		frame.add(lbl_password1);
		lbl_password1.setVisible(true);
		lbl_password1.setFont(f);
		lbl_password1.setForeground(Color.white);
		final JPasswordField txt_password = new JPasswordField();
		txt_password.setBounds(580,200,300,30);
		frame.add(txt_password);
		txt_password.setVisible(true);
		txt_password.setFont(f);
	
	//submit button
		JButton btnsubmit = new JButton("Submit");
		btnsubmit.setBounds(500,300,200,50);
		frame.add(btnsubmit);
		btnsubmit.setFont(f);
		btnsubmit.addActionListener(new ActionListener(){

	//submit button click code
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
	    	String name=txt_name.getText();
			String password = (txt_password.getText());

			if(txt_name.getText().isEmpty()==true || txt_password.getPassword().length==0){
                JOptionPane.showMessageDialog(null, "All fields must be complete to submit.", "Woops", JOptionPane.ERROR_MESSAGE);

	    	}
	    	else 
	         {
	    		try
	    		{
	    			Class.forName("com.mysql.jdbc.Driver");
	    			Connection con = DriverManager.getConnection
	    					("jdbc:mysql://localhost:3306/book_my_show_db" ,"root" ,"Kalyani5*");
	    			Statement stmt_get_user_details =con.createStatement();
	    			ResultSet rs_user_details = stmt_get_user_details.executeQuery("select user_name, user_password, user_id from user_table");

	    			i=0;
	    			while(rs_user_details.next()){
	    			uname[i] = rs_user_details.getString(1);
	    			upassword[i] = rs_user_details.getString(2);
	    			uid[i]=rs_user_details.getInt(3);
	    			i=i+1;
	    			}  
	    			
	    		int count=0, login=0;
				while(count<i){
					if (name.equals(uname[count]) && password.equals(upassword[count]))
					  {    
						login=1;
						user_id=uid[count];
						break;
					 
    				}
					else{
				
						login=0;
					}
					count=count+1;
    			}
				if (login==1)
				  {    
				    JOptionPane.showMessageDialog(null,"Welcome to Book My Show!");  
				    new HomePage(name, user_id);
				 
				    Login_Page.this.dispose();
			    	frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

				}
				else{
					JOptionPane.showMessageDialog(null,"Username & Password do not match! Please try again.");  
				    txt_name.setText(null);
				    txt_password.setText(null);
				}
    		   			
    		      con.close();
    	}catch(Exception e1){
			System.out.println(e1);
		}
	  }}
	});
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub	
	}
}

