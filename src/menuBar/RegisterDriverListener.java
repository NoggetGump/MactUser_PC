package menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlGUI.ControlPanel;

public class RegisterDriverListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(null);
		fc.setDialogTitle("Carregar driver...");
		ControlPanel cp = ControlPanel.getControlPanel();
		
		if(fc.showOpenDialog(cp.getMainContainer()) == JFileChooser.APPROVE_OPTION) {
			File driverFile = fc.getSelectedFile();
			FileInputStream fis = null;
			
			try {
				fis = new FileInputStream(driverFile);
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
				System.out.println(e2.toString());
				return;
			}
			
			byte[] data = new byte[(int) driverFile.length()];
			
			try {
				fis.read(data);
				fis.close();
			} catch (IOException e1) {
				// TODO - Error, couldn't read from the file!
				e1.printStackTrace();
				System.out.println(e1.toString());
				return;
			}

			String driver = null;
			
			try {
				driver = new String(data, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
				System.out.println(e1.toString());
				try {
					driver = new String(data, "ASCII");
				} catch (UnsupportedEncodingException e2) {
					//TODO - Error, encoding not supported!
					e2.printStackTrace();
					System.out.println(e1.toString());
					return;
				}
			}
			
			System.out.println(driver);
			
			// ASK FOR OTHER FIELDS OF OPERATION			
			JTextField type = new JTextField(5);
			JTextField model = new JTextField(5);
			JTextField vendor = new JTextField(5);
			int result;
			
			JPanel myPanel = new JPanel();
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			myPanel.add(new JLabel("Type:"));
			myPanel.add(type);
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			myPanel.add(new JLabel("Model:"));
			myPanel.add(model);
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			myPanel.add(new JLabel("Vendor:"));
			myPanel.add(vendor);
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			
			
			result = JOptionPane.showConfirmDialog(null, myPanel,
					"Please Enter All Data ", JOptionPane.OK_CANCEL_OPTION);
			
			String typeString = type.getText();
			String modelString = model.getText();
			String vendorString = vendor.getText();
			
			if (result == JOptionPane.OK_OPTION) {
				if(driver != null
					&& !typeString.equals("") && !modelString.equals("")
					&& !vendorString.equals("")) {
					
						ControlPanel.getContextnetClient().
						sendDriver2SOM(driver, typeString,
							modelString, vendorString);
						
						return;
				}
				else {
					JOptionPane.showMessageDialog(cp.getMainContainer()
							, "All fields are mandatory and driver must be valid!");
				}
			}
		}
		JOptionPane.showMessageDialog(cp.getMainContainer()
				, "User canceled driver upload operation");
		return;
	}
}
