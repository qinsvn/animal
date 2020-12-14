package com.label.dao.auto;

import com.label.common.model.base.BsStaRecord;
import com.label.common.model.base.BsStaRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsStaRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_sta_record
     *
     * @mbggenerated Sun Aug 04 17:05:06 CST 2019
     */
    int countByExample(BsStaRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_sta_record
     *
     * @mbggenerated Sun Aug 04 17:05:06 CST 2019
     */
    int deleteByExample(BsStaRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_sta_record
     *
     * @mbggenerated Sun Aug 04 17:05:06 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_sta_record
     *
     * @mbggenerated Sun Aug 04 17:05:06 CST 2019
     */
    int insert(BsStaRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_sta_record
     *
     * @mbggenerated Sun Aug 04 17:05:06 CST 2019
     */
    int insertSelective(BsStaRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_sta_record
     *
     * @mbggenerated Sun Aug 04 17:05:06 CST 2019
     */
    List<BsStaRecord> selectByExample(BsStaRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_sta_record
     *
     * @mbggenerated Sun Aug 04 17:05:06 CST 2019
     */
    BsStaRecord selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_sta_record
     *
     * @mbggenerated Sun Aug 04 17:05:06 CST 2019
     */
    int updateByExampleSelective(@Param("record") BsStaRecord record, @Param("example") BsStaRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_sta_record
     *
     * @mbggenerated Sun Aug 04 17:05:06 CST 2019
     */
    int updateByExample(@Param("record") BsStaRecord record, @Param("example") BsStaRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_sta_record
     *
     * @mbggenerated Sun Aug 04 17:05:06 CST 2019
     */
    int updateByPrimaryKeySelective(BsStaRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_sta_record
     *
     * @mbggenerated Sun Aug 04 17:05:06 CST 2019
     */
    int updateByPrimaryKey(BsStaRecord record);
}