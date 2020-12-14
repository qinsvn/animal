package com.i.serial;
/**
 * createtime : 2018年6月1日 上午9:47:36
 */

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import com.i.socket.webSocket.WebsocketServer;
import com.i.util.TextLogUtil;


/**
 * TODO
 * 
 * @author XWF
 */
public class SerialCommunicatTServer {
	
	private static  SerialPort serialPort ;
	
	private static boolean isStart = false;
	
	public static void init(final String portName) {
		// 获得系统端口列表
		getSystemPort();
		start(portName);
	}

	public static void start(final String portName) {
		if (isStart) {
			stop();
		}
		// 开启端口 COM，波特率9600
		 try {
			serialPort = openSerialPort(portName, 9600);
			System.out.println("开始监听端口[" + portName + "]...");
			// 设置串口的listener
			SerialCommunicatTServer.setListenerToSerialPort(serialPort, new SerialPortEventListener() {
				@Override
				public void serialEvent(SerialPortEvent arg0) {
					if (arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {// 数据通知
						byte[] bytes = SerialCommunicatTServer.readData(serialPort);
						try {
							String recStr = new String(bytes, "UTF-8");
							System.out.println("端口[" + serialPort.getName() + "]接收到：" + recStr);
							TextLogUtil.setInCurrent("接收到数据:  "+recStr);
							//将串口数据发送到websocket客户端
							WebsocketServer.broadcastMsg(recStr);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				}
			});
			isStart=true;
		} catch (NoSuchPortException  e1) {
			 TextLogUtil.setInCurrent("没有这个端口！");
			stop();
			e1.printStackTrace();
		}
		 catch  (PortInUseException e1) {
			 TextLogUtil.setInCurrent("端口已经被占用！");
			stop();
			e1.printStackTrace();
		}
		 catch ( UnsupportedCommOperationException e1) {
			 TextLogUtil.setInCurrent("不支持这类型端口！");
			stop();
			e1.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 获得系统端口列表
		getSystemPort();
		start("COM1");
		// start("COM2");
	}

	/**
	 * 获得系统可用的端口名称列表
	 * 
	 * @return 可用端口名称列表
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getSystemPort() {
		List<String> systemPorts = new ArrayList<>();
		// 获得系统可用的端口
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			String portName = portList.nextElement().getName();// 获得端口的名字
			systemPorts.add(portName);
		}
		System.out.println("系统可用端口列表：" + systemPorts);
		return systemPorts;
	}

	/**
	 * 开启串口
	 * 
	 * @param serialPortName
	 *            串口名称
	 * @param baudRate
	 *            波特率
	 * @return 串口对象
	 * @throws NoSuchPortException 
	 * @throws PortInUseException 
	 * @throws UnsupportedCommOperationException 
	 */
	public static SerialPort openSerialPort(String serialPortName, int baudRate) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
			// 通过端口名称得到端口
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(serialPortName);
			// 打开端口，（自定义名字，打开超时时间）
			CommPort commPort = portIdentifier.open(serialPortName, 2222);
		
			// 判断是不是串口
			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				// 设置串口参数（波特率，数据位8，停止位1，校验位无）
				serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);
				System.out.println("开启串口成功，串口名称：" + serialPortName);
				return serialPort;
			} else{
				return null;
			}
				
	}

	/**
	 * 从串口读取数据
	 * 
	 * @param serialPort
	 *            要读取的串口
	 * @return 读取的数据
	 */
	public static byte[] readData(SerialPort serialPort) {
		InputStream is = null;
		byte[] bytes = null;
		try {
			is = serialPort.getInputStream();// 获得串口的输入流
			int bufflenth = is.available();// 获得数据长度
			while (bufflenth != 0) {
				bytes = new byte[bufflenth];// 初始化byte数组
				is.read(bytes);
				bufflenth = is.available();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}

	/**
	 * 给串口设置监听
	 * 
	 * @param serialPort
	 * @param listener
	 */
	public static void setListenerToSerialPort(SerialPort serialPort, SerialPortEventListener listener) {
		try {
			// 给串口添加事件监听
			serialPort.addEventListener(listener);
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
		serialPort.notifyOnDataAvailable(true);// 串口有数据监听
		serialPort.notifyOnBreakInterrupt(true);// 中断事件监听
	}

	public static void stop(){
		if (serialPort!=null) {
			serialPort.close();
			serialPort=null;
			isStart = false;
		}
	}
	
	public static boolean isStart(){
		return isStart;
	}
}