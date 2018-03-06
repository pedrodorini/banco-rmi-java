package br.univel;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBankOperations extends Remote {
	public void clientRegistry(int id, String name) throws RemoteException;
	public void deposit(double value) throws RemoteException;
	public void withdraw(double value) throws RemoteException;
	public double balance() throws RemoteException;
}
