package org.loon.framework.javase.game.srpg.ability;

/**
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
 * @email：ceponline@yahoo.com.cn
 * @version 0.1
 */
public class SRPGDamageAverage {

	public int damage;

	public int mp;

	public int genre;

	public int topdamage;

	public int topmp;

	public int number;

	public SRPGDamageAverage() {

	}

	public int getDamageAverage() {
		return damage / number;
	}

	public int getMPAverage() {
		return mp / number;
	}

	public void addDamage(int d) {
		this.damage += d;
		this.number++;
		if (damage > topdamage) {
			topdamage = damage;
		}
	}

	public void addMP(int mp) {
		this.mp += mp;
		if (mp > topmp) {
			topmp = mp;
		}
	}

}
