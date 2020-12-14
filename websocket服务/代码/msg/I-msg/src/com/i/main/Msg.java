package com.i.main;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;



import com.i.serial.SerialCommunicatTServer;
import com.i.socket.webSocket.WebsocketServer;
import com.i.util.TextLogUtil;

public class Msg {

    public static void init() {
    	
    	 SwingUtilities.invokeLater(new Runnable() {
             public void run() {
            	 List<String> COMs =  SerialCommunicatTServer.getSystemPort();
			    	 JFrame frame = new JFrame("串口通讯插件");
			    	 ImageIcon icon=new ImageIcon("resource/icon.png");
			    	 frame.setIconImage(icon.getImage());
			    	 frame.setResizable(false);
			         Container con = frame.getContentPane();
			         con.setLayout(new FlowLayout());
			         JLabel trimLab = new JLabel("                                                                                                                                                                                                                                                                                ");
			         JLabel serLab = new JLabel("端口：");
			         final JComboBox<String> comboBox=new JComboBox<String>();
			         final String selectItem = "请选择端口                         ";
			 		comboBox.addItem(selectItem);
			 		if (COMs.size()>0) {
			 			for( String com:COMs) {
			 				comboBox.addItem(com);
						}
					}else{
						 TextLogUtil.setInCurrent("没有可用端口，请连接！");
					}
			 		con.add(comboBox);
			         JPanel mlPanel = new JPanel();
			         mlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			         final JTextArea logTextArea = new JTextArea(10, 20);
			         logTextArea.append("     串口通讯插件用于串口设备与浏览器之间进行数据通讯\r\n\r\n");
			         logTextArea.append("1.确保串口设备已经连接你的电脑。\r\n");
			         logTextArea.append("2.在当前界面，选择的端口号。\r\n");
			         logTextArea.append("3.点击启动按钮，完成启动插件。\r\n");
			         logTextArea.append("4.可最小插件，但不可关闭插件。\r\n\r\n");
			         //设置自动换行
			         logTextArea.setLineWrap(true);
			         //定义带滚动条的panel，并将JTextArea存入到panel中，使textarea具有滚动条显示功能。
			         JScrollPane scrollpane = new JScrollPane(logTextArea);
			         //取消显示水平滚动条
			         scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			         //显示垂直滚动条
			         scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			         con.add(BorderLayout.NORTH,trimLab);
			         con.add(BorderLayout.CENTER,serLab);   
			         con.add(comboBox);
			         mlPanel.add(scrollpane);
			         con.add(mlPanel);
			         JButton clearButt = new JButton("清除");
			         clearButt.addActionListener(new ActionListener(){
			             @Override
			             public void actionPerformed(ActionEvent e) {
			                 // TODO Auto-generated method stub
			            	 logTextArea.setText("");
			             }
			         });
			         con.add(clearButt);
			         
			         final JButton actionButt = new JButton("启动");
			         actionButt.addActionListener(new ActionListener(){
			             @Override
			             public void actionPerformed(ActionEvent e) {
			                 // TODO Auto-generated method stub
			            	Object item =  comboBox.getSelectedItem();
			            	if (!selectItem.equals(item)) {
			            		if ("启动".equals(actionButt.getText())) {
			            			TextLogUtil.setInCurrent("正在启动...");
			            			SerialCommunicatTServer.start(item.toString());
			            			WebsocketServer.start();
			            			actionButt.setEnabled(false);
			            			new Thread(){public void run() {
			            					try {
												Thread.sleep(3000);
												actionButt.setEnabled(true);
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}};}.start();
								}else{
									SerialCommunicatTServer.stop();
									WebsocketServer.stop();
			            			 actionButt.setText("启动");
			            			 TextLogUtil.setInCurrent("关闭成功！");
								}
							}else{
								TextLogUtil.setInCurrent("请选择一个端口!");
							}
			             }
			         });
			         con.add(actionButt);
			         frame.setVisible(true);
			         frame.setSize(250,320);
			         frame.setLocationRelativeTo(null);
			         frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			        //线程 处理日志 等
			         new Thread(){
			        	 public void run() {
			        		 while (true) {
			        			 try {
									Thread.sleep(1000);
									//改变按钮文本
									if (SerialCommunicatTServer.isStart()&&WebsocketServer.isStart()&&"启动".equals(actionButt.getText())) {
										actionButt.setText("停止");
										TextLogUtil.setInCurrent("启动成功！");
									}
								
									//输出日志
									String log = 	TextLogUtil.get();
									if (null!=log&&!"".equals(log)) {
										logTextArea.append(log);
										logTextArea.append("\r\n");
										logTextArea.setCaretPosition(logTextArea.getText().length());
									}
									
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
			        			 
							}
			        	 };
			         }.start();
			         //线程 处理日志 等
             }
         });
    	 
    }
}
