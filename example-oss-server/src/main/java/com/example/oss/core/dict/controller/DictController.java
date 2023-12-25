package com.example.oss.core.dict.controller;

import com.example.oss.common.web.Success;
import com.example.oss.core.dict.domain.request.DictRequest;
import com.example.oss.core.dict.service.DictService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典
 */
@RestController
@RequestMapping("/dict")
@Slf4j
public class DictController {
	@Resource
	private DictService dictService;

	@RequestMapping("/getDict")
	public Success getDict(@RequestBody @Valid DictRequest request) {
		return Success.ok(dictService.getDict(request.getDictCode()));
	}


}
