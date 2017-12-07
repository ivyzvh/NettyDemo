package com.vv.model;

public class SystemAccount {
	private String accountId;
	private String accountNo;
	private String accountName;
	private String password;
	
	public SystemAccount(String accountId, String accountNo, String accountName, String password) {
		this.accountId = accountId;
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.password = password;
	}
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
