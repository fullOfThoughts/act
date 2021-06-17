package com.cacs.invest.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

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
@ApiModel(value = "ActTask对象", description = "")
public class ActTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务id")
    @TableId(value = "task_id", type = IdType.ASSIGN_ID)
    private Long taskId;

    @ApiModelProperty(value = "实例id")
    private Long insId;

    @ApiModelProperty(value = "处理次任务的userId")
    private Long assigneeUserId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private Long createUserId;

    @ApiModelProperty(value = "对应的流程id")
    private Integer pscId;

    @ApiModelProperty(value = "是否是回退到次任务，0：false，1：true")
    @TableField("is_back_here")
    private Boolean isBackHere;

//    @ApiModelProperty(value = "任务完成者id")
//    private Long completeUserId;
//
//    @ApiModelProperty(value = "完成时间")
//    private LocalDateTime completeTime;


}
