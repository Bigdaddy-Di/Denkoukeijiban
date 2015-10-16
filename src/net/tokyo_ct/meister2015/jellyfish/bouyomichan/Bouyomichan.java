package net.tokyo_ct.meister2015.jellyfish.bouyomichan;

import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.Socket;

public class Bouyomichan {

	private String host = "localhost";
	private int port = 50001;

	public Bouyomichan() {
	}

	public Bouyomichan(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void clear() {
		this.command(64);
	}

	public void skip(){
		this.command(48);
	}

	public void command(int num) {
		byte b[] = new byte[2];
		b[0] = cutDig(num, 0);
		b[1] = cutDig(num, 1);
		this.send(b);
	}

	private void send(byte[] data) {
		Socket socket = null;
		DataOutputStream dos = null;

		try {
			socket = new Socket(this.host, this.port);
			System.out.println(this.host+" "+this.port+" Connection Successful");

			dos = new DataOutputStream(socket.getOutputStream());
			dos.write(data);
		}catch(ConnectException e){
			System.out.println("Unsuccessful Connection");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (dos != null) {
					dos.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void talk(String msg) {
		try {
			this.talk((short) -1, (short) -1, (short) -1, (short) 0, msg);
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void talk(short vlm, short spd, short tne, short vic, String msg)
			throws UnsupportedEncodingException {
		byte msgData[] = msg.getBytes("UTF-8");
		byte data[] = new byte[15 + msgData.length];

		data[0] = 1;
		data[1] = 0;
		data[2] = cutDig(spd, 0);
		data[3] = cutDig(spd, 1);
		data[4] = cutDig(tne, 0);
		data[5] = cutDig(tne, 1);
		data[6] = cutDig(vlm, 0);
		data[7] = cutDig(vlm, 1);
		data[8] = cutDig(vic, 0);
		data[9] = cutDig(vic, 1);
		data[10] = 0;
		data[11] = cutDig(msgData.length, 0);
		data[12] = cutDig(msgData.length, 1);
		data[13] = cutDig(msgData.length, 2);
		data[14] = cutDig(msgData.length, 3);
		System.arraycopy(msgData, 0, data, 15, msgData.length);
		this.send(data);

	}

	// すごいわかりにくい英語ですが2進数でnumからdig桁を返す関数です
	private static byte cutDig(short num, int dig) {
		return (byte) ((num >>> dig * 8) & 0xFF);
	}

	private static byte cutDig(int num, int dig) {
		return (byte) ((num >>> dig * 8) & 0xFF);
	}
}
