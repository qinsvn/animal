package com.ts.animal.controller;

import java.util.List;
import java.util.Map;

import com.ts.common.annotation.Log;
import com.ts.common.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.ts.animal.domain.CheckRecordDO;
import com.ts.animal.domain.ImmuneRecordDO;
import com.ts.animal.domain.InputsDO;
import com.ts.animal.service.CheckRecordService;
import com.ts.animal.service.ImmuneRecordService;
import com.ts.animal.service.InputsService;
import com.ts.common.service.ExportExcelSevice;
import com.ts.system.util.SystemUtil;

/**
 * 投入品表
 *
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:01:15
 */

@Controller
@RequestMapping("/animal/inputs")
public class InputsController {
    @Autowired
    private InputsService inputsService;
    @Autowired
    private CheckRecordService checkRecordService;
    @Autowired
    private ImmuneRecordService immuneRecordService;

    @GetMapping()
    @RequiresPermissions("animal:inputs:inputs")
    String Inputs() {
        return "animal/inputs/inputs";
    }

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("animal:inputs:inputs")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<Map<String, Object>> inputsList = inputsService.exList(query);
        int total = inputsService.exCount(query);
        PageUtils pageUtils = new PageUtils(inputsList, total);
        //导出 start
        if (StringUtils.isNotEmpty(UtilExportExcelPlus.getFileId(params))) {
            UtilExportExcelPlus.exportExcels(params, inputsList, new ExportExcelSevice() {
                @Override
                public Object changeVal(String key, Object val, int index) {
                    // TODO Auto-generated method stub
                    if (key.equals("typeName")) {
                        return SystemUtil.getDicts().get("animal_type").get(val).getName();
                    }
                    if (key.equals("varietyName")) {
                        return SystemUtil.getDicts().get("animal_varieties").get(val).getName();
                    }
                    if (key.equals("status")) {
                        return SystemUtil.getDicts().get("inputs_status").get(val).getName();
                    }
                    if (key.equals("deptId")) {
                        Long valLong = Long.valueOf(val.toString());
                        return SystemUtil.getDepts().get(valLong).getName();
                    }
                    if (key.equals("createUser")) {
                        Long valLong = Long.valueOf(val.toString());
                        return SystemUtil.getUsers().get(valLong).getName();
                    }
                    if (key.equals("createTime")) {
                        return DateUtils.stampToDate(val.toString(), DateUtils.DATE_TIME_PATTERN);
                    }
                    return val;
                }
            });
            return null;
        }
        //end 导出结束
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("animal:inputs:add")
    String add() {
        return "animal/inputs/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("animal:inputs:edit")
    String edit(@PathVariable("id") Integer id, Model model) {
        InputsDO inputs = inputsService.get(id);
        model.addAttribute("inputs", inputs);
        return "animal/inputs/edit";
    }

    /**
     * 保存
     */
    @Log("添加动物")
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("animal:inputs:add")
    public Result save(InputsDO inputs) {
        Result r = Result.ok();
        inputsService.save(r, inputs);
        return r;
    }

    /**
     * 修改
     */
    @Log("修改动物信息")
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("animal:inputs:edit")
    public Result update(InputsDO inputs) {
        inputsService.update(null, inputs);
        return Result.ok();
    }

    /**
     * 删除
     */
    @Log("删除动物信息")
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("animal:inputs:remove")
    public Result remove(Integer id) {
        if (inputsService.remove(id) > 0) {
            return Result.ok();
        }
        return Result.error();
    }

    /**
     * 删除
     */
    @Log("批量删除动物信息")
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("animal:inputs:batchRemove")
    public Result remove(@RequestParam("ids[]") Integer[] ids) {
        inputsService.batchRemove(ids);
        return Result.ok();
    }


    /**
     * 获取动物信息
     */
    @GetMapping("/info/{num}")
    public String getAnimalInfo(@PathVariable("num") String num, Model model) {
        model.addAttribute("num", num);
        InputsDO inputsDO = null;
        Map<String, Object> map = Maps.newHashMap();
        map.put("num", num);

        List<InputsDO> inputList = inputsService.list(map);
        if (inputList.size() > 0) {
            inputsDO = inputList.get(0);
        } else {
            return "wechat/animalInfo";
        }
        List<CheckRecordDO> checkRecords = checkRecordService.list(map);
        List<ImmuneRecordDO> immuneRecords = immuneRecordService.list(map);

        model.addAttribute("input", inputsDO);
        model.addAttribute("checkRecords", checkRecords);
        model.addAttribute("immuneRecords", immuneRecords);
        return "wechat/animalInfo";
    }

    @Log("上传动物照片")
    @ResponseBody
    @RequestMapping("/updatePhone")
    public Result updatePhone(InputsDO inputs) {
        inputsService.update(inputs);
        return Result.ok();
    }

}
