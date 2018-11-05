package com.own.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1 {
	private static String byteArrayToHex(byte[] byteArray) {

		// 首先初始化一个字符数组，用来存放每个16进制字符

		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };

		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））

		char[] resultCharArray = new char[byteArray.length * 2];

		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去

		int index = 0;

		for (byte b : byteArray) {

			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];

			resultCharArray[index++] = hexDigits[b & 0xf];

		}

		// 字符数组组合成字符串返回

		return new String(resultCharArray);
	}

	/**
	 * 目前与hex_sha1方法共存，此方法对语义识别进行签名
	 * @param inStr
	 * @return
	 */
	private static String sSHA1(String inStr) {
		MessageDigest md = null;
		String outStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1"); // 选择SHA-1，也可以选择MD5
			byte[] digest = md.digest(inStr.getBytes());
			// 返回的是byet[]，要转化为String存储比较方便

			outStr = byteArrayToHex(digest);
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		return outStr;
	}

	public static String bytetoString(byte[] digest) {
		String str = "";
		String tempStr = "";

		for (int i = 1; i < digest.length; i++) {
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1) {
				str = str + "0" + tempStr;
			} else {
				str = str + tempStr;
			}
		}
		return str.toLowerCase();
	}

	private static final boolean hexcase = false;
	private static final int chrsz = 8;

	
	/** 得到字符串SHA-1值的方法
	 * 目前与SHA1方法共存，此方法对微信支付识别进行签名
	 * @param s
	 * @return
	 */
	public static String hex_sha1(String s) {
		return sSHA1(s);
//		 s = (s == null) ? "" : s;
//		 return binb2hex(core_sha1(str2binb(s), s.length() * chrsz));
	}
	// 得到字符串SHA-1值的方法
		public static String hex_sha12(String s) {
//			return SHA1(s);
			 s = (s == null) ? "" : s;
			 return binb2hex(core_sha1(str2binb(s), s.length() * chrsz));
		}

	private static String binb2hex(int[] binarray) {
		String hex_tab = hexcase ? "0123456789abcdef" : "0123456789abcdef";
		String str = "";

		for (int i = 0; i < binarray.length * 4; i++) {
			char a = (char) hex_tab
					.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8 + 4)) & 0xf);
			char b = (char) hex_tab
					.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8)) & 0xf);
			str += (new Character(a).toString() + new Character(b).toString());
		}
		return str;
	}

	private static String binb2str(int[] bin) {
		String str = "";
		int mask = (1 << chrsz) - 1;

		for (int i = 0; i < bin.length * 32; i += chrsz) {
			str += (char) ((bin[i >> 5] >>> (24 - i % 32)) & mask);
		}
		return str;
	}

	private static int[] core_sha1(int[] x, int len) {
		int size = (len >> 5);
		x = strechbinarray(x, size);
		x[len >> 5] |= 0x80 << (24 - len % 32);
		size = ((len + 64 >> 9) << 4) + 15;
		x = strechbinarray(x, size);
		x[((len + 64 >> 9) << 4) + 15] = len;

		int[] w = new int[80];
		int a = 1732584193;
		int b = -271733879;
		int c = -1732584194;
		int d = 271733878;
		int e = -1009589776;

		for (int i = 0; i < x.length; i += 16) {
			int olda = a;
			int oldb = b;
			int oldc = c;
			int oldd = d;
			int olde = e;

			for (int j = 0; j < 80; j++) {
				if (j < 16) {
					w[j] = x[i + j];
				} else {
					w[j] = rol(w[j - 3] ^ w[j - 8] ^ w[j - 14] ^ w[j - 16], 1);
				}

				int t = safe_add(safe_add(rol(a, 5), sha1_ft(j, b, c, d)),
						safe_add(safe_add(e, w[j]), sha1_kt(j)));

				e = d;
				d = c;
				c = rol(b, 30);
				b = a;
				a = t;
			}

			a = safe_add(a, olda);
			b = safe_add(b, oldb);
			c = safe_add(c, oldc);
			d = safe_add(d, oldd);
			e = safe_add(e, olde);
		}

		int[] retval = new int[5];

		retval[0] = a;
		retval[1] = b;
		retval[2] = c;
		retval[3] = d;
		retval[4] = e;

		return retval;
	}

	private static int rol(int num, int cnt) {
		return (num << cnt) | (num >>> (32 - cnt));
	}

	private static int safe_add(int x, int y) {
		int lsw = (int) (x & 0xffff) + (int) (y & 0xffff);
		int msw = (x >> 16) + (y >> 16) + (lsw >> 16);

		return (msw << 16) | (lsw & 0xffff);
	}

	private static int sha1_ft(int t, int b, int c, int d) {
		if (t < 20)
			return (b & c) | ((~b) & d);

		if (t < 40)
			return b ^ c ^ d;

		if (t < 60)
			return (b & c) | (b & d) | (c & d);

		return b ^ c ^ d;
	}

	private static int sha1_kt(int t) {
		return (t < 20) ? 1518500249 : (t < 40) ? 1859775393
				: (t < 60) ? -1894007588 : -899497514;
	}

	public static String str_sha1(String s) {
		s = (s == null) ? "" : s;

		return binb2str(core_sha1(str2binb(s), s.length() * chrsz));
	}

	private static int[] str2binb(String str) {
		str = (str == null) ? "" : str;

		int[] tmp = new int[str.length() * chrsz];
		int mask = (1 << chrsz) - 1;

		for (int i = 0; i < str.length() * chrsz; i += chrsz) {
			tmp[i >> 5] |= ((int) (str.charAt(i / chrsz)) & mask) << (24 - i % 32);
		}

		int len = 0;
		for (int i = 0; i < tmp.length && tmp[i] != 0; i++, len++)
			;

		int[] bin = new int[len];
		for (int i = 0; i < len; i++) {
			bin[i] = tmp[i];
		}

		return bin;
	}

	private static int[] strechbinarray(int[] oldbin, int size) {
		int currlen = oldbin.length;

		if (currlen >= size + 1) {
			return oldbin;
		}
		int[] newbin = new int[size + 1];
		for (int i = 0; i < size; newbin[i] = 0, i++)
			;

		for (int i = 0; i < currlen; i++) {
			newbin[i] = oldbin[i];
		}

		return newbin;
	}

//	public static void main(String[] args) {
//		String str = "appid=wx37d032a14f97f6f1&appkey=h6tRzgXJZ7A1Z9EuzWqT1X2G1IHmBC1K5Ou1Wh5Nfw9zx9rtUcIfw5OT0QADPsIK8x6qG3zhq5di5u8jyHWVDCG9PC71UG8TB5FCY7JKvkSq0R74oUlnRmS3ljpBkomg&issubscribe=1&noncestr=qwZxVMDeDQ8lqHDO&openid=oFbGAjs5VnEMFhnMJrzhxZcg3_5Q&timestamp=1393945618";
//		String keyvaluestring = "appid=wx37d032a14f97f6f1&appkey=h6tRzgXJZ7A1Z9EuzWqT1X2G1IHmBC1K5Ou1Wh5Nfw9zx9rtUcIfw5OT0QADPsIK8x6qG3zhq5di5u8jyHWVDCG9PC71UG8TB5FCY7JKvkSq0R74oUlnRmS3ljpBkomg&package=out_trade_no=384&partner=1218134601&sign=884296C43E2620772B9BFF6B0B4CE56D&timestamp=1395042898424";
//		System.out.println(SHA1.hex_sha12(keyvaluestring));// 494a1bda59a6200ee857f968ca3851ecd686d63f
//	}
}
