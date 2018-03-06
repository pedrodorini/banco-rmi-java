package br.univel;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;


public class Client {
	public static void main(String[] args) {
		System.out.println("Registrando no servidor remoto...");
		String value;
		try {
			Registry registry = LocateRegistry.getRegistry(9876);
			IBankOperations stub = (IBankOperations) registry.lookup("servidor_aula");
			int exit = 0;
			String id = JOptionPane.showInputDialog("Digite seu id: ");
			stub.clientRegistry(new Integer(id));
			while(exit != 4) {
				String option = JOptionPane.showInputDialog("Selecione uma opção\n1 - Saldo\n2 - Deposito\n3 - Saque\n4 - Logout\n5 - Sair");
				switch(option) {
				case "1":
					System.out.println(stub.balance());
					break;
				case "2":
					value = JOptionPane.showInputDialog("Digite o valor do deposito");
					stub.deposit(new Double(value));
					break;
				case "3":
					value = JOptionPane.showInputDialog("Digite o valor do saque");
					stub.withdraw(new Double(value));
					break;
				case "4":
					id = JOptionPane.showInputDialog("Digite seu id: ");
					stub.clientRegistry(new Integer(id));
					break;
				case "5":
					exit = 4;
					break;
				default:
					System.out.println();
					break;
				}
			}
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
}
