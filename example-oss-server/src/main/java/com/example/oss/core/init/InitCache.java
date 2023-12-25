package com.example.oss.core.init;

import com.example.oss.core.dict.service.DictService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitCache implements CommandLineRunner {
	@Resource
	private DictService dictService;

	@Override
	public void run(String... args) {
		dictService.cacheDict();
	}

}
