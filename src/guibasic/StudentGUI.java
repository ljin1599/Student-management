package guibasic;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


public class StudentGUI extends JFrame implements FocusListener, ActionListener{
   private JPanel west_P, input_P, south_P;
   DelDialog de = new DelDialog(this, "����");
   UpdateDialog ud = new UpdateDialog(this, "����");
   IdDialog se = new IdDialog(this, "�˻�");
      
   private JMenuBar jb;
   private JMenu filemenu;
   private JMenuItem saveitem, openitem;

   private JFileChooser fc = new JFileChooser();
  

   
      private JLabel id_L, name_L, score_L;
      private JTextField id_T, name_T, score_T;
      
      private JButton regist_B, view_B, exit_B, delete_B, update_B, n_order_B, s_order_B, save_B, open_B, search_B;
      private JTextArea output_T;
      
      private StudentDAO dao;
      private ArrayList<Student> al;
      
      StudentGUI() {
         this.setTitle("�л��������α׷�");
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.formDesign();
         this.eventHandler();
         this.setSize(650, 230);
         this.setVisible(true);
         
         dao = new StudentDAO();
         al = new ArrayList<Student>();
      }
      public void formDesign() {
         west_P = new JPanel();
         input_P = new JPanel();
         south_P = new JPanel();
         
         jb = new JMenuBar();
         
         filemenu = new JMenu("����");
         
         saveitem = new JMenuItem("�����ϱ�");
         openitem = new JMenuItem("�ҷ�����");	
         
         fc = new JFileChooser();

         this.setJMenuBar(jb);
         jb.add(filemenu);
         filemenu.add(saveitem);
         filemenu.add(openitem);
         
         id_L = new JLabel("ID : ");
         name_L = new JLabel("�̸� : ");
         score_L = new JLabel("���� : ");
         
         id_T = new JTextField();
         name_T = new JTextField();
         score_T = new JTextField();
         
         regist_B = new JButton("���");
         view_B = new JButton("����");
         exit_B = new JButton("����");
         delete_B = new JButton("����");
         update_B = new JButton("����");
         n_order_B = new JButton("�̸���");
         s_order_B = new JButton("������");
         save_B = new JButton("����");
         open_B = new JButton("�ҷ�����");
         search_B = new JButton("�˻�");
         
         output_T = new JTextArea("��ϵ� �л� ����");
         
         this.add(west_P, BorderLayout.WEST);
         west_P.setLayout(new GridLayout(2, 1));
         west_P.add(input_P);
         input_P.setLayout(new GridLayout(3,2));
         input_P.add(id_L);
         input_P.add(id_T);
         input_P.add(name_L);
         input_P.add(id_T);
         input_P.add(name_L);
         input_P.add(name_T);
         input_P.add(score_L);
         input_P.add(score_T);
         
         this.add(south_P, BorderLayout.SOUTH);
         south_P.add(regist_B);
         south_P.add(view_B);
         south_P.add(update_B);
         south_P.add(delete_B);
         south_P.add(exit_B);
         south_P.add(n_order_B);
         south_P.add(s_order_B);
         south_P.add(search_B);
         
         this.add(output_T, BorderLayout.CENTER);
         
         name_T.setEditable(false);
         score_T.setEditable(false);
         
         regist_B.setEnabled(false);
         //view_B.setEnabled(true);
         
      }
      public void eventHandler() {
         id_T.addFocusListener(this);
         name_T.addFocusListener(this);
         score_T.addFocusListener(this);
         exit_B.addActionListener(this);
         regist_B.addActionListener(this);
         view_B.addActionListener(this);
         delete_B.addActionListener(this);
         update_B.addActionListener(this);
         n_order_B.addActionListener(this);
         s_order_B.addActionListener(this);
         save_B.addActionListener(this);
         open_B.addActionListener(this);
         search_B.addActionListener(this);
         saveitem.addActionListener(this);
         openitem.addActionListener(this);

      }


   public static void main(String[] args) {
      new StudentGUI();
   }
   @Override
   public void focusGained(FocusEvent e) {
      if(e.getSource().equals(id_T)) {
         output_T.setText("ID�� �Է����ּ���.\n");
      }else if(e.getSource().equals(name_T)) {
         output_T.setText("�̸��� �Է����ּ���.\n");
      }else if(e.getSource().equals(score_T)) {
         output_T.setText("������ �Է����ּ���.\n");
      }
   }
   @Override
   public void focusLost(FocusEvent e) {
      if(e.getSource().equals(id_T)) {
         if(id_T.getText() == "") {
            output_T.setText("ID�� �Էµ��� �ʾҽ��ϴ�.");
         }else {
            name_T.setEditable(true);
         }
      }else if(e.getSource().equals(name_T)) {
         if(name_T.getText() == "") {
            output_T.setText("�̸��� �Էµ��� �ʾҽ��ϴ�.");
         }else {
            score_T.setEditable(true);
         }
      }else if(e.getSource().equals(score_T)) {
         if(name_T.getText() == "") {
            output_T.setText("������ �Էµ��� �ʾҽ��ϴ�.");
         }else {
            regist_B.setEnabled(true);
            regist_B.requestFocus();
         }
      }
   }
   @Override
   public void actionPerformed(ActionEvent e) {
      if(e.getActionCommand().equals("���")) {
         if(dao.insert(id_T.getText(), name_T.getText(), Integer.parseInt(score_T.getText()))) {
            output_T.setText("����� �Ϸ�Ǿ����ϴ�.");
         }else {
            output_T.setText("id�� �����մϴ�.");
         }
         id_T.setText("");
         name_T.setText("");
         score_T.setText("");
         view_B.setEnabled(true);
      }else if(e.getActionCommand().equals("����")) {
         output_T.setText(dao.inquire());
      }else if(e.getActionCommand().equals("����")) {
         System.exit(0);
      }else if(e.getActionCommand().equals("�̸���")) {
          output_T.setText(dao.nameOrder());
      }else if(e.getActionCommand().equals("������")) {
          output_T.setText(dao.scoreOrder());
      }
      
      else if(e.getActionCommand().equals("�����ϱ�")) {
    	  dao.saveitem();
          output_T.setText("����Ǿ����ϴ�");
      }
      else if(e.getActionCommand().equals("�ҷ�����")) {
          dao.openitem();
          output_T.setText("�ҷ����⸦ �����߽��ϴ�.");
      }
      else if(e.getActionCommand().equals("�˻�")) {
    		  se.setVisible(true);
       se.okbtn.addActionListener(new ActionListener() {
    	   @Override
    	   public void actionPerformed(ActionEvent e) {
    		   String text = se.getInput();
    		   output_T.setText(dao.search(text));
    		 
    	   }
       });
      }
 
      	  
      
   
      
      // ����
      else if(e.getActionCommand().equals("����")) {
    	  de.setVisible(true);
    	  de.okbtn.addActionListener(new ActionListener() {
    	  @Override
    	  public void actionPerformed(ActionEvent e) {
    		  String text = de.getInput();
    		  	if(dao.delete(text)) {
    		  		output_T.setText("������ �Ϸ�Ǿ����ϴ�.");
    		  		de.setVisible(false);
    		  	}else {
    		  		output_T.setText("�й��� �������� �ʽ��ϴ�.");
    		  	}
    		  	de.clear();
    	  }
    	  });
      } 
      
      else if(e.getActionCommand().equals("����")) {
          ud.setVisible(true);
          ud.okbtn.addActionListener(new ActionListener() {

             @Override
             public void actionPerformed(ActionEvent e) {
                
                   String text = ud.getInput();
                   
                   String n_id = ud.getIdInput();
                   String n_name = ud.getNameInput();
                   int n_score = ud.getScoreInput();
                   
                   if(dao.delete(text)) {
                	   if(dao.insert(n_id, n_name, n_score)) { 
                           if(true){
                           output_T.setText("���� �Ϸ�");
                           ud.setVisible(false);
                           
                        }else{
                           output_T.setText("ID �������� ����");
                        }
                           ud.clear(text);
                           ud.clear(n_name);
                           ud.clear(n_id);
                	   }
                   
                }
             }
          });
      }
   }
}

class DelDialog extends JDialog {
	   private JTextField tf = new JTextField(10);
	   JButton okbtn = new JButton("Ȯ��");
	   
	   DelDialog(JFrame frame, String title) {
	      super(frame, title);
	      
	      this.dialogDesign();
	      this.dialogEvent();
	      this.setSize(200, 100);
	   }
	   
	   public void dialogDesign() {
	      this.setLayout(new FlowLayout());
	      this.add(tf);
	      this.add(okbtn);
	   }
	   
	   public void dialogEvent() {
	      okbtn.addActionListener(new ActionListener() {
	    	  
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            DelDialog.this.setVisible(false);
	         }
	         
	      });
	   }
	   public String getInput() {
	      if(tf.getText().length() == 0) return null;
	      else return tf.getText();
	   }
	   
	   public void clear() {
	         tf.setText("");
	      }

	}

	class UpdateDialog extends JDialog {
	   private JTextField tf = new JTextField(10);
	   private JTextField id_tx = new JTextField(10);
	   private JTextField name_tx = new JTextField(10);
	   private JTextField score_tx = new JTextField(10);
	   private JLabel updat_la = new JLabel("�й�");
	   private JLabel id_la = new JLabel("������ �й�");
	   private JLabel name_la = new JLabel("�̸�");
	   private JLabel score_la = new JLabel("����");
	   JButton okbtn = new JButton("Ȯ��");
	   
	   UpdateDialog(JFrame frame, String title) {
	      super(frame, title);
	      
	      this.dialogDesign();
	      this.dialogEvent();
	      this.setSize(100, 270);
	   }
	   
	   public void dialogDesign() {
	      this.setLayout(new FlowLayout());
	      this.add(updat_la);
	      this.add(tf);
	      this.add(id_la);
	      this.add(id_tx);
	      this.add(name_la);
	      this.add(name_tx);
	      this.add(score_la);
	      this.add(score_tx);
	      this.add(okbtn);
	   }
	   
	   public void dialogEvent() {
	      okbtn.addActionListener(new ActionListener() {

	         @Override
	         public void actionPerformed(ActionEvent e) {
	            UpdateDialog.this.setVisible(false);
	         }
	         
	      });
	   }
	   public String getInput() {
	      if(tf.getText().length() == 0) return null;
	      else return tf.getText();
	   }
	   public String getIdInput() {
	      if(id_tx.getText().length() == 0) return null;
	      else return id_tx.getText();
	   }
	   public String getNameInput() {
	      if(name_tx.getText().length() == 0) return null;
	      else return name_tx.getText();
	   }
	   public int getScoreInput() {
	      if(score_tx.getText().length() == 0) return 0;
	      else return Integer.parseInt(score_tx.getText());
	   }
	   public void clear(String del) {
	         if(del == "n_id") {
	            id_tx.setText("");
	         }
	         if(del == "n_name") {
	            name_tx.setText("");
	         }
	         if(del == "n_score") {
	            score_tx.setText("");
	         }   
	      }

	}
	class IdDialog extends JDialog {
		 private JTextField tf = new JTextField(10);
		 	JButton okbtn = new JButton("Ȯ��");
		 private JLabel n_id = new JLabel("�й� :");

		 IdDialog(JFrame frame, String title) {
		 super(frame, title);

		 this.dialogDesign();
		 this.dialogEvent();
		 this.setSize(200, 100);
		 }

		 public void dialogDesign() {
			 this.setLayout(new FlowLayout());
			 this.add(n_id);
			 this.add(tf);
			 this.add(okbtn);
		 }

		 public void dialogEvent() {
			 okbtn.addActionListener(new ActionListener() {
		    	  
		         @Override
		         public void actionPerformed(ActionEvent e) {
		        	 IdDialog.this.setVisible(false);
		         }
		         
		      });
		 }

		 public String getInput() {
			 if(tf.getText().length() == 0) {
				 return null;
			 }
			 else {
				 return tf.getText();
			 }
		 }

		 public void delTf() {
		 tf.setText("");
		 	}
	}
