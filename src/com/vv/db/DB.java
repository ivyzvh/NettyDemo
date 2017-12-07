package com.vv.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vv.model.SystemAccount;

public class DB {

	public static void main(String[] args) {
		getSystemAccountList();
	}

	public static List<SystemAccount> getSystemAccountList() {
		List<SystemAccount> list = new ArrayList<>();
		
		try {
			// ����Class.forName()����������������
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("�ɹ�����MySQL������");

			String url = "jdbc:mysql://172.18.22.203:3306/apollo_eu_erp"; // JDBC��URL
			// ����DriverManager�����getConnection()���������һ��Connection����
			Connection conn = DriverManager.getConnection(url, "renesola", "renes0la.xx");
			// ����һ��Statement����
			Statement stmt = conn.createStatement(); // ����Statement����
			System.out.print("�ɹ����ӵ����ݿ⣡");

			String sql = "select * from t_system_account"; // Ҫִ�е�SQL
			ResultSet rs = stmt.executeQuery(sql);// �������ݶ���
			
			while (rs.next()) {
				list.add(new SystemAccount(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
			rs.close();
			stmt.close();
			conn.close();
			System.out.println("[���ݲ�ѯ]�ɹ���" + list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
