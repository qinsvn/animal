package com.label.dao.auto;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.label.common.model.base.Upload;
import com.label.common.model.base.UploadExample;

public interface UploadMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_upload
     *
     * @mbggenerated Thu Jan 11 11:42:19 CST 2018
     */
    int countByExample(UploadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_upload
     *
     * @mbggenerated Thu Jan 11 11:42:19 CST 2018
     */
    int deleteByExample(UploadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_upload
     *
     * @mbggenerated Thu Jan 11 11:42:19 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_upload
     *
     * @mbggenerated Thu Jan 11 11:42:19 CST 2018
     */
    int insert(Upload record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_upload
     *
     * @mbggenerated Thu Jan 11 11:42:19 CST 2018
     */
    int insertSelective(Upload record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_upload
     *
     * @mbggenerated Thu Jan 11 11:42:19 CST 2018
     */
    List<Upload> selectByExample(UploadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_upload
     *
     * @mbggenerated Thu Jan 11 11:42:19 CST 2018
     */
    Upload selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_upload
     *
     * @mbggenerated Thu Jan 11 11:42:19 CST 2018
     */
    int updateByExampleSelective(@Param("record") Upload record, @Param("example") UploadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_upload
     *
     * @mbggenerated Thu Jan 11 11:42:19 CST 2018
     */
    int updateByExample(@Param("record") Upload record, @Param("example") UploadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_upload
     *
     * @mbggenerated Thu Jan 11 11:42:19 CST 2018
     */
    int updateByPrimaryKeySelective(Upload record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_upload
     *
     * @mbggenerated Thu Jan 11 11:42:19 CST 2018
     */
    int updateByPrimaryKey(Upload record);
}