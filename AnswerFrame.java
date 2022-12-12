package Project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class AnswerFrame
{
	JFrame frame = new JFrame("Answer Frame");
	JPanel p = new JPanel();
	JPanel mainp = new JPanel();
	String Post;
	private int aid,Pid;
	AnswerFrame(String q,int id)
	{
		aid = id;
		Post = q;
		setPid();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel l = new JLabel(q);
		l.setMaximumSize(new Dimension(200,200));
		l.setPreferredSize(new Dimension(200,100));
		frame.add(l,BorderLayout.NORTH);
		refresh();
		addElementstorp();
		frame.setVisible(true);
	}

void refresh()
{
	createPanel();
	ArrayList<String> uname = new ArrayList<String>(); 
	ArrayList<String> post = new ArrayList<String>();
	ArrayList<String> likes = new ArrayList<String>();
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
            		PreparedStatement ps = null;
           		ps = con.prepareStatement("Select count(*) from Post");
		ResultSet rsc = null;
            		rsc = ps.executeQuery();
            		rsc.next();
            		int count = rsc.getInt(1);
		System.out.println(count);
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select a_u_name,a_ans from Account A,answers ans where A.a_id=ans.a_id and ans.p_id = '"+Pid+"'");  
		while(rs.next())
		{
			String s = rs.getString(1);
			String s2 = rs.getString(2);
			//String l = Integer.toString(rs.getInt(3));
			uname.add(s);
			post.add(s2);
			//likes.add(l);
		}
		con.close();  
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	JLabel l[]= new JLabel[uname.size()];
	JButton follow[] = new JButton[uname.size()];
	JPanel toppane[] = new JPanel[uname.size()];
	JLabel pos[]= new JLabel[uname.size()];
	JButton b[] = new JButton[uname.size()];
	JPanel pane[] = new JPanel[uname.size()];
	for(int i=0;i<uname.size();i++)
	{
		String n = uname.get(i);
		String n2 = post.get(i);
		l[i] = new JLabel(n,SwingConstants.LEFT);
		follow[i] = new JButton("Follow");
		pos[i] = new JLabel(n2,SwingConstants.CENTER);
		pos[i].setVerticalAlignment(JLabel.CENTER);
		pos[i].setPreferredSize(new Dimension(60,100));
		toppane[i] = new JPanel();
		toppane[i].setBackground(Color.decode("#ffffff"));
		toppane[i].setLayout(new BoxLayout(toppane[i],BoxLayout.X_AXIS));
		toppane[i].add(l[i]);
		toppane[i].add(Box.createRigidArea(new Dimension(60,0)));
		toppane[i].add(follow[i]);
		pane[i] = new JPanel();
		pane[i].setBackground(Color.decode("#FFFFFF"));
		pane[i].setBorder(BorderFactory.createLineBorder(Color.decode("#E7E9EE")));
		pane[i].setLayout(new BoxLayout(pane[i],BoxLayout.Y_AXIS));
		pane[i].add(toppane[i]);
		pane[i].add(new JSeparator());
		pane[i].add(pos[i]);
		p.add(pane[i]);
		p.add(Box.createRigidArea(new Dimension(0, 60)));
		mainp.add(p,BorderLayout.CENTER);
		frame.add(mainp,BorderLayout.CENTER);
	}
}

void createPanel()//Accessed once in the program
{
	mainp.setLayout(new BorderLayout());
	JLabel Genright=new JLabel();//no need to be declared in class
	JLabel Genleft= new JLabel();//no need to be declared in class
	Genleft.setPreferredSize(new Dimension(50,60));
	Genright.setPreferredSize(new Dimension(50,60));
	p.setBackground(Color.decode("#EAEFF2"));
	mainp.setBackground(Color.decode("#EAEFF2"));
	mainp.add(Genright,BorderLayout.WEST);
	mainp.add(Genleft,BorderLayout.EAST);
	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
}

void addElementstorp()
{
	JPanel southPanel = new JPanel();
	JTextField question = new JTextField(50);
	JButton ask = new JButton("ASK");
	ActionListener b1Event = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {String qgt = question.getText();question.setText("");MakePost(qgt);}};
	ask.addActionListener(b1Event);
	southPanel.add(question);
	southPanel.add(ask);
	frame.add(southPanel,BorderLayout.SOUTH);
}

void MakePost(String p)
{
          try
          {
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
            PreparedStatement ps = null;
           		ps = con.prepareStatement("Select count(*) from answers");
		ResultSet rsc = null;
            		rsc = ps.executeQuery();
            		rsc.next();
            		int count = rsc.getInt(1);
		System.out.println(count);
            ps = con.prepareStatement("insert into answers values("+ aid +",'"+ Pid +"','"+p+"',"+count+")");
            ps.executeUpdate();
            con.close();
	//refresh();
	//addElementstorp();
	new AnswerFrame(Post,aid);
	frame.dispose();
          }
          catch(Exception ex)
          {
	JOptionPane.showMessageDialog(null,"Reached here");
	JOptionPane.showMessageDialog(null,ex);
          }
}


void setPid()
{
          try
          {
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
            PreparedStatement ps = null;
	//JOptionPane.showMessageDialog(null,Post);
            ResultSet rs;
            ps = con.prepareStatement("Select p_id from Post where P_post= '"+Post+"' ");
            rs = ps.executeQuery();
	rs.next();
	Pid = rs.getInt(1);
            con.close();
          }
          catch(Exception ex)
          {
	JOptionPane.showMessageDialog(null,"Reached here");
	JOptionPane.showMessageDialog(null,ex);
          }
}

}