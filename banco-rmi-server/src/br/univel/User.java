package br.univel;

public class User {
	
	private int id;
	private String name;
	private double balance = 0;
	private boolean countDown = false;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isCountDown() {
		return countDown;
	}
	public void setCountDown(boolean countDown) {
		this.countDown = countDown;
	}
	
	

}
