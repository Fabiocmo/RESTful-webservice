package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mukeshmaurya.Student;
import mukeshmaurya.StudentResult;

public class MyConnection {
	String ret = "ERROR";
    Connection conn = null;
    
    private void connectWithMySql(){
    	 try{
    	 Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","1234");
    	 }
    	 catch(Exception e){}
     }	
	public String connect(Student student){
		
	      try {
	    	 connectWithMySql();
	         String sql = "SELECT * FROM webservicedemo WHERE Roll = ?";
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ps.setString(1, student.getRoll());
	         //ps.setString(2, student.getPass());
	         ResultSet rs = ps.executeQuery();

	         while (rs.next()) {
	             student.setName(rs.getString(1));
	            student.setMobile(rs.getString(3));
	            student.setEmail(rs.getString(4));
	            student.setPass(rs.getString(5));
	          ret = "SUCCESS";
	         }
	      }
	      catch (SQLException e) 
	      {
	         ret = "ERROR";
	      } 
	      finally 
	      {
	        if (conn != null) 
	        {
	            try {conn.close();} 
	            catch (Exception e) {
	            e.printStackTrace();}
	         }
	      }
	      return ret;
	   }
	public String connectAs(Student student){

	      try {
	    	 connectWithMySql();
	         String sql = "SELECT * FROM webservicedemo WHERE Roll = ? AND Pass=?";
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ps.setString(1, student.getRoll());
	         ps.setString(2, student.getPass());
	         ResultSet rs = ps.executeQuery();

	         while (rs.next()) {
	             student.setName(rs.getString(1));
	            student.setMobile(rs.getString(3));
	            student.setEmail(rs.getString(4));
	            //student.setPass(rs.getString(5));
	          ret = "SUCCESS";
	         }
	      }
	      catch (SQLException e) 
	      {
	         ret = "ERROR";
	      } 
	      finally 
	      {
	        if (conn != null) 
	        {
	            try {conn.close();} 
	            catch (Exception e) {
	            e.printStackTrace();}
	         }
	      }
	      return ret;
	   }
	public String connectWithDetail(Student student){
	      try {
	    	  connectWithMySql();
	         String sql = "INSERT INTO webservicedemo (Name, Roll, Mobile, Email, Pass) VALUES (?,?,?,?,?)";
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ps.setString(1, student.getName());
	         ps.setString(2, student.getRoll());
	         ps.setString(3, student.getMobile());
	         ps.setString(4, student.getEmail());
	         ps.setString(5, student.getPass());
	         int n=ps.executeUpdate();
	         if(n>0){
	         ret = "SUCCESS";}
	      }
	      catch (SQLException e) 
	      {
	         ret = "ERROR";
	      } 
	      finally 
	      {
	        if (conn != null) 
	        {
	            try {conn.close();} 
	            catch (Exception e) {
	            e.printStackTrace();}
	         }
	      }
	      return ret;
	   }
	public String connectForResult(StudentResult studentResult, String roll) {
		try {
	    	 connectWithMySql();
	         String sql = "SELECT * FROM result WHERE uRoll = ?";
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ps.setString(1, roll);
	         ResultSet rs = ps.executeQuery();

	         while (rs.next()) {
	             studentResult.setSubone(rs.getString(2));
	             studentResult.setSubtwo(rs.getString(3));
	             studentResult.setSubthree(rs.getString(4));
	             studentResult.setSubfour(rs.getString(5));
	             studentResult.setSubfive(rs.getString(6));
	             studentResult.setSubsix(rs.getString(7));
	          ret = "SUCCESS";
	         }
	      }
	      catch (SQLException e) 
	      {
	         ret = "ERROR";
	      } 
	      finally 
	      {
	        if (conn != null) 
	        {
	            try {conn.close();} 
	            catch (Exception e) {
	            e.printStackTrace();}
	         }
	      }
	      return ret;
	}
	}

