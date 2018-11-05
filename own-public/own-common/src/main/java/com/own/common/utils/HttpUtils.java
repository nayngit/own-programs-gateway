package com.own.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

public class HttpUtils {
	 /** 
     * 使用Get方式获取数据 
     *  
     * @param url 
     *            URL包括参数，http://HOST/XX?XX=XX&XXX=XXX 
     * @param charset 
     * @return 
     */  
    public static String sendGet(String url, String charset) {  

		HttpClient httpclient = createHttpClient();
		String body = null;
		HttpGet httget = new HttpGet(url);

		body = invoke(httpclient, httget, charset);
		httpclient.getConnectionManager().shutdown();

		return body;
    }  
  
    
    public static String executePostParamJson(String url, String json) {
		// TODO Auto-generated method stub
		return executePostNoParamName(url, json, "application/json");
	}
    
    public static String executePostNoParamName(String url, String str,
			String contentType) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;
		HttpPost post = postFormNoParamName(url, str, contentType);
		body = invoke(httpclient, post, "UTF-8");

		httpclient.getConnectionManager().shutdown();

		return body;
	}
    private static HttpPost postFormNoParamName(String url, String xml,
			String contentType) {

		HttpPost httpost = new HttpPost(url);
		StringEntity myEntity = null;
		myEntity = new StringEntity(xml, "utf-8");
		httpost.addHeader("Content-Type", contentType);
		httpost.setEntity(myEntity);

		return httpost;
	}
    /**  
     * POST请求，字符串形式数据  
     * @param url 请求地址  
     * @param param 请求数据  
     * @param charset 编码方式  
     */  
    public static String sendPostUrl(String url, String param, String charset) {  
  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            out = new PrintWriter(conn.getOutputStream());  
            // 发送请求参数  
            out.print(param);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(  
                    conn.getInputStream(), charset));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("发送 POST 请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
    /**  
     * POST请求，Map形式数据  
     * @param url 请求地址  
     * @param param 请求数据  
     * @param charset 编码方式  
     */  
    public static String sendPost(String url, Map<String, Object> param,  
            String charset) {  
  
        StringBuffer buffer = new StringBuffer();  
        if (param != null && !param.isEmpty()) {  
            for (Map.Entry<String, Object> entry : param.entrySet()) {  
                try {
					buffer.append(entry.getKey()).append("=")  
					        .append((entry.getValue() instanceof String)?URLEncoder.encode((String)entry.getValue(),"UTF-8"):(entry.getValue().toString()))  
					        .append("&");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
  
            }  
        }  
        buffer.deleteCharAt(buffer.length() - 1);  
  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            out = new PrintWriter(conn.getOutputStream());  
            // 发送请求参数  
            out.print(buffer);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(  
                    conn.getInputStream(), charset));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("发送 POST 请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
    public static String formUpload(String url, Map<String, Object> paramMap) {

		HttpClient httpclient = createHttpClient();
		String body = null;
		HttpPost httpost = new HttpPost(url);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//		Charset charset = Charset.forName("UTF-8");
//		builder.setCharset(charset);
		Set<String> keys = paramMap.keySet();
		for(String key:keys){
			Object val = paramMap.get(key);
			if(val instanceof File){
				builder.addBinaryBody(key, (File)val);
			}else if(val instanceof MultipartFile){
				 MultipartFile file = (MultipartFile)val;
				try {
					builder.addBinaryBody(key,file.getInputStream(),ContentType.parse(file.getContentType()),file.getOriginalFilename());
				} catch (UnsupportedCharsetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(val instanceof String){
				builder.addTextBody(key, val.toString(),ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), "UTF-8"));
			}
		}
		// httpost.addHeader("Content-Type",ContentType.MULTIPART_FORM_DATA);
		httpost.setEntity(builder.build());

		body = invoke(httpclient, httpost, "utf-8");

		httpclient.getConnectionManager().shutdown();

		return body;
	
    }
    
    public static byte[] postInputStream(String url,String param){
    	HttpClient httpclient = createHttpClient();
		HttpPost httpost = new HttpPost(url);
		StringEntity myEntity = new StringEntity(param, "utf-8");
		httpost.setEntity(myEntity);
		HttpResponse response = sendRequest(httpclient, httpost);
		try {
			return EntityUtils.toByteArray(response.getEntity());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    private static String invoke(HttpClient httpclient,
			HttpUriRequest httpost, String encode) {

		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response, encode);

		return body;
	}
    private static String paseResponse(HttpResponse response, String encode) {
		// logger.info("get response from http server..");
		HttpEntity entity = response.getEntity();

		// logger.info("response status: " + response.getStatusLine());
//		String charset = EntityUtils.getContentCharSet(entity);
		// logger.info(charset);

		String body = null;
		try {
			body = EntityUtils.toString(entity, encode);
			// logger.info(body);
		}catch (IOException e) {
			e.printStackTrace();
		}

		return body;
	}

	private static HttpResponse sendRequest(HttpClient httpclient,
			HttpUriRequest httpost) {
		HttpResponse response = null;

		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
    public static String postMedia(String url, Map<String, Object> paramMap){
      
		String ret = formUpload(url, paramMap);
        return ret;
    }
    
    private static HttpClient createHttpClient(){
    	HttpClient httpclient = new DefaultHttpClient();
    	httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);  
    	httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000); 
    	return httpclient;
    }
}
