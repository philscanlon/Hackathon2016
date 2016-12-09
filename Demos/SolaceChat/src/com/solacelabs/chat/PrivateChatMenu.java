package com.solacelabs.chat;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

public class PrivateChatMenu extends MouseAdapter {
	private JPopupMenu popup = new JPopupMenu();
	private Action privateChatAction;
    private JCheckBox Handle;
    private ChatClientApp chatClientApp;
    private boolean firstTime = true;
    
	public PrivateChatMenu(JCheckBox handle, ChatClientApp ccApp) {
		this.Handle = handle;
		chatClientApp = ccApp;
		
		privateChatAction = new AbstractAction("Send Private Chat Message to "+Handle.getText()) {
            @Override
            public void actionPerformed(ActionEvent e) {
            	chatClientApp.createPrivateChat(Handle.getText(), true);
            }

        };
        popup.add(privateChatAction);
 //       popup.addSeparator();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 3) {
            if (!(e.getSource() instanceof JCheckBox)) {
                return;
            }
            displayPopup(e);
        }
    }
	private void displayPopup(MouseEvent e) {

		// Sometimes the menu pops up and then jumps a bit.  Hopefully this will fix it!
		if (!popup.isVisible()) {
			// this might seem strange but it works round an annoying window activation/focus problem (on Mac maybe elsewhere) where
			// if you right click a directory name checkbox when the window doesn't have focus, it works but the window never gets focus
			// and you have to click away and click back to make the UI work again
			if (firstTime) {
			   chatClientApp.bringToFront();
			   firstTime = false;
			   return;
			} else {
				firstTime = true;
			}
			
			// can add some logic if user is grayed out or idle
			if (Handle.isEnabled()) {
				privateChatAction.setEnabled(true);
			} else {
				privateChatAction.setEnabled(false);
			}
	
			int nx = e.getX();
	
			if (nx > 500) {
			    nx = nx - popup.getSize().width;
			}
	
			popup.show(e.getComponent(), nx, e.getY() - popup.getSize().height);
		}
	}
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 3) {
            if (!(e.getSource() instanceof JCheckBox)) {
                return;
            }
            displayPopup(e);
        }
    }
}
