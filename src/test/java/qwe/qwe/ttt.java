package qwe.qwe;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cacs.invest.pojo.entity.ActHis;
import com.cacs.invest.pojo.entity.ActIns;
import com.cacs.invest.pojo.entity.ActPcs;
import com.cacs.invest.pojo.entity.ActTask;
import com.cacs.invest.service.ActHisService;
import com.cacs.invest.service.ActInsService;
import com.cacs.invest.service.ActPcsService;
import com.cacs.invest.service.ActTaskService;
import com.cacs.invest.tototo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author sen.zhang
 * @Date 2021/6/17
 */
@SpringBootTest(classes = tototo.class)
public class ttt {

    @Autowired
    ActHisService actHisService;

    @Autowired
    ActInsService actInsService;

    @Autowired
    ActPcsService actPcsService;

    @Autowired
    ActTaskService actTaskService;

    @Test
    public void createPro() throws Exception {
        createProject(111L, 221L);
        completeTask(1405463823049166848L, 221L, 555L);
//        taskBack("");
//        completeTaskFinal(1405460395216474112L);
    }


    /**
     * 用户创建项目，指定下一项目负责人
     */
    public void createProject(Long uid, Long assigneeId) throws Exception {
        int PCS_ID = 1;
        Long taskId = new Snowflake(1, 1).nextId();
        Long insId = new Snowflake(1, 1).nextId();

        ActIns actIns = new ActIns();
        ActTask actTask = new ActTask();

        actIns.setInsId(insId).setCompleted(false).setCreateUserId(uid).setFreeze(false);
        actTask.setInsId(insId).setCreateUserId(uid).setAssigneeUserId(uid).setPscId(PCS_ID).setTaskId(taskId);
        actInsService.save(actIns);
        actTaskService.save(actTask);
        //  完成
        completeTask(taskId, uid, assigneeId);

    }

    /**
     * 创建任务
     *
     * @param uid        创建者id
     * @param assigneeId 受理者id
     * @param insId      实例id
     * @param pcsId      下一流程id
     */
    public void createTask(Long uid, Long assigneeId, Long insId, int pcsId) {
        Long taskId = new Snowflake(1, 1).nextId();
        ActTask actTask = new ActTask();
        actTask.setTaskId(taskId).setInsId(insId).setAssigneeUserId(assigneeId).setPscId(pcsId).setCreateUserId(uid);
        actTaskService.save(actTask);
    }


    /**
     * 完成任务
     *
     * @param taskId     任务id
     * @param uid        完成者id
     * @param assigneeId 下一任务受理者id
     */
    public void completeTask(Long taskId, Long uid, Long assigneeId) {
        ActTask task = actTaskService.getById(taskId);
        ActPcs pcs = actPcsService.getById(task.getPscId());
        createTask(uid, assigneeId, task.getInsId(), pcs.getPcsNext());
        ActHis actHis = taskConverterToHis(task);
        actTaskService.removeById(taskId);
        actHisService.save(actHis);
    }

    /**
     * 回退任务
     *
     * @param taskId     任务id
     * @param uid        完成者id
     * @param assigneeId 下一任务受理者id
     * @param pcsId      流程id
     */
    public void taskBack(Long taskId, Long uid, Long assigneeId, int pcsId) {
        ActTask task = actTaskService.getById(taskId);
        createTask(uid, assigneeId, task.getInsId(), pcsId);
        ActHis actHis = taskConverterToHis(task);
        actTaskService.removeById(taskId);
        actHisService.save(actHis);
    }

    /**
     * 结束任务
     */
    public void completeTaskFinal(Long taskId) throws Exception {
        ActTask task = actTaskService.getById(taskId);
        ActPcs pcs = actPcsService.getById(task.getPscId());
        if (pcs.getPcsNext() == 9527) {
            //  流程结束
            ActIns ins = actInsService.getById(task.getInsId());
            ins.setCompleted(true);
            actInsService.update(ins, null);
        } else {
            //  未结束
            throw new Exception("任务未结束");
        }
        ActHis actHis = taskConverterToHis(task);
        actTaskService.removeById(taskId);
        actHisService.save(actHis);
    }


    /**
     * 实例id 查询实例历史
     */
    public List<ActHis> queryHistory(Long insId) {
        QueryWrapper<ActHis> wrapper = new QueryWrapper<>();
        wrapper.eq("ins_id", insId);
        return actHisService.list(wrapper);
    }

    /**
     * 查询所有未完成的实例
     */
    public List<ActIns> queryAllUndoneIns() {
        QueryWrapper<ActIns> wrapper = new QueryWrapper<>();
        wrapper.eq("is_completed", 0);
        return actInsService.list(wrapper);
    }

    /**
     * 查询所有冻结的实例
     */
    public List<ActIns> queryAllFreezeIns() {
        QueryWrapper<ActIns> wrapper = new QueryWrapper<>();
        wrapper.eq("is_freeze", 1);
        return actInsService.list(wrapper);
    }

    /**
     * 查询所有已完成的实例
     */
    public List<ActIns> queryAllDoneIns() {
        QueryWrapper<ActIns> wrapper = new QueryWrapper<>();
        wrapper.eq("is_completed", 1);
        return actInsService.list(wrapper);
    }

    /**
     * task-->his
     */
    public ActHis taskConverterToHis(ActTask actTask) {
        ActHis actHis = new ActHis();
        actHis.setAssigneeUserId(actTask.getAssigneeUserId())
                .setCreateTime(actTask.getCreateTime())
                .setCreateUserId(actTask.getCreateUserId())
                .setInsId(actTask.getInsId())
                .setPscId(actTask.getPscId())
                .setAssigneeUserId(actTask.getAssigneeUserId())
                .setTaskId(actTask.getTaskId());
        return actHis;
    }

}
