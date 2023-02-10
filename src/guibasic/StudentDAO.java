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
   
   // 1. 등록
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
   
   // 2. 조회
   public String inquire() {
      String str = "";
      for(int i= 0; i < al.size(); i++) {
         str += "ID : " + al.get(i).getId() + " 이름 : " + al.get(i).getName() + " 성적 : " + al.get(i).getScore() + "\n";
      }
      
      return str;
   } 
   // 3. 수정
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
   
   // 4. 삭제
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
	     
   // 5. 이름순 정렬   
   public String nameOrder() {
      Collections.sort(al);
      String str = "";
      for(int i= 0; i < al.size(); i++) {
         str += "ID : " + al.get(i).getId() + " 이름 : " + al.get(i).getName() + " 성적 : " + al.get(i).getScore() + "\n";
      }
      
      return str;
   }
   
   // 6. 점수순 정렬
   public String scoreOrder() {
      Comparator comparator = new Score();
      Collections.sort(al, comparator);
      String str = "";
      for(int i= 0; i < al.size(); i++) {
         str += "ID : " + al.get(i).getId() + " 이름 : " + al.get(i).getName() + " 성적 : " + al.get(i).getScore() + "\n";
      }
      
      return str;
   }
   
   // 7. 저장
   public void saveitem() {
	   File file = null;
	   
	   FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT text", "txt");
		fc.setFileFilter(filter);
		fc.setCurrentDirectory(new File(".\\"));
		int ret = fc.showSaveDialog(null);
		
		if(ret != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
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
   
   // 8. 불러오기
   public void openitem() {
	   File file = null;
	   
	   FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT text", "txt"); // 파일유형을 어떤거를 가져갈건가
		fc.setFileFilter(filter);
		
		int ret = fc.showOpenDialog(null); // 파일 다이얼로그 출력 null -> 전체 화면을 기준으로 위치를 잡는다.
		if(ret != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다", "경고", JOptionPane.WARNING_MESSAGE); // 파일을 선택하지 않으면
			return;
		}
		// 사용자가 파일을 선택하고 "열기" 버튼을 누른 경우
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

	// 9 .검색
	public String search(String id) {
		String str ="";
		for(int i= 0; i < al.size(); i++) {
			Student std = al.get(i);
			if(id.equals(std.getId())) {
	         str += "ID : " + al.get(i).getId() + " 이름 : " + al.get(i).getName() + " 성적 : " + al.get(i).getScore() + "\n";
	      }
		}
		if(str == "" || str.equals("")) str = "학번이 존재하지 않습니다.";
		return str;
	}
}