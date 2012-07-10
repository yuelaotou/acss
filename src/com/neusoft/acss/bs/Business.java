package com.neusoft.acss.bs;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import com.neusoft.acss.bean.AcssBean;
import com.neusoft.acss.bean.EmployeeDetailBean;
import com.neusoft.acss.bean.EmployeeTotalBean;
import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Vacation;
import com.neusoft.acss.bean.WorkDay;
import com.neusoft.acss.enums.WorkSt;
import com.neusoft.acss.exception.BizException;

/**
 * <p> Title: [业务逻辑类]</p>
 * <p> Description: [本类旨在对各种业务逻辑处理，和文件操作区分开]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class Business {

	/**
	 * <p>Discription:[根据法定假日和串休记录，再结合正常周六周日休息，判断AcssBean是正常上班还是休息]</p>
	 * Created on 2012-7-9
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static void checkVacation(List<AcssBean> acssBeanList, List<Vacation> vacationList, List<WorkDay> workDayList) {
		for (AcssBean acssBean : acssBeanList) {
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(DateUtils.parseDate(acssBean.getDate(), "yyyy-MM-dd"));
			} catch (ParseException e) {
				throw new BizException("考勤文件中日期格式错误，请确认格式为：yyyy-MM-dd，" + acssBean.getDate(), e);
			}

			// 判断是否为周六周日
			if (c.get(Calendar.DAY_OF_WEEK) == 7 || c.get(Calendar.DAY_OF_WEEK) == 1) {
				acssBean.setVacation(true);
				// 如果在串休列表中，直接return flase，不再计算Vacation了。
				for (WorkDay wd : workDayList) {
					if (wd.getDate().compareTo(c.getTime()) == 0) {
						acssBean.setVacation(false);
						break;
					}
				}
			} else {
				// 如果在国家规定的休假列表中，return true。
				for (Vacation vac : vacationList) {
					if (vac.getDate().compareTo(c.getTime()) == 0) {
						acssBean.setVacation(true);
					}
				}
			}
		}
	}

	/**
	 * <p>Discription:[通过导入的考勤记录、出差登记表和相关设置计算职工本月详细考勤记录]</p>
	 * Created on 2012-6-30
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static List<EmployeeDetailBean> generateEmployeeDetailList(List<AcssBean> acssBeanList,
			List<EvectionBean> evectionBeanList) {
		EmployeeDetailBean employeeDetailBean = null;
		List<EmployeeDetailBean> employeeDetailBeanList = new ArrayList<EmployeeDetailBean>();

		String mdate = "";
		for (AcssBean acssBean : acssBeanList) {

			// 新开始一天的打卡记录
			if (!mdate.equals(acssBean.getId() + acssBean.getDate())) {
				if (!mdate.equals("")) {
					employeeDetailBeanList.add(employeeDetailBean);
				}

				employeeDetailBean = new EmployeeDetailBean();
				employeeDetailBean.setDepartment(acssBean.getDepartment());
				employeeDetailBean.setName(acssBean.getName());
				employeeDetailBean.setId(acssBean.getId());
				employeeDetailBean.setDate(acssBean.getDate());
				employeeDetailBean.setTEarly("");
				employeeDetailBean.setTEarly("");
				if (acssBean.isVacation()) {
					// 如果是休息，则认为今天打卡是加班。否则先不处理，等以后再统一处理。
					employeeDetailBean.setStatus(WorkSt.EVECTION);
				}

				// 确保是一个人同一天
				mdate = acssBean.getId() + acssBean.getDate();
			}

			String acssBeanTime = acssBean.getTMorning();
			if (acssBeanTime.compareTo("12:00:00") < 0) {// 上午
				employeeDetailBean.setTEarly(acssBeanTime);
			} else if (acssBeanTime.compareTo("12:00:00") >= 0 && acssBeanTime.compareTo("13:00:00") <= 0) {// 中午
				if (employeeDetailBean.getTEarly().equals("")) {
					employeeDetailBean.setTEarly(acssBeanTime);
				} else {
					employeeDetailBean.setTEarly(acssBeanTime);
				}
			} else if (acssBeanTime.compareTo("13:00:00") > 0) {// 下午
				employeeDetailBean.setTEarly(acssBeanTime);
			}
		}
		employeeDetailBeanList = getEmployeeDetailWorkSt(employeeDetailBeanList);
		return employeeDetailBeanList;
	}

	/**
	 * <p>Discription:[通过EmployeeDetailBean的上班时间，下班时间计算EmployeeDetailBean的打卡情况]</p>
	 * Created on 2012-7-2
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static List<EmployeeDetailBean> getEmployeeDetailWorkSt(List<EmployeeDetailBean> employeeDetailBeanList) {

		// 读取所有设置的信息。
		String beginTime = "";
		String endTime = "";

		for (EmployeeDetailBean employeeDetailBean : employeeDetailBeanList) {
			if (employeeDetailBean.getStatus() != null
					&& employeeDetailBean.getStatus().compareTo(WorkSt.EVECTION) == 0) {
				continue;
			}

			if ("".equals(employeeDetailBean.getTEarly())) {
				employeeDetailBean.setStatus(WorkSt.EVECTION);
			} else if ("".equals(employeeDetailBean.getTEarly())) {
				employeeDetailBean.setStatus(WorkSt.EVECTION);
			} else if (employeeDetailBean.getTEarly().compareTo(beginTime) > 0) {
				employeeDetailBean.setStatus(WorkSt.EVECTION);
			} else if (employeeDetailBean.getTEarly().compareTo(endTime) < 0) {
				employeeDetailBean.setStatus(WorkSt.EVECTION);
			} else {
				employeeDetailBean.setStatus(WorkSt.EVECTION);
			}
		}
		return employeeDetailBeanList;
	}

	/**
	 * <p>Discription:[通过employeeDetailBeanList计算出employeeTotalBeanList]</p>
	 * Created on 2012-7-2
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static List<EmployeeTotalBean> convertDetail2Total(List<EmployeeDetailBean> employeeDetailBeanList) {
		List<EmployeeTotalBean> employeeTotalBeanList = new ArrayList<EmployeeTotalBean>();
		EmployeeTotalBean employeeTotalBean = null;
		int id = 0;
		int c_late = 0;
		int c_early = 0;
		for (EmployeeDetailBean employeeDetailBean : employeeDetailBeanList) {
			if (id != employeeDetailBean.getId()) {
				if (id != 0) {
					employeeTotalBean.setC_late(c_late);
					employeeTotalBean.setC_early(c_early);
					employeeTotalBeanList.add(employeeTotalBean);
				}
				employeeTotalBean = new EmployeeTotalBean();
				employeeTotalBean.setDepartment(employeeDetailBean.getDepartment());
				employeeTotalBean.setName(employeeDetailBean.getName());
				employeeTotalBean.setId(employeeDetailBean.getId());
				// 换人了
				c_late = 0;
				c_early = 0;
			}
			if (employeeDetailBean.getStatus().compareTo(WorkSt.LEAVE) == 0) {
				c_late++;
			}
			if (employeeDetailBean.getStatus().compareTo(WorkSt.LEAVE) == 0) {
				c_early++;
			}
			if (employeeDetailBean.getStatus().compareTo(WorkSt.LEAVE) == 0) {
			}
			if (employeeDetailBean.getStatus().compareTo(WorkSt.LEAVE) == 0) {

				// 判断，若上班或下班时间未打卡，但是因为是加班，就算成是属性设置中的上班时间和下班时间
				if (employeeDetailBean.getTEarly().equals("")) {
					employeeDetailBean.setTEarly("");
				}
				if (employeeDetailBean.getTEarly().equals("")) {
					employeeDetailBean.setTEarly("");
				}

				// 计算两个时间的小时差，也就是加班多少时间
				c_early += minusDate(employeeDetailBean.getTEarly(), employeeDetailBean.getTEarly());
			}
			id = employeeDetailBean.getId();
		}
		return employeeTotalBeanList;
	}

	/**
	 * <p>Discription:[计算两个时间之间相差多少小时]</p>
	 * Created on 2012-7-2
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws ParseException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static int minusDate(String beginTime, String endTime) {
		long begin = 0;
		long end = 0;
		try {
			begin = DateUtils.parseDate(beginTime, "HH:mm:ss").getTime();
			end = DateUtils.parseDate(endTime, "HH:mm:ss").getTime();
		} catch (ParseException e) {
			throw new BizException("计算日期相差时间出错:" + beginTime + "，" + endTime, e);
		}
		return (int) ((end - begin) / (1000 * 60 * 60)) + 1;
	}

}
