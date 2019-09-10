package ru.alexanderdv.swingutilities.components;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

/**
 * 
 * @author AlexandrDV
 *
 */
public class EmailFormattedTextField extends JTextField
{
	private static final long serialVersionUID = -6546322839299284739L;

	/**
	 * Creates new EmailFormattedTextField with text 'text'
	 * 
	 * @param text
	 *            - text of TextField
	 */
	public EmailFormattedTextField(String text)
	{
		super(text);
		addFocusListener(new FocusAdapter()
		{
			public void focusLost(FocusEvent e)
			{
				String text1 = getText();
				if (text1.indexOf('@')>=text1.indexOf('.'))
					setText("");
			}
		});
	}

	/**
	 * Creates new EmailFormattedTextField without text
	 */
	public EmailFormattedTextField()
	{
		this("");
	}
}

