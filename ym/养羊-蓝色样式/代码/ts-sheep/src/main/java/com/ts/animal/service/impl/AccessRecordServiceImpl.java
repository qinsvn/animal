package com.ts.animal.service.impl;

import com.google.common.collect.Maps;
import com.ts.animal.constant.AnimalConstant;
import com.ts.animal.dao.ExAccessRecordDao;
import com.ts.animal.domain.InputsDO;
import com.ts.animal.service.InputsService;
import com.ts.common.aspect.LogAspect;
import com.ts.common.domain.DictDO;
import com.ts.common.redis.RedisUtil;
import com.ts.common.utils.DataPerUitl;
import com.ts.common.utils.DateUtils;
import com.ts.common.utils.ShiroUtils;
import com.ts.system.util.SystemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ts.animal.dao.AccessRecordDao;
import com.ts.animal.domain.AccessRecordDO;
import com.ts.animal.service.AccessRecordService;
import com.ts.common.utils.Result;


@Service
public class AccessRecordServiceImpl implements AccessRecordService {

    private static final Logger logger = LoggerFactory.getLogger(AccessRecordServiceImpl.class);

    @Autowired
    private InputsService inputsService;

    @Autowired
    private AccessRecordDao accessRecordDao;

    @Autowired
    private ExAccessRecordDao exAccessRecordDao;

    @Override
    public AccessRecordDO get(Integer id) {
        return accessRecordDao.get(id);
    }

    @Override
    public List<AccessRecordDO> list(Map<String, Object> map) {
        map.putAll(DataPerUitl.deptPer());
        return exAccessRecordDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        map.putAll(DataPerUitl.deptPer());
        return exAccessRecordDao.count(map);
    }

    @Override
    public int save(Result result, AccessRecordDO accessRecord) {
        accessRecord.setCreateUser((int) ShiroUtils.getUserId().longValue());
        if (accessRecord.getDeptId() == null) {
            accessRecord.setDeptId((int) ShiroUtils.getUser().getDeptId().longValue());
        }
        //查询 是否存在该编号
        Map<String, Object> map = Maps.newHashMap();
        map.put("num", accessRecord.getNum());
        List<InputsDO> InputsDOs = inputsService.list(map);
        if (InputsDOs.size() > 0) {
            accessRecord.setInputsId(InputsDOs.get(0).getId());
            accessRecord.setBatchNum(InputsDOs.get(0).getBatchNum());
            accessRecord.setTypeName(InputsDOs.get(0).getTypeName());
            accessRecord.setVarietyName(InputsDOs.get(0).getVarietyName());
        }
        accessRecord.setCreateTime(new Date());
        int ret = accessRecordDao.save(accessRecord);
        if (ret == 0) {
            result.set(Result.FAIL, "保存失败", null);
        }
        return ret;
    }

    @Override
    public void saveBySystem(String num) {
        Map<Object, DictDO> intervalMap = SystemUtil.getDicts().get(AnimalConstant.DICT_ACCESS_INTERVAL);
        if(RedisUtil.ttl(AnimalConstant.REDIS_DICT_ACCESS_INTERVAL_KEY+num)>0){
            logger.info("重复采集编号【"+num+"】");
            return;
        }
        RedisUtil.set(AnimalConstant.REDIS_DICT_ACCESS_INTERVAL_KEY+num,num);
        //记录超时
        for (Object key : intervalMap.keySet()) {
            String keyStr = key.toString();
            Long timeout = Long.valueOf(keyStr);
            RedisUtil.set(AnimalConstant.REDIS_DICT_ACCESS_INTERVAL_KEY+num,num,timeout);
        }

        Date now = new Date();
        AccessRecordDO accessRecord = new AccessRecordDO();
        accessRecord.setNum(num);
        accessRecord.setOccurrenceTime(now);
        accessRecord.setCreateTime(now);
        Map<Object, DictDO> accessDirectionMap = SystemUtil.getDicts().get(AnimalConstant.DICT_ACCESS_DIRECTION);
        accessRecord.setDirection(AnimalConstant.ACCESS_IN);
        try {
            for (Object key : accessDirectionMap.keySet()) {
                String keyStr = key.toString();
                String[] keys = keyStr.split("~");
                //根据时间判断是否是出栏时间段
                boolean isOut = DateUtils.isEffectiveDate(now, keys[0], keys[1]);
                if (isOut) {
                    accessRecord.setDirection(AnimalConstant.ACCESS_OUT);
                    break;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //查询 是否存在该编号
        Map<String, Object> map = Maps.newHashMap();
        map.put("num", num);
        List<InputsDO> InputsDOs = inputsService.list(map);
        if (InputsDOs.size() > 0) {
            accessRecord.setInputsId(InputsDOs.get(0).getId());
            accessRecord.setBatchNum(InputsDOs.get(0).getBatchNum());
            accessRecord.setTypeName(InputsDOs.get(0).getTypeName());
            accessRecord.setVarietyName(InputsDOs.get(0).getVarietyName());
            accessRecord.setCreateUser(InputsDOs.get(0).getCreateUser());
            accessRecord.setDeptId(InputsDOs.get(0).getDeptId());
        }
        accessRecordDao.save(accessRecord);
    }

    @Override
    public int update(Result result, AccessRecordDO accessRecord) {
        //查询 是否存在该编号
        Map<String, Object> map = Maps.newHashMap();
        map.put("num", accessRecord.getNum());
        List<InputsDO> InputsDOs = inputsService.list(map);
        if (InputsDOs.size() > 0) {
            accessRecord.setInputsId(InputsDOs.get(0).getId());
            accessRecord.setBatchNum(InputsDOs.get(0).getBatchNum());
            accessRecord.setTypeName(InputsDOs.get(0).getTypeName());
            accessRecord.setVarietyName(InputsDOs.get(0).getVarietyName());
            accessRecord.setCreateUser(InputsDOs.get(0).getCreateUser());
            accessRecord.setDeptId(InputsDOs.get(0).getDeptId());
        }
        accessRecord.setUpdateUser((int) ShiroUtils.getUserId().longValue());
        accessRecord.setUpdateTime(new Date());
        int ret = accessRecordDao.update(accessRecord);
        if (ret == 0) {
            result.set(Result.FAIL, "更新失败", null);
        }
        return ret;
    }

    @Override
    public int remove(Result result, Integer id) {
        int ret = accessRecordDao.remove(id);
        if (ret == 0) {
            result.set(Result.FAIL, "删除失败", null);
        }
        return ret;
    }

    @Override
    public int batchRemove(Result result, Integer[] ids) {
        int ret = accessRecordDao.batchRemove(ids);
        if (ret != ids.length) {
            result.set(Result.FAIL, "部分删除失败", null);
        }
        return ret;
    }

}
