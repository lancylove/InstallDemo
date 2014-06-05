package com.justsy.installdemo;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class MyPackageInstaller {
	
	/**
	 * 静默安装
	 * @param file
	 * @return
	 */
	public static boolean slientInstall(File file) {
		boolean result = false;
		Process process = null;
		OutputStream out = null;
		try {
			process = Runtime.getRuntime().exec("su");
			out = process.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);
			dataOutputStream.writeBytes("chmod 777 " + file.getPath() + "\n");
			dataOutputStream.writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " +
					file.getPath());
			// 提交命令
			dataOutputStream.flush();
			// 关闭流操作
			dataOutputStream.close();
			out.close();
			int value = process.waitFor();
			
			// 代表成功
			if (value == 0) {
				result = true;
			} else if (value == 1) { // 失败
				result = false;
			} else { // 未知情况
				result = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
