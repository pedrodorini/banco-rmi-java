package br.univel;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Server implements IBankOperations {

	private int userId;
	private List<User> userList = new ArrayList<User>();
	private Instant lastHighWithdraw;

	public static void main(String[] args) {
		int connections = 0;
		System.out.println("Construindo Servidor Remoto");
		Server server = new Server();
		if (server.checkConnection(connections)) {
			try {
				IBankOperations stub = (IBankOperations) UnicastRemoteObject.exportObject(server, 0);
				Registry registry = LocateRegistry.getRegistry(9876);
				registry.bind("servidor_aula", stub);
				System.out.println("Servidor iniciado...");
				connections++;
				System.out.println(connections);
			} catch (RemoteException | AlreadyBoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Falha para se conectar ou conexão em uso");
		}
	}

	public void clientRegistry(int id) {
		this.userId = id;
		boolean found = false;
		if (!this.userList.isEmpty()) {
			for (User user : userList) {
				if (user.getId() == id) {
					found = true;
				}
			}
		}
		if (!found) {
			User newUser = new User();
			newUser.setId(id);
			userList.add(newUser);
		}
	}

	@Override
	public double balance() {
		return this.userList.get(this.userId - 1).getBalance();
	}

	@Override
	public void deposit(double value) throws RemoteException {
		double balance = this.userList.get(this.userId - 1).getBalance();
		this.userList.get(this.userId - 1).setBalance(balance += value);
	}

	@Override
	public void withdraw(double value) throws RemoteException {
		double balance = this.userList.get(this.userId - 1).getBalance();
		if (value <= balance) {
			if (value >=  500) {
				Instant now = Instant.now();
				if (this.lastHighWithdraw != null) {
					if (now.isAfter(this.lastHighWithdraw.plusSeconds(120))) 
						this.userList.get(this.userId - 1).setCountDown(false);
				}
				if (this.userList.get(this.userId - 1).isCountDown()) {
					System.out.println("Espere 2 minutos para o proximo saque maior que 500 reais");
				} else {
					this.lastHighWithdraw = Instant.now();
					this.userList.get(this.userId - 1).setCountDown(true);
					this.userList.get(this.userId - 1).setBalance(balance -= value);
				}
			} else {
				this.userList.get(this.userId - 1).setBalance(balance -= value);
			}
		} else {
			System.out.println("Saldo insuficiente");
		}
	}

	public boolean checkConnection(int connections) {
		if (connections <= 1)
			return true;
		return false;
	}

}
