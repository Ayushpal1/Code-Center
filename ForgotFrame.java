package Project;

//import Project.SignInFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Project.Account;
import java.sql.*;
//import Project.testframesignin;

public class ForgotFrame
{
	JFrame frame;
 public ForgotFrame()
 {
	frame = new JFrame("Forgot Password");
	frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	JLabel Genjutsu1 = new JLabel();
	Genjutsu1.setPreferredSize(new Dimension(50,600));
	JLabel Genjutsu2 = new JLabel();
	Genjutsu2.setPreferredSize(new Dimension(50,600));
	JLabel Genjutsu3 = new JLabel();
	Genjutsu3.setPreferredSize(new Dimension(600,50));
	JLabel l = new JLabel("Change Password");

	JLabel namel = new JLabel("Enter Registered Username :");
	JPanel namelbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	namelbl.add(namel);
	JTextField name = new JTextField(20);
	JPanel nametf = new JPanel(new FlowLayout(FlowLayout.LEFT));
	nametf.add(name);

	JLabel phnol = new JLabel("Enter Registered Phone Number :");
	JPanel phnolbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	phnolbl.add(phnol);
	JTextField phno = new JTextField(20);
	JPanel phnotf = new JPanel(new FlowLayout(FlowLayout.LEFT));
	phnotf.add(phno);

	JLabel emaill = new JLabel("Enter Registered Email ID :");
	JPanel emaillbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	emaillbl.add(emaill);
	JTextField email = new JTextField(20);
	JPanel emailtf = new JPanel(new FlowLayout(FlowLayout.LEFT));
	emailtf.add(email);

	JLabel passl = new JLabel("Enter Password :");
	JPanel passlbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	passlbl.add(passl);
	JTextField pass = new JTextField(20);
	JPanel passtf = new JPanel(new FlowLayout(FlowLayout.LEFT));
	passtf.add(pass);

	JButton change = new JButton("Change");
	change.addMouseListener(new MouseAdapter()
	{
		public void mouseClicked(MouseEvent e)
		{
			Changepass(name.getText(),phno.getText(),email.getText(),pass.getText());
		}
	});
	JButton cancel = new JButton("Cancel");
	cancel.addMouseListener(new MouseAdapter()
	{
		public void mouseClicked(MouseEvent e)
		{
			new testframesignin();
			frame.dispose();
		}
	});
	JPanel panebtn = new JPanel();
	panebtn.add(change);
	panebtn.add(cancel);
	JPanel TopPanel = new JPanel();
	TopPanel.setPreferredSize(new Dimension(600,60));
	TopPanel.add(l);
	JPanel p = new JPanel(new GridLayout(4,2));
	p.add(namelbl);
	p.add(nametf);
	p.add(phnolbl);
	p.add(phnotf);
	p.add(emaillbl);
	p.add(emailtf);
	p.add(passlbl);
	p.add(passtf);

	JPanel pane = new JPanel();
	pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
	pane.add(p);
	pane.add(Box.createRigidArea(new Dimension(0, 60)));
	pane.add(panebtn);
	pane.add(Box.createRigidArea(new Dimension(0, 60)));

	frame.add(Genjutsu1,BorderLayout.WEST);
	frame.add(Genjutsu2,BorderLayout.EAST);
	frame.add(Genjutsu3,BorderLayout.SOUTH);
	frame.add(TopPanel,BorderLayout.NORTH);
	frame.add(pane,BorderLayout.CENTER);
	frame.setVisible(true);
 }

void Changepass(String uname,String Phno,String emailid,String Pass)
{
	Account up = new Account();
	if(uname.equals("")||Phno.equals("")||emailid.equals("")||Pass.equals(""))
	{
		JOptionPane.showMessageDialog(null,"All Fields are compulsory");
		return;
	}
	//JOptionPane.showMessageDialog(null,emailid);
	if(getname(uname,Phno,emailid))
	{
		JOptionPane.showMessageDialog(null,"Permission granted");
		if(up.checkuname(uname) && up.Ph_no_check_valid(Phno) && up.checkmail(emailid)&& up.checkpass(Pass))
		{
			try
			{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
				PreparedStatement ps = null;
				ps = con.prepareStatement("update account set password = '" + Pass + "' where a_u_name ='"+ uname +"'");
				ps.executeUpdate();
				con.close();
				JOptionPane.showMessageDialog(null,"Password Changed");
				frame.dispose();
				new testframesignin();
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null,"Reached here");
				JOptionPane.showMessageDialog(null,ex);
			}
		}
	}
}

boolean getname(String uname,String p,String e)
{
	String name="";
	String ph = "";
	String eid = "";
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
            		PreparedStatement ps = null;
           		ps = con.prepareStatement("Select a_u_name,ph_no,email_id from Account where a_u_name = '" + uname + "' ");
            		ResultSet rsc = null;
            		rsc = ps.executeQuery();
            		if(rsc.next())
		{
			name = rsc.getString(1);
			ph = rsc.getString(2);
			eid = rsc.getString(3);
			if(name.equals(uname))
			{
				//JOptionPane.showMessageDialog(null,"User exists");
				con.close();
				//return false;
				return true;
			}
			if(!(ph.equals(p)))
			{
				JOptionPane.showMessageDialog(null,"Phone numbers do not match");
				con.close();
				return false;
			}
			if(!(eid.equals(e)))
			{
				JOptionPane.showMessageDialog(null,"Email id does not match");
				con.close();
				return false;
			}
			return true;
		}
		else
		{
			JOptionPane.showMessageDialog(null,"User does not Exists");
			con.close();
			return false;
		}
	}
	catch(Exception ex)
	{
		System.out.println(ex);
	}
	return false;
}

}