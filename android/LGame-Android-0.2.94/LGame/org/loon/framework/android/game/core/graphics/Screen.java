package org.loon.framework.android.game.core.graphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.loon.framework.android.game.IAndroid2DHandler;
import org.loon.framework.android.game.action.map.shapes.RectBox;
import org.loon.framework.android.game.action.sprite.ISprite;
import org.loon.framework.android.game.action.sprite.ISpriteListener;
import org.loon.framework.android.game.action.sprite.Sprites;
import org.loon.framework.android.game.core.EmulatorButtons;
import org.loon.framework.android.game.core.EmulatorListener;
import org.loon.framework.android.game.core.LInput;
import org.loon.framework.android.game.core.LObject;
import org.loon.framework.android.game.core.LSystem;
import org.loon.framework.android.game.core.graphics.device.LGraphics;
import org.loon.framework.android.game.core.graphics.window.actor.Layer;
import org.loon.framework.android.game.core.timer.LTimerContext;
import org.loon.framework.android.game.utils.GraphicsUtils;
import org.loon.framework.android.game.utils.MultitouchUtils;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

/**
 * 
 * Copyright 2008 - 2011
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
 * @email ceponline@yahoo.com.cn
 * @version 0.1.3
 */
public abstract class Screen implements IScreen, LInput {

	// private LTimer adTimer;
	// 线程事件集合
	private final ArrayList<Runnable> runnables;

	private int touchX, touchY, lastTouchX, lastTouchY, touchDX, touchDY,
			touchDirection;

	private float offsetTouchX, offsetMoveX, offsetTouchY, offsetMoveY;

	protected long elapsedTime;

	private final static boolean[] touchDown, keyDown;

	private final static int[] touchPressed, touchReleased;

	private int pressedTouch, releasedTouch;

	private final static int[] keyPressed, keyReleased;

	private final static float ACCEL_OFFEST = 3;

	private final static double LANDSCAPE_OFFSET = 0.2;

	private double landscapeUpdate = 0;

	private int pressedKey, releasedKey;

	private double sensorInclination;

	private float accelX, accelY, accelZ;

	boolean isNext, isMoving, isGravityToKey;

	private int mode;

	private long lastUpdate;

	private Bitmap currentScreen;

	private IAndroid2DHandler handler;

	private int width, height, halfWidth, halfHeight;

	private SensorDirection direction = SensorDirection.NONE;

	private LInput baseInput;

	// 精灵集合
	private Sprites sprites;
	// 桌面集合
	private Desktop desktop;

	private Point touch = new Point(0, 0);

	static {
		keyDown = new boolean[128];
		keyPressed = keyReleased = new int[128];
		touchDown = new boolean[10];
		touchPressed = touchReleased = new int[10];
	}

	public Screen() {
		LSystem.AUTO_REPAINT = true;
		this.mode = SCREEN_CANVAS_REPAINT;
		this.setFPS(getMaxFPS());
		this.runnables = new ArrayList<Runnable>(1);
		this.handler = (IAndroid2DHandler) LSystem.getSystemHandler();
		this.width = handler.getWidth();
		this.height = handler.getHeight();
		this.halfWidth = getWidth() / 2;
		this.halfHeight = getHeight() / 2;
		this.currentScreen = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		this.baseInput = this;
		this.touchX = touchY = lastTouchX = lastTouchY = touchDX = touchDY = 0;
		this.sprites = new Sprites(width, height);
		this.desktop = new Desktop(baseInput, width, height);
		this.isNext = true;
		this.isMoving = false;
	}

	/**
	 * 初始化时加载的数据
	 */
	public void onLoad() {

	}

	/**
	 * 返回精灵监听
	 * 
	 * @return
	 */
	public ISpriteListener getSprListerner() {
		if (sprites == null) {
			return null;
		}
		return sprites.getSprListerner();
	}

	/**
	 * 监听Screen中精灵
	 * 
	 * @param sprListerner
	 */
	public void setSprListerner(ISpriteListener sprListerner) {
		if (sprites == null) {
			return;
		}
		sprites.setSprListerner(sprListerner);
	}

	/**
	 * 获得当前Screen类名
	 */
	public String getName() {
		return getClass().getSimpleName();
	}

	/**
	 * 设定模拟按钮监听器
	 */
	public void setEmulatorListener(EmulatorListener emulator) {
		if (LSystem.getSurface2D() != null) {
			LSystem.getSurface2D().setEmulatorListener(emulator);
		}
	}

	/**
	 * 返回模拟按钮集合
	 * 
	 * @return
	 */
	public EmulatorButtons getEmulatorButtons() {
		if (LSystem.getSurface2D() != null) {
			return LSystem.getSurface2D().getEmulatorButtons();
		}
		return null;
	}

	/**
	 * 设定模拟按钮组是否显示
	 * 
	 * @param visible
	 */
	public void emulatorButtonsVisible(boolean visible) {
		if (LSystem.getSurface2D() != null) {
			try {
				EmulatorButtons es = LSystem.getSurface2D()
						.getEmulatorButtons();
				es.setVisible(visible);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 设定广告显示时间
	 * 
	 * @param delay
	 */
	/*
	 * public void setAdTimer(long delay) { adTimer.setDelay(delay); adDelay =
	 * delay; }
	 */

	/**
	 * 返回广告显示时间
	 * 
	 * @return
	 */
	// public long getAdTimer() {
	// return adTimer.getDelay();
	// }

	/**
	 * 访问指定的网站页面
	 * 
	 * @param url
	 */
	public void openBrowser(final String url) {
		if (handler != null) {
			handler.getLGameActivity().openBrowser(url);
		}
	}

	/**
	 * 广告是否显示
	 * 
	 * @return
	 */
	public boolean isVisibleAD() {
		if (handler != null) {
			return handler.getLGameActivity().isVisibleAD();
		}
		return false;
	}

	/**
	 * 隐藏广告
	 */
	public void hideAD() {
		if (handler != null) {
			handler.getLGameActivity().hideAD();
		}
	}

	/**
	 * 显示广告
	 */
	public void showAD() {
		if (handler != null) {
			handler.getLGameActivity().showAD();
		}
	}

	/**
	 * 选择的结果
	 * 
	 * @return
	 */
	public int getAndroidSelect() {
		if (handler != null) {
			return handler.getLGameActivity().getAndroidSelect();
		}
		return -2;
	}

	/**
	 * 弹出一个Android选框，用以显示指定的进度条
	 * 
	 * @param title
	 * @param message
	 * @return
	 */
	public AlertDialog showAndroidProgress(final String title,
			final String message) {
		if (handler != null) {
			return handler.getLGameActivity().showAndroidProgress(title,
					message);
		}
		return null;
	}

	/**
	 * 弹出一个Android选框，用以显示指定的信息
	 * 
	 * @param message
	 */
	public void showAndroidAlert(final String title, final String message) {
		if (handler != null) {
			handler.getLGameActivity().showAndroidAlert(title, message);
		}

	}

	/**
	 * 弹出一个Android选框，用以显示指定的异常数据
	 * 
	 * @param ex
	 */
	public void showAndroidExceptionMessageBox(final Exception ex) {
		if (handler != null) {
			handler.getLGameActivity().showAndroidException(ex);
		}
	}

	/**
	 * 弹出一个Android选择框，并返回选择结果
	 * 
	 * @param text
	 * @return
	 */
	public int showAndroidSelectMessageBox(final String title,
			final String[] text) {
		if (handler != null) {
			return handler.getLGameActivity().showAndroidSelect(title, text);
		}
		return -1;
	}

	/**
	 * 播放Assets中的音频文件
	 * 
	 * @param file
	 * @param loop
	 */
	public void playtAssetsMusic(final String file, final boolean loop) {
		if (handler != null) {
			handler.getAssetsSound().playSound(file, loop);
		}
	}

	/**
	 * 设置Assets中的音频文件音量
	 * 
	 * @param vol
	 */
	public void resetAssetsMusic(final int vol) {
		if (handler != null) {
			handler.getAssetsSound().setSoundVolume(vol);
		}
	}

	/**
	 * 重置Assets中的音频文件
	 * 
	 */
	public void resetAssetsMusic() {
		if (handler != null) {
			handler.getAssetsSound().resetSound();
		}
	}

	/**
	 * 中断Assets中的音频文件
	 */
	public void stopAssetsMusic() {
		if (handler != null) {
			handler.getAssetsSound().stopSound();
		}
	}

	/**
	 * 中断Assets中指定索引的音频文件
	 */
	public void stopAssetsMusic(int index) {
		if (handler != null) {
			handler.getAssetsSound().stopSound(index);
		}
	}

	/**
	 * 设定背景图像
	 * 
	 * @param screen
	 */
	public void setBackground(Bitmap screen) {
		if (screen != null) {
			setRepaintMode(SCREEN_BITMAP_REPAINT);
			if (screen.getWidth() != getWidth()
					|| screen.getHeight() != getHeight()) {
				screen = GraphicsUtils.getResize(screen, getWidth(),
						getHeight());
			}
			Bitmap tmp = currentScreen;
			currentScreen = screen;
			if (tmp != null) {
				tmp.recycle();
				tmp = null;
			}

		}
	}

	/**
	 * 设定背景图像
	 * 
	 * @param screen
	 */
	public void setBackground(LImage screen) {
		setBackground(screen.getBitmap());
	}

	/**
	 * 设定背景图像
	 */
	public void setBackground(String fileName, boolean transparency) {
		this.setBackground(getImage(fileName, transparency));
	}

	/**
	 * 设定背景图像
	 * 
	 * @param fileName
	 */
	public void setBackground(String fileName) {
		this.setBackground(getImage(fileName, false));
	}

	/**
	 * 返回背景图像
	 * 
	 * @return
	 */
	public Bitmap getBackground() {
		return currentScreen;
	}

	/**
	 * 设定刷新率
	 * 
	 * @param fps
	 */
	public void setFPS(long fps) {
		LSystem.setFPS(fps);
	}

	/**
	 * 返回当前刷新率
	 * 
	 * @return
	 */
	public long getFPS() {
		return LSystem.getFPS();
	}

	/**
	 * 返回最大刷新率
	 * 
	 * @return
	 */
	public long getMaxFPS() {
		return LSystem.getMaxFPS();
	}

	public void setBackground(LColor color) {
		setRepaintMode(SCREEN_BITMAP_REPAINT);
		LImage image = new LImage(currentScreen);
		LGraphics g = image.getLGraphics();
		g.setBackground(color);
		g.dispose();
	}

	public void destroy() {
		synchronized (this) {
			callEvents(false);
			isNext = false;
			sprites = null;
			desktop = null;
			currentScreen = null;
			dispose();
		}
	}

	/**
	 * 释放函数内资源
	 * 
	 */
	public void dispose() {

	}

	public Desktop getDesktop() {
		return desktop;
	}

	public Sprites getSprites() {
		return sprites;
	}

	/**
	 * 返回与指定类匹配的组件
	 */
	public ArrayList<LComponent> getComponents(Class<? extends LComponent> clazz) {
		if (desktop != null) {
			return desktop.getComponents(clazz);
		}
		return null;
	}

	/**
	 * 返回位于屏幕顶部的组件
	 * 
	 * @return
	 */
	public LComponent getTopComponent() {
		if (desktop != null) {
			return desktop.getTopComponent();
		}
		return null;
	}

	/**
	 * 返回位于屏幕底部的组件
	 * 
	 * @return
	 */
	public LComponent getBottomComponent() {
		if (desktop != null) {
			return desktop.getBottomComponent();
		}
		return null;
	}

	/**
	 * 返回位于屏幕顶部的图层
	 */
	public Layer getTopLayer() {
		if (desktop != null) {
			return desktop.getTopLayer();
		}
		return null;
	}

	/**
	 * 返回位于屏幕底部的图层
	 */
	public Layer getBottomLayer() {
		if (desktop != null) {
			return desktop.getBottomLayer();
		}
		return null;
	}

	/**
	 * 返回所有指定类产生的精灵
	 * 
	 */
	public ArrayList<ISprite> getSprites(Class<? extends ISprite> clazz) {
		if (sprites != null) {
			return sprites.getSprites(clazz);
		}
		return null;
	}

	/**
	 * 返回位于数据顶部的精灵
	 * 
	 */
	public ISprite getTopSprite() {
		if (sprites != null) {
			return sprites.getTopSprite();
		}
		return null;
	}

	/**
	 * 返回位于数据底部的精灵
	 * 
	 */
	public ISprite getBottomSprite() {
		if (sprites != null) {
			return sprites.getBottomSprite();
		}
		return null;
	}

	/**
	 * 添加游戏精灵
	 * 
	 * @param sprite
	 */
	public void add(ISprite sprite) {
		if (sprites != null) {
			sprites.add(sprite);
		}
	}

	/**
	 * 添加游戏组件
	 * 
	 * @param comp
	 */
	public void add(LComponent comp) {
		if (desktop != null) {
			desktop.add(comp);
		}
	}

	public void remove(ISprite sprite) {
		if (sprites != null) {
			sprites.remove(sprite);
		}
	}

	public void removeSprite(Class<? extends ISprite> clazz) {
		if (sprites != null) {
			sprites.remove(clazz);
		}
	}

	public void remove(LComponent comp) {
		if (desktop != null) {
			desktop.remove(comp);
		}
	}

	public void removeComponent(Class<? extends LComponent> clazz) {
		if (desktop != null) {
			desktop.remove(clazz);
		}
	}

	public void removeAll() {
		if (sprites != null) {
			sprites.removeAll();
		}
		if (desktop != null) {
			desktop.getContentPane().clear();
		}
	}

	/**
	 * 判断是否点中指定精灵
	 * 
	 * @param sprite
	 * @return
	 */
	public boolean onClick(ISprite sprite) {
		if (sprite == null) {
			return false;
		}
		if (sprite.isVisible()) {
			RectBox rect = sprite.getCollisionBox();
			if (rect.contains(touchX, touchY)
					|| rect.intersects(touchX, touchY)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否点中指定组件
	 * 
	 * @param component
	 * @return
	 */
	public boolean onClick(LComponent component) {
		if (component == null) {
			return false;
		}
		if (component.isVisible()) {
			RectBox rect = component.getCollisionBox();
			if (rect.contains(touchX, touchY)
					|| rect.intersects(touchX, touchY)) {
				return true;
			}
		}
		return false;
	}

	public void centerOn(final LObject object) {
		object.setLocation(getWidth() / 2 - object.getWidth() / 2, getHeight()
				/ 2 - object.getHeight() / 2);
	}

	public void topOn(final LObject object) {
		object.setLocation(getWidth() / 2 - object.getWidth() / 2, 0);
	}

	public void leftOn(final LObject object) {
		object.setLocation(0, getHeight() / 2 - object.getHeight() / 2);
	}

	public void rightOn(final LObject object) {
		object.setLocation(getWidth() - object.getWidth(), getHeight() / 2
				- object.getHeight() / 2);
	}

	public void bottomOn(final LObject object) {
		object.setLocation(getWidth() / 2 - object.getWidth() / 2, getHeight()
				- object.getHeight());
	}

	/**
	 * 获得背景显示模式
	 */
	public int getRepaintMode() {
		return mode;
	}

	/**
	 * 设定背景刷新模式
	 * 
	 * @param mode
	 */
	public void setRepaintMode(int mode) {
		this.mode = mode;
	}

	/**
	 * 增减一个线程事件
	 * 
	 * @param runnable
	 */
	public final void callEvent(Runnable runnable) {
		synchronized (runnables) {
			runnables.add(runnable);
		}
	}

	/**
	 * 暂停指定的线程事件
	 * 
	 * @param runnable
	 */
	public final void callEventWait(Runnable runnable) {
		synchronized (runnable) {
			synchronized (runnables) {
				runnables.add(runnable);
			}
			try {
				runnable.wait();
			} catch (InterruptedException ex) {
			}
		}
	}

	/**
	 * 中断所有线程事件
	 * 
	 */
	public final void callEventInterrupt() {
		synchronized (runnables) {
			for (Iterator<Runnable> it = runnables.iterator(); it.hasNext();) {
				Object running = it.next();
				synchronized (running) {
					if (running instanceof Thread) {
						((Thread) running).setPriority(Thread.MIN_PRIORITY);
						((Thread) running).interrupt();
					}
				}
			}
		}
	}

	/**
	 * 运行线程事件
	 * 
	 */
	public final void callEvents() {
		callEvents(true);
	}

	/**
	 * 执行或中断指定的线程事件
	 * 
	 * @param execute
	 */
	public final void callEvents(boolean execute) {
		if (!execute) {
			synchronized (runnables) {
				runnables.clear();
			}
			return;
		}
		if (runnables.size() == 0) {
			return;
		}
		ArrayList<Runnable> runnableList;
		synchronized (runnables) {
			runnableList = new ArrayList<Runnable>(runnables);
			runnables.clear();
		}
		for (Iterator<Runnable> it = runnableList.iterator(); it.hasNext();) {
			Object running = it.next();
			synchronized (running) {
				try {
					if (running instanceof Thread) {
						Thread thread = (Thread) running;
						if (!thread.isAlive()) {
							thread.start();
						}

					} else {
						((Runnable) running).run();
					}
				} catch (Exception ex) {
				}
				running.notifyAll();
			}
		}
		runnableList = null;
	}

	public LImage getWebImage(String url, boolean transparency) {
		return GraphicsUtils.loadWebImage(url, transparency);
	}

	public LImage getImage(String fileName, boolean transparency) {
		return GraphicsUtils.loadImage(fileName, transparency);
	}

	public LImage[] getSplitImages(String fileName, int row, int col,
			boolean transparency) {
		return getSplitImages(getImage(fileName, transparency), row, col);
	}

	public LImage[] getSplitImages(LImage image, int row, int col) {
		return GraphicsUtils.getSplitImages(image, row, col);
	}

	public synchronized void createUI(LGraphics g) {
		draw(g);
		if (sprites != null) {
			sprites.createUI(g);
		}
		if (desktop != null) {
			desktop.createUI(g);
		}
	}

	public abstract void draw(LGraphics g);

	public void runTimer(final LTimerContext timer) {
		this.elapsedTime = timer.getTimeSinceLastUpdate();
		if (sprites != null && this.sprites.size() > 0) {
			this.sprites.update(elapsedTime);
		}
		if (desktop != null
				&& this.desktop.getContentPane().getComponentCount() > 0) {
			this.desktop.update(elapsedTime);
		}
		this.pressedTouch = releasedTouch = pressedKey = releasedKey = 0;
		this.touchDX = touchX - lastTouchX;
		this.touchDY = touchY - lastTouchY;
		this.lastTouchX = touchX;
		this.lastTouchY = touchY;
		this.alter(timer);
	}

	public boolean next() {
		return isNext;
	}

	public void setNext(boolean next) {
		this.isNext = next;
	}

	public void alter(LTimerContext timer) {

	}

	/**
	 * 设定游戏窗体
	 */
	public void setScreen(IScreen screen) {
		if (handler != null) {
			this.handler.setScreen(screen);
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * 刷新基础设置
	 */
	public void refresh() {
		for (int i = 0; i < touchDown.length; i++) {
			touchDown[i] = false;
		}
		touchDX = touchDY = 0;
		for (int i = 0; i < keyDown.length; i++) {
			keyDown[i] = false;
		}
	}

	/**
	 * 对外的线程暂停器
	 * 
	 * @param timeMillis
	 */
	public void pause(long timeMillis) {
		try {
			TimeUnit.MILLISECONDS.sleep(timeMillis);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted in pause !");
		}
	}

	public Point getTouch() {
		touch.set(touchX, touchY);
		return touch;
	}

	public boolean isPaused() {
		return LSystem.isPaused;
	}

	public boolean isClick() {
		return touchDown[MotionEvent.ACTION_DOWN];
	}

	public boolean isTouchClick() {
		return baseInput.isTouchPressed(MotionEvent.ACTION_DOWN);
	}

	public boolean isTouchClickUp() {
		return baseInput.isTouchPressed(MotionEvent.ACTION_UP);
	}

	public int getTouchPressed() {
		return (pressedTouch > 0) ? touchPressed[pressedTouch - 1]
				: LInput.NO_BUTTON;
	}

	public int getTouchReleased() {
		return (releasedTouch > 0) ? touchReleased[releasedTouch - 1]
				: LInput.NO_BUTTON;
	}

	public boolean isTouchPressed(int button) {
		if (!isTouchReleased(button)) {
			return false;
		}
		try {
			for (int i = 0; i < this.pressedTouch; i++) {
				if (touchPressed[i] == button) {
					return true;
				}
			}
			return touchPressed[0] == button;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isTouchReleased(int button) {
		try {
			for (int i = 0; i < this.releasedTouch; i++) {
				if (touchReleased[i] == button) {
					return true;
				}
			}
			return touchReleased[0] == button;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean getKeyDown(int keyCode) {
		return baseInput.isKeyDown(keyCode);
	}

	public boolean getKeyPressed(int keyCode) {
		return baseInput.isKeyPressed(keyCode);
	}

	public int getTouchX() {
		return touchX;
	}

	public int getTouchY() {
		return touchY;
	}

	public int getTouchDX() {
		return touchDX;
	}

	public int getTouchDY() {
		return touchDY;
	}

	public boolean[] getTouchDown() {
		return touchDown;
	}

	public boolean isTouchDown(int button) {
		return touchDown[button];
	}

	public int getKeyPressed() {
		return (pressedKey > 0) ? keyPressed[pressedKey - 1] : LInput.NO_KEY;
	}

	public boolean isKeyPressed(int keyCode) {
		for (int i = 0; i < pressedKey; i++) {
			if (keyPressed[i] == keyCode) {
				return true;
			}
		}
		return false;
	}

	public int getKeyReleased() {
		return (releasedKey > 0) ? keyReleased[releasedKey - 1] : LInput.NO_KEY;
	}

	public boolean isKeyReleased(int keyCode) {
		for (int i = 0; i < releasedKey; i++) {
			if (keyReleased[i] == keyCode) {
				return true;
			}
		}
		return false;
	}

	public boolean[] getKeyDown() {
		return keyDown;
	}

	public boolean isKeyDown(int keyCode) {
		return keyDown[keyCode];
	}

	public boolean onTrackballEvent(MotionEvent e) {
		return false;
	}

	public boolean onKeyDownEvent(int keyCode, KeyEvent e) {
		try {
			keyDown[keyCode] = true;
			keyPressed[pressedKey] = keyCode;
			pressedKey++;
		} catch (Exception ex) {
			pressedKey = 0;
			Log.d(getName(), "on Key Down !", ex);
		}
		return onKeyDown(keyCode, e);
	}

	public boolean onKeyDown(int keyCode, KeyEvent e) {
		return true;
	}

	public void setKeyDown(int code) {
		try {
			keyDown[code] = true;
			keyPressed[this.pressedKey] = code;
			pressedKey++;
		} catch (Exception e) {
		}
	}

	public boolean onKeyUpEvent(int keyCode, KeyEvent e) {
		try {
			keyDown[keyCode] = false;
			keyReleased[releasedKey] = keyCode;
			releasedKey++;
		} catch (Exception ex) {
			releasedKey = 0;
			Log.d(getName(), "on Key Up !", ex);
		}
		return onKeyUp(keyCode, e);
	}

	public boolean onKeyUp(int keyCode, KeyEvent e) {
		return true;
	}

	public void setKeyUp(int code) {
		try {
			keyDown[code] = false;
			keyReleased[this.releasedKey] = code;
			releasedKey++;
		} catch (Exception e) {
		}
	}

	public boolean onTouchEvent(MotionEvent e) {
		try {
			int code = e.getAction();
			int pointerCount = MultitouchUtils.getPointerCount(e);
			if (MultitouchUtils.isMultitouch() && pointerCount > 1) {
				int id = 0;
				for (int idx = 0; idx < pointerCount; idx++) {
					id = MultitouchUtils.getPointerId(e, idx);
					touchX = Math.round(MultitouchUtils.getX(e, id));
					touchY = Math.round(MultitouchUtils.getY(e, id));
					switch (code) {
					case MotionEvent.ACTION_DOWN:
						if (idx == 0) {
							offsetTouchX = touchX;
							offsetTouchY = touchY;
							isMoving = false;
							touchDown[code] = true;
							touchPressed[pressedTouch] = code;
							touchDX = lastTouchX = touchX;
							touchDY = lastTouchY = touchY;
							pressedTouch++;
							if ((touchX < halfWidth) && (touchY < halfHeight)) {
								this.touchDirection = LInput.UPPER_LEFT;
							} else if ((touchX >= halfWidth)
									&& (touchY < halfHeight)) {
								this.touchDirection = LInput.UPPER_RIGHT;
							} else if ((touchX < halfWidth)
									&& (touchY >= halfHeight)) {
								this.touchDirection = LInput.LOWER_LEFT;
							} else {
								this.touchDirection = LInput.LOWER_RIGHT;
							}
							onTouchDown(e);
							return true;
						}
						break;
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL:
						if (idx == 0) {
							isMoving = false;
							touchDown[code] = false;
							touchReleased[releasedTouch] = code;
							touchDX = lastTouchX = touchX;
							touchDY = lastTouchY = touchY;
							releasedTouch++;
							onTouchUp(e);
							return true;
						}
						break;
					case MotionEvent.ACTION_MOVE:
						if (idx == 0) {
							offsetMoveX = touchX;
							offsetMoveY = touchY;
							if (Math.abs(offsetTouchX - offsetMoveX) > 5
									|| Math.abs(offsetTouchY - offsetMoveY) > 5) {
								isMoving = true;
								onTouchMove(e);
								return true;
							}
						}
						break;
					case MultitouchUtils.ACTION_POINTER_1_DOWN:
						if (idx == 0) {
							onTouchDown(e);
							return true;
						}
						break;
					case MultitouchUtils.ACTION_POINTER_1_UP:
						if (idx == 0) {
							onTouchUp(e);
							return true;
						}
						break;
					case MultitouchUtils.ACTION_POINTER_2_DOWN:
						if (idx == 1) {
							onTouchDown(e);
							return true;
						}
						break;
					case MultitouchUtils.ACTION_POINTER_2_UP:
						if (idx == 1) {
							onTouchUp(e);
							return true;
						}
						break;
					case MultitouchUtils.ACTION_POINTER_3_DOWN:
						if (idx == 2) {
							onTouchDown(e);
							return true;
						}
						break;
					case MultitouchUtils.ACTION_POINTER_3_UP:
						if (idx == 2) {
							onTouchUp(e);
							return true;
						}
						break;
					}
				}
			} else {
				touchX = Math.round(e.getX());
				touchY = Math.round(e.getY());
				switch (code) {
				case MotionEvent.ACTION_DOWN:
					offsetTouchX = touchX;
					offsetTouchY = touchY;
					isMoving = false;
					touchDown[code] = true;
					touchPressed[pressedTouch] = code;
					touchDX = lastTouchX = touchX;
					touchDY = lastTouchY = touchY;
					pressedTouch++;
					if ((touchX < halfWidth) && (touchY < halfHeight)) {
						this.touchDirection = LInput.UPPER_LEFT;
					} else if ((touchX >= halfWidth) && (touchY < halfHeight)) {
						this.touchDirection = LInput.UPPER_RIGHT;
					} else if ((touchX < halfWidth) && (touchY >= halfHeight)) {
						this.touchDirection = LInput.LOWER_LEFT;
					} else {
						this.touchDirection = LInput.LOWER_RIGHT;
					}
					onTouchDown(e);
					return true;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					isMoving = false;
					touchDown[code] = false;
					touchReleased[releasedTouch] = code;
					touchDX = lastTouchX = touchX;
					touchDY = lastTouchY = touchY;
					releasedTouch++;
					onTouchUp(e);
					return true;
				case MotionEvent.ACTION_MOVE:
					offsetMoveX = touchX;
					offsetMoveY = touchY;
					if (Math.abs(offsetTouchX - offsetMoveX) > 5
							|| Math.abs(offsetTouchY - offsetMoveY) > 5) {
						isMoving = true;
						onTouchMove(e);
						return true;
					}
				}
			}
		} catch (Exception ex) {
			pressedTouch = 0;
			releasedTouch = 0;
			Log.d(getName(), "on Touch !", ex);
		}
		return false;

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

	public void onOptionsMenuClosed(Menu menu) {

	}

	public void onResume() {

	}

	public void onPause() {

	}

	private void gravityToKey(SensorDirection d) {
		setKeyUp(KeyEvent.KEYCODE_DPAD_LEFT);
		setKeyUp(KeyEvent.KEYCODE_DPAD_RIGHT);
		setKeyUp(KeyEvent.KEYCODE_DPAD_DOWN);
		setKeyUp(KeyEvent.KEYCODE_DPAD_UP);

		if (d == SensorDirection.LEFT) {
			setKeyDown(KeyEvent.KEYCODE_DPAD_LEFT);
		} else if (d == SensorDirection.RIGHT) {
			setKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT);
		} else if (d == SensorDirection.UP) {
			setKeyDown(KeyEvent.KEYCODE_DPAD_UP);
		} else if (d == SensorDirection.DOWN) {
			setKeyDown(KeyEvent.KEYCODE_DPAD_DOWN);
		}
	}

	public void onSensorChanged(SensorEvent e) {
		try {
			accelX = e.values[SensorManager.DATA_X];
			accelY = e.values[SensorManager.DATA_Y];
			accelZ = e.values[SensorManager.DATA_Z];
			double speed = Math.sqrt(accelX * accelX + accelY * accelY + accelZ
					* accelZ);
			sensorInclination = -Math.asin(accelZ / speed);
		} catch (Exception ex) {
			return;
		}
		long curTime = System.currentTimeMillis();
		if (LSystem.SCREEN_LANDSCAPE) {

			if ((curTime - lastUpdate) > 100) {
				lastUpdate = curTime;
				double move = Math.atan(accelY / accelZ);

				if (move <= -LANDSCAPE_OFFSET) {
					if (landscapeUpdate > -LANDSCAPE_OFFSET) {
						direction = SensorDirection.LEFT;
					}
				} else if (move >= LANDSCAPE_OFFSET) {
					if (landscapeUpdate < LANDSCAPE_OFFSET) {
						direction = SensorDirection.RIGHT;
					}
				} else {
					if (landscapeUpdate <= -LANDSCAPE_OFFSET) {
						direction = SensorDirection.LEFT;
					} else if (landscapeUpdate >= LANDSCAPE_OFFSET) {
						direction = SensorDirection.RIGHT;
					}
				}

				landscapeUpdate = move;
			}
		} else {

			if ((curTime - lastUpdate) > 150) {
				lastUpdate = curTime;

				if (accelX > 0 && accelY <= 0) {
					direction = SensorDirection.RIGHT;
				} else if (Math.abs(accelX) > Math.abs(accelY)) {
					if (accelX < -ACCEL_OFFEST) {
						direction = SensorDirection.LEFT;
					}
					if (accelX > ACCEL_OFFEST) {
						direction = SensorDirection.RIGHT;
					}
				} else {
					if (accelY < -ACCEL_OFFEST) {
						direction = SensorDirection.DOWN;
					}
					if (accelY > ACCEL_OFFEST) {
						direction = SensorDirection.UP;
					}
				}
			}
		}
		if (isGravityToKey) {
			gravityToKey(direction);
		}
		onDirection(direction, accelX, accelY, accelZ);

	}

	public void onDirection(SensorDirection direction, float x, float y, float z) {

	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onTouch(float x, float y, MotionEvent e, int pointerCount,
			int pointerId) {

	}

	public void move(double x, double y) {
		this.touchX = (int) x;
		this.touchY = (int) y;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public int getHalfWidth() {
		return halfWidth;
	}

	public int getHalfHeight() {
		return halfHeight;
	}

	public int getTouchDirection() {
		return touchDirection;
	}

	public SensorDirection getSensorDirection() {
		return direction;
	}

	public double getSensorInclination() {
		return sensorInclination;
	}

	public float getAccelX() {
		return accelX;
	}

	public float getAccelY() {
		return accelY;
	}

	public float getAccelZ() {
		return accelZ;
	}

	public boolean isGravityToKey() {
		return isGravityToKey;
	}

	public void setGravityToKey(boolean isGravityToKey) {
		this.isGravityToKey = isGravityToKey;
	}

}
