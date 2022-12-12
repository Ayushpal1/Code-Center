package Project;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.table.*;
//import Project.MainFrame;
import java.util.*;

public class ReportFrame
{
	JFrame frame;
	int id;
	private JTable tabSales;
	private DefaultTableModel dtm;
	private JPanel panNorth;

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;

	public ReportFrame(int i)
	{
		id = i;
		frame = new JFrame("Report of Accounts");
		panNorth = new JPanel();
		JButton back = new JButton("Back");
		back.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				new MainFrame(id);
				frame.dispose();
			}
		});
		panNorth.add(back);

		dtm = new DefaultTableModel();
		tabSales = new JTable(dtm);
		dtm.addColumn("Account ID");
		dtm.addColumn("Name");
		dtm.addColumn("Gender");
		dtm.addColumn("Preferred Language");
		dtm.addColumn("Country");
		dtm.addColumn("Account Created on");
		dtm.addColumn("Following");
		dtm.addColumn("Followers");
		dtm.addColumn("Total Posts");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.add(panNorth,"North");
		frame.add(new JScrollPane(tabSales),"Center");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try
		{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
			ps = con.prepareStatement("select a.a_id,a_name,a_u_name,gender,a_p_lang,/*ph_no,email_id,*/country,day from account a,log l where l.a_id = a.a_id order by a.a_id");
			rs = ps.executeQuery();
			while(rs.next())
			{
				Vector row = new Vector();
				int aid = rs.getInt(1);
                                                                        row.add(aid);
                                                                        row.add(rs.getString(2));
				String uname = rs.getString(3);
                                                                        row.add(rs.getString(4));
				row.add(rs.getString(5));
				row.add(rs.getString(6));
				row.add(rs.getString(7));
				row.add(getFollowing(uname));
				row.add(getFollowers(uname));
				row.add(getpostcount(uname));

				dtm.addRow(row);
			}
			con.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,e);
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
}

