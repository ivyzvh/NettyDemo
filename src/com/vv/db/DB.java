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
			// 调用Class.forName()方法加载驱动程序
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("成功加载MySQL驱动！");

			String url = "jdbc:mysql://172.18.22.203:3306/apollo_eu_erp"; // JDBC的URL
			// 调用DriverManager对象的getConnection()方法，获得一个Connection对象
			Connection conn = DriverManager.getConnection(url, "renesola", "renes0la.xx");
			// 创建一个Statement对象
			Statement stmt = conn.createStatement(); // 创建Statement对象
			System.out.print("成功连接到数据库！");

			String sql = "select * from t_system_account"; // 要执行的SQL
			ResultSet rs = stmt.executeQuery(sql);// 创建数据对象
			
			while (rs.next()) {
				list.add(new SystemAccount(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
			rs.close();
			stmt.close();
			conn.close();
			System.out.println("[数据查询]成功，" + list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
