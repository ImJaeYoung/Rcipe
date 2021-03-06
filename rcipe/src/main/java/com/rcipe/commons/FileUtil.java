package com.rcipe.commons;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

//우리 시스템에서 필요한 file에 대한 부분을 공통적으로 쓰는 부분을 모아놓았다.
public class FileUtil {

	public FileUtil() {
	}

	// 실제 파일을 업로드하고 파일업로드에 대한 메세지와 새로저장한 파일위치(chageImg)를 MAP에 저장해서 리턴ㄴ
	public static Map<String, String> upload(HttpServletRequest request,
			String path, String nickname) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		if (!(request instanceof MultipartHttpServletRequest)) {
			map.put("massage", "형식에 맞지않습니다.");
			return map;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		for (String fileName : multipartRequest.getFileMap().keySet()) {
			for (MultipartFile file : multipartRequest.getFiles(fileName)) {
				String originalFileName = file.getOriginalFilename();
				String imgExt = originalFileName.substring(
						originalFileName.lastIndexOf(".") + 1,
						originalFileName.length());
				if (imgExt.toUpperCase().equalsIgnoreCase("JPG")
						|| imgExt.toUpperCase().equalsIgnoreCase("BMP")
						|| imgExt.toUpperCase().equalsIgnoreCase("gif")
						|| imgExt.toUpperCase().equalsIgnoreCase("png")) {
					try {
						String str = nickname + "/"
								+ System.currentTimeMillis() + "." + imgExt;
						file.transferTo(new File(path + "/" + str));
						map.put("changeImg", str);
						map.put("success", "T");
						System.err.println("File upload success! ");
					} catch (Exception ie) {
						System.err.println("File writing error! ");
						map.put("succese", "F");
					}
				} else {
					System.err.println("File type error!  FileType:" + imgExt);
					map.put("massage", "해당 파일은 지원하지않습니다.");
					map.put("succese", "F");
					return map;
				}
			}
		}
		map.put("massage", "사진이 업로드 되었습니다.");
		return map;
	}

	// 파일을 지우는 부분
	public static boolean deleteFile(String filePath) {
		if (filePath == null) {
			return false;
		}
		File file = new File(filePath);
		String[] list = file.list();
		if(list==null){
			return file.delete();
		}
		if (list.length != 0) { // 하위 폴더에 내용이 있을경우
			for (int i = 0; i < list.length; i++) { // 내용들을 일일히 체크
				File delFile = new File(file + File.separator + list[i]);// 디렉토리\\화일(혹은디렉토리)
				delFile.delete();
			}
		}
		return file.delete();
	}
}
