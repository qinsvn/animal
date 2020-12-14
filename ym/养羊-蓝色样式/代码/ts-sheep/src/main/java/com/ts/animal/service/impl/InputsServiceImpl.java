package com.ts.animal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.ts.animal.constant.AnimalConstant;
import com.ts.animal.dao.ExInputsDao;
import com.ts.animal.dao.InputsDao;
import com.ts.animal.domain.InputsDO;
import com.ts.animal.service.InputsService;
import com.ts.common.utils.CaseFormatUtil;
import com.ts.common.utils.DataPerUitl;
import com.ts.common.utils.Result;
import com.ts.common.utils.ShiroUtils;
import com.ts.common.utils.StringUtils;


@Transactional
@Service
public class InputsServiceImpl implements InputsService {
    @Autowired
    private InputsDao inputsDao;
    @Autowired
    private ExInputsDao exInputsDao;

    @Override
    public InputsDO get(Integer id) {
        return inputsDao.get(id);
    }

    @Override
    public List<InputsDO> list(Map<String, Object> map) {
        return inputsDao.list(map);
    }

    @Override
    public List<Map<String, Object>> exList(Map<String, Object> map) {
        map.putAll(DataPerUitl.deptPer("bs_inputs.dept_id"));
        return CaseFormatUtil.lowerUnderscore(exInputsDao.list(map));
    }

    @Override
    public int count(Map<String, Object> map) {
        map.putAll(DataPerUitl.deptPer("bs_inputs.dept_id"));
        return exInputsDao.count(map);
    }

    @Override
    public int exCount(Map<String, Object> map) {
        return exInputsDao.count(map);
    }

    @Override
    public int save(Result result, InputsDO inputs) {
        int ret = 0;

        if (!StringUtils.isEmpty(inputs.getParentNum())) {
            if (inputs.getParentNum().equals(inputs.getNum())) {
                result.set(Result.FAIL, "请输入正确的上一代编号", null);
                return ret;
            }
            Map<String, Object> map = Maps.newHashMap();
            map.put("num", inputs.getParentNum());
            List<InputsDO> parents = this.list(map);
            if (parents.size() > 0) {
                inputs.setParentId(parents.get(0).getId());
            } else {
                result.set(Result.FAIL, "请输入正确的上一代编号", null);
                return ret;
            }
        }

        if (StringUtils.isEmpty(inputs.getTypeName())) {
            result.set(Result.FAIL, "请选择投入品类型", null);
            return ret;
        }

        Map<String, Object> map = Maps.newHashMap();
        map.put("num", inputs.getNum());
        if (inputsDao.list(map).size() > 0) {
            result.set(Result.FAIL, "已经存在投入品编号【" + inputs.getNum() + "】", null);
            return ret;
        }
        inputs.setCreateUser((int) ShiroUtils.getUserId().longValue());
        if (inputs.getDeptId() == null) {
            inputs.setDeptId((int) ShiroUtils.getUser().getDeptId().longValue());
        }
        inputs.setCurWeight(inputs.getInitWeight());
        inputs.setCreateTime(new Date());
        inputs.setStatus(AnimalConstant.INPUTS_STATUS_FEEDING);
        ret = inputsDao.save(inputs);
        if (ret == 0) {
            result.set(Result.FAIL, "操作失败", null);
        }
        return ret;
    }

    @Override
    public int update(Result result, InputsDO inputs) {
        int ret = 0;
        if (inputs.getDeptId() == null) {
            inputs.setDeptId((int) ShiroUtils.getUser().getDeptId().longValue());
        }
        inputs.setUpdateUser((int) ShiroUtils.getUserId().longValue());
        inputs.setUpdateTime(new Date());

        if (!StringUtils.isEmpty(inputs.getParentNum())) {
            if (inputs.getParentNum().equals(inputs.getNum())) {
                result.set(Result.FAIL, "请输入正确的上一代编号", null);
                return ret;
            }
            Map<String, Object> map = Maps.newHashMap();
            map.put("num", inputs.getParentNum());
            List<InputsDO> parents = this.list(map);
            if (parents.size() > 0) {
                inputs.setParentId(parents.get(0).getId());
            } else {
                result.set(Result.FAIL, "请输入正确的上一代编号", null);
                return ret;
            }
        } else {
            inputs.setParentId(-1);
        }

        return inputsDao.update(inputs);
    }

    @Override
    public int update(InputsDO inputs) {
        return inputsDao.update(inputs);
    }

    @Override
    public int remove(Integer id) {
        return inputsDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return inputsDao.batchRemove(ids);
    }

}
