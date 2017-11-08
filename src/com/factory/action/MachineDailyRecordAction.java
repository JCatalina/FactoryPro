package com.factory.action;

import java.io.File;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.factory.common.BaseManagerAction;
import com.factory.common.Constants;
import com.factory.common.PageList;
import com.factory.model.MachineDailyRecord;
import com.factory.model.MachineStaff;
import com.factory.model.SalaryPercentage;
import com.factory.model.Staff;
import com.factory.model.Variety;
import com.factory.service.IMachineDailyRecordService;
import com.factory.utils.DownFileUtil;
import com.factory.utils.ExcelUtil;
import com.factory.utils.MyDateUtils;
import com.factory.utils.SimpleToof;
import com.factory.utils.WebUtil;


@ParentPackage(value="admin")
@Namespace("/admin")
@Action(value = "/machineDailyRecord",results={
		 @Result(name = "list", location = "/WEB-INF/page/admin/machineDailyRecord/machineDailyRecord_list.jsp"),
		 @Result(name = "addUI", location = "/WEB-INF/page/admin/machineDailyRecord/machineDailyRecord_addUI.jsp"),
		 @Result(name = "staffMonthlyReport", location = "/WEB-INF/page/admin/machineDailyRecord/staffMonthlyReport.jsp"),
		 @Result(name = "oneMonthSumReport", location = "/WEB-INF/page/admin/machineDailyRecord/oneMonthSumReport.jsp"),
		 @Result(name = "machineMonthlyReport", location = "/WEB-INF/page/admin/machineDailyRecord/machineMonthlyReport.jsp"),
		 @Result(name = "afterEditUI", params={"queryObj.createTime","${queryObj.createTime}"},type="redirect",location = "/admin/machineDailyRecord!list.action"),
})
public class MachineDailyRecordAction extends BaseManagerAction{

	private static final long serialVersionUID = 1L;
	private MachineDailyRecord record;
	private PageList<MachineDailyRecord> pl;
	private final Integer PAGESIZE = 300;
	private MachineDailyRecord queryObj;//查询传值对象
	
	private String createTime;//传递时间参数 string
	private List<Staff> staffList = new ArrayList<Staff>();
	private List<Variety> varietyList= new ArrayList<Variety>();
	
	private Integer[] inputNos;
	private Integer[] nos;
	private String[] varietyNames;
	private Double[] AMs;
	private Double[] BMs;
	private Double[] CMs;
	private Double[] DMs;
	private Double[] sumMs;
	private String[] remarks;
	private String[] staffAIDs; //A班人员ID或者C班员工ID 参数
	private String[] staffBIDs; //B班人员ID或者D班员工ID 参数
	Map<Integer, HashMap<String, Double>> staffMonthlyReport;//员工每个机台的 品种-米数 map
	private HashMap<Integer, Double> everyMachineSalaryMap;//员工每个机台的工资map
	private HashMap<Integer,Double> everyMachinePercentage;//每个机台的工价（倍率）
	private String[] dateStrs;
	
	//汇总每月每天的数据
	private Double sum;//总米数
	private Integer sumRows;//每天行数等于米数
	Map<Date,Double> everyDaySumRecord;
	Map<Date,BigInteger> everyDaySumRows;
	
	private Integer startNo = 1;//查询机台号 起始位
	
	TreeMap<Integer,List<MachineDailyRecord>> everyMachineOneMonthRecord;
	private static final int MAX_PAGE = 10;
	
	private File file;//导入数据的文件
	
	private static final Integer VMSIZE = 5;//代表5组 品种-米数数据
	private static final String[] HEAD_LIST={
		"机台号","品种","米数","品种","米数","品种","米数","品种","米数","品种","米数","工价","金额","总价"
	};
	private static final String[] FIELD_LIST={
		"no","v1","m1","v2","m2","v3","m3","v4","m4","v5","m5","per","money","sum"
	};
	
	@Resource(name="machineDailyRecordService")
	private IMachineDailyRecordService machineDailyRecordService;
	
	
	public String list(){
		//查询条件
		where.append(" 1= 1 ");
		if(queryObj==null){//默认查询当天的数据
			where.append(" and DATE(o.createTime) = CURDATE()");
			queryObj=new MachineDailyRecord();
			queryObj.setCreateTime(new Date());
		}else{
			
			if(queryObj.getNo()!=null){
				where.append(" and o.no = ? ");
				values.add(queryObj.getNo());
			}
			
			if(queryObj.getCreateTime()!=null){
				where.append(" and DATE(o.createTime)= DATE(?)");
				values.add(queryObj.getCreateTime());
			}
		}
		
		//按照输入的排序：1.2...
		orderby.put("inputNo", "asc");
		
		pl=new PageList<MachineDailyRecord>(this.PAGESIZE, getFirstIndex());
		pl.setQueryResult(dm.getScrollData(MachineDailyRecord.class, pl.getFirstindex(), pl.getMaxresult(),where.toString(),values.toArray(), orderby));
		
		//员工，品种查询下拉框
		staffList= dm.getList(Staff.class);
		//varietyList= dm.getList(Variety.class);
		
		return "list";
	}
	
	//添加页面
	public String addUI(){
		staffList= dm.getList(Staff.class);
		
		queryObj.setCreateTime(new Date());
		return "addUI";
	}

	//添加方法
	public String add(){
		if(queryObj == null || queryObj.getCreateTime()==null){
			errMsg="创建数据没有选择日期";
			return ERROR;
		}
		machineDailyRecordService.batchAddMachineDailyRecord(queryObj.getCreateTime(),nos, AMs, BMs,CMs,DMs,sumMs, remarks, staffAIDs, staffBIDs);
		
		return list();
	}
	
	/**下载excel批量导入模版
	 * @throws Exception **/
	public void downLoadExcel() throws Exception{
		//String outPath = PathUtil.getRootPath() + "excleModel/每日数据表.xls";
		String outPath = getSession().getServletContext().getRealPath("/factory_file") + "/excleModel/每日数据表.xls";
		DownFileUtil.DownFile(outPath);
	}
	
	//excel导入数据
	public String importDataByFile() throws ParseException{
		if(queryObj.getCreateTime()==null){
			errMsg="未选择数据日期";
			return ERROR;
		}
		
		if (!SimpleToof.checkNotNull(file)){
			errMsg="上传文件不能为空";
			return ERROR;
		}

		if (file.getPath().indexOf("xls") != -1){
			errMsg="请上传xls格式的excel文件";
			return ERROR;
		}
		
		Date date=queryObj.getCreateTime();
		String path = "";
		try {
			//1、读取上传文件
			path = SimpleToof.uploadRealExcelFile(file, getSession().getServletContext().getRealPath("/factory_file/uploadExcel/"), "xls");
			//path =  + path;
			List<String[]> list = ExcelUtil.readExcel(path);
			List<MachineDailyRecord> dataList = new ArrayList<MachineDailyRecord>();
			Staff noselectStaff = new Staff("-1",null);
			if(list !=null){
				Staff staffC = null;
				Staff staffD = null;
				//2、遍历每行每列数据（不遍历标题行数据）
				int length = list.size();
				for (int i = 1; i < length; i++) {
					String[] row = list.get(i);
					//每一列数据: [0]机台号  [1]A班米数  [2]B班米数 [3]C班米数  [4]D班米数  [5]总米数  [6]备注  [7]C班人员  [8]D班人员
					//机台号不能为空
					if(SimpleToof.checkNotNull(row[0])){
						//根据机台号查出AB班人员
						MachineStaff machineStaff = dm.getT(MachineStaff.class, " o.no = ? ", new Object[]{Integer.valueOf(row[0])});
						if(machineStaff == null){
							//System.out.println("机台号:"+row[0]+"未设置A B 班人员信息");
							continue;
						}
						staffC = null;
						staffD = null;
						if(SimpleToof.checkNotNull(row[7])){
							staffC=dm.getT(Staff.class, " o.name = ?  ", new Object[]{row[7]});
						}
						if(SimpleToof.checkNotNull(row[8])){
							staffD=dm.getT(Staff.class, " o.name = ?  ", new Object[]{row[8]});
						}
						staffC =( staffC == null?noselectStaff:staffC );
						staffD =( staffD == null?noselectStaff:staffD );

						//3、生成实体数据
						MachineDailyRecord record= 
								new MachineDailyRecord(date, Integer.valueOf(row[0]), row[1],row[2], row[3], row[4], row[6],
										machineStaff.getStaffA(),machineStaff.getStaffB(),staffC,staffD,i);
						dataList.add(record);
					}else{
						continue;
					}
				}
				//4、批量保存
				machineDailyRecordService.saveImportData(dataList, date);
			}
			
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
			throw new RuntimeException("数组越界异常!~");
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("上传excel文档发生错误!~");
		}
		return list();
	}
	
	//编辑修改
	public String edit(){
		machineDailyRecordService.batchEditMachineDailyRecord(ids, nos, AMs, BMs,CMs, DMs, sumMs,remarks, staffAIDs, staffBIDs);

		MachineDailyRecord machineDailyRecord = dm.find(MachineDailyRecord.class, ids[0]);
		if(machineDailyRecord!=null)
		{
			queryObj=new MachineDailyRecord();
			queryObj.setCreateTime(machineDailyRecord.getCreateTime());
		}
		return "afterEditUI";
	}
	
	//删除
	public String delete(){
		if(SimpleToof.checkNotNull(id)){
			//dm.delete(machineDailyRecord.class, id);
		}
		return list();
	}

	//根据  员工ID 和 月份 汇总个人工作总量（员工每月数据统计）
	public String calculateStaffMonthlyReport() throws ParseException{
		staffList= dm.getList(Staff.class);
		sum = 0D;
		if(queryObj !=null && !queryObj.getId().equalsIgnoreCase("-1") && SimpleToof.checkNotNull(createTime)){
			//1、汇总计算员工本月的各机台的品种-米数。
			Date date=MyDateUtils.formatDateToYearMonth(createTime);
			queryObj.setCreateTime(date);
			staffMonthlyReport = machineDailyRecordService.calculateStaffMonthlyReport(queryObj.getId(),date);
			
			for (Staff ss : staffList) {
				if(ss.getId().equals(queryObj.getId())){
					queryObj.setRemark(ss.getName());
				}
			}
			
			//2、根据汇总计算的staffMonthlyReport(Map)计算各机台的工资
			everyMachineSalaryMap =new HashMap<Integer, Double>();//记录员工每种机台的工资
			everyMachinePercentage=new HashMap<Integer, Double>();//记录每种机台的工价
			//调用方法
			this.calSalaryByStaffMonthlyReport(staffMonthlyReport, everyMachineSalaryMap, everyMachinePercentage, sum, errMsg);
			
			
		}else{
			queryObj=new MachineDailyRecord(new Date());
		}
		
		return "staffMonthlyReport";
	}
	
	//根据员工各机台的 品种和米数Map(staffMonthlyReport) 计算工资
	private void calSalaryByStaffMonthlyReport(Map<Integer, HashMap<String, Double>> staffMonthlyReport,
			HashMap<Integer, Double> everyMachineSalaryMap,
			HashMap<Integer,Double> everyMachinePercentage,
			Double sum,
			String errMsg)
	{
		//2.1查询出机台倍率实体记录
		List<SalaryPercentage> spList = dm.getList(SalaryPercentage.class);
		//暂时只有有两种机台倍率
		if(spList!=null && spList.size() >=1){
			String machineNos = spList.get(0).getMachineNos();
			List<String> asList = Arrays.asList(machineNos.split(","));
			Double everyMachineSalary = 0D;
			Double variety = 0D;
			//2.2迭代并汇总计算每个机台的工资
			for (Map.Entry<Integer, HashMap<String, Double>> staffReport : staffMonthlyReport.entrySet()) {
				
				HashMap<String, Double> maps = staffReport.getValue();
				everyMachineSalary = 0D;
				for (Map.Entry<String, Double> o : maps.entrySet()) {
					//品种未填时有可能为""或者null
					if(SimpleToof.checkNotNull(o.getKey())){
						variety= Double.valueOf(o.getKey());
						everyMachineSalary+=variety * o.getValue();//品种 * 米数
					}
				}
				//不大于0没必要计算-->即品种没有填
				if( everyMachineSalary.compareTo(0D) > 0){
					//当前机台工资=机台的倍率 *(机台品种*米数+机台品种*米数....) =机台倍率 * sum
					// 记录在数据库的机台号为 大倍率的，其余没有记录的默认小倍率
					if(asList.contains(staffReport.getKey().toString())){
						everyMachineSalary=Constants.BIG_PERCENTAGE * everyMachineSalary;
						everyMachinePercentage.put(staffReport.getKey(), Constants.BIG_PERCENTAGE);
					}else{
						everyMachineSalary=Constants.LITTLE_PERCENTAGE * everyMachineSalary;
						everyMachinePercentage.put(staffReport.getKey(), Constants.LITTLE_PERCENTAGE);
					}
				}
				everyMachineSalaryMap.put(staffReport.getKey(), SimpleToof.getTypeDouble(everyMachineSalary));
				sum +=SimpleToof.getTypeDouble(everyMachineSalary);
			}
			
			this.setSum(SimpleToof.getTypeDouble(sum));
			
		}else{
			errMsg = "数据库没有记录机台的倍率，请检查数据库是否有录入";
		}
	}
	
	//根据员工id查询计算出 品种-米数  数据的Map,返回 封装excel的每一行数据 的list 
		private List<Map<String, Object>>  getStaffMonthlyDataById(String id,String createTime) throws ParseException{
			Date date=MyDateUtils.formatDateToYearMonth(createTime);
			
			staffMonthlyReport = machineDailyRecordService.calculateStaffMonthlyReport(id,date);
			
			//根据汇总计算的staffMonthlyReport(Map)计算各机台的工资
			everyMachinePercentage=new HashMap<Integer, Double>();//记录每种机台的工价
			everyMachineSalaryMap =new HashMap<Integer, Double>();//记录员工每种机台的工资
			sum = 0D;
			this.calSalaryByStaffMonthlyReport(staffMonthlyReport, everyMachineSalaryMap, everyMachinePercentage, sum, errMsg);
			
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			
			for (Integer no : staffMonthlyReport.keySet()) {
				HashMap<String, Double> vmData = staffMonthlyReport.get(no);
				//封装一行数据
				Map<String, Object> rowData = new LinkedHashMap<String, Object>();
				rowData.put("no", no);//机台号
				int count = 1;
				for (Entry<String, Double> vm : vmData.entrySet()) {
					rowData.put("v"+count, vm.getKey());
					rowData.put("m"+count, vm.getValue());
					count++;
				}
				//补充空白数据
				while( (count-1 ) <= VMSIZE ){
					rowData.put("v"+count, "");
					rowData.put("m"+count, "");
					count++;
				}
				
				//工资信息显示列
				//工价
				rowData.put("per", everyMachinePercentage.get(no));
				//当前机台的金额
				rowData.put("money", everyMachineSalaryMap.get(no));
				//总价(放一个就好)
				if(dataList.size()== 0)
					rowData.put("sum", this.getSum());
				
				dataList.add(rowData);
			}
			return dataList;
		}
		
	
	//导出 根据员工ID和月份 汇总个人工作总量(员工每月数据统计)表格
	public String downloadStaffMonthlyReport() throws ParseException{
		if(queryObj !=null && !queryObj.getId().equalsIgnoreCase("-1") && SimpleToof.checkNotNull(createTime)){
			try {
				Staff staff=dm.find(Staff.class, queryObj.getId());
				
				List<Map<String, Object>> dataList = getStaffMonthlyDataById(staff.getId(),createTime);
				
				//生成导出文件
				String outPath = getSession().getServletContext().getRealPath("/factory_file")+"/produceExcel/"+createTime+"/";
				String outName = staff.getName()+createTime+"产量.xls";
				ExcelUtil.createExcel(outPath, outName, HEAD_LIST,
						FIELD_LIST, dataList);
				DownFileUtil.DownFile(outPath + outName);
			}catch (Exception e) {
				e.printStackTrace();
				errMsg="导出出错了";
				return ERROR;
			}
			return NONE;
		}else{
			errMsg="未选择员工";
			return ERROR;
		}
	}

	//一次性生成所有员工月数据统计
	public String downloadAllStaffMonthlyReport() throws Exception{
		if(SimpleToof.checkNotNull(createTime)){
			try{
				staffList=dm.getList(Staff.class);
				List<String> staffNames=new ArrayList<>(staffList.size());
				List<List<Map<String, Object>>> sheetData = new ArrayList<>(staffList.size());
				List<Map<String, Object>> dataList = null;
				for (Staff staff : staffList) {
					if(staff.getId().equals("-1"))continue;
					//计算单个员工的sheet表格数据
					dataList = getStaffMonthlyDataById(staff.getId(),createTime);
					//添加到List
					staffNames.add(staff.getName());
					sheetData.add(dataList);
				}
				
				//生成导出文件
				String outPath = getSession().getServletContext().getRealPath("/factory_file")+"/produceExcel/"+createTime+"/";
				String outName = staffList.size()+"人"+createTime+"产量表.xls";
				ExcelUtil.createAllStaffMonthlyReport(outPath, outName, HEAD_LIST,
						FIELD_LIST, staffNames,sheetData,createTime);
				DownFileUtil.DownFile(outPath + outName);
				return NONE;
			}catch(Exception e){
				e.printStackTrace();
				errMsg="导出出错了";
				return ERROR;
			}
		}else{
			errMsg="未选择时间";
			return ERROR;
		}

	}
	
	
	
	//查询 每月各天的产量（月总量数据统计）
	public String calculateOneMonthReport(){
		try{
			if(SimpleToof.checkNotNull(createTime)){
				sum=0D;
				sumRows=0;
				Date date=MyDateUtils.formatDateToYearMonth(createTime);
				queryObj=new MachineDailyRecord(date);
				
				String sql="select createTime,SUM(sumM),COUNT(id) from machinedailyrecord WHERE DATE_FORMAT(createTime,'%Y%m')=DATE_FORMAT(?,'%Y%m') GROUP BY createTime";
				List<Object[]> result = dm.createSQLQuery(sql, new Object[]{date});
				everyDaySumRecord=new TreeMap<Date, Double>();
				everyDaySumRows=new TreeMap<Date, BigInteger>();
				for (Object[] obj : result) {
					sum+=(Double)obj[1];
					sumRows+=Integer.valueOf((BigInteger)obj[2]+"");
					everyDaySumRecord.put((Date)obj[0], (Double)obj[1]);
					everyDaySumRows.put((Date)obj[0], (BigInteger)obj[2]);
				}
			}else{
				queryObj=new MachineDailyRecord(new Date());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "oneMonthSumReport";
	}
	
	//查询每月 各机台产量（机台每月数据统计）
	public String calculateMachineMonthlyReport() throws ParseException{
		orderby.put("createTime", "asc");
		if(SimpleToof.checkNotNull(createTime)){
			Date date=MyDateUtils.formatDateToYearMonth(createTime);
			queryObj=new MachineDailyRecord(date);
			
			everyMachineOneMonthRecord=new TreeMap<Integer,List<MachineDailyRecord>>();
			
//			//查询本月的机台数
//			List<Object[]> noList = dm.createSQLQuery("select distinct(no) from machinedailyrecord o where DATE_FORMAT(o.createTime,'%Y%m')=DATE_FORMAT(?,'%Y%m') order by no limit ?,?",
//					new Object[]{date,(getFirstIndex()-1)*MAX_PAGE,MAX_PAGE});
			
			//查询每台机器的月记录
			//每次查询10台，编号为 startNo~startNo+9
			List<MachineDailyRecord> result=null;
			//1、无151 -159
			if(startNo == 160 ){
				result = dm.getList(MachineDailyRecord.class, 
						"o.no = ? and DATE_FORMAT(o.createTime,'%Y%m')=DATE_FORMAT(?,'%Y%m') ", 
						new Object[]{160,date},
						orderby);
				everyMachineOneMonthRecord.put(160, result);
			}else{
			//2、正常遍历
				for(int i = startNo ; i<= startNo +4  ; i++){
					result = dm.getList(MachineDailyRecord.class, 
							" o.no = ? and  DATE_FORMAT(o.createTime,'%Y%m')=DATE_FORMAT(?,'%Y%m')  ", 
							new Object[]{i,date},
							orderby);
					everyMachineOneMonthRecord.put(i, result);
				}
			}
			
		}else{
			queryObj=new MachineDailyRecord(new Date());
		}
		
		staffList= dm.getList(Staff.class);
		return "machineMonthlyReport";
	}
	
	//月总结 编辑
	public void batchEditMachineDailyRecordOnMonth() throws Exception{
		JSONObject res=new JSONObject();
		try{
			 res = machineDailyRecordService.batchEditMachineDailyRecordOnMonth(createTime,dateStrs,ids, varietyNames, nos, AMs, BMs, CMs, DMs, sumMs, remarks, staffAIDs, staffBIDs);
		}catch(Exception e){
			res.put("status", 1);
			res.put("errMsg", e.getMessage());
		}
		WebUtil.AJAXMsg(res.toString());
	}
	
	
	
	
	/**********************************setting and getting******************************************************/
	public MachineDailyRecord getRecord() {
		return record;
	}


	public void setRecord(MachineDailyRecord record) {
		this.record = record;
	}


	public PageList<MachineDailyRecord> getPl() {
		return pl;
	}


	public void setPl(PageList<MachineDailyRecord> pl) {
		this.pl = pl;
	}


	public MachineDailyRecord getQueryObj() {
		return queryObj;
	}


	public void setQueryObj(MachineDailyRecord queryObj) {
		this.queryObj = queryObj;
	}


	public List<Staff> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<Staff> staffList) {
		this.staffList = staffList;
	}


	public List<Variety> getVarietyList() {
		return varietyList;
	}


	public void setVarietyList(List<Variety> varietyList) {
		this.varietyList = varietyList;
	}

	public Integer[] getNos() {
		return nos;
	}


	public void setNos(Integer[] nos) {
		this.nos = nos;
	}


	public String[] getVarietyNames() {
		return varietyNames;
	}


	public void setVarietyNames(String[] varietyNames) {
		this.varietyNames = varietyNames;
	}


	public Double[] getAMs() {
		return AMs;
	}


	public void setAMs(Double[] aMs) {
		AMs = aMs;
	}


	public Double[] getBMs() {
		return BMs;
	}


	public void setBMs(Double[] bMs) {
		BMs = bMs;
	}

	public Double[] getCMs() {
		return CMs;
	}

	public void setCMs(Double[] cMs) {
		CMs = cMs;
	}

	public Double[] getDMs() {
		return DMs;
	}

	public void setDMs(Double[] dMs) {
		DMs = dMs;
	}

	public Double[] getSumMs() {
		return sumMs;
	}

	public void setSumMs(Double[] sumMs) {
		this.sumMs = sumMs;
	}

	public String[] getRemarks() {
		return remarks;
	}

	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}

	public String[] getStaffAIDs() {
		return staffAIDs;
	}

	public void setStaffAIDs(String[] staffAIDs) {
		this.staffAIDs = staffAIDs;
	}

	public String[] getStaffBIDs() {
		return staffBIDs;
	}

	public void setStaffBIDs(String[] staffBIDs) {
		this.staffBIDs = staffBIDs;
	}


	public Integer getPAGESIZE() {
		return PAGESIZE;
	}


	public IMachineDailyRecordService getMachineDailyRecordService() {
		return machineDailyRecordService;
	}


	public void setMachineDailyRecordService(
			IMachineDailyRecordService machineDailyRecordService) {
		this.machineDailyRecordService = machineDailyRecordService;
	}


	public Map<Integer, HashMap<String, Double>> getStaffMonthlyReport() {
		return staffMonthlyReport;
	}


	public void setStaffMonthlyReport(
			Map<Integer, HashMap<String, Double>> staffMonthlyReport) {
		this.staffMonthlyReport = staffMonthlyReport;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public Map<Date, Double> getEveryDaySumRecord() {
		return everyDaySumRecord;
	}


	public void setEveryDaySumRecord(Map<Date, Double> everyDaySumRecord) {
		this.everyDaySumRecord = everyDaySumRecord;
	}


	public Double getSum() {
		return sum;
	}


	public void setSum(Double sum) {
		this.sum = sum;
	}


	public TreeMap<Integer, List<MachineDailyRecord>> getEveryMachineOneMonthRecord() {
		return everyMachineOneMonthRecord;
	}


	public void setEveryMachineOneMonthRecord(
			TreeMap<Integer, List<MachineDailyRecord>> everyMachineOneMonthRecord) {
		this.everyMachineOneMonthRecord = everyMachineOneMonthRecord;
	}


	public Integer[] getInputNos() {
		return inputNos;
	}

	public void setInputNos(Integer[] inputNos) {
		this.inputNos = inputNos;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}

	public Map<Date, BigInteger> getEveryDaySumRows() {
		return everyDaySumRows;
	}

	public void setEveryDaySumRows(Map<Date, BigInteger> everyDaySumRows) {
		this.everyDaySumRows = everyDaySumRows;
	}

	public Integer getSumRows() {
		return sumRows;
	}

	public void setSumRows(Integer sumRows) {
		this.sumRows = sumRows;
	}

	public int getIntFromDouble(double d){
		return (int)d;
	}

	public Integer getStartNo() {
		return startNo;
	}

	public void setStartNo(Integer startNo) {
		this.startNo = startNo;
	}

	public String[] getDateStrs() {
		return dateStrs;
	}

	public void setDateStrs(String[] dateStrs) {
		this.dateStrs = dateStrs;
	}

	public HashMap<Integer, Double> getEveryMachineSalaryMap() {
		return everyMachineSalaryMap;
	}

	public void setEveryMachineSalaryMap(
			HashMap<Integer, Double> everyMachineSalaryMap) {
		this.everyMachineSalaryMap = everyMachineSalaryMap;
	}

	public HashMap<Integer, Double> getEveryMachinePercentage() {
		return everyMachinePercentage;
	}

	public void setEveryMachinePercentage(
			HashMap<Integer, Double> everyMachinePercentage) {
		this.everyMachinePercentage = everyMachinePercentage;
	}
	
	
	

	
}
