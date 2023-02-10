package guibasic;

import java.io.Serializable;

public class Student implements Comparable<Student>, Serializable{
   private String id;
   private String name;
   private int score;
   
   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getScore() {
      return score;
   }

   public void setScore(int score) {
      this.score = score;
   }

   public Student(String id, String name, int score) {
      this.id = id;
      this.name = name;
      this.score = score;
   }

   public String toString() {
      return "학번 : " + id + " 이름 : " + name + " 성적 : " + score;
   }
   //객체가 같은지 판별
      
   public boolean equals(Object obj) {
      boolean result = false;
      
      if(obj instanceof Student) {
         Student stu = (Student) obj;
         return this.id.equals(stu.id);
      }
      return result;
   }
   
   public int hashCode() {
      return (id + name + score).hashCode();
   }

   @Override
   public int compareTo(Student o) {
      return this.name.compareTo(o.name);
   }
}