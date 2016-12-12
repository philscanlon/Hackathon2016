package com.solacelabs.chat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.nio.ByteBuffer;

import com.solacelabs.chat.ActivityMonitor.userState;
import com.solacesystems.jcsmp.BytesXMLMessage;
import com.solacesystems.jcsmp.DeliveryMode;
import com.solacesystems.jcsmp.Destination;
import com.solacesystems.jcsmp.InvalidPropertiesException;
import com.solacesystems.jcsmp.JCSMPChannelProperties;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPProperties;
import com.solacesystems.jcsmp.JCSMPSession;
import com.solacesystems.jcsmp.JCSMPStreamingPublishEventHandler;
import com.solacesystems.jcsmp.SessionEventArgs;
import com.solacesystems.jcsmp.SessionEventHandler;
import com.solacesystems.jcsmp.Topic;
import com.solacesystems.jcsmp.User_Cos;
import com.solacesystems.jcsmp.XMLMessage;
import com.solacesystems.jcsmp.XMLMessageConsumer;
import com.solacesystems.jcsmp.XMLMessageListener;
import com.solacesystems.jcsmp.XMLMessageProducer;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.InputEvent;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

public class ChatClientApp {

	private static final int IDLE_TIME = 2*60*1000; // 2 mins
	private JFrame frame;
	private JTextField txtFldHandle;
	private JTextField txtFldConnectString;
	private JTextField txtFldUser;
	private JTextField txtFldVPN;
	private JPasswordField txtFldPassword;

	
	JCSMPSession session = null;
	
	XMLMessageConsumer cons = null;
    private XMLMessageProducer messageProducer = null;
	private byte[] pingBytes;
	private Topic topicPing;
	private final String chatTopicPrefix = "topic/SolaceChat";
	// subscribe to all ping messages
	private final String pingTopicSuffix = "/Directory/Ping/";
	private final String chatMessagesPrefix = chatTopicPrefix+"/Messages/";	
	private final String publicChatMessagesPrefix = chatMessagesPrefix+"Public/";
	private final String privateChatMessagesPrefix = chatMessagesPrefix+"Private/";	
	private HashMap<String,Topic> directoryTopicMap = new HashMap<String, Topic>();
	private HashMap<String, Topic> chatTopics = new HashMap<String, Topic>();
	private HashMap<String, Integer> chatPaneIndexes = new HashMap<String, Integer>();
	private HashMap<String,String> messageTopicMap = new HashMap<String, String>();
	private HashSet<String> subscribedUsersSet = new HashSet<String>();
	private HashMap<String,JCheckBox> directoryUsersMap = new HashMap<String, JCheckBox>();
	private HashMap<String,ActivityMonitor> directoryUsersActivityMap = new HashMap<String, ActivityMonitor>();
	private byte[] disconnectBytes = "disconnect".getBytes();
	private byte[] idleBytes = "idle".getBytes();
	private boolean isIdle = false;
	private boolean isConnected = false;
	private JPanel directoryPanel;
	private Topic topicSelf;
	private JEditorPane chatInputPane;
	private Timer directorySortTimer;
	private Topic topicPingAll;
	private Timer pingTimer;
//	private JTextPane publicChatMessagesPane;
	private String LF = System.getProperty("line.separator");
	private Font chatFont;
	private Font chatFontBold;
	private SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//	private StyledDocument chatDoc;
	private SimpleAttributeSet userName;
	private SimpleAttributeSet dateStyle;
//	private JScrollPane scrollPanePublicMessages;
	private JCheckBox chckbxAutoscroll;
	private JCheckBox chckbxAutoSubscribe;
	private AdjustmentListener messagesAdjustmentListener;
	private JTabbedPane chatTabs;
	private Timer idleTimer;
	private Topic topicSelfPrivate;
	private String Self;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatClientApp window = new ChatClientApp();
					try {
					    // Significantly improves the look of the output in each OS..
					    // By making it look 'just like' all the other apps.
					    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						// need to try seaglass				
//					    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
					} catch(Exception weTried) {}
					
					window.frame.setAutoRequestFocus(true);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatClientApp() {
		initialize();
	}
	
	public void bringToFront() {
		frame.setAlwaysOnTop(true);
		Robot bot;
		try {
			bot = new Robot();
			Point origLoc = MouseInfo.getPointerInfo().getLocation();
			bot.mouseMove(frame.getX()+20, frame.getY()+30);
			bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			bot.mouseMove(origLoc.x, origLoc.y);
			bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
			bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        frame.setAlwaysOnTop(false);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 522, 674);
		frame.setTitle("Solace Chat");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				/// Disconnect gracefully
				disconnectFromRouter();
			}
			public void windowIconified(WindowEvent e) {
				/// set Idle
				setIdle(true);
			}
			public void windowDeIconified(WindowEvent e) {
				/// unset Idle
				setIdle(false);
			}
			public void windowActivated(WindowEvent e) {
				/// unset Idle
				setIdle(false);
			}
			public void windowDeActivated(WindowEvent e) {
				/// set Idle
				setIdle(true);
			}
		});
		
		frame.addWindowFocusListener(new WindowAdapter() {
	        @Override
	        public void windowLostFocus(WindowEvent e) {
				setIdle(true);
	        }
	        @Override
	        public void windowGainedFocus(WindowEvent e) {
				setIdle(false);
	        }
		});

		frame.getContentPane().setLayout(new MigLayout("", "[522px][522px,grow]", "[16px][174.00][][grow][][fill]"));
		
		JLabel lblConnect = new JLabel("Connection Details");
		frame.getContentPane().add(lblConnect, "flowx,cell 1 0 2 1");
				
		JLabel lblDirectory = new JLabel("Directory");
		frame.getContentPane().add(lblDirectory, "flowx,cell 0 0,aligny center");
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 0 1,grow");
		
		directoryPanel = new JPanel();
		scrollPane.setViewportView(directoryPanel);
		directoryPanel.setLayout(new BoxLayout(directoryPanel, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		
		frame.getContentPane().add(panel, "cell 1 1,grow");
		panel.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][]"));
		
		JLabel lblHandle = new JLabel("Handle");
		panel.add(lblHandle, "cell 0 0,alignx trailing");
		
		txtFldHandle = new JTextField();
		panel.add(txtFldHandle, "cell 1 0,growx");
		txtFldHandle.setColumns(10);
		
		JLabel lblRouter = new JLabel("Router");
		panel.add(lblRouter, "cell 0 1,alignx trailing");
		
		txtFldConnectString = new JTextField("52.31.220.231");
		panel.add(txtFldConnectString, "cell 1 1,growx");
		txtFldConnectString.setColumns(10);
		
		JLabel lblUser = new JLabel("User");
		panel.add(lblUser, "cell 0 2,alignx trailing");
		
		txtFldUser = new JTextField();
		txtFldUser.setText("hackathon");
		panel.add(txtFldUser, "cell 1 2,growx");
		txtFldUser.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		panel.add(lblPassword, "cell 0 3,alignx trailing");
		
		txtFldPassword = new JPasswordField();
		panel.add(txtFldPassword, "cell 1 3,growx");
		txtFldPassword.setColumns(10);
		
		JLabel lblVpn = new JLabel("VPN");
		panel.add(lblVpn, "cell 0 4,alignx trailing");
		
		txtFldVPN = new JTextField();
		txtFldVPN.setText("hackathon");
		panel.add(txtFldVPN, "cell 1 4,growx");
		txtFldVPN.setColumns(10);
		
		JToggleButton tglbtnConnect = new JToggleButton("Connect");
		tglbtnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tglbtnConnect.isSelected()) {
					if (!validateInputs()) {
						if (connectToRouter()) {
							connectClients();
							startHandlePing();
							startDirectorySorter();
							toggleFieldEnablement(false);
						} else {
							tglbtnConnect.setSelected(false);
						}
					} else {
						tglbtnConnect.setSelected(false);
					}
				} else {
					stopHandlePing();
					disconnectFromRouter();
					stopDirectorySorter();
					toggleFieldEnablement(true);
				}
			}
		});
		panel.add(tglbtnConnect, "cell 1 5");
		
		// add enter key as secondary way of connecting
		// TODO this isn't working...
		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tglbtnConnect.doClick(0);
				}
			}
		});
	
		JLabel lblMessages = new JLabel("Messages");
		frame.getContentPane().add(lblMessages, "flowx,cell 0 2 2 1");
		
		chatTabs = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(chatTabs, "cell 0 3 2 1,grow");
		
		addChatMessagesPane("Public");
		chatFont = ((JScrollPane)chatTabs.getComponentAt(0)).getFont();

		userName = new SimpleAttributeSet();
		StyleConstants.setForeground(userName, Color.RED);
//		StyleConstants.setBackground(userName, Color.WHITE);
		StyleConstants.setBold(userName, true);
		
		dateStyle = new SimpleAttributeSet();
		StyleConstants.setForeground(dateStyle, Color.GRAY);
		StyleConstants.setFontSize(dateStyle, chatFont.getSize() - 2);
				
		JLabel lblChat = new JLabel("Chat");
		frame.getContentPane().add(lblChat, "cell 0 4");
		
		JScrollPane scrollPaneChat = new JScrollPane();
		frame.getContentPane().add(scrollPaneChat, "cell 0 5 2 1,grow");
		
		chatInputPane = new JEditorPane();
		chatInputPane.setEditable(false);		
		this.frame.addWindowFocusListener(new WindowAdapter() {
		    public void windowGainedFocus(WindowEvent e) {
		    	chatInputPane.requestFocusInWindow();
		    }
		});
		chatInputPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendChatMessage(chatInputPane.getText());
					chatInputPane.setText("");
				}
			}
		});
		scrollPaneChat.setViewportView(chatInputPane);
		scrollPaneChat.setMinimumSize(new Dimension(400,80));
		
		chckbxAutoscroll = new JCheckBox("AutoScroll");
		chckbxAutoscroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxAutoscroll.isSelected()) {
					enableAutoScroll(true);
				} else {
					enableAutoScroll(false);
				}
			}
		});
		chckbxAutoscroll.setSelected(true);
		frame.getContentPane().add(chckbxAutoscroll, "cell 0 2");
		
		// set up auto scrolling
		messagesAdjustmentListener = new AdjustmentListener() {
			@Override
	        public void adjustmentValueChanged(AdjustmentEvent e) {  
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
	        }
		};
				
		chckbxAutoSubscribe = new JCheckBox("Auto Subscribe");
		chckbxAutoSubscribe.setSelected(true);
		frame.getContentPane().add(chckbxAutoSubscribe, "cell 0 0");
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = chatTabs.getSelectedIndex();
				String Handle = chatTabs.getTitleAt(index);
				removePrivateChat(Handle, index);
			}
		});
		frame.getContentPane().add(btnClose, "cell 1 2");
		
		// create IdleTimer
		ActionListener idleListener = new ActionListener(){
			public void actionPerformed(ActionEvent event){
				isIdle = true;
			}
		};
		idleTimer =  new Timer(IDLE_TIME, idleListener);
		idleTimer.setRepeats(false);
	}

	protected void removePrivateChat(String Handle, int index) {
		if (Handle.contentEquals("Public")) {
			// don't close the public window
			return;
		}
		chatTopics.remove(Handle);
		chatTabs.remove(index);
		chatPaneIndexes.remove(Handle);
	}

	public void createPrivateChat(String Handle, boolean bSwitch) {
		// add private chat stuff
		if (chatTopics.containsKey(Handle)) {
			// if someone clicks the create private chat menu twice, just switch to it
			if (bSwitch) {
				chatTabs.setSelectedIndex(chatTabs.indexOfTab(Handle));
				chatInputPane.requestFocusInWindow();
				frame.revalidate();
			}
			return;
		}
		System.out.println("called Create private chat");
		Topic topic = JCSMPFactory.onlyInstance().createTopic(privateChatMessagesPrefix+Handle);
		System.out.println("Created chat topic "+topic.getName());
		chatTopics.put(Handle, topic);

		addChatMessagesPane(Handle);
		if (bSwitch) {
			chatTabs.setSelectedIndex(chatTabs.indexOfTab(Handle));
			chatInputPane.requestFocusInWindow();
			frame.revalidate();
		}
	}
	protected boolean validateInputs() {
		String message = "";
		boolean error = false;
		if (txtFldHandle.getText().length() == 0) {
			message += "Please set a Handle.  ";
			error = true;
		}
		if (txtFldConnectString.getText().length() == 0) {
			message += "Please set a Connect String.  ";
			error = true;
		}
		if (txtFldUser.getText().length() == 0) {
			message += "Please set a User.  ";
			error = true;
		}
		if (txtFldVPN.getText().length() == 0) {
			message += "Please set a VPN.  ";
			error = true;
		}
		if (error) {
			infoBox(message, "Invalid Connection Details");
		}
		return error;
	}
	
	private void toggleFieldEnablement(boolean enable) {
		txtFldConnectString.setEnabled(enable);
		txtFldHandle.setEnabled(enable);
		txtFldPassword.setEnabled(enable);
		txtFldUser.setEnabled(enable);
		txtFldVPN.setEnabled(enable);
	}


	protected void enableAutoScroll(boolean autoUpdate) {
		if (autoUpdate) {
			// iterate through tabs
			for (int i=0; i < chatTabs.getTabCount(); i++) {
				((JScrollPane)chatTabs.getComponentAt(i)).getVerticalScrollBar().addAdjustmentListener(messagesAdjustmentListener);
			}			
		} else {
			// iterate through tabs
			for (int i=0; i < chatTabs.getTabCount(); i++) {
				((JScrollPane)chatTabs.getComponentAt(i)).getVerticalScrollBar().removeAdjustmentListener(messagesAdjustmentListener);
			}
		}
	}

	protected void sendChatMessage(String text) {
		BytesXMLMessage msg = JCSMPFactory.onlyInstance().createMessage(BytesXMLMessage.class);
		msg.setCos(User_Cos.USER_COS_1);
		msg.setDeliveryMode(DeliveryMode.DIRECT);
		msg.writeAttachment(text.getBytes());
		msg.setApplicationMessageId("SolaceChat:"+Self);
		try {
			int index = chatTabs.getSelectedIndex();
			String Handle = chatTabs.getTitleAt(index);
			System.out.println("Sending to: "+chatTopics.get(Handle));
			messageProducer.send(msg, chatTopics.get(Handle));
			if (index > 0) {
				// It's a private message, update the local pane directly
				displayMessage(msg, Handle, false, true);
			}
		} catch (JCSMPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void setIdle(boolean idle) {
		// don't set idle unless we're connected
		if (isConnected) {
			// launch a timer and set the use idle in 2 mins if window still not active
			if (idle && !idleTimer.isRunning()) {
				idleTimer.start();
			} else if (!idle) {
				if (idleTimer.isRunning()) {
					idleTimer.stop();
				}
				isIdle = idle;
			}
		}
	}

	protected void connectClients() {
		try {
			
			// Acquire a message consumer and open the data channel to the appliance.
			System.out.println("About to connect to appliance.");
			cons = session.getMessageConsumer(new SubMessageHandler());			
			
			// Create ping topic plus ping subscription.
			Self = txtFldHandle.getText();
			if (Self.equalsIgnoreCase("Aaron")) {
				Self = "Kaisu";
			} else if (Self.equalsIgnoreCase("Phil")) {
				Self = "Blanks";
			}
			
			topicPing = JCSMPFactory.onlyInstance().createTopic(chatTopicPrefix+pingTopicSuffix+Self);
			topicPingAll = JCSMPFactory.onlyInstance().createTopic(chatTopicPrefix+pingTopicSuffix+">");
			System.out.printf("Setting topic subscription '%s'...\n", topicPingAll.getName());
			// set the ping message to be the handle name
			pingBytes = txtFldHandle.getText().getBytes();
			session.addSubscription(topicPingAll);
			
			// subscribe to our own messages
			topicSelf = JCSMPFactory.onlyInstance().createTopic(publicChatMessagesPrefix+Self);
			System.out.printf("Setting topic subscription '%s'...\n", topicSelf.getName());
			session.addSubscription(topicSelf);
			topicSelfPrivate =  JCSMPFactory.onlyInstance().createTopic(privateChatMessagesPrefix+Self);
			session.addSubscription(topicSelfPrivate);
			chatTopics.put("Public", topicSelf);

			subscribedUsersSet.add(txtFldHandle.getText());
			System.out.println("Connected!");
			
			// if we are reconnecting, rebuild valid subscriptions for existing directory
			if (!messageTopicMap.isEmpty()) {
				for (String subscribedTopics: messageTopicMap.values()) {
					Topic topic = JCSMPFactory.onlyInstance().createTopic(subscribedTopics);
					session.addSubscription(topic);
				}
			}

			// Receive messages.
			cons.start();
			chatInputPane.setEditable(true);

			// Acquire a message producer.
			
			System.out.println("Create producer.");
			messageProducer = session.getMessageProducer(new PrintingPubCallback());

		} catch (Exception ex) {
			// Normally, we would differentiate the handling of various exceptions, but
			// to keep this sample simple, all exceptions
			// are handled in the same way.
			System.err.println("Encountered an Exception: " + ex.getMessage());
			ex.printStackTrace(System.err);
			infoBox(ex.getMessage(),"Encountered an Exception");
		}
	}
	
	protected void addChatMessagesPane(String Handle) {
		JScrollPane scrollPane = new JScrollPane();
		chatTabs.addTab(Handle, null, scrollPane, null);

		JTextPane messagesPane = new JTextPane();
		messagesPane.setEditable(false);
		
		scrollPane.setViewportView(messagesPane);			
		scrollPane.getVerticalScrollBar().addAdjustmentListener(messagesAdjustmentListener);
		//add tabbed index
		chatPaneIndexes.put(Handle, chatTabs.indexOfTab(Handle));
	}

	protected void sendHandlePing(){
		XMLMessage msg = JCSMPFactory.onlyInstance().createMessage(BytesXMLMessage.class);
		if (!isIdle) {
			msg.writeAttachment(pingBytes);
		} else {
			msg.writeAttachment(idleBytes);
		}
		
		msg.setCos(User_Cos.USER_COS_1);
		msg.setDeliveryMode(DeliveryMode.DIRECT);
		try {
			messageProducer.send(msg, topicPing);
		} catch (JCSMPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void startHandlePing() {
		ActionListener listenerPing = new ActionListener(){
			public void actionPerformed(ActionEvent event){
			// send ping
				sendHandlePing();
			}
		};
		pingTimer = new Timer(5000, listenerPing);
		pingTimer.start();
	}
	
	protected void stopHandlePing() {
		pingTimer.stop();
	}

	protected void updateDirectoryListing(String Handle, boolean disconnect, boolean idle) {
		if (Handle.contentEquals(topicPing.getName().substring(topicPing.getName().lastIndexOf("/")+1))) {
			// don't add or remove ourself
			return;
		}
		
		// we need to handle the case where the first ping we receive is an idle ping
		if (idle) {
			if (directoryUsersMap.containsKey(Handle)) {
				JCheckBox jCB = (JCheckBox)directoryUsersMap.get(Handle);
				jCB.setForeground(Color.ORANGE);
				directoryPanel.revalidate();
				return;
			}
		}
		if (disconnect) {
			JCheckBox jCB = (JCheckBox)directoryUsersMap.get(Handle);
			jCB.setForeground(Color.BLACK);
 		    jCB.setEnabled(false);
			directoryPanel.revalidate();
			return;
		}
		if (directoryUsersMap.containsKey(Handle)) {
			// just enable the checkbox if it exists but don't add an existing directory member again
			// (it may be already enabled but this is simpler than checking first
			JCheckBox jCB = (JCheckBox)directoryUsersMap.get(Handle);
			jCB.setForeground(Color.BLACK);
			jCB.setEnabled(true);
			directoryPanel.revalidate();
			return;
		}
		JCheckBox chckbxNewCheckBox = new JCheckBox(Handle);
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isConnected) {
					JCheckBox jCB=(JCheckBox)e.getSource();
					updateDirectorySubscriptions(jCB.getText(), jCB.isSelected());
				}
			}
		});
		chckbxNewCheckBox.addMouseListener(new PrivateChatMenu(chckbxNewCheckBox, this));
//		System.out.println("Update Directory");
		if (idle) {
			chckbxNewCheckBox.setForeground(Color.ORANGE);
		}
		directoryPanel.add(chckbxNewCheckBox);
		directoryUsersMap.put(Handle, chckbxNewCheckBox);
		directoryPanel.revalidate();
		Topic topic = JCSMPFactory.onlyInstance().createTopic(publicChatMessagesPrefix+Handle);
		directoryTopicMap.put(Handle,topic);
		if (chckbxAutoSubscribe.isSelected()) {
			chckbxNewCheckBox.setSelected(true);
			updateDirectorySubscriptions(Handle, true);
		}
	}
	
	protected void updateDirectorySubscriptions(String Handle, boolean bAdd) {
		System.out.println("update subscription called");
		try {
			if (bAdd) {
//				System.out.println("Added Subsription: "+ directoryTopicMap.get(Handle).getName());
				session.addSubscription(directoryTopicMap.get(Handle));
				messageTopicMap.put(Handle, directoryTopicMap.get(Handle).getName());
			} else {
				session.removeSubscription(directoryTopicMap.get(Handle));
				messageTopicMap.remove(Handle);
			}
		} catch (JCSMPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected boolean connectToRouter() {
		// Create session from JCSMPProperties. Validation is performed by
		// the API, and it throws InvalidPropertiesException upon failure.
		System.out.println("About to create session.");
		System.out.println("Configuration: " + txtFldConnectString + " " + txtFldUser);			
		
		JCSMPProperties properties = new JCSMPProperties();
		
		properties.setProperty(JCSMPProperties.HOST, txtFldConnectString.getText());
		properties.setProperty(JCSMPProperties.USERNAME, txtFldUser.getText());
		
		if (!txtFldVPN.getText().isEmpty()) {
			properties.setProperty(JCSMPProperties.VPN_NAME, txtFldVPN.getText());
		}
		
		properties.setProperty(JCSMPProperties.PASSWORD, new String(txtFldPassword.getPassword()));
        
		// With reapply subscriptions enabled, the API maintains a
		// cache of added subscriptions in memory. These subscriptions
		// are automatically reapplied following a channel reconnect.
		properties.setBooleanProperty(JCSMPProperties.REAPPLY_SUBSCRIPTIONS, true);
		
        // Disable certificate checking
        properties.setBooleanProperty(JCSMPProperties.SSL_VALIDATE_CERTIFICATE, false);

        properties.setProperty(JCSMPProperties.AUTHENTICATION_SCHEME, JCSMPProperties.AUTHENTICATION_SCHEME_BASIC);   

        // Channel properties
        JCSMPChannelProperties cp = (JCSMPChannelProperties) properties.getProperty(JCSMPProperties.CLIENT_CHANNEL_PROPERTIES);
        			
		cp.setConnectRetries(5);
		
		try {
			session =  JCSMPFactory.onlyInstance().createSession(properties);
		} catch (InvalidPropertiesException ipe) {
			ipe.printStackTrace();
			infoBox(Arrays.toString(ipe.getMessages()), "Error Connecting To Router");
			return false;
		}
		isConnected = true;

		return true;
	}

	protected boolean disconnectFromRouter() {
		// Stop the consumer and remove the subscription.
		if (isConnected) {
			SubMessageHandler smh = (SubMessageHandler) cons.getMessageListener();
			// send disconnect message
			XMLMessage msg = JCSMPFactory.onlyInstance().createMessage(BytesXMLMessage.class);
			msg.writeAttachment(disconnectBytes);
			try {
				messageProducer.send(msg, topicPing);
				cons.stop();	
				messageProducer.close();
				session.removeSubscription(topicPingAll);
				session.closeSession();
				chatInputPane.setEditable(false);
				isConnected = false;
			} catch (JCSMPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	return true;
	}
	
	protected void stopDirectorySorter() {
		directorySortTimer.stop();
	}

	
	private void startDirectorySorter() {
		ActionListener listenerDSort = new ActionListener(){
			public void actionPerformed(ActionEvent event){
			// Update directory
				sortDirectory();
			}
		};
		directorySortTimer = new Timer(5000, listenerDSort);
		directorySortTimer.start();
	}
	
	public static void infoBox(String infoMessage, String titleBar)
	{
		JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void sortDirectory() {
		TreeMap<String,JCheckBox> tm = new TreeMap<String,JCheckBox>(directoryUsersMap);
		directoryPanel.removeAll();
		for (JCheckBox jCB: tm.values()) {
			directoryPanel.add(jCB);
		}
		directoryPanel.revalidate();
	}
	class SubMessageHandler implements XMLMessageListener {
		
		public SubMessageHandler() {
		}
		
		public int msgCount = 0;
		
		public void onException(JCSMPException exception) {
			System.err.println("Error occurred, printout follows.");
			exception.printStackTrace();
		}

		public void onReceive(BytesXMLMessage msg) {
			String topicName = msg.getDestination().getName();

			if (topicName.contains(pingTopicSuffix)) {
				ByteBuffer bb = msg.getAttachmentByteBuffer();
				boolean disconnect = false;
				boolean idle = false;
				String content = new String(bb.array());
				String Handle = topicName.substring(topicPing.getName().lastIndexOf("/")+1);
//				System.out.println("Ping Name: "+name);
				if (content.contentEquals("idle")) {
					idle = true;
				} else if (content.contentEquals("disconnect")) {
					disconnect = true;
				} 
				updateDirectoryListing(Handle, disconnect, idle);
			}
			if (topicName.contains(chatMessagesPrefix)) {
				System.out.println("AppID: "+msg.getApplicationMessageId());
				String Handle = msg.getApplicationMessageId().substring(msg.getApplicationMessageId().indexOf(":")+1);
				if (topicName.contains(publicChatMessagesPrefix)) {
					displayMessage(msg, Handle, true, false);
				} else {
					displayMessage(msg, Handle, false, false);
				}
//				System.out.println("Received message:");
//				System.out.println("topicName "+topicName);
//				System.out.print(msg.dump(XMLMessage.MSGDUMP_FULL));
			}
		}
	}
	public class PrintingPubCallback implements JCSMPStreamingPublishEventHandler {
		public void handleError(String messageID, JCSMPException cause, long timestamp) {
			System.err.println("Error occurred for message: " + messageID);
			cause.printStackTrace();
		}

		// This method is only invoked for persistent and non-persistent
		// messages.
		public void responseReceived(String messageID) {
			System.out.println("Response received for message: " + messageID);
		}
	}

	public class PrintingSessionEventHandler implements SessionEventHandler {
        public void handleEvent(SessionEventArgs event) {
            System.out.printf("Received Session Event %s with info %s\n", event.getEvent(), event.getInfo());
        }
	}

	public void displayMessage(BytesXMLMessage msg, String Handle, boolean bPublic, boolean selfUpdate) {
		Date date = new Date();
		String dateFormatted = formatter.format(date);
		if (bPublic) {
			Handle = "Public";
		}
		// if this is a private message from a user we've not chatted with before add a window
		if (!bPublic && !chatPaneIndexes.containsKey(Handle)) {
			createPrivateChat(Handle, false);
		}

		int index = chatPaneIndexes.get(Handle);
		System.out.println("displaymessage "+Handle+" index: "+index);
//		publicChatMessagesPane.setFont(chatFontBold);
		String chatMessage;
		if (bPublic) {
			chatMessage = msg.getDestination().getName().substring(msg.getDestination().getName().lastIndexOf("/")+1)+": ";
		} else if (selfUpdate) {
			chatMessage = Self+" (private): ";
		} else {
			chatMessage = Handle+" (private): ";
		}
		// Phew this is a lot of casting...
		JTextPane pane = (JTextPane) ((JScrollPane)chatTabs.getComponentAt(index)).getViewport().getView();
		StyledDocument chatDocument = pane.getStyledDocument();
	    try {
	    	chatDocument.insertString(chatDocument.getLength(), chatMessage, userName);
	    	chatDocument.insertString(chatDocument.getLength(),dateFormatted+LF, dateStyle);
			chatMessage = new String(msg.getAttachmentByteBuffer().array());
			// if you type really quickly sometimes the final LF gets dropped
			if (!chatMessage.endsWith(LF)) {
				chatMessage += LF;
			}
			chatDocument.insertString(chatDocument.getLength(), chatMessage, null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
}
