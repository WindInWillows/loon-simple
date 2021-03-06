package org.loon.framework.android.game.core.graphics;

import org.loon.framework.android.game.action.sprite.ISprite;
import org.loon.framework.android.game.core.EmulatorButtons;
import org.loon.framework.android.game.core.EmulatorListener;
import org.loon.framework.android.game.core.graphics.device.LGraphics;
import org.loon.framework.android.game.core.timer.LTimerContext;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

/**
 * 
 * Copyright 2008 - 2010
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
 * @version 0.1.1
 */
public interface IScreen {

	public void onResume();

	public void onPause();

	public boolean onCreateOptionsMenu(Menu menu);

	public boolean onOptionsItemSelected(MenuItem item);
	
	public void onOptionsMenuClosed(Menu menu);
	
	public boolean onTrackballEvent(MotionEvent e);

	public boolean onKeyDownEvent(int keyCode, KeyEvent e);

	public boolean onKeyUpEvent(int keyCode, KeyEvent e);

	public boolean onTouchEvent(MotionEvent e);

	/**
	 * 获得当前Screen类名
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 初始化时加载的数据
	 */
	public void onLoad();

	/**
	 * 判断是否点中指定精灵
	 * 
	 * @param sprite
	 * @return
	 */
	public boolean onClick(ISprite sprite);

	/**
	 * 判断是否点中指定组件
	 * 
	 * @param component
	 * @return
	 */
	public boolean onClick(LComponent component);

	/**
	 * 设定模拟按钮监听器
	 */
	public void setEmulatorListener(EmulatorListener emulator);

	/**
	 * 设定模拟按钮组是否显示
	 * 
	 * @param visible
	 */
	public void emulatorButtonsVisible(boolean visible);

	/**
	 * 返回模拟按钮集合
	 * 
	 * @return
	 */
	public EmulatorButtons getEmulatorButtons();

	/**
	 * 广告是否显示
	 * 
	 * @return
	 */
	public abstract boolean isVisibleAD();

	/**
	 * 隐藏广告
	 */
	public abstract void hideAD();

	/**
	 * 显示广告
	 */
	public abstract void showAD();

	/**
	 * 选择的结果
	 * 
	 * @return
	 */
	public abstract int getAndroidSelect();

	/**
	 * 播放Assets中的音频文件
	 * 
	 * @param file
	 * @param loop
	 */
	public abstract void playtAssetsMusic(final String file, final boolean loop);

	/**
	 * 设置Assets中的音频文件音量
	 * 
	 * @param vol
	 */
	public void resetAssetsMusic(final int vol);

	/**
	 * 重置Assets中的音频文件
	 * 
	 */
	public abstract void resetAssetsMusic();

	/**
	 * 中断Assets中的音频文件
	 */
	public abstract void stopAssetsMusic();

	/**
	 * 中断Assets中指定索引的音频文件
	 * 
	 * @param index
	 */
	public abstract void stopAssetsMusic(int index);

	/**
	 * 移动坐标
	 * 
	 * @param x
	 * @param y
	 */
	public abstract void move(double x, double y);

	/**
	 * 访问指定的网站页面
	 * 
	 * @param url
	 */
	public abstract void openBrowser(final String url);

	/**
	 * 弹出一个Android选框，用以显示指定的异常数据
	 * 
	 * @param ex
	 */
	public abstract void showAndroidExceptionMessageBox(final Exception ex);

	/**
	 * 弹出一个Android选框，用以显示指定的进度条
	 * 
	 * @param title
	 * @param message
	 * @return
	 */
	public abstract AlertDialog showAndroidProgress(final String title,
			final String message);

	/**
	 * 弹出一个Android选框，用以显示指定的信息
	 * 
	 * @param message
	 */
	public abstract void showAndroidAlert(final String title,
			final String message);

	/**
	 * 弹出一个Android选择框，并返回选择结果
	 * 
	 * @param text
	 * @return
	 */
	public abstract int showAndroidSelectMessageBox(final String title,
			final String[] text);

	/**
	 * 注销资源
	 */
	public abstract void destroy();

	/**
	 * 设定背景图像
	 * 
	 * @param screen
	 */
	public abstract void setBackground(Bitmap screen);

	/**
	 * 设定背景图像
	 * 
	 * @param screen
	 */
	public abstract void setBackground(LImage screen);

	/**
	 * 设定背景图像
	 * 
	 * @param fileName
	 * @param transparency
	 */
	public abstract void setBackground(String fileName, boolean transparency);
	
	/**
	 * 设定背景图像
	 * 
	 * @param fileName
	 */
	public abstract void setBackground(String fileName);
	
	/**
	 * 设定背景颜色
	 * 
	 * @param color
	 */
	public void setBackground(LColor color);

	/**
	 * 返回背景图像
	 * 
	 * @return
	 */
	public abstract Bitmap getBackground();

	/**
	 * 设定刷新率
	 * 
	 * @param fps
	 */
	public abstract void setFPS(long fps);

	/**
	 * 返回当前刷新率
	 * 
	 * @return
	 */
	public abstract long getFPS();

	/**
	 * 返回最大刷新率
	 * 
	 * @return
	 */
	public abstract long getMaxFPS();

	/**
	 * 获得背景震动频率
	 */
	public abstract int getShakeNumber();

	/**
	 * 设定背景震动频率
	 * 
	 * @param shake
	 */
	public abstract void setShakeNumber(int shake);

	/**
	 * 触摸屏按下
	 * 
	 * @param e
	 * @return
	 */
	public abstract boolean onTouchDown(MotionEvent e);

	/**
	 * 触摸屏放开
	 * 
	 * @param e
	 * @return
	 */
	public abstract boolean onTouchUp(MotionEvent e);

	public abstract boolean onTouchMove(MotionEvent e);

	public abstract boolean onKeyDown(int keyCode, KeyEvent e);

	public abstract boolean onKeyUp(int keyCode, KeyEvent e);

	public abstract boolean next();

	/**
	 * 增减一个线程事件
	 * 
	 * @param runnable
	 */
	public abstract void callEvent(Runnable runnable);

	/**
	 * 暂停指定的线程事件
	 * 
	 * @param runnable
	 */
	public abstract void callEventWait(Runnable runnable);

	/**
	 * 中断所有线程事件
	 * 
	 */
	public abstract void callEventInterrupt();

	/**
	 * 运行线程事件
	 * 
	 */
	public abstract void callEvents();

	/**
	 * 运行线程事件
	 * 
	 */
	public abstract void callEvents(boolean execute);

	public abstract LImage getWebImage(String url, boolean transparency);

	public abstract LImage getImage(String fileName, boolean transparency);

	public abstract LImage[] getSplitImages(String fileName, int row, int col,
			boolean transparency);

	public abstract LImage[] getSplitImages(LImage image, int row, int col);

	public abstract void draw(LGraphics g);

	public abstract void alter(LTimerContext timer);

	public abstract void setScreen(IScreen screen);

	public abstract int getWidth();

	public abstract int getHeight();

	public abstract int getHalfWidth();

	public abstract int getHalfHeight();

	/**
	 * 对外的线程暂停器
	 * 
	 * @param timeMillis
	 */
	public abstract void pause(long timeMillis);

	public abstract Point getTouch();

	public abstract boolean isPaused();

	public abstract boolean isClick();

	public abstract int getTouchX();

	public abstract int getTouchY();

	public abstract int getTouchDX();

	public abstract int getTouchDY();

	public abstract int getTouchDirection();

	public abstract void createUI(LGraphics g);

	public abstract void runTimer(LTimerContext timer);

}
