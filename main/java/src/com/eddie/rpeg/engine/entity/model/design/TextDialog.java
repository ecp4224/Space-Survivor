/**
 * Programmer: Eddie Penta
 * Date: Mar 25, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: TextDialog.java
 */
package com.eddie.rpeg.engine.entity.model.design;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.eddie.rpeg.engine.entity.model.StaticEntity;
import com.eddie.rpeg.engine.entity.types.Foreground;
import com.eddie.rpeg.engine.render.gui.CustomString;
import com.eddie.rpeg.engine.system.Core;
import com.eddie.rpeg.engine.level.Level;

public class TextDialog extends StaticEntity implements Foreground {
	private static final long serialVersionUID = 4821190450309030767L;
	final ArrayList<SH> text = new ArrayList<SH>();
	int char_offset;
	int line_offset;
	int timeout;
	boolean speed;
	
	public TextDialog(Core system, Level level) {
		super("TEXT", system, level);
		setX(0);
		setY(0);
	}
	
	public void addText(CustomString s, int line) {
		SH ss = new SH();
		ss.s = s;
		ss.line = line;
		ss.ID = text.size();
		text.add(ss);
	}
	
	public void drawText(Graphics g) {
		ArrayList<SH> used = new ArrayList<SH>();
		int xoffset = 10;
		int yoffset = (int)((system.getMaxScreenY() - (system.getMaxScreenY() / 5)) + 50);
		for (int i = 0; i <= line_offset; i++) {
			if (i >= 3)
				break;
			if (i == line_offset) {
				int current_char_offset = 0;
				final int total_chars = getTotalChars(i);
				while (current_char_offset < char_offset) {
					SH current = null;
					for (SH s : text) {
						if (used.contains(s))
							continue;
						else if (s.line == i && current == null)
							current = s;
						else if (s.line == i && s.ID < current.ID)
							current = s;
					}
					if (current == null) {
						char_offset = 0;
						line_offset++;
						break;
					}
					for (int z = 0; z < current.s.getText().toCharArray().length; z++) {
						if (current_char_offset >= char_offset)
							break;
						if (current_char_offset  >= total_chars)
							break;
						g.setFont(current.s.getFont());
						g.drawString("" + current.s.getText().toCharArray()[z], xoffset, yoffset);
						current_char_offset++;
						xoffset += 	g.getFontMetrics().stringWidth("" + current.s.getText().toCharArray()[z]);
					}
					used.add(current);
				}
			}
			else {
				while (true) {
					SH current = null;
					for (SH s : text) {
						if (used.contains(s))
							continue;
						else if (s.line == i && current == null)
							current = s;
						else if (s.line == i && s.ID < current.ID)
							current = s;
					}
					if (current == null)
						break;
					for (int z = 0; z < current.s.getText().toCharArray().length; z++) {
						g.setFont(current.s.getFont());
						g.drawString("" + current.s.getText().toCharArray()[z], xoffset, yoffset);
						xoffset += 	g.getFontMetrics().charWidth(current.s.getText().toCharArray()[z]);
					}
					used.add(current);
				}
			}
			yoffset += g.getFontMetrics().getHeight();
			xoffset = 10;
		}
	}
	
	public int getTotalChars(int line) {
		int toreturn = 0;
		for (SH s : text) {
			if (s.line == line)
				toreturn += s.s.getText().toCharArray().length;
		}
		return toreturn;
	}
	
	@Override
	public void setX(double x) {
		super.x = 0;
	}
	
	@Override
	public void setY(double y) {
		super.y = 0;
	}
	
	public void speedUp() {
		speed = true;
	}
	
	public void slowDown() {
		speed = false;
	}
	
	@Override
	public void tick() {
			char_offset++;
	}
	
	@Override
	public int getTimeout() {
		return (speed ? 10 : 75);
	}

	/* (non-Javadoc)
	 * @see com.eddie.game.game.Entity#draw(java.awt.Graphics, java.awt.image.BufferedImage)
	 */
	@Override
	public void draw(Graphics g, BufferedImage screen) {
		g.setColor(Color.gray);
		g.fillRect((int)0, (int)(system.getMaxScreenY() - (system.getMaxScreenY() / 5)), system.getMaxScreenX(), (int)((system.getMaxScreenY() / 5)));
		g.setColor(Color.WHITE);
		drawText(g);
	}
	
	private class SH {
		public CustomString s;
		public int line;
		public int ID;
		
		@Override
		public int hashCode() {
			return ID;
		}
	}
}
