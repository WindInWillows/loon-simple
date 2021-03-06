package org.loon.framework.javase.game.core.graphics.opengl.particle;

import org.loon.framework.javase.game.core.geom.Vector2f;
import org.loon.framework.javase.game.core.graphics.opengl.GL;
import org.loon.framework.javase.game.utils.MathUtils;

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
public class DustParticleSystem extends ParticleSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DustParticleSystem(int howManyEffects, String textureFileName) {
		super(howManyEffects, textureFileName);
	}

	protected void initializeConstants() {
		this.minInitialSpeed = 20;
		this.maxInitialSpeed = 100;
		this.minAcceleration = -4;
		this.maxAcceleration = -10;
		this.minLifetime = 0.5f;
		this.maxLifetime = 2.0f;
		this.minScale = 0.1f;
		this.maxScale = 0.02f;
		this.minNumParticles = 15;
		this.maxNumParticles = 25;
		this.minRotationSpeed = -2f * MathUtils.PI;
		this.maxRotationSpeed = -MathUtils.PI;
		this.spriteBlendMode = GL.MODE_NORMAL;
	}

	protected Vector2f pickRandomDirection() {
		float radians = ParticleSystem.getRandomBetween(MathUtils.toRadians(0),
				MathUtils.toRadians(30));
		Vector2f direction = new Vector2f();
		direction.x = (float) Math.cos(radians);
		direction.y = -(float) Math.sin(radians);
		return direction;
	}
}
