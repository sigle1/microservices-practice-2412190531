package com.zjgsu.chr;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

	@GetMapping("/api/hello")
	public Map<String, String> hello() {
		return Map.of(
				"project", "宠安心——宠物医院预约与诊疗管理系统",
				"message", "宠安心单体应用运行正常");
	}
}
