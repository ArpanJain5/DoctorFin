package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import connection.DBConection;
import model.PatientModel;

public class PatintDao {
	public static void insertPatient(PatientModel p) {
		try {
			Connection con = DBConection.createConnection();
			String sql = "insert into patient_data(image,name,contact,address,email,password) values(?,?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, p.getImage());
			pst.setString(2, p.getName());
			pst.setInt(3, p.getContact());
			pst.setString(4, p.getAddress());
			pst.setString(5, p.getEmail());
			pst.setString(6, p.getPassword());
			pst.executeUpdate();
			System.out.println("data inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
