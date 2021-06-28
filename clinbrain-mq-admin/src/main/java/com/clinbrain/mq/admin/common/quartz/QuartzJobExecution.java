package com.clinbrain.mq.admin.common.quartz;


import com.clinbrain.mq.admin.common.quartz.utils.JobInvokeUtil;
import com.clinbrain.mq.admin.model.auto.SysQuartzJob;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（允许并发执行）
 * 
 * @author  jan  橙寂
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, SysQuartzJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }


}
