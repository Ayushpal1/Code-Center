package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import Project.testframesignin;
//import Project.CreateAccount;

public class Login_page
{

public Login_page()
{
	JFrame frame = new JFrame("Code Center");
	frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(500,700);

	JLabel l = new JLabel();
	l.setPreferredSize(new Dimension(564,543));
	l.setIcon(new ImageIcon(new ImageIcon("cc.jpeg").getImage().getScaledInstance(564, 543, Image.SCALE_DEFAULT)));
	l.setHorizontalAlignment(JLabel.CENTER);
	l.setOpaque(true);
	l.setBackground(Color.BLACK);

	JPanel empl = new JPanel();
	empl.setPreferredSize(new Dimension(10000,50));
	empl.setBackground(Color.BLACK);

	JButton sign = new JButton("      Sign IN      ");
	ActionListener b1Event = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {new testframesignin();frame.dispose();}};
	sign.addActionListener(b1Event);

	JButton create = new JButton("Create Account");
	ActionListener b2Event = new ActionListener() {@Override public void actionPerformed(ActionEvent e) {new CreateAccount();frame.dispose();}};
	create.addActionListener(b2Event);

	JPanel panel = new JPanel();
	panel.setBackground(Color.BLACK);
	panel.add(sign);
	panel.add(create);

	frame.setBackground(Color.RED);
	frame.add(l,BorderLayout.NORTH);
	frame.add(panel,BorderLayout.CENTER);
	frame.add(empl,BorderLayout.SOUTH);
	frame.setVisible(true);
}

}