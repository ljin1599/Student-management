package guibasic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class StudentDAO {
   private Scanner scan;
   private boolean iFlag;
   private ArrayList<Student> al;
   
   private String id;
   private String name;
   private int score;
   
   private JFileChooser fc = new JFileChooser();
   
   StudentDAO() {
      scan = new Scanner(System.in);
      
      al = new ArrayList<Student>();
   }
   
   // 1. ���
   public boolean insert(String id, String name, int score) {      
      for(int i = 0; i < al.size(); i++) {
         Student s = al.get(i);
         if(id.equals(s.getId())) {
            return false;
         }
      }
         Student stu = new Student(id, name, score);
         al.add(stu);
      
      return true;
   }
   
   // 2. ��ȸ
   public String inquire() {
      String str = "";
      for(int i= 0; i < al.size(); i++) {
         str += "ID : " + al.get(i).getId() + " �̸� : " + al.get(i).getName() + " ���� : " + al.get(i).getScore() + "\n";
      }
      
      return str;
   } 
   // 3. ����
   public boolean update(String id) {
       for(int i = 0; i < al.size(); i++) {
          Student stu = al.get(i);
          if(id.equals(stu.getId())) {
             al.remove(i);
          } else {
             return true;
          }
       }
    return false;
 }
   
   // 4. ����
   public boolean delete(String id) {
	      boolean found = false;
	      int tempIndex = 0;
	      
	      for(int i = 0;  i < al.size(); i++) {
	         Student stu = al.get(i);
	         if(id.equals(stu.getId())) {
	            found = true;
	            tempIndex = i;
	         }
	      }
	      if(found) {
	         al.remove(tempIndex);
	         return true;
	      }else {
	         return false;      
	      }
   }
	     
   // 5. �̸��� ����   
   public String nameOrder() {
      Collections.sort(al);
      String str = "";
      for(int i= 0; i < al.size(); i++) {
         str += "ID : " + al.get(i).getId() + " �̸� : " + al.get(i).getName() + " ���� : " + al.get(i).getScore() + "\n";
      }
      
      return str;
   }
   
   // 6. ������ ����
   public String scoreOrder() {
      Comparator comparator = new Score();
      Collections.sort(al, comparator);
      String str = "";
      for(int i= 0; i < al.size(); i++) {
         str += "ID : " + al.get(i).getId() + " �̸� : " + al.get(i).getName() + " ���� : " + al.get(i).getScore() + "\n";
      }
      
      return str;
   }
   
   // 7. ����
   public void saveitem() {
	   File file = null;
	   
	   FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT text", "txt");
		fc.setFileFilter(filter);
		fc.setCurrentDirectory(new File(".\\"));
		int ret = fc.showSaveDialog(null);
		
		if(ret != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "������ �������� �ʾҽ��ϴ�.", "���", JOptionPane.WARNING_MESSAGE);
			return;
		}
		String filePath = fc.getSelectedFile().getPath();
		
      try {
         BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
         ObjectOutputStream oos = new ObjectOutputStream(bos);
         oos.writeObject(al);
      
         oos.close();
         bos.close();
      } catch (IOException e) {
         System.out.println("IOException1");
      }
   }
   
   // 8. �ҷ�����
   public void openitem() {
	   File file = null;
	   
	   FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT text", "txt"); // ���������� ��Ÿ� �������ǰ�
		fc.setFileFilter(filter);
		
		int ret = fc.showOpenDialog(null); // ���� ���̾�α� ��� null -> ��ü ȭ���� �������� ��ġ�� ��´�.
		if(ret != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "������ �������� �ʾҽ��ϴ�", "���", JOptionPane.WARNING_MESSAGE); // ������ �������� ������
			return;
		}
		// ����ڰ� ������ �����ϰ� "����" ��ư�� ���� ���
		String filePath = fc.getSelectedFile().getPath();
      try {
         BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
         ObjectInputStream ois = new ObjectInputStream(bis);
         
         al = (ArrayList<Student>) ois.readObject();
         
         bis.close();
         ois.close();
      } catch (IOException e) {
         System.out.println("IOException2");
      } catch (ClassNotFoundException e) {
         System.out.println("ClassNotFoundException");
      }
   }

	// 9 .�˻�
	public String search(String id) {
		String str ="";
		for(int i= 0; i < al.size(); i++) {
			Student std = al.get(i);
			if(id.equals(std.getId())) {
	         str += "ID : " + al.get(i).getId() + " �̸� : " + al.get(i).getName() + " ���� : " + al.get(i).getScore() + "\n";
	      }
		}
		if(str == "" || str.equals("")) str = "�й��� �������� �ʽ��ϴ�.";
		return str;
	}
}