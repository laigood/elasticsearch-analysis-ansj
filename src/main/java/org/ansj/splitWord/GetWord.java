package org.ansj.splitWord;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Arrays;

import org.ansj.domain.Forest;
import org.ansj.domain.WoodInterface;
import org.ansj.library.Library;

public class GetWord {
	private static final String EMPTYSTRING = "" ;
	
	private Forest forest;
	private char[] chars;
	private String str;
	public int offe;
	private int tempOffe;
	private String[] param;
	byte status = 0;

	WoodInterface branch = this.forest;

	int root = 0;
	int i = this.root;
	boolean isBack = false;

	public GetWord(Forest forest, String content) {
		this.chars = Arrays.copyOf(content.toCharArray(), content.length() + 1);
		this.forest = forest;
		this.branch = forest;
	}

	public String getAllWords() {
		String temp = this.allWords();
		while (EMPTYSTRING.equals(temp)) {
			temp = this.allWords();
		}
		return temp;
	}

	public String getFrontWords() {
		String temp = this.frontWords();
		while (EMPTYSTRING.equals(temp)) {
			temp = this.frontWords();
		}
		return temp;
	}

	private String allWords() {
		if ((!this.isBack) || (this.i == this.chars.length - 1)) {
			this.i = (this.root - 1);
		}
		for (this.i += 1; this.i < this.chars.length; this.i = (this.i + 1)) {
			this.branch = this.branch.get(this.chars[this.i]);
			if (this.branch == null) {
				this.root += 1;
				this.branch = this.forest;
				this.i = (this.root - 1);
				this.isBack = false;
			} else {
				switch (this.branch.getStatus()) {
				case 2:
					this.isBack = true;
					this.offe = (this.tempOffe + this.root);
					this.param = this.branch.getParams();
					return new String(this.chars, this.root, this.i - this.root + 1);
				case 3:
					this.offe = (this.tempOffe + this.root);
					this.str = new String(this.chars, this.root, this.i - this.root + 1);
					this.param = this.branch.getParams();
					this.branch = this.forest;
					this.isBack = false;
					this.root += 1;
					return this.str;
				}
			}
		}
		this.tempOffe += this.chars.length;
		return null;
	}

	private String frontWords() {
		for (; this.i < this.chars.length; this.i = (this.i + 1)) {
			this.branch = this.branch.get(this.chars[this.i]);
			if (this.branch == null) {
				this.branch = this.forest;
				if (this.isBack) {
					this.str = new String(this.chars, this.root, this.tempOffe);

					if ((this.root > 0) && (isE(this.chars[(this.root - 1)])) && (isE(this.str.charAt(0)))) {
						this.str = EMPTYSTRING;
					}

					if ((this.str.length() != 0) && (this.root + this.tempOffe < this.chars.length) && (isE(this.str.charAt(this.str.length() - 1)))
							&& (isE(this.chars[(this.root + this.tempOffe)]))) {
						this.str = EMPTYSTRING;
					}
					if (this.str.length() == 0) {
						this.root += 1;
						this.i = this.root;
					} else {
						this.offe = (this.tempOffe + this.root);
						this.i = (this.root + this.tempOffe);
						this.root = this.i;
					}
					this.isBack = false;

					if (EMPTYSTRING.equals(this.str)) {
						return EMPTYSTRING;
					}
					return this.str;
				}
				this.i = this.root;
				this.root += 1;
			} else {
				switch (this.branch.getStatus()) {
				case 2:
					this.isBack = true;
					this.tempOffe = (this.i - this.root + 1);
					this.param = this.branch.getParams();
					break;
				case 3:
					this.offe = (this.tempOffe + this.root);
					this.str = new String(this.chars, this.root, this.i - this.root + 1);
					String temp = this.str;

					if ((this.root > 0) && (isE(this.chars[(this.root - 1)])) && (isE(this.str.charAt(0)))) {
						this.str = EMPTYSTRING;
					}

					if ((this.str.length() != 0) && (this.i + 1 < this.chars.length) && (isE(this.str.charAt(this.str.length() - 1)))
							&& (isE(this.chars[(this.i + 1)]))) {
						this.str = EMPTYSTRING;
					}
					this.param = this.branch.getParams();
					this.branch = this.forest;
					this.isBack = false;
					if (temp.length() > 0) {
						this.i += 1;
						this.root = this.i;
					} else {
						this.i = (this.root + 1);
					}
					if (EMPTYSTRING.equals(this.str)) {
						return EMPTYSTRING;
					}
					return this.str;
				}
			}
		}
		this.tempOffe += this.chars.length;
		return null;
	}

	public boolean isE(char c) {
		if ((c >= 'a') && (c <= 'z')) {
			return true;
		}
		switch (c) {
		case '.':
			return true;
		case '-':
			return true;
		case '/':
			return true;
		case '#':
			return true;
		case '?':
			return true;
		}
		return false;
	}

	public void reset(String content) {
		this.offe = 0;
		this.status = 0;
		this.root = 0;
		this.i = this.root;
		this.isBack = false;
		this.tempOffe = 0;
		this.chars = content.toCharArray();
		this.branch = this.forest;
	}

	public String getParam(int i) {
		if ((this.param == null) || (this.param.length < i + 1)) {
			return null;
		}
		return this.param[i];
	}

	public static void main(String[] args) throws Exception {
		/**
		 * 词典的构造.一行一个词后面是参数.可以从文件读取.可以是read流.
		 */
		String dic = "java学习\t10\nc\t100\nC++\t10\nc++\t5\nc#\t100".toLowerCase();
		Forest forest = Library.makeForest(new BufferedReader(new StringReader(dic)));

		/**
		 * 删除一个单词
		 */
		Library.removeWord(forest, "中国");
		/**
		 * 增加一个新词
		 */
		Library.insertWord(forest, "中国人");
		String content = "c c++ c++ c++ ".toLowerCase();
		GetWord udg = forest.getWord(content.toLowerCase());

		String temp = null;
		while ((temp = udg.getFrontWords()) != null)
			System.out.println(temp + "\t\t" + udg.getParam(0) + "\t\t" + udg.getParam(2));
	}
}