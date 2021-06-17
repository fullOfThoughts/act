package com.cacs.invest.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@ApiModel(value="ActPcs对象", description="")
public class ActPcs implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流程id，")
      @TableId(value = "pcs_id", type = IdType.AUTO)
    private Integer pcsId;

    @ApiModelProperty(value = "流程名称")
    private String pcsName;

    @ApiModelProperty(value = "流程对应的阶段")
    private String pcsStage;

    @ApiModelProperty(value = "下一流程的id，9527为结束id")
    private Integer pcsNext;

    @ApiModelProperty(value = "当前流程可以回退到的流程id，以,分割，-1表示不可回退")
    private String pcsBackId;


}
