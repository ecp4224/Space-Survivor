/**
 * Programmer: Eddie Penta
 * Date: Mar 25, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: CustomString.java
 */
package com.eddie.rpeg.engine.render.gui;

import java.awt.Font;
import com.eddie.rpeg.engine.system.Core;

public final class CustomString {
	private String line;
	private Font font = Core.DEFAULT_FONT;
	private boolean bold;
	private boolean italic;
	public static final String DEFAULT_FONT = Core.DEFAULT_FONT_NAME;
	
	public CustomString(String line) { this.line = line; }
	
	public void setText(String text, boolean bold, boolean italic, int size) {
		this.line = text;
		setBold(bold);
		setItalic(italic);
		setSize(size);
	}
	
	public void setText(String text, boolean bold, boolean italic) {
		setText(text, bold, italic, 12);
	}
	
	public void setText(String text, boolean bold) {
		setText(text, bold, false, 12);
	}
	
	public void setText(String text) {
		setText(text, false, false, 12);
	}
	
	public void setBold(boolean bold) {
		this.bold = bold;
		updateFont();
	}
	
	public void setItalic(boolean italic) {
		this.italic = italic;
		updateFont();
	}
	
	public void setFont(Font f) {
		this.font = f;
		this.bold = f.isBold();
		this.italic = f.isItalic();
		f.getFamily();
	}
	
	public void setSize(float size) {
		font = font.deriveFont(size);
	}
	
	public int getSize() {
		return font.getSize();
	}
	
	public void setStyle(String font_name) {
		updateFont();
	}
	
	public String getFontStyle() {
		return font.getFamily();
	}
	
	public boolean isBold() {
		return font.isBold();
	}
	
	public boolean isItalic() {
		return font.isItalic();
	}
	
	public String getText() {
		return line;
	}
	
	public Font getFont() {
		if (font == null)
			font = Core.DEFAULT_FONT;
		return font;
	}
	
	private void updateFont() {
		int temp = Font.PLAIN;
		if (bold && !italic)
			temp = Font.BOLD;
		else if (!bold && italic)
			temp = Font.ITALIC;
		else if (bold && italic)
			temp = Font.BOLD + Font.ITALIC;
		font = font.deriveFont(temp);
	}
	
	@Override
	public int hashCode() {
		return line.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CustomString) {
			CustomString s = (CustomString)obj;
			return s.line.equals(line) && s.font.equals(font);
		}
		if (obj instanceof String) {
			String s = (String)obj;
			return s.equals(line);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return line;
	}
}
