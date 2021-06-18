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
import com.sun.org.apache.xpath.internal.operations.Bool;
import jdk.nashorn.internal.ir.CallNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
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
//        createProject(111L, new Long[]{1L, 2L}, "南湖审计", "这是一个审计项目");
//        Long[] taskAssigneeIds = getTaskAssigneeIds(1405782774320861184L);
//        System.out.println(taskAssigneeIds);
//        taskBack(1405804260012199936L, 123L, new Long[]{1L, 2L, 3L}, 3);
        completeTask(1405805210441486336L, 1L, new Long[]{11L, 33L}, false);
//        for (Integer integer : getBackId(1405804260012199936L)) {
//            System.out.println(integer);
//        }
//        taskBack("");
//        completeTaskFinal(1405460395216474112L);
    }


    /**
     * 创建项目
     *
     * @param uid         创建者id
     * @param assigneeIds 受理者ids
     * @param insName     实例名称
     * @param insDes      实例描述
     */
    public void createProject(Long uid, Long[] assigneeIds, String insName, String insDes) {
        //  初始化bean
        int PCS_ID = 1;
        Long insId = new Snowflake(1, 1).nextId();
        Long[] longs = {uid};

        ActIns actIns = new ActIns();
        actIns.setInsId(insId).setInsName(insName).setInsDescription(insDes).setCompleted(false).setCreateUserId(uid).setFreeze(false);
        //  操作数据库
        actInsService.save(actIns);
        Long taskId = createTask(uid, longs, insId, PCS_ID, false, false);
        //  完成
        completeTask(taskId, uid, assigneeIds, false);
    }

    /**
     * 指定角色
     */
    public void createProject(Long uid, String assigneeRole, String insName, String insDes) {
        //  初始化bean
        int PCS_ID = 1;
        Long insId = new Snowflake(1, 1).nextId();
        Long[] longs = {uid};

        ActIns actIns = new ActIns();
        ActTask actTask = new ActTask();
        actIns.setInsId(insId).setInsName(insName).setInsDescription(insDes).setCompleted(false).setCreateUserId(uid).setFreeze(false);
        //  操作数据库
        actInsService.save(actIns);
        Long taskId = createTask(uid, longs, insId, PCS_ID, false, false);
        //  完成
        completeTask(taskId, uid, assigneeRole, false);
    }

    /**
     * 创建任务
     *
     * @param uid         创建者id
     * @param assigneeIds 受理者id
     * @param insId       实例id
     * @param pcsId       下一流程id
     */
    public Long createTask(Long uid, Long[] assigneeIds, Long insId, int pcsId, Boolean isBackHere, Boolean isDeliver) {
        Long taskId = new Snowflake(1, 1).nextId();
        ActTask actTask = new ActTask();
        actTask.setTaskId(taskId)
                .setInsId(insId)
                .setAssigneeUserIds(longToString(assigneeIds))
                .setPscId(pcsId)
                .setCreateUserId(uid)
                .setIsBackHere(isBackHere)
                .setIsDeliver(isDeliver);
        actTaskService.save(actTask);
        return taskId;
    }

    /**
     * 角色创建任务
     */
    public Long createTask(Long uid, String assigneeRole, Long insId, int pcsId, Boolean isBackHere, Boolean isDeliver) {
        Long taskId = new Snowflake(1, 1).nextId();
        ActTask actTask = new ActTask();
        actTask.setTaskId(taskId)
                .setInsId(insId)
                .setAssigneeUserRole(assigneeRole)
                .setPscId(pcsId)
                .setCreateUserId(uid)
                .setIsBackHere(isBackHere)
                .setIsDeliver(isDeliver);
        actTaskService.save(actTask);
        return taskId;
    }


    /**
     * 完成任务
     *
     * @param taskId      任务id
     * @param uid         完成者id
     * @param assigneeIds 下一任务受理者id
     */
    public Long completeTask(Long taskId, Long uid, Long[] assigneeIds, Boolean isDeliver) {
        ActTask task = actTaskService.getById(taskId);
        ActPcs pcs = actPcsService.getById(task.getPscId());
        //  判断流程是否结束
        if (pcs.getPcsNext() == 9527) {
            dataRemoval(task, uid);
            return null;
        }
        Long newTaskId = null;
        //  是否是工作转交
        if (isDeliver) {
            newTaskId = createTask(uid, assigneeIds, task.getInsId(), pcs.getPcsId(), false, isDeliver);
        } else {
            newTaskId = createTask(uid, assigneeIds, task.getInsId(), pcs.getPcsNext(), false, isDeliver);
        }
        dataRemoval(task, uid);
        return newTaskId;
    }

    /**
     * 指定角色完成任务
     */
    public Long completeTask(Long taskId, Long uid, String assigneeRole, Boolean isDeliver) {
        ActTask task = actTaskService.getById(taskId);
        ActPcs pcs = actPcsService.getById(task.getPscId());
        //  判断流程是否结束
        if (pcs.getPcsNext() == 9527) {
            dataRemoval(task, uid);
            return null;
        }
        Long newTaskId = null;
        //  是否是工作转交
        if (isDeliver) {
            newTaskId = createTask(uid, assigneeRole, task.getInsId(), pcs.getPcsId(), false, isDeliver);
        } else {
            newTaskId = createTask(uid, assigneeRole, task.getInsId(), pcs.getPcsNext(), false, isDeliver);
        }
        dataRemoval(task, uid);
        return newTaskId;
    }


    /**
     * 回退任务
     *
     * @param taskId      任务id
     * @param uid         完成者id
     * @param assigneeIds 下一任务受理者id
     * @param pcsId       流程id
     */
    public Long taskBack(Long taskId, Long uid, Long[] assigneeIds, int pcsId) {
        ActTask task = actTaskService.getById(taskId);
        Long newTaskId = createTask(uid, assigneeIds, task.getInsId(), pcsId, true, false);
        dataRemoval(task, uid);
        return newTaskId;
    }

    public Long taskBack(Long taskId, Long uid, String assigneeRole, int pcsId) {
        ActTask task = actTaskService.getById(taskId);
        Long newTaskId = createTask(uid, assigneeRole, task.getInsId(), pcsId, true, false);
        dataRemoval(task, uid);
        return newTaskId;
    }


    /**
     * 获取任务负责人ids
     */
    public Long[] getTaskAssigneeIds(Long taskId) {
        ActTask task = actTaskService.getById(taskId);
        return stringToLong(task.getAssigneeUserIds());
    }

    /**
     * 获取任务负责人role
     */
    public String getTaskAssigneeRole(Long taskId) {
        ActTask task = actTaskService.getById(taskId);
        return task.getAssigneeUserRole();
    }

    /**
     * 获取任务后退ids
     */
    public Integer[] getBackId(Long taskId) {
        ActTask task = actTaskService.getById(taskId);
        ActPcs pcs = actPcsService.getById(task.getPscId());
        String pcsBackId = pcs.getPcsBackId();
        return stringToInteger(pcsBackId);
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
        actHis.setAssigneeUserIds(actTask.getAssigneeUserIds())
                .setCreateTime(actTask.getCreateTime())
                .setCreateUserId(actTask.getCreateUserId())
                .setInsId(actTask.getInsId())
                .setPscId(actTask.getPscId())
                .setAssigneeUserRole(actTask.getAssigneeUserRole())
                .setTaskId(actTask.getTaskId())
                .setCompleteTime(actTask.getCompleteTime())
                .setCompleteUserId(actTask.getCompleteUserId())
                .setIsDeliver(actTask.getIsDeliver());
        return actHis;
    }

    /**
     * task数据迁移
     */
    public void dataRemoval(ActTask task, Long uid) {
        task.setCompleteUserId(uid);
        task.setCompleteTime(LocalDateTime.now());
        ActHis actHis = taskConverterToHis(task);
        actTaskService.removeById(task.getTaskId());
        actHisService.save(actHis);
    }

    /**
     * long[]-->String,以 , 分割
     */
    public static String longToString(Long[] ids) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ids.length; i++) {
            if (i == ids.length - 1) {
                builder.append(ids[i]);
            } else {
                builder.append(ids[i] + ",");
            }
        }
        return builder.toString();
    }

    /**
     * String-->long[]
     */
    public static Long[] stringToLong(String str) {
        String[] split = str.split(",");
        int length = split.length;
        Long[] longs = new Long[length];
        for (int i = 0; i < split.length; i++) {
            longs[i] = Long.valueOf(split[i]);
        }
        return longs;
    }

    /**
     * String-->long[]
     */
    public static Integer[] stringToInteger(String str) {
        String[] split = str.split(",");
        int length = split.length;
        Integer[] integers = new Integer[length];
        for (int i = 0; i < split.length; i++) {
            integers[i] = Integer.valueOf(split[i]);
        }
        return integers;
    }


}
