package com.achai.framework.logs;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author tom_achai
 * 
 */
public class SessionManager {
	// 产生session id间隔时间, 现在暂时想不到放在那里好...
	protected int generateTime = 1000;

	private Queue<SecureRandom> randoms = new ConcurrentLinkedQueue<SecureRandom>();

	/**
	 * 获取一个session id
	 * 
	 * @return
	 */
	public String generateSessionId() {
		byte random[] = new byte[16];

		// 把进制结果转换为String 类型
		StringBuilder buffer = new StringBuilder();

		getRandomBytes(random);
		// 把byte 流转换成string
		// 0 0 o O
		for (int i = 0; i < random.length; i++) {
			byte b1 = (byte) ((random[i] & 0xf0) >> 4);
			byte b2 = (byte) ((random[i] & 0x0f));
			if (b1 < 10)
				buffer.append((char) ('0' + b1));
			else
				buffer.append((char) ('A' + (b1 - 10)));
			if (b2 < 10)
				buffer.append((char) ('0' + b2));
			else
				buffer.append((char) ('A' + (b2 - 10)));
		}

		return buffer.toString();
	}

	/**
	 * 把强加密随机数生成器 加到队列当中
	 * 
	 * @param bytes
	 */
	private void getRandomBytes(byte[] bytes) {
		SecureRandom random = randoms.poll();
		if (random == null) {
			random = createSecureRandom();
		}
		random.nextBytes(bytes);
		randoms.add(random);
	}

	/**
	 * 生成一个强加密随机数生成器 (RNG)对象。
	 * 
	 * @return
	 */
	private SecureRandom createSecureRandom() {
		SecureRandom result = null;

		try {
			result = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result.nextInt();
		return result;
	}
}
