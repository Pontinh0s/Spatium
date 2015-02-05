package Multiplayer;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class TCPclient {
	public final static int
		defaultCreatePort = 777,
		defaultSearchPort = 778; //Dados partilhados com o servidor
	public final static String host = "localhost";
	public static String message = "A minha nova sala!";
	private static Socket client;

	//other Player
	private static float posX=0, posY=0;
	private static float bulletX = 0, bulletY = 0;
	private static int nShield = 0;
	
	//other Player
	private static float otherPosX=0, otherPosY=0;
	private static float otherBulletX = 0, otherBulletY = 0;
	private static int otherNShield = 0;
	 
	public static void main(String argv[]){
		createRoom();
	}
	 
	// Retorna -1 se houver problemas com a ligação, 0 se com o servidor, e 1 se correr tudo bem
	public static int searchRooms(){
		ArrayList<String> rooms;
		int data = -2;
		
		try {
			client = new Socket(host, defaultSearchPort);
			System.out.print("Conected. Awaiting for room names...\n\n");
			InputStream in = client.getInputStream();
			OutputStream out = client.getOutputStream();
			
			rooms = new ArrayList<String>();
			String roomName = "";
			
			do{
				data = in.read();
				if (data == '\n') {
					if (roomName == "")
						break;
					else{
						rooms.add(roomName);
						roomName = "";
					}
			 	}
				else if (data != -1)
					roomName += (char) data;
		 	} while (data != -1);
			
			for(int i = 0; i<rooms.size(); i++){
				System.out.print("\n\nRoom " +i+ "\nName: " +rooms.get(i));
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		if (data == -1){
			System.out.print("Problem with server. Please try again in a few minutes..\n");
			return 0;
		}
		else if (data == -2){
			System.out.print("Not connecting with server. Pleas check your internet connection..");
			return -1;
		}
		else
			return 1;
	}
	 
	public static void createRoom(){
		 try {
															System.out.print("trying to connect to " + host + " with port " + defaultCreatePort + "...\n");
			client = new Socket(host, defaultCreatePort);
															System.out.print("_client connected_\n");
			OutputStream out = client.getOutputStream();
															System.out.print("_output stream created_\n");
			
			// Send data:
			//java.io.DataOutputStream dataOut = new java.io.DataOutputStream(out);
			//dataOut.writeInt(12);
			
			// Hopefully, recieves the http data from google.pt
		 	InputStream in = client.getInputStream();
		 													System.out.print("_input stream created_\n");
		  	String mensagem = "";
		  	int data;
		  													System.out.print("_start reading: ");
		 	do{
		 		data = in.read();
	 														System.out.print((char)data);
		 		if (data != -1)
		 			mensagem += (char) data;
		 	} while (data != -1);
		 	
		 	client.close();
		 													System.out.print("\n\nReaded:\n" + mensagem);
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }

	 public static void startGame(){
		 try {
			 client = new Socket(host, defaultCreatePort);
			 OutputStream out = client.getOutputStream();
			 InputStream in = client.getInputStream();
			 
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void updatePosition(OutputStream out) throws IOException{
		float shipPosX = posX; //DEBUG
		
		String message = "p" + shipPosX;
		out.write(message.getBytes());
	}
	
	private static void fireBullet(OutputStream out) throws IOException{
		float bulletPosX = bulletX, bulletPosY = bulletY; //DEBUG
		
		String message = ("t" + bulletPosX) + bulletPosY;
		out.write(message.getBytes());
	}
	 
	private static void updateShieldNumber(OutputStream out) throws IOException{
		int shieldNumber = nShield; //DEBUG

		String message = "s" + shieldNumber;
		out.write(message.getBytes());
	}
	
	private static void recieveData(InputStream in, OutputStream out) throws IOException{
		String message = "";
		char tipo;
		
		byte[] auxb = null;
		char auxc;
		float auxf;
		int auxi;
		
		tipo = (char) in.read();
		//Tamanho das variáveis:
		//http://www.javacamp.org/javaI/primitiveTypes.html
		switch (tipo) {
			case 'p':
				auxc = (char) in.read(auxb, 0, 4);
				if (auxc != -1) {
					otherPosX = ByteBuffer.wrap(auxb).getFloat();
					message = "+p";
				}
				break;
			case 't':
				auxc = (char) in.read(auxb, 0, 4);
				if (auxc != -1) {
					otherBulletX = ByteBuffer.wrap(auxb).getFloat();
					auxc = (char) in.read(auxb, 0, 4);
					otherBulletY = ByteBuffer.wrap(auxb).getFloat();
				}
				if (auxc != -1)
					message = "+t";
				break;
			case 's':
				auxc = (char) in.read(auxb, 0, 4);
				otherNShield = ByteBuffer.wrap(auxb).getInt();
				message = "+s";
				break;
			default:
				break;
		}
		out.write(message.getBytes());
	}
}