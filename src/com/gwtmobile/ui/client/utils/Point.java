/*
 * Copyright (c) 2010 Zhihua (Dennis) Jiang
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
 */

package com.gwtmobile.ui.client.utils;

public class Point {

	private double x, y;

	public Point(Point c) {
		x = c.x;
		y = c.y;
	}

	public double X() {
		return x;
	}

	public double Y() {
		return y;
	}

	public Point(double x, double y) {
		Set(x, y);
	}

	public Point Set(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point)) {
			return false;
		}
		Point c = (Point) obj;
		return (x == c.x) && (y == c.y);
	}

	@Override
	public int hashCode() {
		return (int) x ^ (int) y;
	}

	public Point minus(Point c) {
		this.x -= c.x;
		this.y -= c.y;
		return this;
	}

	public Point plus(Point c) {
		this.x += c.x;
		this.y += c.y;
		return this;
	}

	public Point clone(Point c) {
		this.x = c.x;
		this.y = c.y;
		return this;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")"; 
	}
}
