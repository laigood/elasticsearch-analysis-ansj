package org.ansj.domain;

public abstract interface WoodInterface {
	public abstract WoodInterface add(WoodInterface paramWoodInterface);

	public abstract WoodInterface get(char paramChar);

	public abstract boolean contains(char paramChar);

	public abstract int compareTo(char paramChar);

	public abstract boolean equals(char paramChar);

	public abstract byte getStatus();

	public abstract char getC();

	public abstract void setStatus(int paramInt);

	public abstract String[] getParams();

	public abstract void setParam(String[] paramArrayOfString);
}