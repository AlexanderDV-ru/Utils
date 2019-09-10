package ru.alexanderdv.swingutilities.components;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

/**
 * ScrollbarX
 * 
 * @author AlexandrDV/AlexanderDV
 *
 */
public class ScrollbarX extends JPanel
{
	private static final long serialVersionUID = -4660676518023017891L;
	private ButtonX btn;
	private float size;
	private int mousePos;
	private boolean horizontal;

	public ScrollbarX(float size, boolean horizontal)
	{
		super();
		this.horizontal = horizontal;
		this.size = size;
		ScrollbarX scrollBar = this;
		setLayout(null);
		btn = new ButtonX("", new int[] { 8, 8, 8, 8 });
		scrollBar.add(btn);
		btn.addMouseMotionListener(new MouseMotionListener()
		{

			@Override
			public void mouseMoved(MouseEvent e)
			{
				if (btn.isDisabled())
					if (e.getY() - mousePos != 0)
						btn.setLocation(btn.getX(), Math.max(Math.min(btn.getY() + e.getY() - mousePos, scrollBar.getHeight() - btn.getHeight()), 0));
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
				mouseMoved(e);
			}
		});
		btn.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				btn.setDisabled(false);
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				btn.setDisabled(true);
				mousePos = horizontal ? e.getX() : e.getY();
			}
		});
		resize();
	}

	public ScrollbarX(boolean horizontal)
	{
		this(1, horizontal);
	}

	public ScrollbarX(float size)
	{
		this(size, false);
	}

	public ScrollbarX()
	{
		this(false);
	}

	@Override
	public void setSize(Dimension d)
	{
		super.setSize(d);
		resize();
	}

	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		resize();
	}

	@Override
	public void setBounds(int x, int y, int width, int height)
	{
		super.setBounds(x, y, width, height);
		resize();
	}

	@Override
	public void setBounds(Rectangle r)
	{
		super.setBounds(r);
		resize();
	}

	public void resize()
	{
		btn.setLocation(0, 0);
		if (horizontal)
			btn.setSize((int) (getHeight() / size), getWidth() - 2);
		else btn.setSize(getWidth() - 2, (int) (getHeight() / size));
	}

}
