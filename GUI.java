import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.sql.*;
import java.sql.Connection;
import java.util.Properties;

class GUI2 extends JFrame {

	private JRadioButton res, par;
	private ButtonGroup group;
	private JLabel logo_name, delivery_type, date_time, table_no, name, address, contact,
			quantity_label;
	private ImageIcon logo;
	private JTextField table_no_tf, name_tf, address_tf, contact_tf;
	private JPanel input_panel, delivery_panel, quantity_panel, initial_panel,
			buttons_panel,pizza_panel,pizza2_panel;
	private JPanel top1_panel, top2_panel;
	private JCheckBox top_onion, top_mushroom, top_broccoli, top_corn, top_capsicum, top_cheese;
	String date;
	public float rate =0; // base rate is Rs.80

	private JLabel quantity_op_label, quantity_op, rate_label, rate_op_label;
	private JButton print, clear;
	private ImageIcon print_icon, clear_icon;
	private JLabel table_no_op, name_op, address_op, contact_op,pizza_label;
	String table_no_str, name_str, address_str, contact_str;
	private JLabel table_no2, name2, address2, contact2;
	private JLabel date_time2;
	private JLabel logo_name2;
	Connection conn;

	public GUI2() {
		super("Pizza Palace");
		setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
		logo = new ImageIcon("pizza.png");
		logo_name = new JLabel("Pizza Palace", logo, 0);
		initial_panel = new JPanel();
		initial_panel.setLayout(new GridLayout(2, 1));
		JPanel panel = new JPanel(new BorderLayout(5, 5));

		JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
		label.add(new JLabel("E-Mail", SwingConstants.RIGHT));
		label.add(new JLabel("Password", SwingConstants.RIGHT));
		panel.add(label, BorderLayout.WEST);

		JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
		JTextField username = new JTextField();
		controls.add(username);
		JPasswordField password = new JPasswordField();
		controls.add(password);
		panel.add(controls, BorderLayout.CENTER);

		int result = JOptionPane.showConfirmDialog(new JFrame(), panel, "login", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {

			try {

				String dbURL3 = "jdbc:postgresql://localhost:5432/java_project";
				Properties parameters = new Properties();
				parameters.put("user", "postgres");
				parameters.put("password", "abhinav99");

				conn = DriverManager.getConnection(dbURL3, parameters);
				if (conn != null) {
					System.out.println("Connected to database");
				}

				else {
					throw new SQLException("Couldn't connect to database");
				}

			}

			catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println(ex);
				System.exit(0);
			}

			if (loginVerify(username.getText(), password.getText()) == true) {
				System.out.println("User Logged in");
			}

			else {
				JOptionPane.showMessageDialog(null, "User doesnt exist. Try again");
				System.exit(0);
			}

		}

		// Logo Icon and Name
		logo = new ImageIcon("pizza.png");
		logo_name = new JLabel("Pizza Palace", logo, 0);
		initial_panel.add(logo_name);

		// Date and Time
		date = new SimpleDateFormat("EEEE yyyy-MM-dd   hh:mm:ss a zzz").format(new Date());
		date_time = new JLabel(date);
		initial_panel.add(date_time);

		add(initial_panel);
	}

	public boolean loginVerify(String user, String password) {
		int count = 0;
		try {
			String sql = "select count(*) from login_table where username_= ? and password_= ? ";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, user);
			stat.setString(2, password);
			ResultSet rs = stat.executeQuery();
			rs.next();
			count = rs.getInt(1);

		}

		catch (SQLException ex) {
			
			ex.printStackTrace();
		}

		if (count == 1)
			return true;
		return false;
	}

	public void Input() {
		input_panel = new JPanel();
		input_panel.setLayout(new GridLayout(5, 2, 5, 5));

		delivery_panel = new JPanel();
		delivery_panel.setLayout(new GridLayout(1, 3));

		// Delivery Type
		delivery_type = new JLabel("Delivery Type:");
		// delivery_panel.add(delivery_type);
		res = new JRadioButton("In-Restaurant");
		res.setActionCommand("res");
		par = new JRadioButton("Parcel");
		par.setActionCommand("par");

		group = new ButtonGroup();
		group.add(res);
		group.add(par);
		delivery_panel.add(res);
		delivery_panel.add(par);

		// add(delivery_panel);

		input_panel.add(delivery_type);
		input_panel.add(delivery_panel);

		// Table Number
		table_no = new JLabel("Table Number");
		table_no_tf = new JTextField("", 5);
		input_panel.add(table_no);
		input_panel.add(table_no_tf);

		// Parcel Details
		name = new JLabel("Name");
		address = new JLabel("Address");
		contact = new JLabel("Contact Number");
		name_tf = new JTextField("", 10);
		address_tf = new JTextField("", 20);
		contact_tf = new JTextField("", 10);
		input_panel.add(name);
		input_panel.add(name_tf);
		input_panel.add(address);
		input_panel.add(address_tf);
		input_panel.add(contact);
		input_panel.add(contact_tf);

		add(input_panel);

		// Disable Extra Labels & TextFields depending on RadioButton Selected
		res.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (res.isSelected()) {
					name.setEnabled(false);
					address.setEnabled(false);
					contact.setEnabled(false);
					name_tf.setEditable(false);
					address_tf.setEditable(false);
					contact_tf.setEditable(false);
					name_tf.setEnabled(false);
					address_tf.setEnabled(false);
					contact_tf.setEnabled(false);
					table_no.setEnabled(true);
					table_no_tf.setEnabled(true);
					table_no_tf.setEditable(true);
				}
			}
		});

		par.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (par.isSelected()) {
					table_no.setEnabled(false);
					table_no_tf.setEnabled(false);
					table_no_tf.setEditable(false);
					name.setEnabled(true);
					address.setEnabled(true);
					contact.setEnabled(true);
					name_tf.setEditable(true);
					address_tf.setEditable(true);
					contact_tf.setEditable(true);
					name_tf.setEnabled(true);
					address_tf.setEnabled(true);
					contact_tf.setEnabled(true);
				}
			}
		});

	}

	public void Pizzas() {
		top1_panel = new JPanel();
		top1_panel.setLayout(new GridLayout(1, 3));

		top2_panel = new JPanel();
		top2_panel.setLayout(new GridLayout(1, 3));

		pizza_panel = new JPanel();
		pizza_panel.setLayout(new GridLayout(2, 1));

		pizza2_panel = new JPanel();
		pizza2_panel.setLayout(new GridLayout(2, 1));

		pizza_label = new JLabel("Pizza:");

		int onion_rate = 230;
		int mushroom_rate = 240;
		int brocolli_rate = 260;
		int corn_rate = 270;
		int capsicum_rate = 280;
		int cheese_rate = 290;

		top_onion = new JCheckBox("Onion pizza : Rs. " + Integer.toString(onion_rate), false);
		top_mushroom = new JCheckBox("Mushroom pizza : Rs. " + Integer.toString(mushroom_rate), false);
		top_broccoli = new JCheckBox("Brocolli pizza : Rs. " + Integer.toString(brocolli_rate), false);
		top_corn = new JCheckBox("Corn pizza : Rs. " + Integer.toString(corn_rate), false);
		top_capsicum = new JCheckBox("Capsicum pizza : Rs. " + Integer.toString(capsicum_rate), false);
		top_cheese = new JCheckBox("Cheese pizza : Rs. " + Integer.toString(cheese_rate), false);

		ItemListener itemListener1 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					rate = rate + onion_rate;
				}
			}
		};

		ItemListener itemListener2 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					rate = rate + mushroom_rate;
				}
			}
		};

		ItemListener itemListener3 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					rate = rate + brocolli_rate;
				}
			}
		};

		ItemListener itemListener4 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					rate = rate + corn_rate;
				}
			}
		};

		ItemListener itemListener5 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					rate = rate + capsicum_rate;
				}
			}
		};

		ItemListener itemListener6 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					rate = rate + cheese_rate;
				}
			}
		};

		top_onion.addItemListener(itemListener1);
		top_mushroom.addItemListener(itemListener2);
		top_broccoli.addItemListener(itemListener3);
		top_corn.addItemListener(itemListener4);
		top_capsicum.addItemListener(itemListener5);
		top_cheese.addItemListener(itemListener6);

		top1_panel.add(top_onion);
		top1_panel.add(top_mushroom);
		top1_panel.add(top_broccoli);
		top2_panel.add(top_corn);
		top2_panel.add(top_capsicum);
		top2_panel.add(top_cheese);

		pizza_panel.add(top1_panel);
		pizza_panel.add(top2_panel);

		pizza2_panel.add(pizza_label);
		pizza2_panel.add(pizza_panel);
		add(pizza2_panel);

	}

	public void Quantity() {
		quantity_label = new JLabel("Quantity: ");
		quantity_op_label = new JLabel("Quantity: ");
		quantity_op = new JLabel();
		quantity_panel = new JPanel();
		quantity_panel.setLayout(new GridLayout(2, 2));
		String[] quantity = { "1", "2", "3", "4", "5" };

		JComboBox<String> quantity_box = new JComboBox<String>();
		for (int i = 0; i < quantity.length; i++)
			quantity_box.addItem(quantity[i]);

		quantity_box.setSelectedIndex(0);
		quantity_op.setText("1");

		quantity_panel.add(quantity_label);
		quantity_panel.add(quantity_box);

		quantity_box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quantity_op.setText((String) ((JComboBox) e.getSource()).getSelectedItem());
			}
		});

		add(quantity_panel);
	}

	public void Output() {
		print_icon = new ImageIcon("print.png");
		clear_icon = new ImageIcon("clear.png");

		buttons_panel = new JPanel();
		buttons_panel.setLayout(new GridLayout(1, 2, 50, 10));

		table_no2 = new JLabel("Table No.: ");
		name2 = new JLabel("Name: ");
		address2 = new JLabel("Address: ");
		contact2 = new JLabel("Contact Number: ");

		String rate_str;
		rate_str = Float.toString(rate);
		rate_label = new JLabel("Rate: ");
		rate_op_label = new JLabel(rate_str);

		print = new JButton("Print");
		print.setRolloverIcon(print_icon);
		print.setRolloverEnabled(true);
		clear = new JButton("Clear");

		buttons_panel.add(print);
		buttons_panel.add(clear);

		add(buttons_panel);

		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table_no_tf.setText("");
				name_tf.setText("");
				address_tf.setText("");
				contact_tf.setText("");
				group.clearSelection();
				name.setEnabled(true);
				address.setEnabled(true);
				contact.setEnabled(true);

				name_tf.setEditable(true);
				address_tf.setEditable(true);
				contact_tf.setEditable(true);
				name_tf.setEnabled(true);
				address_tf.setEnabled(true);
				contact_tf.setEnabled(true);
				table_no.setEnabled(true);
				table_no_tf.setEnabled(true);
				table_no_tf.setEditable(true);

				top_broccoli.setSelected(false);
				top_capsicum.setSelected(false);
				top_cheese.setSelected(false);
				top_corn.setSelected(false);
				top_mushroom.setSelected(false);
				top_onion.setSelected(false);

			}
		});

		print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rate_str;
				int quantity = Integer.parseInt(quantity_op.getText());
				rate_str = Float.toString(rate * quantity);
				rate_label = new JLabel("Rate: ");
				rate_op_label = new JLabel(rate_str);

				JFrame output = new JFrame();
				output.setLayout(new GridLayout(8, 2));

				logo_name2 = new JLabel("Pizza Palace", logo, 0);
				output.add(logo_name2);
				date = new SimpleDateFormat("E yyyy-MM-dd HH:mm:ss").format(new Date());
				date_time2 = new JLabel(date);
				output.add(date_time2);

				table_no_str = table_no_tf.getText();
				name_str = name_tf.getText();
				address_str = address_tf.getText();
				contact_str = contact_tf.getText();

				table_no_op = new JLabel(table_no_str);
				name_op = new JLabel(name_str);
				address_op = new JLabel(address_str);
				contact_op = new JLabel(contact_str);

				output.add(table_no2);
				output.add(table_no_op);
				output.add(name2);
				output.add(name_op);
				output.add(address2);
				output.add(address_op);
				output.add(contact2);
				output.add(contact_op);

				output.add(rate_label);
				output.add(rate_op_label);
				output.add(quantity_op_label);
				output.add(quantity_op);
				output.pack();
				output.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				output.setLocationRelativeTo(null);
				// output.setSize(100, 100);
				output.setVisible(true);

				JOptionPane.showMessageDialog(null, "Your Order Has Been Placed!");
			}
		});

	}

}

class GUI {
	public static void main(String[] args) {
		GUI2 gui_obj = new GUI2();
		gui_obj.Input();
		gui_obj.Pizzas();
		gui_obj.Quantity();
		gui_obj.Output();

		gui_obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui_obj.setLocationByPlatform(true);
		gui_obj.setSize(600, 600);
		gui_obj.setVisible(true);

	}
}