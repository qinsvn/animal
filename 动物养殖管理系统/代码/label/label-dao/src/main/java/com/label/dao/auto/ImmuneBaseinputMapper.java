package com.label.dao.auto;

import com.label.common.model.base.ImmuneBaseinput;
import com.label.common.model.base.ImmuneBaseinputExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImmuneBaseinputMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_immune_baseinput
     *
     * @mbg.generated Fri Oct 18 17:49:29 CST 2019
     */
    long countByExample(ImmuneBaseinputExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_immune_baseinput
     *
     * @mbg.generated Fri Oct 18 17:49:29 CST 2019
     */
    int deleteByExample(ImmuneBaseinputExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_immune_baseinput
     *
     * @mbg.generated Fri Oct 18 17:49:29 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_immune_baseinput
     *
     * @mbg.generated Fri Oct 18 17:49:29 CST 2019
     */
    int insert(ImmuneBaseinput record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_immune_baseinput
     *
     * @mbg.generated Fri Oct 18 17:49:29 CST 2019
     */
    int insertSelective(ImmuneBaseinput record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_immune_baseinput
     *
     * @mbg.generated Fri Oct 18 17:49:29 CST 2019
     */
    List<ImmuneBaseinput> selectByExample(ImmuneBaseinputExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_immune_baseinput
     *
     * @mbg.generated Fri Oct 18 17:49:29 CST 2019
     */
    ImmuneBaseinput selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_immune_baseinput
     *
     * @mbg.generated Fri Oct 18 17:49:29 CST 2019
     */
    int updateByExampleSelective(@Param("record") ImmuneBaseinput record, @Param("example") ImmuneBaseinputExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_immune_baseinput
     *
     * @mbg.generated Fri Oct 18 17:49:29 CST 2019
     */
    int updateByExample(@Param("record") ImmuneBaseinput record, @Param("example") ImmuneBaseinputExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_immune_baseinput
     *
     * @mbg.generated Fri Oct 18 17:49:29 CST 2019
     */
    int updateByPrimaryKeySelective(ImmuneBaseinput record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_immune_baseinput
     *
     * @mbg.generated Fri Oct 18 17:49:29 CST 2019
     */
    int updateByPrimaryKey(ImmuneBaseinput record);
}