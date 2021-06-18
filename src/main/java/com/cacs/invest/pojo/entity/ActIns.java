package com.cacs.invest.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author sen.zhang
 * @since 2021-06-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ActIns对象", description = "")
public class ActIns implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "实例id")
    @TableId(value = "ins_id",type = IdType.ASSIGN_ID)
    private Long insId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建者id")
    private Long createUserId;

    @ApiModelProperty(value = "实例状态，0：false，1：true")
    @TableField("is_freeze")
    private Boolean freeze;

    @ApiModelProperty(value = "是否完成，0：false，1：true")
    @TableField("is_completed")
    private Boolean completed;

    @ApiModelProperty(value = "实例名称")
    private String insName;

    @ApiModelProperty(value = "实例描述")
    private String insDescription;


}
