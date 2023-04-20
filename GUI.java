import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import java.util.*;

public class GUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome to Agency Center");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(80, 11, 263, 28);
		contentPane.add(lblNewLabel);

		JButton btnNewButton_1 = new JButton("ABORT");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(254, 178, 89, 23);
		contentPane.add(btnNewButton_1);

		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1.0, 0.0, null, 0.1));
		spinner.setBounds(299, 50, 44, 20);
		contentPane.add(spinner);

		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(0, 0, 8, 1));
		spinner_1.setBounds(299, 94, 44, 20);
		contentPane.add(spinner_1);

		JSpinner spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(new Integer(10), null, null, new Integer(1)));
		spinner_2.setBounds(299, 135, 44, 20);
		contentPane.add(spinner_2);

		JLabel lblNewLabel_1 = new JLabel("Operators Working Time:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(10, 50, 243, 20);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Extra number of Executives:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1_1.setBounds(10, 97, 243, 20);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Number of Operations for the Day:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1_2.setBounds(10, 138, 263, 20);
		contentPane.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_2_1 = new JLabel("Extra number of Executives cannot be more than 8!");
		lblNewLabel_1_2_1.setForeground(Color.RED);
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_2_1.setBounds(0, 219, 434, 20);
		contentPane.add(lblNewLabel_1_2_1);

		JButton btnNewButton = new JButton("START");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Agency agency = new Agency();
				Queue<Call> callQ = new Queue<Call>();
				Queue<Task> taskQ = new Queue<Task>();
				BoundedQueue<Operation> bQ = new BoundedQueue<Operation>();
				AnsweringMachine am = new AnsweringMachine("D:\\Eclipse\\Projects\\AgencyCenter\\src\\example.txt",
						callQ);
				int numOfOperations = ((int) (spinner_2.getValue()));
				AgencyManager boss = new AgencyManager(numOfOperations);
				Database db = new Database();
				InformationSystem is = new InformationSystem(db);
				boss.start();
				am.start();
				for (int i = 0; i < 5; i++) {
					Secretary s = new Secretary(callQ, am, taskQ);
					s.start();
				}
				for (int i = 0; i < 3; i++) {
					TaskManager tm = new TaskManager(taskQ, boss, is);
					tm.start();
				}

				for (int i = 0; i < 3; i++) {
					Operator o = new Operator(is, agency, boss, bQ, ((double) (spinner.getValue())));
					o.start();
				}
				int guiExecutives = ((int) (spinner_1.getValue()));
				for (int i = 0; i < 5 + guiExecutives; i++) {
					Executive exec = new Executive(bQ, boss, agency);
					exec.start();

				}

			}
		});
		btnNewButton.setBounds(80, 178, 89, 23);
		contentPane.add(btnNewButton);
	}
}
