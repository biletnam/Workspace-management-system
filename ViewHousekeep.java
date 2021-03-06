import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;

public class ViewHousekeep extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					  ViewHousekeep frame = new ViewHousekeep();
					frame.setVisible(true);
					/*JButton b= new JButton("LOGOUT");
			        b.addActionListener(new ActionListener() {
			          	public void actionPerformed(ActionEvent e)
			          	{
			          		Registered.main(new String[]{});
			          		frame.dispose();
			          	}
			          });
			        b.setBounds(150,100,120,30);
			        frame.add(b, BorderLayout.CENTER);*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public  ViewHousekeep() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBackground(Color.WHITE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		String data[][]=null;
		String column[]=null;
		boolean flag=false;
		try{
			
			Connection con;
			con=DB.getConnection();
			PreparedStatement ps=con.prepareStatement("select * from Housekeep",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs=ps.executeQuery();
			
			ResultSetMetaData rsmd=rs.getMetaData();
			int cols=rsmd.getColumnCount();
			column=new String[cols];
			for(int i=1;i<=cols;i++){
				column[i-1]=rsmd.getColumnName(i);
			}
			
			rs.last();
			int rows=rs.getRow();
			rs.beforeFirst();

			data=new String[rows][cols];
			int count=0;
			while(rs.next()){
				for(int i=1;i<=cols;i++){
					data[count][i-1]=rs.getString(i);
				}
				count++;
			}
			con.close();
		}catch(Exception e){System.out.println(e);}
		
		table = new JTable(data,column){
	    	 
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
	            if (flag) {
	                return true;
	            }
	            return false;
	        }
	    };
	    
		JScrollPane sp=new JScrollPane(table);
		
		contentPane.add(sp, BorderLayout.CENTER);
		Inspire.main(new String[]{});
	}

}