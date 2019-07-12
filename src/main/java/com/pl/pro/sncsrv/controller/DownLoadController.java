package com.pl.pro.sncsrv.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pl.pro.sncsrv.config.server.heartbeat.ServerHandler;

@Controller
public class DownLoadController {

	@RequestMapping("/downloadFile")
	private void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String downloadFilePath = ServerHandler.PATH;// 被下载的文件在服务器中的路径,
		String fileName = request.getParameter("filename");// 被下载文件的名称

		File file = new File(downloadFilePath+fileName);
		if (file.exists()) {
			response.setContentType("image/jpg");// 设置强制下载不打开
			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				OutputStream outputStream = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					outputStream.write(buffer, 0, i);
					i = bis.read(buffer);
				}			
				outputStream.flush();
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
