package com.ruoyi.zyrj.workcondition.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.*;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.zyrj.company.domain.ZyrjCompany;
import com.ruoyi.zyrj.company.mapper.ZyrjCompanyMapper;
import com.ruoyi.zyrj.deviceData.domain.DeviceData;
import com.ruoyi.zyrj.deviceData.domain.DeviceDataVO;
import com.ruoyi.zyrj.deviceData.mapper.DeviceDataMapper;
import com.ruoyi.zyrj.deviceEncode.domain.DeviceEncode;
import com.ruoyi.zyrj.deviceEncode.mapper.DeviceEncodeMapper;
import com.ruoyi.zyrj.exceptionSystem.domain.ZyrjExceptionRecord;
import com.ruoyi.zyrj.exceptionSystem.mapper.ZyrjExceptionRecordMapper;
import com.ruoyi.zyrj.governFacility.domain.*;
import com.ruoyi.zyrj.governFacility.mapper.ZyrjFacilityDeviceMapper;
import com.ruoyi.zyrj.hadoop.mapper.HadoopMapper;
import com.ruoyi.zyrj.lineFacility.domain.LineFacility;
import com.ruoyi.zyrj.lineFacility.mapper.LineFacilityMapper;
import com.ruoyi.zyrj.pollutionEquipment.domain.PollutionEquipment;
import com.ruoyi.zyrj.pollutionEquipment.domain.PollutionEquipmentSub;
import com.ruoyi.zyrj.pollutionEquipment.mapper.PollutionEquipmentMapper;
import com.ruoyi.zyrj.productionLine.domain.ProductionLine;
import com.ruoyi.zyrj.productionLine.mapper.ProductionLineMapper;
import com.ruoyi.zyrj.util.CaculateTime;
import com.ruoyi.zyrj.util.DeviceBaseEntity;
import com.ruoyi.zyrj.workcondition.domain.WorkConditionDetail;
import com.ruoyi.zyrj.wx.service.impl.WxMpTemplateMsgServiceImplTest;
import com.ruoyi.zyrj.zyrjDeviceStatus.domain.ZyrjDeviceStatus;
import com.ruoyi.zyrj.zyrjDeviceStatus.mapper.ZyrjDeviceStatusMapper;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.ruoyi.zyrj.workcondition.mapper.WorkconditionMapper;
import com.ruoyi.zyrj.workcondition.domain.Workcondition;
import com.ruoyi.zyrj.workcondition.service.IWorkconditionService;

/**
 * 工况数据Service业务层处理
 *
 * @author ruoyi
 * @date 2021-12-09
 */
@Service
public class WorkconditionServiceImpl implements IWorkconditionService {

    @Autowired
    private WorkconditionMapper workconditionMapper;

    @Autowired
    private ProductionLineMapper productionLineMapper;

    @Autowired
    private PollutionEquipmentMapper pollutionEquipmentMapper;

    @Autowired
    private LineFacilityMapper lineFacilityMapper;

    @Autowired
    private ZyrjFacilityDeviceMapper facilityDeviceMapper;

    @Autowired
    private DeviceDataMapper deviceDataMapper;

    @Autowired
    private ZyrjCompanyMapper companyMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ZyrjDeviceStatusMapper zyrjDeviceStatusMapper;

    @Autowired
    private DeviceEncodeMapper deviceEncodeMapper;

    @Autowired
    private ZyrjExceptionRecordMapper exceptionRecordMapper;

    @Autowired
    private HadoopMapper hadoopMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private WxMpTemplateMsgServiceImplTest wxMpTemplateMsgServiceImplTest;


    @Override
    public List<Workcondition> selectCompanyLine(Workcondition workcondition) {
        return workconditionMapper.selectCompanyLine(workcondition);
    }

    /**
     * 查询工况数据
     *
     * @param id 工况数据主键
     * @return 工况数据
     */
    @Override
    public Workcondition selectWorkconditionById(Integer id) {
        return workconditionMapper.selectWorkconditionById(id);
    }

    /**
     * 查询工况数据列表
     *
     * @param workcondition 工况数据
     * @return 工况数据
     */
    @Override
    public List<Workcondition> selectWorkconditionList(Workcondition workcondition) {
        //如果没有公司id默认查询全部的工况数据，如果有公司id就作为键值
        /*String key = "workconditions";
        if (workcondition.getCompanyId() != null && workcondition.getCompanyId() != "") {
            key = workcondition.getCompanyId();
        }*/
        //根据key值获取redis中的对应数据,获取数据不为空则返回查询到的数据，否则就在数据库中查询并存入redis并返回结果，
        /*List<Workcondition> redisWorkconditions = redisTemplate.boundListOps(key).range(0, -1);
        if (!CollectionUtils.isEmpty(redisWorkconditions)) {
            return redisWorkconditions;
        } else {*/
            List<Workcondition> workconditions = workconditionMapper.selectWorkconditionList(workcondition);
            /*if (workconditions != null && workconditions.size() > 0) {
                redisTemplate.delete(key);
                for (Workcondition workcondition1:workconditions){
                    redisTemplate.opsForList().rightPush(key, workcondition1);
                }
                //设置redis指定键值数据的过期时间
                redisTemplate.expire(key,5,TimeUnit.MINUTES);
            }*/
            return workconditions;
        //}
    }
    @Override
    public List<Workcondition> selectWorkconditionListForRealTime(Workcondition workcondition) {
        List<Workcondition> workconditions = workconditionMapper.selectWorkconditionListForRealTime(workcondition);
        return workconditions;
    }

    /**
     * 新增工况数据
     *
     * @param workcondition 工况数据
     * @return 结果
     */
    @Override
    public int insertWorkcondition(Workcondition workcondition) {
        return workconditionMapper.insertWorkcondition(workcondition);
    }

    public void insertWorkconditionList(List<Workcondition> workconditionList,Date sysTime) {
        for (Workcondition workcondition : workconditionList) {
            workcondition.setAddtime(sysTime);
            workconditionMapper.insertWorkcondition(workcondition);
        }

    }

    /**
     * 修改工况数据
     *
     * @param workcondition 工况数据
     * @return 结果
     */
    @Override
    public int updateWorkcondition(Workcondition workcondition) {
        return workconditionMapper.updateWorkcondition(workcondition);
    }

    /**
     * 批量删除工况数据
     *
     * @param ids 需要删除的工况数据主键
     * @return 结果
     */
    @Override
    public int deleteWorkconditionByIds(Integer[] ids) {
        return workconditionMapper.deleteWorkconditionByIds(ids);
    }

    /**
     * 删除工况数据信息
     *
     * @param id 工况数据主键
     * @return 结果
     */
    @Override
    public int deleteWorkconditionById(Integer id) {
        return workconditionMapper.deleteWorkconditionById(id);
    }


    /**
     * 获取工况数据详情*/
    @Override
    public JSONObject getWorkConditionDetail(String lineId, String type) {
        JSONObject jsonObject = new JSONObject();
        List<HashMap> workConditionDetailList = new ArrayList<>();
        PollutionEquipment pollutionEquipment = new PollutionEquipment();
        pollutionEquipment.setLineId(lineId);
        if (type.equals("0")) {//type为生产线
            //根据生产线id查找线下设备
            List<PollutionEquipment> pollutionEquipmentList = pollutionEquipmentMapper.selectPollutionEquipmentList(pollutionEquipment);
            //遍历生产线下设备，获取每个设备的状态
            LinkedHashSet max = new LinkedHashSet();
            for (PollutionEquipment tempPoll : pollutionEquipmentList) {
                LinkedHashMap map = getWorkConditionStatusTest(tempPoll);
                max.addAll(map.keySet());
                workConditionDetailList.add(map);
            }
            max.remove("运行状态");
            max.add("运行状态");
            jsonObject.put("list",max);
            jsonObject.put("listData",workConditionDetailList);
        } else {
            //根据生产线id查找治污线设备
            List<WorkConditionDetail> workConditionDetails = new ArrayList<>();
            workConditionDetails = workconditionMapper.selectWorkConditionDetail(lineId);
            LinkedHashSet max = new LinkedHashSet();
            for (WorkConditionDetail tempWkd : workConditionDetails) {
                LinkedHashMap map = getLineDeviceStatusTest(tempWkd);
                max.addAll(map.keySet());
                workConditionDetailList.add(map);
            }
            max.remove("运行状态");
            max.add("运行状态");
            jsonObject.put("list",max);
            jsonObject.put("listData",workConditionDetailList);
        }

        return jsonObject;
    }

    /**
     * 采集生产线设备信息*/
    //判断设备状态（1.开。2.关（阈值与功率判断）。3.未联网（从未收到报文）。4.掉线（曾经收到过报文，且30分钟内没收到报文;5.功率异常
    public LinkedHashMap getWorkConditionStatusTest(PollutionEquipment pollutionEquipment) {
        DeviceData deviceData ;
        DeviceEncode deviceEncode = deviceEncodeMapper.selectDeviceEncodeByUid(pollutionEquipment.getUid());

        //WorkConditionDetail workConditionDetail = new WorkConditionDetail();
        LinkedHashMap workConditionDetail = new LinkedHashMap();
        workConditionDetail.put("设备名称",pollutionEquipment.getEquipmentName());
        workConditionDetail.put("监测时间",new Date());
        workConditionDetail.put("阈值",pollutionEquipment.getThresholdValue());
        workConditionDetail.put("峰值",pollutionEquipment.getMaxValue());
        //workConditionDetail = transitionEntitySe(pollutionEquipment);
        if(deviceEncode == null){//没有任何记录代表未联网
            workConditionDetail.put("运行状态","3");
            return workConditionDetail;
        }
        try {
            deviceData = deviceDataMapper.selectDeviceDataByUid(pollutionEquipment.getUid());
            if (StringUtils.isNotNull(deviceData)) {
                DeviceBaseEntity pollutionDevice = pollutionEquipmentMapper.selectPollutionEquipmentById(pollutionEquipment.getId());
                HashMap<String,HashMap> resultMap = ProductionEncodeValueDecide(pollutionDevice, deviceData);
                workConditionDetail.put("设备功率（Kw）",deviceData.getPwRtdLow());
                if (deviceData.getPwRtdLow().compareTo(pollutionEquipment.getThresholdValue()) > 0) {//阈值 超过 功率为关,功率超过阈值为开

                    if (deviceData.getPwRtdLow().compareTo(pollutionEquipment.getThresholdValue()) < 0 || deviceData.getPwRtdLow().compareTo(pollutionEquipment.getMaxValue()) >= 1) {
                        workConditionDetail.put("运行状态","5");

                        return workConditionDetail;
                    } else if (resultMap.size()>0) {
                        HashMap tempMap = resultMap.get("deviceMap");
                        HashMap keyValueMap = resultMap.get("keyValueMap");
                        workConditionDetail.putAll(keyValueMap);
                        if(tempMap != null) {
                            workConditionDetail.put("运行状态","5");
                            return workConditionDetail;
                        }
                        workConditionDetail.put("运行状态","1");
                        return workConditionDetail;
                    }
                    workConditionDetail.put("运行状态","1");
                    return workConditionDetail;
                } else {
                    workConditionDetail.put("运行状态","2");
                    if(resultMap.size()>0){
                        HashMap keyValueMap = resultMap.get("keyValueMap");
                        workConditionDetail.putAll(keyValueMap);
                    }
                    return workConditionDetail;
                }
            } else {
                workConditionDetail.put("运行状态","4");
                return workConditionDetail;
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }

        return workConditionDetail;
    }



    /**
     *采集治污线设备的信息
     * 判断设备状态（1.开。2.关（阈值与功率判断）。3.未联网（从未收到报文）。4.掉线（曾经收到过报文，且30分钟内没收到报文;5.数值异常
     * */
    public LinkedHashMap getLineDeviceStatusTest(WorkConditionDetail facilityDevice){
        DeviceData deviceData ;
        DeviceEncode deviceEncode = deviceEncodeMapper.selectDeviceEncodeByUid(facilityDevice.getUid());

        LinkedHashMap workConditionDetail = new LinkedHashMap();
        workConditionDetail.put("设备名称",facilityDevice.getDeviceName());
        workConditionDetail.put("监测时间",new Date());
        workConditionDetail.put("阈值",facilityDevice.getThresholdValue());
        workConditionDetail.put("峰值",facilityDevice.getMaxValue());
        if(StringUtils.isNotNull(facilityDevice.getUseTime())){
            if(facilityDevice.getUseTime()>0) {
                workConditionDetail.put("活性炭吸附", "剩余" + facilityDevice.getUseTime() + "小时");
                workConditionDetail.put("运行状态", "非联网设备");//非联网设备
                return workConditionDetail;
            }
        }
        if(deviceEncode == null){//没有任何记录代表未联网
            workConditionDetail.put("运行状态","未联网");
            return workConditionDetail;
        }
        try {
            deviceData = deviceDataMapper.selectDeviceDataByUid(facilityDevice.getUid());
            if (StringUtils.isNotNull(deviceData)) {
                DeviceBaseEntity tempFacilityDevice = facilityDeviceMapper.selectZyrjFacilityDeviceById(facilityDevice.getId());
                HashMap<String,HashMap> resultMap = encodeValueDecide(tempFacilityDevice, deviceData);
                workConditionDetail.put("设备功率（Kw）",deviceData.getPwRtdLow());
                if (deviceData.getPwRtdLow().compareTo(facilityDevice.getThresholdValue()) > 0) {//阈值 超过 功率为关,功率超过阈值为开

                    if (deviceData.getPwRtdLow().compareTo(facilityDevice.getThresholdValue()) < 0 || deviceData.getPwRtdLow().compareTo(facilityDevice.getMaxValue()) >= 1) {
                        workConditionDetail.put("运行状态","功率异常");
                        return workConditionDetail;
                    }else if (resultMap.size()>0) {
                        HashMap tempMap = resultMap.get("deviceMap");
                        HashMap keyValueMap = resultMap.get("keyValueMap");
                        workConditionDetail.putAll(keyValueMap);
                        if(tempMap != null) {
                            workConditionDetail.put("运行状态",tempMap.get("msg"));
                            return workConditionDetail;
                        }
                        workConditionDetail.put("运行状态","开");
                        return workConditionDetail;
                    }
                    workConditionDetail.put("运行状态","开");
                    return workConditionDetail;
                }else{
                    workConditionDetail.put("运行状态","关");
                    if(resultMap.size()>0){
                        HashMap keyValueMap = resultMap.get("keyValueMap");
                        workConditionDetail.putAll(keyValueMap);
                    }
                    return workConditionDetail;
                }
            } else {
                workConditionDetail.put("运行状态","已掉线");
                return workConditionDetail;
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return workConditionDetail;
    }

    /**获取企业工况数据列表(定时任务)*/
    public void getCompanyWorkConditionList() throws IllegalAccessException, ParseException {
        //ExecutorService pool = Executors.newCachedThreadPool();
        List<ZyrjCompany> zyrjCompanyList = companyMapper.selectCompanyLists();
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -5);// 5分钟之前的时间
        Date beforeD = beforeTime.getTime();
        String before5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beforeD);//五分钟前的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        Date date = new Date();
        String date1 = sdf.format(date);
        Date sysTime = sdf.parse(date1);
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        Semaphore semaphore = new Semaphore(8);//定义几个许可
        ExecutorService executorService = Executors.newFixedThreadPool(8);//创建一个固定的线程池
        //long startTime=System.currentTimeMillis();
        /*scheduledThreadPoolExecutor.schedule(new Runnable() {

            @SneakyThrows
            @Override
            public void run() {*/
                   //获取开始时间
                //需要延迟执行
                for (ZyrjCompany tempCompany : zyrjCompanyList) {
                    //semaphore.acquire();
                    executorService.execute(() -> {
                            ProductionLine productionLine = new ProductionLine();
                            productionLine.setCompanyId(tempCompany.getCompanyId());
                            try {
                                selectWorkConditionListPlus(productionLine, tempCompany, sysTime,before5);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        semaphore.release();
                    });
                }
                /*executorService.shutdown();
            }
        }, 30, TimeUnit.SECONDS);*/
    }

    public void selectWorkConditionListPlus(ProductionLine productionLine,ZyrjCompany zyrjCompany,Date sysTime,String before5) throws IllegalAccessException{
        //查找企业下的生产线
        List<ProductionLine> productionLineList = productionLineMapper.selectProductionLineList(productionLine);
        List<Workcondition> workConditionList = new ArrayList<>();
        //遍历生产线列表，查找关联治理设施
        for (ProductionLine tempProductionLine : productionLineList) {
            Workcondition workcondition = new Workcondition();//工况数据
            workcondition.setCompanyId(tempProductionLine.getCompanyId());
            workcondition.setLineId(tempProductionLine.getLineId());
            workcondition.setLineName(tempProductionLine.getLineName());
            int facilityStatusCode = 1;//治理设施状态码，默认关
            int pollutionStatusCode = 1;//生产设施状态码，默认关
            LineFacility lineFacility = new LineFacility();
            lineFacility.setLineId(tempProductionLine.getLineId());
            FacilityDeviceBase facilityDeviceBase = new FacilityDeviceBase();//异常用--必要参数
            List<ZyrjFacilityDevice> facilityDeviceList = new ArrayList<>();//设备列表--记录异常用
            StringBuilder facilityIdSB = new StringBuilder();//记录治污设施id
            StringBuilder facilityNameSB = new StringBuilder();//记录治污设施名称
            //查找治理设施列表
            List<LineFacility> lineFacilities = lineFacilityMapper.selectLineFacilityByLineId(lineFacility);
            //遍历当前治理设施列表
            for (LineFacility tempLineFacility : lineFacilities) {
                String facilityId = tempLineFacility.getFacilityId();
                String facilityName = tempLineFacility.getFacilityName();
                facilityIdSB.append(facilityId+";");
                facilityNameSB.append(facilityName+";");
                //查找当前的治理设施下属于监测范围的设备列表
                List<ZyrjFacilityDevice> facilityDevice = facilityDeviceMapper.selectZyrjFacilityDeviceByFacilityIdForMonitor(facilityId);
                //用于记录设备状态
                List<Integer> facilityDeviceArr = new LinkedList<>();
                //遍历治理设施下的设备列表
                for (ZyrjFacilityDevice tempFacilityDevice : facilityDevice) {
                    //facilityDeviceBase.setFacilityName(tempLineFacility.getFacilityName());//设施名称
                    //唯一id存在说明报文有数据，根据uid查找设备报文数据
                    if(tempFacilityDevice.getSynchronization().equals("是")) {
                        if (tempFacilityDevice.getUid() != null) {
                            DeviceData deviceData = deviceDataMapper.selectMasterDeviceDataByUidForMonitor(tempFacilityDevice.getUid(),before5);
                            if (StringUtils.isNotNull(deviceData)) {
                                //功率 超过 阈值 状态为 开 | 功率低于阈值就判断 关
                                if (deviceData.getPwRtdLow().compareTo(tempFacilityDevice.getThresholdValue()) > 0 ) {
                                    //状态码 0开启 1关闭
                                    facilityDeviceArr.add(0);
                                    HashMap<String,HashMap> resultMap =  encodeValueDecide(tempFacilityDevice,deviceData);
                                    if(resultMap.get("deviceMap") != null){
                                        HashMap tempMap = resultMap.get("deviceMap");
                                        if(tempMap != null) {
                                            tempFacilityDevice.setMark("因子类型值异常");
                                            facilityDeviceArr.add(1);
                                            tempFacilityDevice.setEText(tempMap.get("msg").toString());
                                            facilityDeviceList.add(tempFacilityDevice);
                                            facilityDeviceBase.setZyrjFacilityDevice(facilityDeviceList);
                                        }
                                    }
                                }else{//那么为关
                                    facilityDeviceArr.add(1);
                                    tempFacilityDevice.setPower(deviceData.getPwRtdLow());
                                    facilityDeviceList.add(tempFacilityDevice);
                                    facilityDeviceBase.setZyrjFacilityDevice(facilityDeviceList);
                                }
                                //pw小于阈值 大于峰值
                                if (deviceData.getPwRtdLow().compareTo(tempFacilityDevice.getThresholdValue()) < 0 || deviceData.getPwRtdLow().compareTo(tempFacilityDevice.getMaxValue()) >= 1) {
                                    facilityDeviceArr.add(1);
                                    tempFacilityDevice.setMark("功率异常");
                                    facilityDeviceList.add(tempFacilityDevice);
                                    facilityDeviceBase.setZyrjFacilityDevice(facilityDeviceList);
                                    HashMap<String,HashMap> resultMap =  encodeValueDecide(tempFacilityDevice,deviceData);
                                    if(resultMap.get("deviceMap") != null){
                                        HashMap tempMap = resultMap.get("deviceMap");
                                        if(tempMap != null) {
                                            tempFacilityDevice.setMark("因子类型值异常");
                                            facilityDeviceArr.add(1);
                                            tempFacilityDevice.setEText(tempMap.get("msg").toString());
                                            facilityDeviceList.add(tempFacilityDevice);
                                            facilityDeviceBase.setZyrjFacilityDevice(facilityDeviceList);
                                        }
                                    }
                                }

                            }else{
                                facilityDeviceArr.add(1);
                                tempFacilityDevice.setMark("掉线");
                                facilityDeviceList.add(tempFacilityDevice);
                                facilityDeviceBase.setZyrjFacilityDevice(facilityDeviceList);
                            }
                        }else{
                            facilityDeviceArr.add(1);
                            tempFacilityDevice.setMark("未联网");
                            facilityDeviceList.add(tempFacilityDevice);
                            facilityDeviceBase.setZyrjFacilityDevice(facilityDeviceList);
                        }
                        deviceStatus(tempFacilityDevice,zyrjCompany.getCompanyId(),"1",before5);
                    }else{
                        //facilityDeviceArr.add(0);
                        //facilityDeviceList.add(tempFacilityDevice);
                        facilityDeviceBase.setZyrjFacilityDevice(null);
                    }
                    //设备状态存储方法
                    //if(!StringUtils.isNotEmptyDiy(tempFacilityDevice.getOutage(),"0"))//如果设备不是停运就参与采集率，停运不参与采集率

                }
                //调用记录异常

                facilityDeviceBase.setFacilityId(facilityIdSB.toString());
                facilityDeviceBase.setFacilityName(facilityNameSB.toString());
                facilityStatusCode = Status(facilityDeviceArr,1);
                workcondition.setFacilityStatus(facilityStatusCode);
            }
            //查找生产线下的设备列表
            /*PollutionEquipment pollutionEquipment = new PollutionEquipment();
            pollutionEquipment.setLineId(tempProductionLine.getLineId());*/
            List<PollutionEquipment> pollutionEquipmentList = pollutionEquipmentMapper.selectPollutionEquipmentForMonitor(tempProductionLine.getLineId());
            if(!StringUtils.isNull(pollutionEquipmentList)){
                //遍历生产线下生产设备

                //用于记录设备状态
                List<Integer> pollutionEquipmentArr = new LinkedList<>();
                for(PollutionEquipment tempPollutionEquipment : pollutionEquipmentList){
                    //唯一id存在说明报文有数据，根据uid查找设备报文数据
                    if (tempPollutionEquipment.getUid() != null) {
                        DeviceData deviceData = deviceDataMapper.selectMasterDeviceDataByUidForMonitor(tempPollutionEquipment.getUid(),before5);
                        if (StringUtils.isNotNull(deviceData)) {

                            //功率 超过 阈值 状态为 开 | 功率低于阈值就判断 关
                            if (deviceData.getPwRtdLow().compareTo(tempPollutionEquipment.getThresholdValue()) > 0) {
                                //状态码 0开启 1关闭
                                pollutionEquipmentArr.add(0);
                            }else{
                                pollutionEquipmentArr.add(1);
                            }
                            if(deviceData.getPwRtdLow().compareTo(tempPollutionEquipment.getThresholdValue()) < 0 || deviceData.getPwRtdLow().compareTo(tempPollutionEquipment.getMaxValue()) >= 1){
                                pollutionEquipmentArr.add(1);
                            }
                        }else{
                            pollutionEquipmentArr.add(1);
                        }
                    }else{
                        pollutionEquipmentArr.add(1);
                    }

                    //设备状态存储方法
                    //if(!StringUtils.isNotEmptyDiy(tempPollutionEquipment.getOutage(),"0"))//如果设备不是停运就参与采集率
                        deviceStatus(tempPollutionEquipment,zyrjCompany.getCompanyId(),"0",before5);
                }
                pollutionStatusCode = Status(pollutionEquipmentArr,0);
            }

            /**
             * 如果产污是关闭，那就是正常
             * 如果产污不是关闭，判断治污是否关闭，治污为关就是异常，否则就是正常
             */
            if(pollutionStatusCode == 1){//生产设施关闭
                zyrjCompany.setProductionStatus("0");//生产状态正常
                workcondition.setRunStatus(0);
                recoverNormal(tempProductionLine, facilityDeviceBase,zyrjCompany,sysTime);
            }else{
                if(facilityStatusCode == 1) {
                    zyrjCompany.setProductionStatus("1");//生产状态异常常
                    workcondition.setRunStatus(1);
                    recordExceptionTest(tempProductionLine, facilityDeviceBase,zyrjCompany,sysTime);
                }else {
                    workcondition.setRunStatus(0);
                    zyrjCompany.setProductionStatus("0");
                    recoverNormal(tempProductionLine, facilityDeviceBase,zyrjCompany,sysTime);//正常后在异常记录里恢复异常
                }
            }
            companyMapper.updateZyrjCompany(zyrjCompany);//更新企业生产状态
            workConditionList.add(workcondition);
        }
        insertWorkconditionList(workConditionList,sysTime);
    }


    //治理设施下因子类型下阈值峰值判断
    public HashMap encodeValueDecide(DeviceBaseEntity deviceBaseEntity, DeviceData deviceData) throws IllegalAccessException {
        DeviceDataVO deviceDataVO = new DeviceDataVO(deviceData);
        ZyrjFacilityDevice facilityDevices = facilityDeviceMapper.selectZyrjFacilityDeviceById(deviceBaseEntity.getId());
        List<ZyrjFacilityDeviceSub> subList = facilityDevices.getZyrjFacilityDeviceSubList();
        HashMap<String,Object> resultMap = new HashMap<>();
        HashMap deviceMap = new HashMap();
        HashMap keyValueMap = new HashMap();
        if(StringUtils.isNotNull(subList)) {
            String msg = "";
            for (ZyrjFacilityDeviceSub sub : subList) {
                String key = sub.getEncodeType();
                Field[] fields = DeviceDataVO.class.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    field.setAccessible(true);
                    if (field.getName().contains(key)) {
                        Object fieldObject = field.get(deviceDataVO);
                        if (fieldObject instanceof BigDecimal) {
                            BigDecimal values = (BigDecimal) fieldObject;//设备值
                            SysDictData sysDictData = workconditionMapper.selectEncodeKey(key);
                            keyValueMap.put(sysDictData.getDictLabel(),values);
                            double value = values.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            double ThresholdValue = sub.getThresholdValue().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//阈值
                            double maxValue = sub.getMaxValue().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//峰值
                            if(value < 0){
                                if (value > ThresholdValue || value < maxValue) {
                                    msg = sysDictData.getDictLabel() + " 异常";
                                    deviceMap.put("code","101");
                                    deviceMap.put("msg",msg);
                                    resultMap.put("deviceMap",deviceMap);
                                    resultMap.put("keyValueMap",keyValueMap);
                                    return resultMap;
                                }
                            }else {
                                if (value < ThresholdValue || value > maxValue) {
                                    msg = sysDictData.getDictLabel() + " 异常";
                                    deviceMap.put("code", "101");
                                    deviceMap.put("msg", msg);
                                    resultMap.put("deviceMap",deviceMap);
                                    resultMap.put("keyValueMap",keyValueMap);
                                    return resultMap;
                                }
                            }
                        }
                    }
                }
            }
        }
        resultMap.put("keyValueMap",keyValueMap);
        return resultMap;
    }

    /**
     * 产污设备的因子数值判断
     * @param deviceBaseEntity
     * @param deviceData
     * @return
     * @throws IllegalAccessException
     */
    public HashMap ProductionEncodeValueDecide(DeviceBaseEntity deviceBaseEntity, DeviceData deviceData) throws IllegalAccessException {
        DeviceDataVO deviceDataVO = new DeviceDataVO(deviceData);
        PollutionEquipment pollutionEquipment = pollutionEquipmentMapper.selectPollutionEquipmentById(deviceBaseEntity.getId());
        List<PollutionEquipmentSub> subList = pollutionEquipment.getPollutionEquipmentSubList();
        HashMap<String,Object> resultMap = new HashMap<>();
        HashMap deviceMap = new HashMap();
        HashMap keyValueMap = new HashMap();
        if(StringUtils.isNotNull(subList)) {
            String msg = "";
            for (PollutionEquipmentSub sub : subList) {
                String key = sub.getEncodeType();
                Field[] fields = DeviceDataVO.class.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    field.setAccessible(true);
                    if (field.getName().contains(key)) {
                        Object fieldObject = field.get(deviceDataVO);

                        if (fieldObject instanceof BigDecimal) {
                            BigDecimal values = (BigDecimal) fieldObject;//设备值
                            SysDictData sysDictData = workconditionMapper.selectEncodeKey(key);
                            keyValueMap.put(sysDictData.getDictLabel(),values);
                            double value = values.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            double ThresholdValue = sub.getThresholdValue().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//阈值
                            double maxValue = sub.getMaxValue().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//峰值
                            if(value < 0){
                                if (value > ThresholdValue || value < maxValue) {
                                    msg = sysDictData.getDictLabel() + " 异常";
                                    deviceMap.put("code","101");
                                    deviceMap.put("msg",msg);
                                    resultMap.put("deviceMap",deviceMap);
                                    resultMap.put("keyValueMap",keyValueMap);
                                    return resultMap;
                                }
                            }else {
                                if (value < ThresholdValue || value > maxValue) {
                                    msg = sysDictData.getDictLabel() + " 异常";
                                    deviceMap.put("code","101");
                                    deviceMap.put("msg",msg);
                                    resultMap.put("deviceMap",deviceMap);
                                    resultMap.put("keyValueMap",keyValueMap);
                                    return resultMap;
                                }
                            }
                        }
                    }
                }
            }
        }
        resultMap.put("keyValueMap",keyValueMap);
        return resultMap;
    }


    /**
     * 记录异常
     */
    public void recordExceptionTest(ProductionLine productionLine,FacilityDeviceBase facilityDeviceBase,ZyrjCompany zyrjCompany,Date sysTime){
        ZyrjExceptionRecord zyrjExceptionRecord = new ZyrjExceptionRecord();
        zyrjExceptionRecord.setCompanyId(productionLine.getCompanyId());
        zyrjExceptionRecord.setUid(productionLine.getLineId());
        zyrjExceptionRecord.setExceptionStatus("0");
        zyrjExceptionRecord.setEventType("设备异常");
        ZyrjExceptionRecord checkExceptionRecord = exceptionRecordMapper.selectExceptionStatus(zyrjExceptionRecord);
        try {
            if (StringUtils.isNull(checkExceptionRecord)) {//isNull则进行插入
                BeanUtils.copyProperties(zyrjCompany, zyrjExceptionRecord);
                String lineName = productionLine.getLineName();
                String text = "";
                String deviceName = "";

                if (StringUtils.isNotNull(facilityDeviceBase.getZyrjFacilityDevice())) {
                    if (facilityDeviceBase.getZyrjFacilityDevice().size() > 0) {
                        for (ZyrjFacilityDevice facilityDevice : facilityDeviceBase.getZyrjFacilityDevice()) {
                            if (!StringUtils.isNotEmptyDiy(facilityDevice.getOutage(), "1")) {//如果设备不是维保就参与报警
                                if (Objects.equals(facilityDevice.getMark(), "功率异常")) {
                                    deviceName += facilityDevice.getDeviceName() + ";";
                                    text += "设备名称为：" + facilityDevice.getDeviceName() + ":功率异常;";
                                } else if (Objects.equals(facilityDevice.getMark(), "掉线")) {
                                    deviceName += facilityDevice.getDeviceName() + ";";
                                    text += "设备名称为：" + facilityDevice.getDeviceName() + ":已掉线;";
                                } else if (Objects.equals(facilityDevice.getMark(), ":未联网")) {
                                    deviceName += facilityDevice.getDeviceName() + ";";
                                    text += "设备名称为：" + facilityDevice.getDeviceName() + "未联网;";
                                } else if (Objects.equals(facilityDevice.getMark(), "因子类型值异常")) {
                                    deviceName += facilityDevice.getDeviceName() + ";";
                                    text += "设备名称为：" + facilityDevice.getDeviceName() + ":" + facilityDevice.getEText() + ";";
                                } else {
                                    deviceName += facilityDevice.getDeviceName() + ";";
                                    text += "设备名称为：" + facilityDevice.getDeviceName() + ";实时功率：" + facilityDevice.getPower() + ";额定功率：" + facilityDevice.getThresholdValue();
                                }
                            }
                        }
                        if (deviceName.length() > 0) {
                            String context = lineName + "正常生产。关联的治污设施：" + facilityDeviceBase.getFacilityName() + ";" + text + "状态异常。";
                            zyrjExceptionRecord.setStartTime(sysTime);
                            zyrjExceptionRecord.setContent(context);
                            zyrjExceptionRecord.setExceptionStatus("0");
                            zyrjExceptionRecord.setDisposeStatus("1");//0未处理
                            zyrjExceptionRecord.setEventType("设备异常");
                            zyrjExceptionRecord.setDeviceName(deviceName);
                            zyrjExceptionRecord.setMonitoringPoint(facilityDeviceBase.getFacilityName());
                            zyrjExceptionRecord.setFacilityId(facilityDeviceBase.getFacilityId());
                            exceptionRecordMapper.insertZyrjExceptionRecord(zyrjExceptionRecord);
                            wxMpTemplateMsgServiceImplTest.sendMessageForCompany(zyrjExceptionRecord, sysTime);
                            return;
                        }
                    }
                }

            } else if (CaculateHour(checkExceptionRecord.getStartTime())) {//判断异常报警是否已超时
                Date date = checkExceptionRecord.getStartTime();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime startTime = LocalDateTime.ofInstant(date.toInstant(), zoneId);
                LocalDateTime endTime = LocalDateTime.ofInstant(sysTime.toInstant(), zoneId);
                checkExceptionRecord.setExceptionStatus("1");
                checkExceptionRecord.setEndTime(sysTime);
                HashMap dateMap = CaculateTime.calculateIntervalTime(startTime, endTime);
                String LastingTime = dateMap.get("resultStr").toString();
                checkExceptionRecord.setLastingTime(LastingTime);
                exceptionRecordMapper.updateZyrjExceptionRecord(checkExceptionRecord);
                if(checkExceptionRecord.getParkId().equals("20221018")) {
                    wxMpTemplateMsgServiceImplTest.sendMessage(checkExceptionRecord, sysTime);
                }
                return;
            } else {//不是空说明已存在数据，再进行校验设备是否正常
                if (facilityDeviceBase.getZyrjFacilityDevice().size() == 0) {
                    Date date = checkExceptionRecord.getStartTime();
                    ZoneId zoneId = ZoneId.systemDefault();
                    LocalDateTime startTime = LocalDateTime.ofInstant(date.toInstant(), zoneId);
                    LocalDateTime endTime = LocalDateTime.ofInstant(sysTime.toInstant(), zoneId);
                    checkExceptionRecord.setExceptionStatus("1");
                    checkExceptionRecord.setEndTime(sysTime);
                    HashMap dateMap = CaculateTime.calculateIntervalTime(startTime, endTime);
                    String LastingTime = dateMap.get("resultStr").toString();
                    checkExceptionRecord.setLastingTime(LastingTime);
                    exceptionRecordMapper.updateZyrjExceptionRecord(checkExceptionRecord);

                    return;
                }
            }
        }catch (RuntimeException e){
            e.printStackTrace();
            return;
        } catch (IOException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
            return;
        }

    }

    //生产状态正常后结束异常
    public void recoverNormal(ProductionLine productionLine,FacilityDeviceBase facilityDeviceBase,ZyrjCompany zyrjCompany,Date sysTime){
        ZyrjExceptionRecord zyrjExceptionRecord = new ZyrjExceptionRecord();
        zyrjExceptionRecord.setCompanyId(zyrjCompany.getCompanyId());
        zyrjExceptionRecord.setExceptionStatus("0");
        zyrjExceptionRecord.setEventType("设备异常");
        zyrjExceptionRecord.setUid(productionLine.getLineId());
        zyrjExceptionRecord.setFacilityId(facilityDeviceBase.getFacilityId());
        List<ZyrjExceptionRecord> checkExceptionRecord = exceptionRecordMapper.selectExceptionByCompanyId(zyrjExceptionRecord);
        if(StringUtils.isNotEmpty(checkExceptionRecord)) {
            for (ZyrjExceptionRecord exceptionRecord : checkExceptionRecord) {
                Date date = exceptionRecord.getStartTime();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime startTime = LocalDateTime.ofInstant(date.toInstant(), zoneId);
                LocalDateTime endTime = LocalDateTime.ofInstant(sysTime.toInstant(), zoneId);
                exceptionRecord.setExceptionStatus("1");
                exceptionRecord.setEndTime(sysTime);
                HashMap dateMap = CaculateTime.calculateIntervalTime(startTime, endTime);
                String LastingTime = dateMap.get("resultStr").toString();
                exceptionRecord.setLastingTime(LastingTime);
                exceptionRecord.setContent(exceptionRecord.getContent()+"    生产线正常状态,恢复正常");
                //如果时限较短则默认处理,目前设定为30分钟内
                if((Long)dateMap.get("minutes")<30L){
                    exceptionRecord.setDisposeStatus("4");
                    exceptionRecord.setDeclareStatus("4");
                }
                exceptionRecordMapper.updateZyrjExceptionRecord(exceptionRecord);
                try {
                    wxMpTemplateMsgServiceImplTest.sendMessageForCompany(exceptionRecord, sysTime);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //根据设定的报警时长来判定异常状态结束
    private boolean CaculateHour(Date StartTime){
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nd = 1000*24*60*60;//一天的毫秒数
        long nh = 1000*60*60;//一小时的毫秒数
        long nm = 1000*60;
        Date now = new Date();
        long diff = now.getTime() - StartTime.getTime();
        long hour = diff%nd/nh;//计算差多少小时
        long minute = diff/1000/60;

        Long target =Long.valueOf(configService.selectConfigByKey("zyrj.exception.timeLimit"));
        if(minute>=target){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据当前设备最近一条报文(五分钟内)判断设备状态
     * 待用方法（若有单独判断设备异常的逻辑，重构并使用该方法）
     */
    private int DeviceStatus(DeviceData deviceData) {

        return 0;
    }

    //设备状态存储方法
    private void deviceStatus(Object equipment, String companyId, String type,String before5) {

        DeviceBaseEntity equipment1;
        if (Objects.equals(type, "0")){
            equipment1 = (PollutionEquipment)equipment;
        }else {
            equipment1 = (ZyrjFacilityDevice)equipment;
        }
        if(StringUtils.isNotEmptyDiy(equipment1.getOutage(),"0")){
            recordStatus(equipment1,type,"3");//停运
            return;
        }
        //设备状态
        DeviceEncode deviceEncode = deviceEncodeMapper.selectDeviceEncodeByUid(equipment1.getUid());
        equipment1.setCompanyId(companyId);
        if (deviceEncode == null){//没有任何记录代表未联网
            //记录数据
            recordStatus(equipment1,type,"1");
        }else {
            DeviceData deviceData = deviceDataMapper.selectMasterDeviceDataByUidForMonitor(equipment1.getUid(),before5);
            if (deviceData == null){//掉线
                //记录数据
                recordStatus(equipment1,type,"2");
            }else {//正常状态(目前没有判断设备异常的完整逻辑，暂定接收到报文均为正常状态)
                //后续更改在这里添加异常判断逻辑
                //记录数据
                recordStatus(equipment1,type,"0");
            }
        }
    }

    /*记录设备当前状态*/
    public void recordStatus(Object equipment,String equipmentType,String Status){
        ZyrjDeviceStatus zyrjDeviceStatus = new ZyrjDeviceStatus();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(now);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        String nowTime = format.format(now);
        zyrjDeviceStatus.setRecordDate(nowTime);
        zyrjDeviceStatus.setStatus(Status);
        zyrjDeviceStatus.setEquipmentType(equipmentType);
        String uid;
        if (Objects.equals(equipmentType, "0")){
            PollutionEquipment pollutionEquipment = (PollutionEquipment)equipment;
            zyrjDeviceStatus.setEquipmentId(pollutionEquipment.getEquipmentId());
            zyrjDeviceStatus.setEquipmentName(pollutionEquipment.getEquipmentName());
            zyrjDeviceStatus.setUid(pollutionEquipment.getUid());
            uid = pollutionEquipment.getUid();
            zyrjDeviceStatus.setCompanyId(pollutionEquipment.getCompanyId());
            zyrjDeviceStatus.setCounty(zyrjDeviceStatusMapper.selectCountyByCompanyId(pollutionEquipment.getCompanyId()));
        }else {
            ZyrjFacilityDevice facilityDevice = (ZyrjFacilityDevice) equipment;
            zyrjDeviceStatus.setEquipmentId(String.valueOf(facilityDevice.getId()));
            zyrjDeviceStatus.setEquipmentName(facilityDevice.getDeviceName());
            zyrjDeviceStatus.setUid(facilityDevice.getUid());
            uid = facilityDevice.getUid();
            zyrjDeviceStatus.setCompanyId(facilityDevice.getCompanyId());
            zyrjDeviceStatus.setCounty(zyrjDeviceStatusMapper.selectCountyByCompanyId(facilityDevice.getCompanyId()));
        }

        //若数据存在就更新，若数据不存在就插入
        if (zyrjDeviceStatusMapper.selectOneByEquipmentIdAndDate(zyrjDeviceStatus) >= 1){
            ZyrjDeviceStatus zyrjDeviceStatus1 = zyrjDeviceStatusMapper.selectZyrjDeviceStatusByEquipmentIdAndDate(zyrjDeviceStatus);
            if(!zyrjDeviceStatus1.getStatus().equals("0")){
                zyrjDeviceStatus1.setStatus(Status);
            }
            if (Objects.equals(Status, "0") || Objects.equals(Status,"3")){
                zyrjDeviceStatus1.setDataTotalDay((zyrjDeviceStatus1.getDataTotalDay()+1L>=288?288L:zyrjDeviceStatus1.getDataTotalDay()+1L));
                zyrjDeviceStatus1.setStatus(Status);
            }
            if (zyrjDeviceStatus1.getUid() == null)
                zyrjDeviceStatus1.setUid(uid);

            zyrjDeviceStatusMapper.updateZyrjDeviceStatus(zyrjDeviceStatus1);
        }else {
            if (Objects.equals(Status, "0")){
                zyrjDeviceStatus.setDataTotalDay(1L);
            }else {
                zyrjDeviceStatus.setDataTotalDay(0L);
            }
            zyrjDeviceStatusMapper.insertZyrjDeviceStatus(zyrjDeviceStatus);
        }

    }

    //type为0是生产线，type为1是治理线
    private int Status (List<Integer> statusList,int type){
        int total = 0;
        for (Integer i: statusList) {
            total += i;
        }
        if (statusList.size() == 0){
            return 2;
        }else{
            if (type == 0){
                if (total < statusList.size())//返回0为开，返回1是关
                    return 0;
                return 1;
            }else {
                if (total > 0)
                    return 1;
                return 0;
            }
        }
    }
}
