package com.label.service.web;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	
	Object create(MultipartFile file, int type);
	
}
