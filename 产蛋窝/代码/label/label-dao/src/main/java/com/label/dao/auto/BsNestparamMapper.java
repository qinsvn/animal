package com.label.dao.auto;

import com.label.common.model.base.BsNestparam;
import com.label.common.model.base.BsNestparamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsNestparamMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_nestparam
     *
     * @mbggenerated Wed Nov 27 21:13:00 CST 2019
     */
    int countByExample(BsNestparamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_nestparam
     *
     * @mbggenerated Wed Nov 27 21:13:00 CST 2019
     */
    int deleteByExample(BsNestparamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_nestparam
     *
     * @mbggenerated Wed Nov 27 21:13:00 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_nestparam
     *
     * @mbggenerated Wed Nov 27 21:13:00 CST 2019
     */
    int insert(BsNestparam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_nestparam
     *
     * @mbggenerated Wed Nov 27 21:13:00 CST 2019
     */
    int insertSelective(BsNestparam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_nestparam
     *
     * @mbggenerated Wed Nov 27 21:13:00 CST 2019
     */
    List<BsNestparam> selectByExample(BsNestparamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_nestparam
     *
     * @mbggenerated Wed Nov 27 21:13:00 CST 2019
     */
    BsNestparam selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_nestparam
     *
     * @mbggenerated Wed Nov 27 21:13:00 CST 2019
     */
    int updateByExampleSelective(@Param("record") BsNestparam record, @Param("example") BsNestparamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_nestparam
     *
     * @mbggenerated Wed Nov 27 21:13:00 CST 2019
     */
    int updateByExample(@Param("record") BsNestparam record, @Param("example") BsNestparamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_nestparam
     *
     * @mbggenerated Wed Nov 27 21:13:00 CST 2019
     */
    int updateByPrimaryKeySelective(BsNestparam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_nestparam
     *
     * @mbggenerated Wed Nov 27 21:13:00 CST 2019
     */
    int updateByPrimaryKey(BsNestparam record);
}