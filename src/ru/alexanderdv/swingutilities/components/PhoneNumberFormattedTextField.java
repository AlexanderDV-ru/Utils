package ru.alexanderdv.swingutilities.components;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

/**
 * 
 * @author AlexandrDV
 *
 */
public class PhoneNumberFormattedTextField extends JTextField
{
	private static final long serialVersionUID = -6546322839299284739L;

	/**
	 * Creates new PhoneNumberFormattedTextField with text 'text'
	 * 
	 * @param text
	 *            - text of TextField
	 */
	public PhoneNumberFormattedTextField(String text)
	{
		super(text);
		addFocusListener(new FocusAdapter()
		{
			public void focusLost(FocusEvent e)
			{
				String text1 = getText().replace(" ", "").replace("-", "").replace("(", "").replace(")", "");
				String text2 = text1.replace("1", "0").replace("2", "0").replace("3", "0").replace("4", "0").replace("5", "0").replace("6", "0").replace("7",
						"0").replace("8", "0").replace("9", "0");
				if (!text2.startsWith("+"))
					text2 = "+" + text2;
				if (text2.equals("+00000000000") || text2.equals("+000000000000") || text2.equals("+0000000000000"))
					setText((!text1.startsWith("+") ? "+" : "") + text1.substring(0, text1.length() - 10) + "(" + text1.substring(text1.length() - 10, text1
							.length() - 7) + ")" + text1.substring(text1.length() - 7, text1.length() - 4) + "-" + text1.substring(text1.length() - 4, text1
									.length() - 2) + "-" + text1.substring(text1.length() - 2));
				else setText("");
			}
		});
	}

	/**
	 * Creates new PhoneNumberFormattedTextField without text
	 */
	public PhoneNumberFormattedTextField()
	{
		this("");
	}
}
