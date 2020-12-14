package com.label.dao.auto;

import com.label.common.model.base.BsWarn;
import com.label.common.model.base.BsWarnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsWarnMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_warn
     *
     * @mbg.generated Fri Jul 26 10:56:45 CST 2019
     */
    long countByExample(BsWarnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_warn
     *
     * @mbg.generated Fri Jul 26 10:56:45 CST 2019
     */
    int deleteByExample(BsWarnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_warn
     *
     * @mbg.generated Fri Jul 26 10:56:45 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_warn
     *
     * @mbg.generated Fri Jul 26 10:56:45 CST 2019
     */
    int insert(BsWarn record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_warn
     *
     * @mbg.generated Fri Jul 26 10:56:45 CST 2019
     */
    int insertSelective(BsWarn record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_warn
     *
     * @mbg.generated Fri Jul 26 10:56:45 CST 2019
     */
    List<BsWarn> selectByExample(BsWarnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_warn
     *
     * @mbg.generated Fri Jul 26 10:56:45 CST 2019
     */
    BsWarn selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_warn
     *
     * @mbg.generated Fri Jul 26 10:56:45 CST 2019
     */
    int updateByExampleSelective(@Param("record") BsWarn record, @Param("example") BsWarnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_warn
     *
     * @mbg.generated Fri Jul 26 10:56:45 CST 2019
     */
    int updateByExample(@Param("record") BsWarn record, @Param("example") BsWarnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_warn
     *
     * @mbg.generated Fri Jul 26 10:56:45 CST 2019
     */
    int updateByPrimaryKeySelective(BsWarn record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_warn
     *
     * @mbg.generated Fri Jul 26 10:56:45 CST 2019
     */
    int updateByPrimaryKey(BsWarn record);
}