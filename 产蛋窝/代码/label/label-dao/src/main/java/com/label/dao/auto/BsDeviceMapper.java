package com.label.dao.auto;

import com.label.common.model.base.BsDevice;
import com.label.common.model.base.BsDeviceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsDeviceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_device
     *
     * @mbg.generated Tue Jul 30 18:05:49 CST 2019
     */
    long countByExample(BsDeviceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_device
     *
     * @mbg.generated Tue Jul 30 18:05:49 CST 2019
     */
    int deleteByExample(BsDeviceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_device
     *
     * @mbg.generated Tue Jul 30 18:05:49 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_device
     *
     * @mbg.generated Tue Jul 30 18:05:49 CST 2019
     */
    int insert(BsDevice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_device
     *
     * @mbg.generated Tue Jul 30 18:05:49 CST 2019
     */
    int insertSelective(BsDevice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_device
     *
     * @mbg.generated Tue Jul 30 18:05:49 CST 2019
     */
    List<BsDevice> selectByExample(BsDeviceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_device
     *
     * @mbg.generated Tue Jul 30 18:05:49 CST 2019
     */
    BsDevice selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_device
     *
     * @mbg.generated Tue Jul 30 18:05:49 CST 2019
     */
    int updateByExampleSelective(@Param("record") BsDevice record, @Param("example") BsDeviceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_device
     *
     * @mbg.generated Tue Jul 30 18:05:49 CST 2019
     */
    int updateByExample(@Param("record") BsDevice record, @Param("example") BsDeviceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_device
     *
     * @mbg.generated Tue Jul 30 18:05:49 CST 2019
     */
    int updateByPrimaryKeySelective(BsDevice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_bs_device
     *
     * @mbg.generated Tue Jul 30 18:05:49 CST 2019
     */
    int updateByPrimaryKey(BsDevice record);
}