package org.ansj.library;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.ansj.domain.Branch;
import org.ansj.domain.Forest;
import org.ansj.domain.Value;
import org.ansj.domain.WoodInterface;
import org.ansj.util.IOUtil;

public class Library {

	public static Forest makeForest(String path) throws Exception {
		return makeForest(new FileInputStream(path));
	}

	public static Forest makeForest(InputStream inputStream) throws Exception {
		return makeForest(IOUtil.getReader(inputStream, "UTF-8"));
	}

	public static Forest makeForest(BufferedReader br) throws Exception {
		return makeLibrary(br, new Forest());
	}

	/**
	 * 传入value数组.构造树
	 * 
	 * @param values
	 * @param forest
	 * @return
	 */
	public static Forest makeForest(List<Value> values) {
		Forest forest = new Forest();
		for (Value value : values) {
			insertWord(forest, value.toString());
		}
		return forest;
	}

	private static Forest makeLibrary(BufferedReader br, Forest forest) throws Exception {
		try {
			String temp = null;
			while ((temp = br.readLine()) != null) {
				insertWord(forest, temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
		}
		return forest;
	}

	public static void insertWord(Forest forest, Value value) {
		insertWord(forest, value.toString());
	}

	/**
	 * 插入一个词
	 * 
	 * @param forest
	 * @param temp
	 */
	public static void insertWord(Forest forest, String temp) {
		String[] param = temp.split("\t");

		temp = param[0];

		String[] resultParams = null;

		WoodInterface branch = forest;
		char[] chars = temp.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars.length == i + 1) {
				resultParams = new String[param.length - 1];
				for (int j = 1; j < param.length; j++) {
					resultParams[j - 1] = param[j];
				}
				branch.add(new Branch(chars[i], 3, resultParams));
			} else {
				branch.add(new Branch(chars[i], 1, null));
			}
			branch = branch.get(chars[i]);
		}
	}

	/**
	 * 删除一个词
	 * 
	 * @param forest
	 * @param temp
	 */
	public static void removeWord(Forest forest, String word) {
		WoodInterface branch = forest;
		char[] chars = word.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if (branch == null)
				return;
			if (chars.length == i + 1) {
				branch.add(new Branch(chars[i], -1, null));
			}
			branch = branch.get(chars[i]);
		}
	}
}