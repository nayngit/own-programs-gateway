package com.own.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.slf4j.Logger;

/**
 * 
 * 类 名: SerializeUtil<br/>
 * 描 述: 序列化工具<br/>
 * 作 者: 郭昕<br/>
 * 创 建： 2013-6-26<br/>
 *
 * 历 史: (版本) 作者 时间 注释
 */
public class SerializeUtil {

	/**
	 * 日志处理
	 */
    private static final Logger LOGGER = LoggerProxyFactory.getLogger(SerializeUtil.class);
    
    /** 16 */
	private static final int BYTES_LEN = 1024;
    
   /**
    * 描 述：对象to字节数组<br/>
    * 作 者：郭昕<br/>
    * 历 史: (版本) 作者 时间 注释 <br/>
    * @param obj 序列化对象
    * @return 字节数组
    */
    public static final byte[] obj2Bytes(Serializable obj) {
        if (obj == null) {
            LOGGER.warn("对象为空，无法序列化为字节数组");
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(BYTES_LEN);
        ObjectOutputStream oos = null;
        byte[] b = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            b = baos.toByteArray();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return b;
    }

    /**
     * 描述：字节数组to对象<br/>
     * 作 者：郭昕<br/>
     * 历 史: (版本) 作者 时间 注释 <br/>
     * @param b 字节数组
     * @return 序列化对象
     */
    @SuppressWarnings("unchecked")
    public static final <T extends Serializable> T bytes2Obj(byte[] b) {
        if (b == null || b.length == 0) {
            LOGGER.warn("对象字节数组为空，无法反序列化为对象");
            return null;
        }
        ObjectInputStream ois = null;
        T resultObj = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(b));
            Object obj = ois.readObject();
            resultObj = (T) obj;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return resultObj;
    }
}
