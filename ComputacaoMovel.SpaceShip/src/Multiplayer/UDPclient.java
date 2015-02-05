package Multiplayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.andengine.util.adt.array.ArrayUtils;
import org.andengine.util.adt.data.operator.StringOperator;

import android.util.Log;

public class UDPclient extends Thread {
	public int gamePort = 777;
	public final static String host = "172.16.7.254";
	private InetAddress server;
	private DatagramSocket socket;
	private DatagramPacket sendPacket;
	private DatagramPacket recievePacket;
	
	//CONSTS
	private final int CHAR_SIZE = 1;
	private final int INT_SIZE = 2;
	private final int FLOAT_SIZE = 2;
	
	//Threads
	private Thread otherListener;
	private Thread sendPositions;
	private Thread sendFireBullet;
	private Thread sendShields;
	private Thread sendLifes;
	private Thread sendJump;
	
	//this Player
	private float posX=0;
	private float bulletX = 0, bulletY = 0;
	private int nShields = 100;
	private int nLifes = 100;
	private boolean saltar = false;
	
	//other Player
	public float otherPosX=0;
	public float otherBulletX = 0;
	public float otherBulletY = 0;
	public int otherShields = 100;
	public int otherLifes = 100;
	public boolean otherSaltar = false;
	
	
	@Override
	public void run() {
		super.run();
	}
	
	@Override
	public synchronized void start() {
		super.start();
		
		//Starting Connection
		try {
			server = InetAddress.getByName(host);
			socket = new DatagramSocket(gamePort, server);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//Defining Threads
		otherListener = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					try {
						recieveData();
					} catch (IOException e) { }
				}
			}
		});
		sendPositions = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					try {
						updatePosition();
					} catch (IOException e) { }
				}
			}
		});
		sendShields = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					try {
						updateShieldNumber();
					} catch (IOException e) { }
				}
			}
		});
		sendLifes = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true){
						try {
							updateLifesNumber();
						} catch (IOException e) { }
					}
				}
			});
		sendFireBullet = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					try {
						fireBullet();
					} catch (IOException e) { }
				}
			}
		});
		sendJump = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					try {
						sendJump();
					} catch (IOException e) { }
				}
			}
		});


		//Starting Game
		otherListener.start();
		sendPositions.start();
	}

	
	///-----------------------
	/// Getters & Setters
	///-----------------------
	public void setPosition(float positionX) {
		 posX = positionX;
	}

	public void setShields(float shields) {
		 nShields = (int)Math.floor(shields);
		 sendShields.start();
	}

	public void setLifes(int lifes) {
		 nLifes = lifes;
		 sendLifes.start();
	}

	public void fireBullet(float bulletPosX, float bulletPosY) {
		 bulletX = bulletPosX;
		 bulletY = bulletPosY;
		 sendFireBullet.start();
	}

	public void setJump(boolean isJumping) {
		 saltar = isJumping;
		 sendJump.start();
	}
	
	
	///-----------------------
	/// FUNÇÕES INTERNAS
	///-----------------------
	
	///SENDERS
	//Position - 'p'
	private void updatePosition() throws IOException{
		byte[] id = "p".getBytes();
		byte[] data = ByteBuffer.allocate(FLOAT_SIZE).putFloat(posX).array();
		byte[] buffer = concatenateArray(id, data);
		
		sendPacket = new DatagramPacket(buffer, buffer.length);
		socket.send(sendPacket);
	}
	//Bullets - 't'
	private void fireBullet() throws IOException{
		byte[] id = "t".getBytes();
		byte[] data1 = ByteBuffer.allocate(FLOAT_SIZE).putFloat(bulletX).array();
		byte[] data2 = ByteBuffer.allocate(FLOAT_SIZE).putFloat(bulletY).array();
		byte[] buffer = concatenateArray(concatenateArray(id, data1), data2);
		
		sendPacket = new DatagramPacket(buffer, buffer.length);
		socket.send(sendPacket);
	}
	//Shields - 's'
	private void updateShieldNumber() throws IOException{
		byte[] id = "s".getBytes();
		byte[] data = ByteBuffer.allocate(INT_SIZE).putFloat(nShields).array();
		byte[] buffer = concatenateArray(id, data);
		
		sendPacket = new DatagramPacket(buffer, buffer.length);
		socket.send(sendPacket);
	}
	//Lifes - 'l'
	private void updateLifesNumber() throws IOException{
		byte[] id = "l".getBytes();
		byte[] data = ByteBuffer.allocate(INT_SIZE).putFloat(nLifes).array();
		byte[] buffer = concatenateArray(id, data);
		
		sendPacket = new DatagramPacket(buffer, buffer.length);
		socket.send(sendPacket);
	}
	//Jump - 'j'
	private  void sendJump() throws IOException{
		byte[] buffer = "j".getBytes();
		
		sendPacket = new DatagramPacket(buffer, buffer.length);
		socket.send(sendPacket);
	}
	

	//RECIEVERS
	private void recieveData() throws IOException{
		socket.receive(recievePacket);
		byte[] recieved = recievePacket.getData();
		char tipo = (char)recieved[0];
		
		//Tamanho das variáveis:
		//http://www.javacamp.org/javaI/primitiveTypes.html
		switch (tipo) {
			case 'p':
				readPosition(recieved);
				break;
			case 't':
				readBullet(recieved);
				break;
			case 's':
				readShield(recieved);
				break;
			case 'l':
				readLife(recieved);
				break;
			case 'j':
				readJump();
				break;
				
			case '+':
				stopThread((char)recieved[1]);
				break;
		}
	}
	
	private void readPosition(byte[] recieved) throws IOException {
		byte[] aux = Arrays.copyOfRange(recieved, CHAR_SIZE, CHAR_SIZE + FLOAT_SIZE);
		
		otherPosX = ByteBuffer.wrap(aux).getFloat();
		sendConfirmation('p');
	}
	
	private  void readBullet(byte[] recieved) throws IOException {
		byte[] aux1 = Arrays.copyOfRange(recieved, CHAR_SIZE, CHAR_SIZE + FLOAT_SIZE);
		byte[] aux2 = Arrays.copyOfRange(recieved, CHAR_SIZE + FLOAT_SIZE, CHAR_SIZE + FLOAT_SIZE + FLOAT_SIZE);

		otherBulletX = ByteBuffer.wrap(aux1).getFloat();
		otherBulletY = ByteBuffer.wrap(aux2).getFloat();
		sendConfirmation('t');
	}
	
	private void readShield(byte[] recieved) throws IOException {
		byte[] aux = Arrays.copyOfRange(recieved, CHAR_SIZE, CHAR_SIZE + INT_SIZE);

		otherShields = ByteBuffer.wrap(aux).getInt();
		sendConfirmation('s');
	}
	
	private void readLife(byte[] recieved) throws IOException {
		byte[] aux = Arrays.copyOfRange(recieved, CHAR_SIZE, CHAR_SIZE + INT_SIZE);

		otherLifes = ByteBuffer.wrap(aux).getInt();
		sendConfirmation('l');
	}
	
	private void readJump() {
		otherSaltar = true;
		sendConfirmation('j');
	}
	
	//CONTROL
	private void sendConfirmation(char messageType) {
		byte[] message = ("+" + messageType).getBytes();
		sendPacket = new DatagramPacket(message, message.length);
	}
	
	private boolean stopThread(char type) throws IOException {
		switch (type) {
			case 't':
				sendFireBullet.interrupt();
				break;
			case 's':
				sendShields.interrupt();
				break;
			case 'l':
				sendLifes.interrupt();
				break;
			case 'j':
				sendJump.interrupt();
				break;
			default:
				return false;
		}

		return true;
	}
	
	private byte[] concatenateArray(byte[] a, byte[] b) {
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}
}