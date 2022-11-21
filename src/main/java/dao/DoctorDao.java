package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.DBConection;
import model.DoctorModel;

public class DoctorDao {
	public static void insertDoctor(DoctorModel d) {
		try {
			Connection conn = DBConection.createConnection();
			String sql="insert into doctor_data(image,name,contact,address,speciality,experience,email,password) values(?,?,?,?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, d.getImage());
			pst.setString(2, d.getName());
			pst.setInt(3, d.getContact());
			pst.setString(4, d.getAddress());
			pst.setString(5, d.getSpeciality());
			pst.setString(6, d.getExperince());
			pst.setString(7, d.getEmail());
			pst.setString(8, d.getPassword());
			pst.executeUpdate();
			System.out.println("data inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static DoctorModel doctorLogin(DoctorModel d) {
		DoctorModel d1 = null;
		try {
			Connection conn = DBConection.createConnection();
			String sql="select * from doctor_data where email=? and password=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, d.getEmail());
			pst.setString(2, d.getPassword());
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				d1 =new DoctorModel();
				d1.setId(rs.getInt(1));
				d1.setImage(rs.getString(2));
				d1.setName(rs.getString(3));
				d1.setContact(rs.getInt(4));
				d1.setAddress(rs.getString(5));
				d1.setSpeciality(rs.getString(6));
				d1.setExperince(rs.getString(7));
				d1.setEmail(rs.getString(8));
				d1.setPassword(rs.getString(9));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d1;
	}
	public static void doctorChangePasword(String email,String np) {
		try {
			Connection conn = DBConection.createConnection();
			String sql="update doctor_data set password=? where email=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, np);
			pst.setString(2, email);
			pst.executeUpdate();
			System.out.println("passsword updated");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			Connection conn = DBConection.createConnection();
			String sql="select * from doctor_data where email=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, email);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
