package Project;

import java.lang.*;
import Project.Account;
import Project.testframesignin;
import java.awt.*;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

public class CreateAccount
{
  JFrame frame = new JFrame();
 public CreateAccount()
 {
	createframe();
	addtoframe();
  }

  public void addtoframe()
  {
    JLabel G1 = new JLabel();
    JLabel G2 = new JLabel();
    JLabel G3 = new JLabel();
    G1.setPreferredSize(new Dimension(50,600));
    G2.setPreferredSize(new Dimension(50,600));
    G3.setPreferredSize(new Dimension(600,50));
    frame.add(G1,BorderLayout.WEST);
    frame.add(G2,BorderLayout.EAST);
    frame.add(G3,BorderLayout.SOUTH);

    JLabel l = new JLabel("Create Account",SwingConstants.CENTER);
    l.setVerticalAlignment(JLabel.CENTER);
    l.setPreferredSize(new Dimension(600,60));
    frame.add(l,BorderLayout.NORTH);

    //CREATE ACCOUNT SECTION
    JTextField Nametf = new JTextField(20);
    JLabel Namel = new JLabel("Name :",SwingConstants.RIGHT);
    JPanel name = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel namelbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    namelbl.add(Namel);
    name.add(Nametf);

    JLabel Unamel = new JLabel("User Name :",SwingConstants.RIGHT);
    JTextField Unametf = new JTextField(20);
    JPanel uname = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel unamelbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    uname.add(Unametf);
    unamelbl.add(Unamel);

	JComboBox monthcb, c2,yearcb;
		String month[] = { "January", "February", "March", "April", "May","June","July","August","September","October","November","December"};
		yearcb = new JComboBox();
		for(int i=2010;i>1950;i--)
		{
			yearcb.addItem(i);
		}
		monthcb = new JComboBox(month); 
		monthcb.setSelectedIndex(0);
		c2 = new JComboBox();
		for(int i=1;i<32;i++)
		{
			c2.addItem(i);
		}
		ItemListener il = new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				c2.removeAllItems();
				c2.setEnabled(true);
				if (monthcb.getSelectedIndex()==3||monthcb.getSelectedIndex()==5||monthcb.getSelectedIndex()==8||monthcb.getSelectedIndex()==10)
				{
					for(int i=1;i<31;i++)
					{
						c2.addItem(i);
					}
				}
				else
				{
					if(monthcb.getSelectedIndex()==1)
					{
						int leapyear = (int)yearcb.getSelectedItem();
						if(leapyear%4 == 0)
						{
							for(int i=1;i<30;i++)
							{
								c2.addItem(i);
							}
						}
						else
						{
							for(int i=1;i<29;i++)
							{
								c2.addItem(i);
							}
						}
					}
					else
					{
						for(int i=1;i<32;i++)
						{
							c2.addItem(i);
						}
					}
				}
			}
		}; 
		monthcb.addItemListener(il);
		yearcb.addItemListener(il);
    JLabel dobl = new JLabel("Select Date of Birth :",SwingConstants.RIGHT);
    JPanel age = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel agelbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
   agelbl.add(dobl);
    age.add(yearcb);
    age.add(monthcb);
    age.add(c2);

    JLabel Genderl = new JLabel("Gender :");
    String sex[] = {"Male","Female"};
    JComboBox cb = new JComboBox(sex);
    JPanel gender = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel genderlbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    genderlbl.add(Genderl);
    gender.add(cb);

    JPasswordField Passwordpf = new JPasswordField(20);
    JLabel Passwordl = new JLabel("Password :");
	Passwordpf.setEchoChar('\u25CF');
	JCheckBox cb1 = new JCheckBox("Show Password");
	cb1.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){if(cb1.isSelected()){Passwordpf.setEchoChar((char)0);}else{Passwordpf.setEchoChar('\u25CF');}}});
    JPanel password = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel passwordlbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    password.add(Passwordpf);
    password.add(cb1);
    passwordlbl.add(Passwordl);

    JPasswordField Cpasswordpf = new JPasswordField(20);
    JLabel Cpasswordl = new JLabel("Confirm Password :");
	Cpasswordpf.setEchoChar('\u25CF');
	JCheckBox cb2 = new JCheckBox("Show Password");
	cb2.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){if(cb2.isSelected()){Cpasswordpf.setEchoChar((char)0);}else{Cpasswordpf.setEchoChar('\u25CF');}}});
    JPanel cpassword = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel cpasswordlbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    cpassword.add(Cpasswordpf);
    cpassword.add(cb2);
    cpasswordlbl.add(Cpasswordl);

    JTextField Ph_notf = new JTextField(20);
    JLabel Ph_nol = new JLabel("Phone number :");
    JPanel phno = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel phnolbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    phnolbl.add(Ph_nol);
    phno.add(Ph_notf);

    JTextField Emailtf = new JTextField(20);
    JLabel Emaill = new JLabel("Enter Email :");
    JPanel epane = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel epanelbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    epanelbl.add(Emaill);
    epane.add(Emailtf);

    String country[] = {"Afghanistan","Albania","Algeria","Andorra","Angola","Antigua and Barbuda","Argentina","Armenia","Australia","Austria","Azerbaijan","The Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bhutan","Bolivia","Bosnia and Herzegovina","Botswana","Brazil","Brunei","Bulgaria","Burkina Faso","Burundi","Cabo Verde","Cambodia","Cameroon","Canada","Central African Republic","Chad","Chile","China","Colombia","Comoros","Congo, Democratic Republic of the","Congo, Republic of the","Costa Rica","Côte d’Ivoire","Croatia","Cuba","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","East Timor (Timor-Leste)","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Eswatini","Ethiopia","Fiji","Finland","France","Gabon","The Gambia","Georgia","Germany","Ghana","Greece","Grenada","Guatemala","Guinea","Guinea-Bissau","Guyana","Haiti","Honduras","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Israel","Italy","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Kiribati","Korea, North","Korea, South","Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Mauritania","Mauritius","Mexico","Micronesia, Federated States of","Moldova","Monaco","Mongolia","Montenegro","Morocco","Mozambique","Myanmar (Burma)","Namibia","Nauru","Nepal","Netherlands","New Zealand","Nicaragua","Niger","Nigeria","North Macedonia","Norway","Oman","Pakistan","Palau","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Qatar","Romania","Russia","Rwanda","Saint Kitts and Nevis","Saint Lucia","Saint Vincent and the Grenadines","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","Spain","Sri Lanka","Sudan","Sudan, South","Suriname","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Togo","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States","Uruguay","Uzbekistan","Vanuatu","Vatican City","Venezuela","Vietnam","Yemen","Zambia","Zimbabwe"};
    JComboBox Countrycb = new JComboBox(country);
    Countrycb.setSelectedIndex(77);
    JLabel Countryl = new JLabel("Country :");
    JPanel cc = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel cclbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    cclbl.add(Countryl);
    cc.add(Countrycb);

    String lang[] = {"C","C#","C++","Java","Html","Javascript"};
    JComboBox langcb = new JComboBox(lang);
    JLabel langlbl = new JLabel("Select Preferred Language : ");
    JPanel language = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel languagelbl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    languagelbl.add(langlbl);
    language.add(langcb);

    JButton Createaccountbtn = new JButton("Create Account");
    ActionListener b1Event = new ActionListener() {@Override public void actionPerformed(ActionEvent e)
	{
		String dob;
		String s = (String)cb.getSelectedItem();
		String prg = (String)langcb.getSelectedItem();
		String c = (String)Countrycb.getSelectedItem();
		int y = (Integer)yearcb.getSelectedItem();
		dob = Integer.toString(y);
		int m = monthcb.getSelectedIndex();
		m++;
		if(m>9)
		{
			dob = dob + Integer.toString(m);
		}
		else
		{
			dob = dob + "0" + Integer.toString(m);
		}
		int d = (Integer)c2.getSelectedItem();
		if(d>9)
		{
			dob = dob + Integer.toString(d);
		}
		else
		{
			dob = dob +"0"+Integer.toString(d);
		}
		//JOptionPane.showMessageDialog(null,dob);
		new Account(Nametf.getText(),Unametf.getText(),dob,Passwordpf.getText(),Cpasswordpf.getText(),s,Ph_notf.getText(),Emailtf.getText(),c,prg);}
	};
    Createaccountbtn.addActionListener(b1Event);

	JPanel createaccount = new JPanel(new FlowLayout());
	createaccount.add(Createaccountbtn);

	JLabel Signinl = new JLabel("Already have an account");
	JButton Signinbtn = new JButton("Sign IN");
	ActionListener b2Event = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {closeframe();}};
	Signinbtn.addActionListener(b2Event);
	JPanel signin = new JPanel();
	signin.add(Signinl);
	signin.add(Signinbtn);

	JPanel p = new JPanel();
	p.setLayout(new GridLayout(10,2,0,0));
	p.add(namelbl);
	p.add(name);
	p.add(unamelbl);
	p.add(uname);
	p.add(agelbl);
	p.add(age);
	p.add(genderlbl);
	p.add(gender);
	p.add(passwordlbl);
	p.add(password);
	p.add(cpasswordlbl);
	p.add(cpassword);
	p.add(phnolbl);
	p.add(phno);
	p.add(epanelbl);
	p.add(epane);
	p.add(cclbl);
	p.add(cc);
	p.add(languagelbl);
	p.add(language);

	JPanel pane = new JPanel();
	pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
	pane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	pane.add(p);
	pane.add(Box.createRigidArea(new Dimension(0, 30)));
	pane.add(createaccount);
	pane.add(signin);
	pane.add(Box.createRigidArea(new Dimension(0, 30)));

	frame.add(pane,BorderLayout.CENTER);
	frame.setVisible(true);
 }
 public void createframe()
 {
    frame.setTitle("Create Account");
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setSize(500,700);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
 public void closeframe()
 {
	frame.dispose();
	new testframesignin();
 }
}