package Project;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import Project.Login_page;
import Project.Account;
import Project.AnswerFrame;
import Project.ReportFrame;

public class MainFrame
{
	JFrame frame = new JFrame();
	JPanel p = new JPanel();
	JPanel mainp = new JPanel();
	JPanel leftpanel = new JPanel();
	JPanel rightpanel = new JPanel();
	int id,fid;
	String username;
	//JLabel l = new JLabel();
MainFrame(int n)//called by the previous frame which gives the id of the user and stores it in the class
{
	id = n;
	CreateMainFrame();
	AddToMainFrame();
}

void CreateMainFrame()//accessed once in the program and called only by the constructor
{
	frame.setTitle("Code Center");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(500,700);
	frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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

void AddToMainFrame()//Accessed once in the program
{
	addElementstolp();
	addElementstorp();
	//SPLIT SECTION
	refresh();
	JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	//sp.setResizeWeight(0.09);
	sp.setDividerLocation(0.5);
	sp.setEnabled(false);
	sp.setDividerSize(2);
	sp.add(leftpanel);
	//sp.add(l);
	JScrollPane scroll = new JScrollPane(p);
	scroll.getVerticalScrollBar().setPreferredSize (new Dimension(0,0));
	scroll.setBorder(null);
	mainp.add(scroll,BorderLayout.CENTER);
	rightpanel.add(mainp);
	sp.add(rightpanel);
	frame.add(sp, BorderLayout.CENTER);
	frame.setVisible(true);
}

//adds elements to the left panel
void addElementstolp()//Accessed once in the program
{
	leftpanel.setLayout(new GridLayout(10,1));
	JButton name,Following,likedposts,Logout,home,Reports;
	home = new JButton("Code Center");
	ActionListener callmain = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {new MainFrame(id);frame.dispose();}};
	home.addActionListener(callmain);
	name = new JButton(getname());
	username = getname();
	ActionListener callupdate = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {clearpanel();updateacc();}};
	name.addActionListener(callupdate);
	Following = new JButton("Following");
	ActionListener clearpanelcall = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {clearpanel();showFollowing();}};
	Following.addActionListener(clearpanelcall);
	likedposts = new JButton("Posts You've liked");
	ActionListener liked = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {clearpanel();likedposts();}};
	likedposts.addActionListener(liked);
	Reports = new JButton("Report");
	ActionListener callreport = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {new ReportFrame(id);frame.dispose();}};
	Reports.addActionListener(callreport);
	Logout = new JButton("Log Out");
	ActionListener b1Event = new ActionListener() {@Override public void actionPerformed(ActionEvent e)
	{
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", dialogButton);
		if(dialogResult == 0)
		{
			new Login_page();
			frame.dispose();
		}
		else
		{
			System.out.println("No Option");
		}
	}};
	Logout.addActionListener(b1Event);
	leftpanel.add(home);
	//leftpanel.
	leftpanel.add(name);
	leftpanel.add(Following);
	leftpanel.add(likedposts);
	leftpanel.add(Reports);
	leftpanel.add(Logout);

	//JLabel l = new JLabel();
	//l.setPreferredSize(new Dimension(280,900));
	//l.setIcon(new ImageIcon(new ImageIcon("f.jpg").getImage().getScaledInstance(280, 900, Image.SCALE_DEFAULT)));
	//l.setHorizontalAlignment(JLabel.CENTER);
	//l.setOpaque(true);
	//l.setBounds(0,0,300,500);
	//l.setLayout(new FlowLayout());
	//l.insets = new Insets(0,0,0,0);
	//l.setBackground(Color.BLACK);
	//leftpanel.add(l);
}

//adds element to the right panel
void addElementstorp()//Accessed once in the program
{
	rightpanel.setLayout(new BorderLayout());
	JPanel southPanel = new JPanel();
	JLabel top,search,saved;
	JTextField question = new JTextField(50);
	JButton ask = new JButton("ASK");
	ActionListener b1Event = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {String qgt = question.getText();question.setText("");MakePost(qgt);}};
	ask.addActionListener(b1Event);
	top = new JLabel("Code Center",SwingConstants.CENTER);
	top.setFont(new Font("Serif", Font.PLAIN, 40));
	top.setOpaque(true);
	top.setBackground(Color.decode("#009BE5"));
	southPanel.add(question);
	southPanel.add(ask);
	rightpanel.add(top,BorderLayout.NORTH);
	rightpanel.add(southPanel,BorderLayout.SOUTH);
	createPanel();
}

//gets name of the user who logged in and set the value of the button to the og name of the user
String getname()
{
	String name="";
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
            		PreparedStatement ps = null;
           		ps = con.prepareStatement("Select a_u_name from Account where a_id = " + id + " ");
            		ResultSet rsc = null;
            		rsc = ps.executeQuery();
            		rsc.next();
            		name = rsc.getString(1);
		con.close();
		return name; 
	}
	catch(Exception ex)
	{
		System.out.println(ex);
	}
	return name;
}

//Makes a post
void MakePost(String p)
{
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
		PreparedStatement ps = null;
		int count;
		count = checkpid();
		ps = con.prepareStatement("insert into Post values("+ count +",'"+ p +"',0," + id + ")");
		ps.executeUpdate();
		con.close();
		new MainFrame(id);
		frame.dispose();
	}
	catch(Exception ex)
	{
		JOptionPane.showMessageDialog(null,"Reached here");
		JOptionPane.showMessageDialog(null,ex);
	}
}

void refresh()
{
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
		ResultSet rs=stmt.executeQuery("select a_u_name,P_post,Likes from Account A,Post P where A.a_id=P.a_id order by p.a_id desc");  
		while(rs.next())
		{
			String s = rs.getString(1);
			String s2 = rs.getString(2);
			String l = Integer.toString(rs.getInt(3));
			uname.add(s);
			post.add(s2);
			likes.add(l);
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
	JPanel bottompane[] = new JPanel[uname.size()];
	JButton Answers[] = new JButton[uname.size()];
	JLabel pos[]= new JLabel[uname.size()];
	JButton b[] = new JButton[uname.size()];
	JPanel pane[] = new JPanel[uname.size()];
	for(int i=0;i<uname.size();i++)
	{
		String n = uname.get(i);
		String n2 = post.get(i);
		String likecount = likes.get(i);
		l[i] = new JLabel(n,SwingConstants.LEFT);
		l[i].setBounds(0,0,100,20);
		follow[i] = new JButton(checkfollowingtext(n));
		pos[i] = new JLabel(n2,SwingConstants.CENTER);
		pos[i].setVerticalAlignment(JLabel.CENTER);
		pos[i].setPreferredSize(new Dimension(60,100));
		b[i] = new JButton(likecount);
		Answers[i] = new JButton("Answers");
		ActionListener AnswerEvent = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {new AnswerFrame(n2,id);/*frame.dispose();*/}};
		Answers[i].addActionListener(AnswerEvent);
		toppane[i] = new JPanel();
		toppane[i].setBackground(Color.decode("#ffffff"));
		toppane[i].setLayout(null);
		toppane[i].add(l[i]);
		toppane[i].setPreferredSize(new Dimension(100,50));
		if(n.equals(getname()))
		{
			follow[i].setText("Delete");
			follow[i].setBounds(1190,0,100,40);
			ActionListener deletepostEvent = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {deletepost(n2);}};
			follow[i].addActionListener(deletepostEvent);
			toppane[i].add(follow[i]);
		}
		else
		{
			follow[i].setBounds(1190,0,100,40);
			ActionListener checkfollowEvent = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {checkfollowing(n);}};
			follow[i].addActionListener(checkfollowEvent);
			toppane[i].add(follow[i]);
		}
		bottompane[i] = new JPanel();
		bottompane[i].setBackground(Color.decode("#ffffff"));
		bottompane[i].add(b[i]);
		bottompane[i].add(Answers[i]);
		ActionListener b1Event = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {likegiven(n2);}};
		b[i].addActionListener(b1Event);
		pane[i] = new JPanel();
		pane[i].setBackground(Color.decode("#FFFFFF"));
		pane[i].setBorder(BorderFactory.createLineBorder(Color.decode("#E7E9EE")));
		pane[i].setLayout(new BoxLayout(pane[i],BoxLayout.Y_AXIS));
		pane[i].add(toppane[i]);
		pane[i].add(new JSeparator());
		pane[i].add(pos[i]);
		pane[i].add(bottompane[i]);
		p.add(pane[i]);
		p.add(Box.createRigidArea(new Dimension(0, 60)));
	}
}

void testlike(int pid)
{
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
		PreparedStatement ps = null;
		ps = con.prepareStatement("Select likes from Post where p_id = "+pid+"");
		ResultSet rs = null;
		rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		count++;
		ps = null;
		ps = con.prepareStatement("update post set likes = "+count+" where p_id = "+pid+"");
		ps.executeUpdate();
		ps = null;
		ps = con.prepareStatement("insert into likedposts values("+pid+","+id+")");
		ps.executeUpdate();
		new MainFrame(id);
		frame.dispose();
		con.close();
	}
	catch(Exception ex)
	{
		JOptionPane.showMessageDialog(null,"Reached here");
		JOptionPane.showMessageDialog(null,ex);
	}
}

void likegiven(String testpost)
{
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
		PreparedStatement ps = null;
		ps = con.prepareStatement("Select p_id from Post where p_post = '" + testpost + "' ");
		ResultSet rs = null;
		rs = ps.executeQuery();
		rs.next();
		int pid = rs.getInt(1);
		ps = null;
		rs = null;
		ps = con.prepareStatement("select * from likedposts where p_id ="+pid+" and a_id="+id+"");
		rs = ps.executeQuery();
		if(!(rs.next()))
			testlike(pid);
		else
			likeremoved(pid);
		con.close();
	}
	catch(Exception ex)
	{
		JOptionPane.showMessageDialog(null,"Reached here");
		JOptionPane.showMessageDialog(null,ex);
	}
}

void clearpanel()
{
	p.removeAll();
	p.revalidate();
	p.repaint();
}

void likeremoved(int pid)
{
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
		PreparedStatement ps = null;
		ps = con.prepareStatement("Select likes from Post where p_id = "+pid+"");
		ResultSet rs = null;
		rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		count--;
		ps = null;
		ps = con.prepareStatement("update post set likes = "+count+" where p_id = "+pid+"");
		ps.executeUpdate();
		ps = null;
		ps = con.prepareStatement("delete from likedposts where p_id = "+pid+"");
		ps.executeUpdate();
		new MainFrame(id);
		frame.dispose();
		con.close();
	}
	catch(Exception ex)
	{
		JOptionPane.showMessageDialog(null,"Reached here");
		JOptionPane.showMessageDialog(null,ex);
	}
}

void likedposts()
{
	ArrayList<String> uname = new ArrayList<String>(); 
	ArrayList<String> post = new ArrayList<String>();
	ArrayList<String> likes = new ArrayList<String>();
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select a_u_name,P_post,Likes from Post P,Account A,likedposts LP where LP.p_id=P.p_id and P.a_id = a.a_id and LP.a_id ="+id+" order by P.p_id desc");  
		while(rs.next())
		{
			String s = rs.getString(1);
			String s2 = rs.getString(2);
			String l = Integer.toString(rs.getInt(3));
			uname.add(s);
			post.add(s2);
			likes.add(l);
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
	JPanel bottompane[] = new JPanel[uname.size()];
	JButton Answers[] = new JButton[uname.size()];
	JLabel pos[]= new JLabel[uname.size()];
	JButton b[] = new JButton[uname.size()];
	JPanel pane[] = new JPanel[uname.size()];
	for(int i=0;i<uname.size();i++)
	{
		String n = uname.get(i);
		String n2 = post.get(i);
		String likecount = likes.get(i);
		l[i] = new JLabel(n,SwingConstants.LEFT);
		follow[i] = new JButton("Follow");
		pos[i] = new JLabel(n2,SwingConstants.CENTER);
		pos[i].setVerticalAlignment(JLabel.CENTER);
		pos[i].setPreferredSize(new Dimension(60,100));
		b[i] = new JButton(likecount);
		Answers[i] = new JButton("Answers");
		toppane[i] = new JPanel();
		toppane[i].setBackground(Color.decode("#ffffff"));
		toppane[i].setLayout(new BoxLayout(toppane[i],BoxLayout.X_AXIS));
		toppane[i].add(l[i]);
		toppane[i].add(Box.createRigidArea(new Dimension(60,0)));
		toppane[i].add(follow[i]);
		bottompane[i] = new JPanel();
		bottompane[i].setBackground(Color.decode("#ffffff"));
		bottompane[i].setLayout(new BoxLayout(bottompane[i],BoxLayout.X_AXIS));
		bottompane[i].add(b[i]);
		bottompane[i].add(Answers[i]);
		ActionListener b1Event = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {likegiven(n2);clearpanel();likedposts();}};
		b[i].addActionListener(b1Event);
		pane[i] = new JPanel();
		pane[i].setBackground(Color.decode("#FFFFFF"));
		pane[i].setBorder(BorderFactory.createLineBorder(Color.decode("#E7E9EE")));
		pane[i].setLayout(new BoxLayout(pane[i],BoxLayout.Y_AXIS));
		pane[i].add(toppane[i]);
		pane[i].add(new JSeparator());
		pane[i].add(pos[i]);
		pane[i].add(bottompane[i]);
		p.add(pane[i]);
		p.add(Box.createRigidArea(new Dimension(0, 60)));
	}
}

void checkfollowing(String holder)
{
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
		PreparedStatement ps = null;
		ps = con.prepareStatement("Select a_id from Account where a_u_name ='"+ holder+"' ");
		ResultSet rs = null;
		rs = ps.executeQuery();
		rs.next();
		int pid = rs.getInt(1);
		ps = null;
		rs = null;
		ps = con.prepareStatement("select * from Following where a_id ="+id+" and f_id="+pid+"");
		rs = ps.executeQuery();
		if(!(rs.next()))
		{
			ps = con.prepareStatement("insert into Following values("+id+","+pid+")");
			ps.executeUpdate();
		}
		else
		{
			ps = con.prepareStatement("delete from following where f_id = "+pid+"");
			ps.executeUpdate();
		}
		con.close();
	}
	catch(Exception ex)
	{
		JOptionPane.showMessageDialog(null,"Reached here");
		JOptionPane.showMessageDialog(null,ex);
	}
	new MainFrame(id);
	frame.dispose();
}

void showFollowing()
{
	ArrayList<String> uname = new ArrayList<String>(); 
	ArrayList<String> followers = new ArrayList<String>();
	ArrayList<String> following = new ArrayList<String>();
	ArrayList<String> tposts = new ArrayList<String>();
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select a.a_u_name from account a,following f where f.a_id =" +id+" and a.a_id = f.f_id and f.f_id != "+id+"");  
		while(rs.next())
		{
			String s = rs.getString(1);
			String s2 = Integer.toString(getFollowers(s));
			String l = Integer.toString(getFollowing(s));
			String postc = Integer.toString(getpostcount(s));
			uname.add(s);
			followers.add(s2);
			following.add(l);
			tposts.add(postc);
		}
		con.close();  
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	JLabel l[]= new JLabel[uname.size()];
	JPanel bottompane[] = new JPanel[uname.size()];
	JLabel tpostcount[] = new JLabel[uname.size()];
	JLabel pos[]= new JLabel[uname.size()];
	JLabel b[] = new JLabel[uname.size()];
	JPanel pane[] = new JPanel[uname.size()];
	for(int i=0;i<uname.size();i++)
	{
		String n = uname.get(i);
		String n2 = followers.get(i);
		String likecount = following.get(i);
		String postcount = tposts.get(i);
		l[i] = new JLabel(n,SwingConstants.CENTER);
		pos[i] = new JLabel("No of Followers \n"+n2,SwingConstants.CENTER);
		b[i] = new JLabel("No. of following \n"+likecount);
		tpostcount[i] = new JLabel("No. of Posts \n" +postcount,SwingConstants.CENTER);
		bottompane[i] = new JPanel();
		bottompane[i].setLayout(new GridLayout(1,3));
		bottompane[i].setBackground(Color.decode("#ffffff"));
		//bottompane[i].setLayout(new BoxLayout(bottompane[i],BoxLayout.X_AXIS));
		bottompane[i].add(b[i]);
		bottompane[i].add(pos[i]);
		bottompane[i].add(tpostcount[i]);
		pane[i] = new JPanel();
		pane[i].setBackground(Color.decode("#FFFFFF"));
		pane[i].setBorder(BorderFactory.createLineBorder(Color.decode("#E7E9EE")));
		pane[i].setLayout(new GridLayout(2,1));
		pane[i].add(l[i]);
		pane[i].add(bottompane[i]);
		p.add(pane[i]);
		p.add(Box.createRigidArea(new Dimension(0, 60)));
	}
}

int getFollowing(String username)
{
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select count(*) from following f,account a where f.a_id = a.a_id and a.a_u_name = '"+username+"'");  
		if(rs.next())
		{
			int s = rs.getInt(1);
			con.close();
			return s;
		}
		else
		{
			con.close();
			return 0;
		} 
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return 0;
}

int getFollowers(String username)
{
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select count(*) from following f,account a where f.f_id = a.a_id and a.a_u_name = '"+username+"'");  
		if(rs.next())
		{
			int s = rs.getInt(1);
			con.close();
			return s;
		}
		else
		{
			con.close();
			return 0;
		} 
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return 0;
}

int getpostcount(String username)
{
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select count(*) from post p,account a where a.a_u_name = '"+username+"' and a.a_id = p.a_id;");  
		if(rs.next())
		{
			int s = rs.getInt(1);
			con.close();
			return s;
		}
		else
		{
			con.close();
			return 0;
		} 
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return 0;
}

int checkpid()
{
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
		PreparedStatement ps = null;
		ps = con.prepareStatement("Select count(*),max(p_id) from post");
		ResultSet rs = null;
		rs = ps.executeQuery();
		rs.next();
		int total = rs.getInt(1);
		int max = rs.getInt(2);
		if(total == max)
		{
			con.close();
			return total+1;
		}
		else
		{
			ps = null;
			ps = con.prepareStatement("Select p_id from post");
			rs = null;
			rs = ps.executeQuery();
			for(int i=1;i<max;i++)
			{
				rs.next();
				int c_a_id =rs.getInt(1);
				if(i!=c_a_id)
				{
					con.close();
					return i;
				}
			}
		}
	}
	catch(Exception ex)
	{
		JOptionPane.showMessageDialog(null,"An id issue Occured");
		JOptionPane.showMessageDialog(null,ex);
	}
	return 0;
}

public void updateacc()
{
	JPanel mainupdatepanel = new JPanel();
	mainupdatepanel.setLayout(new BoxLayout(mainupdatepanel,BoxLayout.Y_AXIS));
	JPanel Containerpanel = new JPanel();
	Containerpanel.setLayout(new GridLayout(4,2));
	JPanel BottomPanel = new JPanel(new FlowLayout());

	JLabel Unamel = new JLabel("User Name :");
	JPanel unamelbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	unamelbl.add(Unamel);
	JTextField Unametf = new JTextField(20);
	JPanel unametfp = new JPanel(new FlowLayout(FlowLayout.LEFT));
	unametfp.add(Unametf);
	Unametf.setText(username);

	JTextField Ph_notf = new JTextField(20);
	Ph_notf.setText(getphno());
	JLabel Ph_nol = new JLabel("Phone's number :");
	JPanel phnolbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	phnolbl.add(Ph_nol);
	JPanel phnotf = new JPanel(new FlowLayout(FlowLayout.LEFT));
	phnotf.add(Ph_notf);

	JTextField Emailtf = new JTextField(20);
	Emailtf.setText(getemailid());
	JPanel emailtfp = new JPanel(new FlowLayout(FlowLayout.LEFT));
	emailtfp.add(Emailtf);
	JLabel Emaill = new JLabel("Enter Email :");
	JPanel emaillbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	emaillbl.add(Emaill);

	String lang[] = {"C","C#","C++","Java","Html","Javascript"};
	JComboBox langcb = new JComboBox(lang);
	JLabel langlbl = new JLabel("Select Preferred Language : ");
	JPanel langl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	langl.add(langlbl);
	JPanel langp = new JPanel(new FlowLayout(FlowLayout.LEFT));
	langp.add(langcb);

	JButton updatebtn = new JButton("Update");
	ActionListener updateaccevent = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {String c = (String)langcb.getSelectedItem();updateaccount(Unametf.getText(),Ph_notf.getText(),Emailtf.getText(),c);}};
	updatebtn.addActionListener(updateaccevent);
	JButton deletebtn = new JButton("delete");
	ActionListener deleteaccevent = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {deleteaccount();}};
	deletebtn.addActionListener(deleteaccevent);

	Containerpanel.add(unamelbl);
	Containerpanel.add(unametfp);
	Containerpanel.add(phnolbl);
	Containerpanel.add(phnotf);
	Containerpanel.add(emaillbl);
	Containerpanel.add(emailtfp);
	Containerpanel.add(langl);
	Containerpanel.add(langp);
	BottomPanel.add(updatebtn);
	BottomPanel.add(deletebtn);
	mainupdatepanel.add(Containerpanel);
	mainupdatepanel.add(Box.createRigidArea(new Dimension(0, 30)));
	mainupdatepanel.add(BottomPanel);

	p.add(mainupdatepanel);
 }

void updateaccount(String uname,String Phno,String emailid,String plang)
{
	Account up = new Account();
	if(up.checkuname(uname) && up.Ph_no_check_valid(Phno) && up.checkmail(emailid))
	{
		try
		{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
			PreparedStatement ps = null;
			ps = con.prepareStatement("update account set a_u_name = '" + uname + "' , ph_no = '" + Phno + "', email_id='" + emailid + "',a_p_lang = '" + plang + "' where a_id ="+ id +"");
			ps.executeUpdate();
			con.close();
			JOptionPane.showMessageDialog(null,"Account Updated");
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null,"Reached here");
			JOptionPane.showMessageDialog(null,ex);
		}
	}
}

void deleteaccount()
{
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
		PreparedStatement ps = null;
		ps = con.prepareStatement("delete from account where a_id ="+ id +"");
		ps.executeUpdate();
		ps = null;
		ps = con.prepareStatement("delete from answers where a_id ="+ id +"");
		ps.executeUpdate();
		ps = null;
		ps = con.prepareStatement("delete from following where a_id ="+ id +" or f_id ="+id+"");
		ps.executeUpdate();
		ps = null;
		ps = con.prepareStatement("delete from likedposts where a_id ="+ id +"");
		ps.executeUpdate();
		ps = null;
		ps = con.prepareStatement("delete from log where a_id ="+ id +"");
		ps.executeUpdate();
		ps = null;
		ps = con.prepareStatement("delete from post where a_id ="+ id +"");
		ps.executeUpdate();
		con.close();
		JOptionPane.showMessageDialog(null,"Account Deleted");
		new Login_page();
		frame.dispose();
	}
	catch(Exception ex)
	{
		JOptionPane.showMessageDialog(null,"Reached here delete account section");
		JOptionPane.showMessageDialog(null,ex);
	}
}

String checkfollowingtext(String holder)
{
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
		PreparedStatement ps = null;ps = con.prepareStatement("Select a_id from Account where a_u_name ='"+ holder+"' ");
		ResultSet rs = null;
		rs = ps.executeQuery();
		rs.next();
		int pid = rs.getInt(1);
		ps = null;
		rs = null;
		ps = con.prepareStatement("select * from Following where a_id ="+id+" and f_id="+pid+"");
		rs = ps.executeQuery();
		if(rs.next())
		{
			con.close();
			return "UnFollow";//Should be unfollow
		}
		else
		{
			con.close();
			return "Follow";//should be following
		}
	}
	catch(Exception ex)
	{
		JOptionPane.showMessageDialog(null,"Reached here");
		JOptionPane.showMessageDialog(null,ex);
	}
	return "dk";
}

void deletepost(String testpost)
{
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
		PreparedStatement ps = null;
		ps = con.prepareStatement("Select p_id from Post where p_post = '" + testpost + "' ");
		ResultSet rs = null;
		rs = ps.executeQuery();
		rs.next();
		int pid = rs.getInt(1);
		ps = null;
		ps = con.prepareStatement("delete from post where p_id ="+ pid +"");
		ps.executeUpdate();
		ps = null;
		ps = con.prepareStatement("delete from answers where p_id ="+ pid +"");
		ps.executeUpdate();
		ps = null;
		ps = con.prepareStatement("delete from likedposts where p_id ="+ pid +"");
		ps.executeUpdate();
		ps = null;
		JOptionPane.showMessageDialog(null,"Post Deleted");
		new MainFrame(id);
		frame.dispose();
	}
	catch(Exception ex)
	{
		JOptionPane.showMessageDialog(null,"Reached here delete account section");
		JOptionPane.showMessageDialog(null,ex);
	}
}

String getphno()
{
	String name="";
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
            		PreparedStatement ps = null;
           		ps = con.prepareStatement("Select ph_no from Account where a_id = " + id + " ");
            		ResultSet rsc = null;
            		rsc = ps.executeQuery();
            		rsc.next();
            		name = rsc.getString(1);
		con.close();
		return name; 
	}
	catch(Exception ex)
	{
		System.out.println(ex);
	}
	return name;
}

String getemailid()
{
	String name="";
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
            		PreparedStatement ps = null;
           		ps = con.prepareStatement("Select email_id from Account where a_id = " + id + " ");
            		ResultSet rsc = null;
            		rsc = ps.executeQuery();
            		rsc.next();
            		name = rsc.getString(1);
		con.close();
		return name; 
	}
	catch(Exception ex)
	{
		System.out.println(ex);
	}
	return name;
}


}