package org.ansj.domain;

import org.ansj.splitWord.GetWord;


public class Forest implements WoodInterface {
	WoodInterface[] chars = new WoodInterface[65536];
	

	public WoodInterface add(WoodInterface branch) {
		WoodInterface temp = this.chars[branch.getC()];
		if (temp == null)
			this.chars[branch.getC()] = branch;
		else {
			switch (branch.getStatus()) {
			case 1:
				if (temp.getStatus() == 3) {
					temp.setStatus(2);
				}
				break;
			case 3:
				if (temp.getStatus() == 1) {
					temp.setStatus(2);
				}
				temp.setParam(branch.getParams()) ;
			}
		}

		return this.chars[branch.getC()];
	}

	public boolean contains(char c) {
		return this.chars[c] != null;
	}

	public WoodInterface get(char c) {
		if (c > 66535) {
			System.out.println(c);
			return null;
		}
		return this.chars[c];
	}

	public static void main(String[] args) {
		Forest f = new Forest();
		System.out.println(f.chars[19968]);
	}

	public int compareTo(char c) {
		return 0;
	}

	public boolean equals(char c) {
		return false;
	}

	public char getC() {
		return '\000';
	}

	public int getNature() {
		return 0;
	}

	public byte getStatus() {
		return 0;
	}

	public void setNature(int nature) {
	}

	public void setStatus(int status) {
	}

	public int getSize() {
		return this.chars.length;
	}

	public String[] getParams() {
		return null;
	}

	public void setParam(String[] param) {
	}

	/**
	 * 得到一个分词对象
	 * 
	 * @param content
	 * @return
	 */
	public GetWord getWord(String content) {
		return new GetWord(this, content);
	}
}