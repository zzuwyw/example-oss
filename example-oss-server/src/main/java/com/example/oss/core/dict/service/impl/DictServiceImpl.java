package com.example.oss.core.dict.service.impl;

import com.example.oss.core.dict.domain.entity.Dict;
import com.example.oss.core.dict.domain.entity.DictItem;
import com.example.oss.core.dict.domain.response.DictResponse;
import com.example.oss.core.dict.mapper.DictItemMapper;
import com.example.oss.core.dict.mapper.DictMapper;
import com.example.oss.core.dict.service.DictService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DictServiceImpl implements DictService {
    @Resource
    private DictMapper dictMapper;
    @Resource
    private DictItemMapper dictItemMapper;
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Override
    public void cacheDict() {
        Set<String> keys = redisTemplate.keys("Dict*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
        String prefix = "Dict:";
        List<Dict> dictList = getAllDict();
        if (!CollectionUtils.isEmpty(dictList)) {
            HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
            Map<String, String> dictMap = new HashMap<>();
            List<DictItem> allItem = getAllDictItem();

            for (Dict dict : dictList) {
                String dictCode = dict.getDictCode();
                String dictName = dict.getDictName();
                dictMap.put(dictCode, dictName);

                List<DictItem> dictItems = getDictItems(allItem, dictCode);
                LinkedHashMap<String, String> itemMap = new LinkedHashMap<>();

                for (DictItem dictItem : dictItems) {
                    String itemCode = dictItem.getItemCode();
                    String itemName = dictItem.getItemName();
                    itemMap.put(itemCode, itemName);
                }

                String itemRedisKey = prefix + dictCode;
                hashOperations.putAll(itemRedisKey, itemMap);
            }
            hashOperations.putAll("Dict", dictMap);

        }
    }

    @Override
    public DictResponse getDict(String dictCode) {
        DictResponse dictResponse;
        String prefix = "Dict:";
        String dictKey = prefix + dictCode;
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        String dictName = (String) hashOperations.get("Dict", dictCode);

        if (StringUtils.hasText(dictName)) {
            List<DictResponse.DictItemResponse> itemList = new ArrayList<>();
            Map<Object, Object> dictItemMap = hashOperations.entries(dictKey);
            dictItemMap.forEach((itemCode, itemName) -> {
                DictResponse.DictItemResponse item = new DictResponse.DictItemResponse();
                item.setItemCode((String) itemCode);
                item.setItemName((String) itemName);
                itemList.add(item);
            });
            dictResponse = new DictResponse();
            dictResponse.setDictCode(dictCode);
            dictResponse.setDictName(dictName);
            dictResponse.setItemList(itemList);
        } else {
            // 缓存不存在，查库
            Dict dict = dictMapper.getDict(dictCode);
            if (dict == null) return null;

            List<DictItem> dictItemList = dictItemMapper.getDictItemList(dictCode);
            List<DictResponse.DictItemResponse> itemList = getDictItemResponses(dictItemList);
            dictResponse = new DictResponse();
            dictResponse.setDictCode(dict.getDictCode());
            dictResponse.setDictName(dict.getDictName());
            dictResponse.setItemList(itemList);
        }

        return dictResponse;
    }

    private static List<DictResponse.DictItemResponse> getDictItemResponses(List<DictItem> dictItemList) {
        List<DictResponse.DictItemResponse> itemList = null;

        if (!CollectionUtils.isEmpty(dictItemList)) {
            itemList = new ArrayList<>(dictItemList.size());
            for (DictItem dictItem : dictItemList) {
                DictResponse.DictItemResponse item = new DictResponse.DictItemResponse();
                item.setItemCode(dictItem.getItemCode());
                item.setItemName(dictItem.getItemName());
                itemList.add(item);
            }
        }
        return itemList;
    }

    List<Dict> getAllDict() {
        return dictMapper.getAll();
    }

    List<DictItem> getAllDictItem() {
        return dictItemMapper.getAll();
    }

    List<DictItem> getDictItems(List<DictItem> allItem, String dictCode) {
        return allItem.stream().filter(i -> i.getDictCode().equals(dictCode))
                .sorted(Comparator.comparing(DictItem::getSort))
                .collect(Collectors.toList());
    }


}
