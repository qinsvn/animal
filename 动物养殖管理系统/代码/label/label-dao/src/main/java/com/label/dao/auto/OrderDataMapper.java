package com.label.dao.auto;

import com.label.common.model.base.OrderData;
import com.label.common.model.base.OrderDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderDataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_data
     *
     * @mbggenerated Sun Nov 03 22:22:02 CST 2019
     */
    int countByExample(OrderDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_data
     *
     * @mbggenerated Sun Nov 03 22:22:02 CST 2019
     */
    int deleteByExample(OrderDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_data
     *
     * @mbggenerated Sun Nov 03 22:22:02 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_data
     *
     * @mbggenerated Sun Nov 03 22:22:02 CST 2019
     */
    int insert(OrderData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_data
     *
     * @mbggenerated Sun Nov 03 22:22:02 CST 2019
     */
    int insertSelective(OrderData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_data
     *
     * @mbggenerated Sun Nov 03 22:22:02 CST 2019
     */
    List<OrderData> selectByExample(OrderDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_data
     *
     * @mbggenerated Sun Nov 03 22:22:02 CST 2019
     */
    OrderData selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_data
     *
     * @mbggenerated Sun Nov 03 22:22:02 CST 2019
     */
    int updateByExampleSelective(@Param("record") OrderData record, @Param("example") OrderDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_data
     *
     * @mbggenerated Sun Nov 03 22:22:02 CST 2019
     */
    int updateByExample(@Param("record") OrderData record, @Param("example") OrderDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_data
     *
     * @mbggenerated Sun Nov 03 22:22:02 CST 2019
     */
    int updateByPrimaryKeySelective(OrderData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_data
     *
     * @mbggenerated Sun Nov 03 22:22:02 CST 2019
     */
    int updateByPrimaryKey(OrderData record);
}