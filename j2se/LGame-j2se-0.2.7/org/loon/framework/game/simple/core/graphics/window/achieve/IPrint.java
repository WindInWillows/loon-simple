package org.loon.framework.game.simple.core.graphics.window.achieve;

import java.awt.Color;
import java.awt.Image;

import org.loon.framework.game.simple.action.map.shapes.Vector2D;
import org.loon.framework.game.simple.core.graphics.device.LGraphics;

/**
 * Copyright 2008 - 2009
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loonframework
 * @author chenpeng
 * @email��ceponline@yahoo.com.cn
 * @version 0.1.1
 */
public class IPrint {

	private char[] showMessages;

	private int iconWidth;

	private Color fontColor = Color.white;

	private int interceptMaxString;

	private int interceptCount;

	private int messageLength = 10;

	private String messages;

	private boolean onComplete, newLine, visible;

	private StringBuffer messageBuffer = new StringBuffer(messageLength);

	private int width, height, leftOffset, topOffset, next, messageCount;

	private double alpha;

	private int size, tmp_left, left, fontSize, fontHeight;

	private Vector2D vector;

	private Image creeseIcon;

	public IPrint(Vector2D vector, int width, int height) {
		this("", vector, width, height);
	}

	public IPrint(String context, Vector2D vector, int width, int height) {
		this.setMessage(context);
		this.vector = vector;
		this.width = width;
		this.height = height;
	}

	public void setMessage(String context) {
		setMessage(context, false);
	}

	public void setMessage(String context, boolean isComplete) {
		this.visible = false;
		this.showMessages = new char[] { '\0' };
		this.interceptMaxString = 0;
		this.next = 0;
		this.messageCount = 0;
		this.interceptCount = 0;
		this.size = 0;
		this.tmp_left = 0;
		this.left = 0;
		this.fontSize = 0;
		this.fontHeight = 0;
		this.messages = context;
		this.next = context.length();
		this.onComplete = false;
		this.newLine = false;
		this.messageCount = 0;
		this.messageBuffer.delete(0, messageBuffer.length());
		if (isComplete) {
			this.complete();
		}
		this.visible = true;
	}

	public String getMessage() {
		return messages;
	}

	private Color getColor(char flagName) {
		if ('r' == flagName || 'R' == flagName) {
			return Color.red;
		}
		if ('b' == flagName || 'B' == flagName) {
			return Color.black;
		}
		if ('l' == flagName || 'L' == flagName) {
			return Color.blue;
		}
		if ('g' == flagName || 'G' == flagName) {
			return Color.green;
		}
		if ('o' == flagName || 'O' == flagName) {
			return Color.orange;
		}
		if ('y' == flagName || 'Y' == flagName) {
			return Color.yellow;
		} else {
			return null;
		}
	}

	public void draw(LGraphics g) {
		draw(g, Color.WHITE);
	}

	private void drawMessage(LGraphics g, Color old) {
		if (!visible) {
			return;
		}
		synchronized (showMessages) {
			this.size = showMessages.length;
			this.fontSize = g.getFont().getSize();
			this.fontHeight = g.getFontMetrics().getHeight();
			this.tmp_left = (width - (fontSize * messageLength)) / 2
					- (int) (fontSize * 1.5);
			this.left = tmp_left;
			int index = 0, offset = 0, font = 0, tmp_font = 0;
			int fontSizeDouble = fontSize * 2;
			for (int i = 0; i < size; i++) {
				if (interceptCount < interceptMaxString) {
					interceptCount++;
					g.setColor(fontColor);
					continue;
				} else {
					interceptMaxString = 0;
					interceptCount = 0;
				}
				if (showMessages[i] == 'n'
						&& showMessages[i > 0 ? i - 1 : 0] == '\\') {
					index = 0;
					left = 0;
					offset++;
					continue;
				} else if (showMessages[i] == '\n') {
					index = 0;
					left = tmp_left;
					offset++;
					continue;
				} else if (showMessages[i] == '<') {
					Color color = getColor(showMessages[i < size - 1 ? i + 1
							: i]);
					if (color != null) {
						interceptMaxString = 1;
						fontColor = color;
					}
					next();
					continue;
				} else if (showMessages[i] == '/') {
					if (showMessages[i < size - 1 ? i + 1 : i] == '>') {
						interceptMaxString = 1;
						fontColor = old;
					}
					continue;
				} else if (index > messageLength) {
					index = 0;
					left = tmp_left;
					offset++;
					newLine = false;
				} else if (showMessages[i] == '\\') {
					continue;
				}
				String mes = String.valueOf(showMessages[i]);
				tmp_font = g.getFontMetrics().charWidth(showMessages[i]);
				if (Character.isLetter(showMessages[i])) {
					font = tmp_font;
				} else {
					font = fontSize;
				}
				left += font;
				if (i != size - 1) {
					g.setAntialias(true);
					g.drawString(mes, vector.x() + left + leftOffset,
							(offset * fontHeight) + vector.y() + fontSizeDouble
									+ topOffset);
					g.setAntialias(false);
				} else if (!newLine && !onComplete) {
					g.drawImage(creeseIcon, vector.x() + left + leftOffset
							+ iconWidth, (offset * fontHeight) + vector.y()
							+ fontSize + topOffset);
				}
				index++;
			}
			if (messageCount == next) {
				onComplete = true;
			}
		}
	}

	public void draw(LGraphics g, Color old) {
		if (!visible) {
			return;
		}
		alpha = g.getAlpha();
		g.setAlpha(1.0);
		drawMessage(g, old);
		g.setAlpha(alpha);
	}

	public void complete() {
		synchronized (showMessages) {
			this.onComplete = true;
			this.messageCount = messages.length();
			this.next = messageCount;
			this.showMessages = messages.toCharArray();
			this.size = showMessages.length;
		}
	}

	public boolean isComplete() {
		return onComplete;
	}

	public boolean next() {
		synchronized (showMessages) {
			if (!onComplete) {
				if (messageCount == next) {
					onComplete = true;
					return false;
				}
				if (messageBuffer.length() > 0) {
					messageBuffer.delete(messageBuffer.length() - 1,
							messageBuffer.length());
				}
				this.messageBuffer.append(messages.charAt(messageCount));
				this.messageBuffer.append("_");
				this.showMessages = messageBuffer.toString().toCharArray();
				this.size = showMessages.length;
				this.messageCount++;
			} else {
				return false;
			}
			return true;
		}
	}

	public Image getCreeseIcon() {
		return creeseIcon;
	}

	public void setCreeseIcon(Image creeseIcon) {
		this.creeseIcon = creeseIcon;
		this.iconWidth = creeseIcon.getWidth(null);
	}

	public int getMessageLength() {
		return messageLength;
	}

	public void setMessageLength(int messageLength) {
		this.messageLength = messageLength;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLeftOffset() {
		return leftOffset;
	}

	public void setLeftOffset(int leftOffset) {
		this.leftOffset = leftOffset;
	}

	public int getTopOffset() {
		return topOffset;
	}

	public void setTopOffset(int topOffset) {
		this.topOffset = topOffset;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
