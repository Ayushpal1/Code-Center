package Project;

import java.lang.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.lang.Character.*;

public class Account
{
	private int a_id;
	private String a_name,a_u_name,gender,a_p_lang,password,emailid,ph_no,country,age,confirmpassword;
	private boolean Pass = false;
	private boolean Name = false;
	private boolean id = false;
	private boolean phno = false;
	private boolean u_name = false;

	Account()
	{
		a_id = 0;
		a_name = "";
		a_u_name = "";
		gender = "";
		a_p_lang = "";
		password ="";
		emailid = "";
		ph_no = "";
		country = "";
		age = "";
	}

	public Account(String n,String u, String a,String p,String cp,String sex,String ph,String e,String c,String prg)
	{
		checkid();
		a_name = n;
		a_u_name = u;
		password = p;
		confirmpassword = cp;
		gender = sex;
		ph_no = ph;
		emailid = e;
		country = c;
		a_p_lang = prg;
		age = a;
		if(a_name.length() == 0 || a_u_name.length() == 0 ||password.length() == 0 || ph_no.length() == 0 || emailid.length() == 0 || country.length() == 0)
		{
			JOptionPane.showMessageDialog(null,"All Fields are Compulsory");
		}
		else
		{
			if(password.equals(confirmpassword))
			{
				Name = checkname(a_name);
				Pass = checkpass(password);
				phno = Ph_no_check_valid(ph_no);
				u_name = check_u_name(a_u_name);
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Passwords do not match");
			}
		}
		if(Pass && Name && phno && u_name)
		{
			if(checkmail())
			{
				try
				{
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
					PreparedStatement ps = null;
					ps = con.prepareStatement("insert into Account values("+ a_id +",'"+ a_name +"','"+ a_u_name +"','"+ gender+"','"+ prg +"','" + password +"',"+ ph_no +",'"+ emailid +"','"+ country +"',"+age+")");
					ps.executeUpdate();
					ps = con.prepareStatement("insert into log values("+ a_id +",'"+java.time.LocalDate.now()+"')");
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null,"Account Created");
					con.close();
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null,"Phone Number or Email id Already Registered");
					//JOptionPane.showMessageDialog(null,ex);
				}
			}
		}
	}

	public boolean checkpass(String s)
	{
		String name = new String(s);
		char ch;
		int l = s.length();
		boolean lFlag = false;
		boolean uCaseFlag = false;
		boolean sCaseFlag = false;
		boolean digitFlag = false;
		boolean specialFlag = false;
		if(7< l && l<21)
		{
			lFlag = true;
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Password length should be more than 8 and less than 21");
			return false;
		}
		for(int i=0;i<l;i++)
		{
			ch = s.charAt(i);
			if( Character.isDigit(ch))
			{
				digitFlag = true;
			}
			else if(Character.isUpperCase(ch))
			{
				uCaseFlag = true;
			}
			else if(Character.isLowerCase(ch))
			{
				sCaseFlag = true;
			}
			else
			{
				specialFlag = true;
			}
		}
		if(lFlag && uCaseFlag && sCaseFlag && digitFlag && specialFlag)
		{
			return true;
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Password should contain small, capital letter, a digit and a special symbol");
			return false;
		}
	}

	boolean checkname(String s)
	{
		String name = new String(s);
		char ch;
		int l = s.length();
		if(!(2< l && l<21))
		{
			JOptionPane.showMessageDialog(null,"Length of name is big or too small");
			return false;
		}
		for(int i=0;i<l;i++)
		{
			ch = s.charAt(i);
			if(!(Character.isLetter(ch)))
			{
				if(Character.isSpace(ch))
				{
					continue;
				}
				if(Character.isDigit(ch))
				{
					JOptionPane.showMessageDialog(null,"Name Cannot contain Digits");
					return false;
				}
				JOptionPane.showMessageDialog(null,"Name cannot contain Special Character");
				return false;
			}
		}
		return true;
	}

	boolean checkmail()
	{
		boolean attherate = false;
		String mail = new String(emailid);
		Character ch;
		if(mail.matches("(.*).com")||mail.matches("(.*).net")||mail.matches("(.*).org"))
		{
			for(int i=0;i<mail.length();i++)
			{
				ch = mail.charAt(i);
				if(ch == '@')
				{
					return true;
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Please enter a Valid Email ID");
			return false;
		}
		return false;
	}

	void checkid()
	{
		try
		{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
			PreparedStatement ps = null;
			ps = con.prepareStatement("Select count(*),max(a_id) from Account");
			ResultSet rs = null;
			rs = ps.executeQuery();
			rs.next();
			int total = rs.getInt(1);
			int max = rs.getInt(2);
			if(total == max)
			{
				a_id = total+1;
				id = true;
			}
			else
			{
				ps = null;
				ps = con.prepareStatement("Select a_id from Account");
				rs = null;
				rs = ps.executeQuery();
				for(int i=1;i<max;i++)
				{
					rs.next();
					int c_a_id =rs.getInt(1);
					if(i!=c_a_id)
					{
						a_id = i;
						id = true;
						break;
					}
				}
			}
			con.close();
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null,"An id issue Occured");
			JOptionPane.showMessageDialog(null,ex);
		}
	}

	public boolean Ph_no_check_valid(String s)
	{
		Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		Matcher m = p.matcher(s); 
		if(m.find() && m.group().equals(s))
		{
			return true;
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Please enter valid Phone no.");
			return false;
		}
	}

	public boolean check_u_name(String tname)
	{
		try
		{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
			PreparedStatement ps = null;
			ps = con.prepareStatement("Select a_u_name from Account where a_u_name='"+tname+"'");
			ResultSet rs = null;
			rs = ps.executeQuery();
			//rs.next();
			if(rs.next())
			{
				JOptionPane.showMessageDialog(null,"Username not available");
				con.close();
				return false;
			}
			else
			{
				//JOptionPane.showMessageDialog(null,"Username Available");
				char ch;
				for(int i=0;i<tname.length();i++)
				{
					ch = tname.charAt(i);
					if(!(Character.isLetter(ch)) && !(Character.isDigit(ch)) || Character.isSpace(ch))
					{
						JOptionPane.showMessageDialog(null,"Spaces and Special Symbols are not allowed In Username");
						return false;
					}
				}
				con.close();
				return true;
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null,"An User name Error Occured");
			JOptionPane.showMessageDialog(null,ex);
		}
		return false;
	}

	public boolean checkmail(String s)
	{
		boolean attherate = false;
		String mail = new String(s);
		Character ch;
		if(mail.matches("(.*).com")||mail.matches("(.*).net")||mail.matches("(.*).org"))
		{
			for(int i=0;i<mail.length();i++)
			{
				ch = mail.charAt(i);
				if(ch == '@')
				{
					return true;
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Please enter a Valid Email ID");
			return false;
		}
		return false;
	}
	public boolean checkuname(String tname)
	{
		try
		{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/base","root","");
			PreparedStatement ps = null;
			ps = con.prepareStatement("Select a_u_name from Account where a_u_name='"+tname+"'");
			ResultSet rs = null;
			rs = ps.executeQuery();
			//rs.next();
			if(rs.next())
			{
				//JOptionPane.showMessageDialog(null,"Username not available");
				con.close();
				return true;
			}
			else
			{
				//JOptionPane.showMessageDialog(null,"Username Available");
				char ch;
				for(int i=0;i<tname.length();i++)
				{
					ch = tname.charAt(i);
					if(!(Character.isLetter(ch)) && !(Character.isDigit(ch)) || Character.isSpace(ch))
					{
						JOptionPane.showMessageDialog(null,"Spaces and Special Symbols are not allowed In Username");
						return false;
					}
				}
				con.close();
				return true;
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null,"An User name Error Occured");
			JOptionPane.showMessageDialog(null,ex);
		}
		return false;
	}
}