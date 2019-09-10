package ru.alexanderdv.swingutilities.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTextField;

/**
 * ButtonX
 * 
 * @author AlexandrDV/AlexanderDV
 *
 */
public class ButtonX extends JTextField
{
	public static boolean anticntrlc, antiscreenshot;
	public static KeyListener antish;
	private static final long serialVersionUID = 5629188079550741270L;
	private boolean selected = false, clicked = false, pressed = false;
	private Color selectedBackground, pressedBackground, normalBackground, disabledBackground, clickedBackground, clickedSelectedBackground,
			clickedPressedBackground;
	private Color selectedFrame, pressedFrame, normalFrame, disabledFrame, clickedFrame, clickedSelectedFrame, clickedPressedFrame;
	private Color selectedForeground, pressedForeground, normalForeground, disabledForeground, clickedForeground, clickedSelectedForeground,
			clickedPressedForeground;
	private ArrayList<ActionListener> actionListeners;
	private int framesize = 4;
	private int[] rounding;
	private boolean disabled;
	private boolean cntrl;
	private boolean canCopy;

	/**
	 * 
	 * @param text
	 * @param rounding
	 * @param rect
	 */
	public ButtonX(String text, int[] rounding)
	{
		this.disabled = false;
		this.rounding = rounding;
		setEditable(false);
		cntrl = false;
		if (anticntrlc)
			addKeyListener(new KeyAdapter()
			{
				@Override
				public void keyReleased(KeyEvent e)
				{
					if (e.getKeyCode() == KeyEvent.VK_CONTROL)
						cntrl = false;
					if (e.getKeyCode() == KeyEvent.VK_C && cntrl && !canCopy)
					{
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						clipboard.setContents(new StringSelection(""), null);
					}
				}

				@Override
				public void keyPressed(KeyEvent e)
				{
					if (e.getKeyCode() == KeyEvent.VK_CONTROL)
						cntrl = true;
					if (e.getKeyCode() == KeyEvent.VK_C && cntrl && !canCopy)
					{
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						clipboard.setContents(new StringSelection(""), null);
					}

				}
			});
		if (antiscreenshot)
			addKeyListener(antish);
		setText(text);
		setBorder(null);
		setHorizontalAlignment(CENTER);
		actionListeners = new ArrayList<ActionListener>();
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				if (!isDisabled())
				{
					selected = true;
					repaint();
				}
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				if (!isDisabled())
				{
					pressed = true;
					repaint();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				if (!isDisabled())
				{
					clicked = true;
					pressed = false;
					repaint();
					ActionEvent ev = new ActionEvent(ButtonX.this, 45, "click");
					if (actionListeners != null)
						for (ActionListener listener : actionListeners)
							listener.actionPerformed(ev);
				}
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				if (!isDisabled())
				{
					selected = false;
					repaint();
				}
			}
		});
		selectedBackground = new Color(210, 210, 210);
		pressedBackground = new Color(180, 180, 180);
		normalBackground = new Color(255, 255, 255);
		disabledBackground = new Color(200, 200, 200);
		clickedBackground = new Color(0, 150, 255);
		clickedSelectedBackground = new Color(0, 120, 220);
		clickedPressedBackground = new Color(0, 70, 170);
		selectedFrame = new Color(160, 160, 160);
		pressedFrame = new Color(130, 130, 130);
		normalFrame = new Color(200, 200, 200);
		disabledFrame = new Color(150, 150, 150);
		clickedFrame = new Color(0, 110, 200);
		clickedSelectedFrame = new Color(0, 90, 170);
		clickedPressedFrame = new Color(0, 50, 120);
		selectedForeground = new Color(0, 0, 0);
		pressedForeground = new Color(0, 0, 0);
		normalForeground = new Color(0, 0, 0);
		disabledForeground = new Color(0, 0, 0);
		clickedForeground = new Color(0, 0, 0);
		clickedSelectedForeground = new Color(0, 0, 0);
		clickedPressedForeground = new Color(0, 0, 0);
		repaint();
	}

	/**
	 * 
	 * @param g
	 *            - Graphics
	 * @param x1
	 *            - first x
	 * @param y1
	 *            - first y
	 * @param x2
	 *            - second x
	 * @param y2
	 *            - second y
	 * @param rounding
	 *            - power of rounding angles of rectangle
	 */
	void fillRoundedRect(Graphics g, int x1, int y1, int x2, int y2, int r1, int r2, int r3, int r4)
	{
		r1 *= 2;
		r2 *= 2;
		r3 *= 2;
		r4 *= 2;
		g.fillOval(x1, y1, Math.min(x1 + r1, x2) - x1, Math.min(y1 + r1, y2) - y1);
		g.fillOval(x2 - (Math.min(x1 + r2, x2) - x1), y1, (Math.min(x1 + r2, x2) - x1), Math.min(y1 + r2, y2) - y1);
		g.fillOval(x2 - (Math.min(x1 + r3, x2) - x1), y2 - (Math.min(y1 + r3, y2) - y1), Math.min(x1 + r3, x2) - x1, Math.min(y1 + r3, y2) - y1);
		g.fillOval(x1, y2 - (Math.min(y1 + r4, y2) - y1), Math.min(x1 + r4, x2) - x1, (Math.min(y1 + r4, y2) - y1));
		g.fillRect(x1 + r1 / 2, y1, x2 - x1 - r2 / 2 - r1 / 2, (y2 - y1) / 2);
		g.fillRect(x1, y1 + r1 / 2, (x2 - x1) / 2, y2 - y1 - r4 / 2 - r1 / 2);
		g.fillRect(x1 + r4 / 2, (y2 - y1) / 2, x2 - x1 - r3 / 2 - r4 / 2, y2 - (y2 - y1) / 2);
		g.fillRect((x2 - x1) / 2, y1 + r2 / 2, x2 - (x2 - x1) / 2, y2 - y1 - r4 / 2 - r2 / 2);
	}

	/**
	 * @param g
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g)
	{
		g.setColor(SystemColor.info);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(!isDisabled() ? (isPressed() ? (isClicked() ? clickedPressedFrame : pressedFrame)
				: (isClicked() ? (isSelected() ? clickedSelectedFrame : clickedFrame) : (isSelected() ? selectedFrame : normalFrame))) : disabledFrame);
		fillRoundedRect(g, 0, 0, getWidth() - 1, getHeight() - 1, rounding[0], rounding[1], rounding[2], rounding[3]);
		g.setColor(!isDisabled() ? (isPressed() ? (isClicked() ? clickedPressedBackground : pressedBackground)
				: (isClicked() ? (isSelected() ? clickedSelectedBackground : clickedBackground) : (isSelected() ? selectedBackground : normalBackground)))
				: disabledBackground);
		setForeground(!isDisabled() ? (isPressed() ? (isClicked() ? clickedPressedForeground : pressedForeground)
				: (isClicked() ? (isSelected() ? clickedSelectedForeground : clickedForeground) : (isSelected() ? selectedForeground : normalForeground)))
				: disabledForeground);
		fillRoundedRect(g, framesize, framesize, getWidth() - framesize - 1, getHeight() - framesize - 1, (((int) (rounding[0] * 0.8)) == 0 ? rounding[0]
				: (int) (rounding[0] * 0.8)), (((int) (rounding[1] * 0.8)) == 0 ? rounding[1] : (int) (rounding[1] * 0.8)), (((int) (rounding[2] * 0.8)) == 0
						? rounding[2]
						: (int) (rounding[2] * 0.8)), (((int) (rounding[3] * 0.8)) == 0 ? rounding[3] : (int) (rounding[3] * 0.8)));
		g.setFont(getFont());
		super.setBackground(new Color(0, 0, 0, 0));
		super.paint(g);
	}

	/**
	 * Adds new ActionListener 'listener' to list of ActionListener's
	 * 
	 * @param listener
	 *            - ActionListener to add
	 */
	public void addActionListener(ActionListener listener)
	{
		actionListeners.add(listener);
	}

	/**
	 * 
	 * @param min
	 * @param val
	 * @param max
	 * @return
	 */
	public static int clamp(int min, int val, int max)
	{
		return Math.max(min, Math.min(val, max));
	}

	/**
	 * Clears action listeners list
	 */
	public void clearActionListeners()
	{
		actionListeners.clear();
	}

	/**
	 * @return the rounding
	 */
	public int[] getRounding()
	{
		return rounding;
	}

	/**
	 * Sets color of ButtonX selected state after click to 'clickedColor'
	 * 
	 * @param rounding
	 *            - color using to set color of ButtonX text
	 */
	public void setRounding(int[] rounding)
	{
		this.rounding = rounding;
		repaint();
	}

	/**
	 * @return the frameSize
	 */
	public int getFramesize()
	{
		return framesize;
	}

	/**
	 * Sets color of ButtonX selected state after click to 'clickedColor'
	 * 
	 * @param framesize
	 *            - color using to set color of ButtonX text
	 */
	public void setFramesize(int frameSize)
	{
		this.framesize = frameSize;
		repaint();
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected()
	{
		return selected;
	}

	/**
	 * Sets color of ButtonX selected state after click to 'clickedColor'
	 * 
	 * @param selected
	 *            - color using to set color of ButtonX text
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
		repaint();
	}

	/**
	 * @return the clicked
	 */
	public boolean isClicked()
	{
		return clicked;
	}

	/**
	 * Sets color of ButtonX selected state after click to 'clickedColor'
	 * 
	 * @param clicked
	 *            - color using to set color of ButtonX text
	 */
	public void setClicked(boolean clicked)
	{
		this.clicked = clicked;
		repaint();
	}

	/**
	 * @return the pressed
	 */
	public boolean isPressed()
	{
		return pressed;
	}

	/**
	 * Sets color of ButtonX selected state after click to 'clickedColor'
	 * 
	 * @param pressed
	 *            - color using to set color of ButtonX text
	 */
	public void setPressed(boolean pressed)
	{
		this.pressed = pressed;
		repaint();
	}

	/**
	 * @return the canCopy
	 */
	public boolean isCanCopy()
	{
		return canCopy;
	}

	/**
	 * @param canCopy
	 *            the canCopy to set
	 */
	public void setCanCopy(boolean canCopy)
	{
		this.canCopy = canCopy;
	}

	/**
	 * Simulates click on button
	 */
	public void click()
	{
		if (!isDisabled())
		{
			clicked = true;
			pressed = false;
			repaint();
			ActionEvent ev = new ActionEvent(ButtonX.this, 45, "click");
			if (actionListeners != null)
				for (ActionListener listener : actionListeners)
					listener.actionPerformed(ev);
		}
	}

	/**
	 * @return the selectedBackground
	 */
	public Color getSelectedBackground()
	{
		return selectedBackground;
	}

	/**
	 * @param selectedBackground
	 *            the selectedBackground to set
	 */
	public void setSelectedBackground(Color selectedBackground)
	{
		this.selectedBackground = selectedBackground;
		repaint();
	}

	/**
	 * @return the pressedBackground
	 */
	public Color getPressedBackground()
	{
		return pressedBackground;
	}

	/**
	 * @param pressedBackground
	 *            the pressedBackground to set
	 */
	public void setPressedBackground(Color pressedBackground)
	{
		this.pressedBackground = pressedBackground;
		repaint();
	}

	/**
	 * @return the normalBackground
	 */
	public Color getNormalBackground()
	{
		return normalBackground;
	}

	/**
	 * @param normalBackground
	 *            the normalBackground to set
	 */
	public void setNormalBackground(Color normalBackground)
	{
		this.normalBackground = normalBackground;
		repaint();
	}

	/**
	 * @return the disabledBackground
	 */
	public Color getDisabledBackground()
	{
		return disabledBackground;
	}

	/**
	 * @param disabledBackground
	 *            the disabledBackground to set
	 */
	public void setDisabledBackground(Color disabledBackground)
	{
		this.disabledBackground = disabledBackground;
		repaint();
	}

	/**
	 * @return the clickedBackground
	 */
	public Color getClickedBackground()
	{
		return clickedBackground;
	}

	/**
	 * @param clickedBackground
	 *            the clickedBackground to set
	 */
	public void setClickedBackground(Color clickedBackground)
	{
		this.clickedBackground = clickedBackground;
		repaint();
	}

	/**
	 * @return the clickedSelectedBackground
	 */
	public Color getClickedSelectedBackground()
	{
		return clickedSelectedBackground;
	}

	/**
	 * @param clickedSelectedBackground
	 *            the clickedSelectedBackground to set
	 */
	public void setClickedSelectedBackground(Color clickedSelectedBackground)
	{
		this.clickedSelectedBackground = clickedSelectedBackground;
		repaint();
	}

	/**
	 * @return the clickedPressedBackground
	 */
	public Color getClickedPressedBackground()
	{
		return clickedPressedBackground;
	}

	/**
	 * @param clickedPressedBackground
	 *            the clickedPressedBackground to set
	 */
	public void setClickedPressedBackground(Color clickedPressedBackground)
	{
		this.clickedPressedBackground = clickedPressedBackground;
		repaint();
	}

	/**
	 * @return the selectedFrame
	 */
	public Color getSelectedFrame()
	{
		return selectedFrame;
	}

	/**
	 * @param selectedFrame
	 *            the selectedFrame to set
	 */
	public void setSelectedFrame(Color selectedFrame)
	{
		this.selectedFrame = selectedFrame;
		repaint();
	}

	/**
	 * @return the pressedFrame
	 */
	public Color getPressedFrame()
	{
		return pressedFrame;
	}

	/**
	 * @param pressedFrame
	 *            the pressedFrame to set
	 */
	public void setPressedFrame(Color pressedFrame)
	{
		this.pressedFrame = pressedFrame;
		repaint();
	}

	/**
	 * @return the normalFrame
	 */
	public Color getNormalFrame()
	{
		return normalFrame;
	}

	/**
	 * @param normalFrame
	 *            the normalFrame to set
	 */
	public void setNormalFrame(Color normalFrame)
	{
		this.normalFrame = normalFrame;
		repaint();
	}

	/**
	 * @return the disabledFrame
	 */
	public Color getDisabledFrame()
	{
		return disabledFrame;
	}

	/**
	 * @param disabledFrame
	 *            the disabledFrame to set
	 */
	public void setDisabledFrame(Color disabledFrame)
	{
		this.disabledFrame = disabledFrame;
		repaint();
	}

	/**
	 * @return the clickedFrame
	 */
	public Color getClickedFrame()
	{
		return clickedFrame;
	}

	/**
	 * @param clickedFrame
	 *            the clickedFrame to set
	 */
	public void setClickedFrame(Color clickedFrame)
	{
		this.clickedFrame = clickedFrame;
		repaint();
	}

	/**
	 * @return the clickedSelectedFrame
	 */
	public Color getClickedSelectedFrame()
	{
		return clickedSelectedFrame;
	}

	/**
	 * @param clickedSelectedFrame
	 *            the clickedSelectedFrame to set
	 */
	public void setClickedSelectedFrame(Color clickedSelectedFrame)
	{
		this.clickedSelectedFrame = clickedSelectedFrame;
		repaint();
	}

	/**
	 * @return the clickedPressedFrame
	 */
	public Color getClickedPressedFrame()
	{
		return clickedPressedFrame;
	}

	/**
	 * @param clickedPressedFrame
	 *            the clickedPressedFrame to set
	 */
	public void setClickedPressedFrame(Color clickedPressedFrame)
	{
		this.clickedPressedFrame = clickedPressedFrame;
		repaint();
	}

	/**
	 * @return the selectedForeground
	 */
	public Color getSelectedForeground()
	{
		return selectedForeground;
	}

	/**
	 * @param selectedForeground
	 *            the selectedForeground to set
	 */
	public void setSelectedForeground(Color selectedForeground)
	{
		this.selectedForeground = selectedForeground;
		repaint();
	}

	/**
	 * @return the pressedForeground
	 */
	public Color getPressedForeground()
	{
		return pressedForeground;
	}

	/**
	 * @param pressedForeground
	 *            the pressedForeground to set
	 */
	public void setPressedForeground(Color pressedForeground)
	{
		this.pressedForeground = pressedForeground;
		repaint();
	}

	/**
	 * @return the normalForeground
	 */
	public Color getNormalForeground()
	{
		return normalForeground;
	}

	/**
	 * @param normalForeground
	 *            the normalForeground to set
	 */
	public void setNormalForeground(Color normalForeground)
	{
		this.normalForeground = normalForeground;
		repaint();
	}

	/**
	 * @return the disabledForeground
	 */
	public Color getDisabledForeground()
	{
		return disabledForeground;
	}

	/**
	 * @param disabledForeground
	 *            the disabledForeground to set
	 */
	public void setDisabledForeground(Color disabledForeground)
	{
		this.disabledForeground = disabledForeground;
		repaint();
	}

	/**
	 * @return the clickedForeground
	 */
	public Color getClickedForeground()
	{
		return clickedForeground;
	}

	/**
	 * @param clickedForeground
	 *            the clickedForeground to set
	 */
	public void setClickedForeground(Color clickedForeground)
	{
		this.clickedForeground = clickedForeground;
		repaint();
	}

	/**
	 * @return the clickedSelectedForeground
	 */
	public Color getClickedSelectedForeground()
	{
		return clickedSelectedForeground;
	}

	/**
	 * @param clickedSelectedForeground
	 *            the clickedSelectedForeground to set
	 */
	public void setClickedSelectedForeground(Color clickedSelectedForeground)
	{
		this.clickedSelectedForeground = clickedSelectedForeground;
		repaint();
	}

	/**
	 * @return the clickedPressedForeground
	 */
	public Color getClickedPressedForeground()
	{
		return clickedPressedForeground;
	}

	/**
	 * @param clickedPressedForeground
	 *            the clickedPressedForeground to set
	 */
	public void setClickedPressedForeground(Color clickedPressedForeground)
	{
		this.clickedPressedForeground = clickedPressedForeground;
		repaint();
	}

	/**
	 * @return the enabled
	 */
	public boolean isDisabled()
	{
		return disabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
		repaint();
	}
}
