package com.ts.tags.define;
import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

/*
<html xmlns:th="http://www.thymeleaf.org" xmlns:sys="">

静态数据字典
<sys:dict type="animal_type" id="animalId" name="animalName" class="form-control"  style=""  firstValue="-1" firstText="请选择动物类型" selected="pig" />

动态数据字典
<sys:dict url="/sys/menu/list" valueText="" nameText=""  id="animalId" name="animalName" class="form-control"  style=""  firstValue="-1" firstText="请选择动物类型" selected="pig" />

*/

/**
 * Thymeleaf 方言：系统用
 */
public class SysDialect extends AbstractProcessorDialect {
    // 定义方言名称
    private static final String DIALECT_NAME = "Sys Dialect";
    public SysDialect() {
        // 设置自定义方言与“方言处理器”优先级相同
        super(DIALECT_NAME, "sys", StandardDialect.PROCESSOR_PRECEDENCE);
    }
    /**
     * 元素处理器
     * @param dialectPrefix 方言前缀
     * @return
     */
    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        Set<IProcessor> processors = new HashSet<IProcessor>();
        // 添加自定义标签
        processors.add(new SysDictTagProcessor(dialectPrefix));
        processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
        return processors;
    }
}