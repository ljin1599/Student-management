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
   DelDialog de = new DelDialog(this, "삭제");
   UpdateDialog ud = new UpdateDialog(this, "수정");
   IdDialog se = new IdDialog(this, "검색");
      
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
         this.setTitle("학생관리프로그램");
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
         
         filemenu = new JMenu("파일");
         
         saveitem = new JMenuItem("저장하기");
         openitem = new JMenuItem("불러오기");	
         
         fc = new JFileChooser();

         this.setJMenuBar(jb);
         jb.add(filemenu);
         filemenu.add(saveitem);
         filemenu.add(openitem);
         
         id_L = new JLabel("ID : ");
         name_L = new JLabel("이름 : ");
         score_L = new JLabel("성적 : ");
         
         id_T = new JTextField();
         name_T = new JTextField();
         score_T = new JTextField();
         
         regist_B = new JButton("등록");
         view_B = new JButton("보기");
         exit_B = new JButton("종료");
         delete_B = new JButton("삭제");
         update_B = new JButton("수정");
         n_order_B = new JButton("이름순");
         s_order_B = new JButton("성적순");
         save_B = new JButton("저장");
         open_B = new JButton("불러오기");
         search_B = new JButton("검색");
         
         output_T = new JTextArea("등록된 학생 보기");
         
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
         output_T.setText("ID를 입력해주세요.\n");
      }else if(e.getSource().equals(name_T)) {
         output_T.setText("이름을 입력해주세요.\n");
      }else if(e.getSource().equals(score_T)) {
         output_T.setText("성적을 입력해주세요.\n");
      }
   }
   @Override
   public void focusLost(FocusEvent e) {
      if(e.getSource().equals(id_T)) {
         if(id_T.getText() == "") {
            output_T.setText("ID가 입력되지 않았습니다.");
         }else {
            name_T.setEditable(true);
         }
      }else if(e.getSource().equals(name_T)) {
         if(name_T.getText() == "") {
            output_T.setText("이름이 입력되지 않았습니다.");
         }else {
            score_T.setEditable(true);
         }
      }else if(e.getSource().equals(score_T)) {
         if(name_T.getText() == "") {
            output_T.setText("성적이 입력되지 않았습니다.");
         }else {
            regist_B.setEnabled(true);
            regist_B.requestFocus();
         }
      }
   }
   @Override
   public void actionPerformed(ActionEvent e) {
      if(e.getActionCommand().equals("등록")) {
         if(dao.insert(id_T.getText(), name_T.getText(), Integer.parseInt(score_T.getText()))) {
            output_T.setText("등록이 완료되었습니다.");
         }else {
            output_T.setText("id가 존재합니다.");
         }
         id_T.setText("");
         name_T.setText("");
         score_T.setText("");
         view_B.setEnabled(true);
      }else if(e.getActionCommand().equals("보기")) {
         output_T.setText(dao.inquire());
      }else if(e.getActionCommand().equals("종료")) {
         System.exit(0);
      }else if(e.getActionCommand().equals("이름순")) {
          output_T.setText(dao.nameOrder());
      }else if(e.getActionCommand().equals("성적순")) {
          output_T.setText(dao.scoreOrder());
      }
      
      else if(e.getActionCommand().equals("저장하기")) {
    	  dao.saveitem();
          output_T.setText("저장되었습니다");
      }
      else if(e.getActionCommand().equals("불러오기")) {
          dao.openitem();
          output_T.setText("불러오기를 성공했습니다.");
      }
      else if(e.getActionCommand().equals("검색")) {
    		  se.setVisible(true);
       se.okbtn.addActionListener(new ActionListener() {
    	   @Override
    	   public void actionPerformed(ActionEvent e) {
    		   String text = se.getInput();
    		   output_T.setText(dao.search(text));
    		 
    	   }
       });
      }
 
      	  
      
   
      
      // 삭제
      else if(e.getActionCommand().equals("삭제")) {
    	  de.setVisible(true);
    	  de.okbtn.addActionListener(new ActionListener() {
    	  @Override
    	  public void actionPerformed(ActionEvent e) {
    		  String text = de.getInput();
    		  	if(dao.delete(text)) {
    		  		output_T.setText("삭제가 완료되었습니다.");
    		  		de.setVisible(false);
    		  	}else {
    		  		output_T.setText("학번이 존재하지 않습니다.");
    		  	}
    		  	de.clear();
    	  }
    	  });
      } 
      
      else if(e.getActionCommand().equals("수정")) {
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
                           output_T.setText("수정 완료");
                           ud.setVisible(false);
                           
                        }else{
                           output_T.setText("ID 존재하지 않음");
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
	   JButton okbtn = new JButton("확인");
	   
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
	   private JLabel updat_la = new JLabel("학번");
	   private JLabel id_la = new JLabel("수정할 학번");
	   private JLabel name_la = new JLabel("이름");
	   private JLabel score_la = new JLabel("성적");
	   JButton okbtn = new JButton("확인");
	   
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
		 	JButton okbtn = new JButton("확인");
		 private JLabel n_id = new JLabel("학번 :");

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
