package org.loon.framework.game.simple.core.graphics.window;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import org.loon.framework.game.simple.action.sprite.WaitAnimation;
import org.loon.framework.game.simple.core.LSystem;
import org.loon.framework.game.simple.core.graphics.LContainer;
import org.loon.framework.game.simple.core.timer.LTimer;
import org.loon.framework.game.simple.utils.GraphicsUtils;
import org.loon.framework.game.simple.utils.PassWordUtils;
import org.loon.framework.game.simple.utils.http.InternalDownload;
import org.loon.framework.game.simple.utils.http.WebClient;

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
 * @email：ceponline@yahoo.com.cn
 * @version 0.1
 */
public class LPicture extends LContainer {

	private Map lazyImage = new HashMap(LSystem.DEFAULT_MAX_CACHE_SIZE * 2);

	private LTimer timer;

	private Image image;

	private WaitAnimation wait;

	private int states;

	public LPicture(int x, int y, int w, int h) {
		super(x, y, w, h);
		this.timer = new LTimer(100);
		this.wait = new WaitAnimation(w, h);
		this.setBackground(Color.WHITE);
		this.wait.setRunning(true);
		this.customRendering = true;
		this.setElastic(false);
		this.setLocked(true);
		this.setLayer(100);
	}

	public void notBackground() {
		setBackground((Image) null);
	}

	public void loadImage(final String fileName) {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				image = GraphicsUtils.loadImage(fileName);
			}
		});
		thread.start();
		thread = null;
	}

	public void loadWebImage(final String url) {
		final String key = PassWordUtils.toMD5(url);
		if (key != null) {
			if (lazyImage.size() > LSystem.DEFAULT_MAX_CACHE_SIZE * 2) {
				lazyImage.clear();
			}
			image = (Image) lazyImage.get(key);
			if (image == null) {
				Thread thread = new Thread(new Runnable() {
					public void run() {
						WebClient client = new WebClient(url);
						InternalDownload inner = client.getInternalDownload();
						inner.download();
						while (states != InternalDownload.COMPLETE
								& states != InternalDownload.ERROR) {
							states = inner.getStatus();
						}
						if (states == InternalDownload.COMPLETE) {
							image = GraphicsUtils.loadImage(inner.getBytes());
							lazyImage.put(key, image);
						}
						inner.reset();
						inner = null;
					}
				});
				thread.start();
				thread = null;
			}
		}
	}

	public Image getImage() {
		return image;
	}

	public void doClick() {

	}

	protected void processMousePressed() {
		if (this.isSelected() && this.input.leftClick()) {
			this.doClick();
		}
	}

	protected void processKeyPressed() {
		if (this.isSelected()
				&& this.input.getKeyPressed() == KeyEvent.VK_ENTER) {
			this.doClick();
		}
	}

	public void update(long elapsedTime) {
		if (visible) {
			super.update(elapsedTime);
			if (timer.action(elapsedTime) && image == null) {
				wait.next();
			}
		}
	}
	
	protected void createCustomUI(Graphics2D g, int x, int y, int w, int h) {
		if (visible) {
			if (image == null) {
				wait.draw(g, x, y, w, h);
			} else {
				g.drawImage(image, x, y, w, h, null);
			}
		}
	}


	protected void processMouseDragged() {
		if (!locked) {
			if (getContainer() != null) {
				getContainer().sendToFront(this);
			}
			this.move(this.input.getMouseDX(), this.input.getMouseDY());
		}
	}

	public int getStates() {
		return states;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getUIName() {
		return "Picture";
	}

}
