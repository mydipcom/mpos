/**   
 * @Title: RightsController.java 
 * @Package com.uswop.action 
 * @Description: TODO
 * @author Phills Li    
 * @date Sep 2, 2014 7:25:22 PM 
 * @version V1.0   
 */
package com.mpos.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.LogManageTools;
import com.mpos.commons.MposException;
import com.mpos.dto.Tlanguage;
import com.mpos.dto.TlocalizedField;
import com.mpos.dto.Tmenu;
import com.mpos.dto.Tstore;
import com.mpos.model.DataTableParamter;
import com.mpos.model.MenuModel;
import com.mpos.model.PageModel;
import com.mpos.model.PagingData;
import com.mpos.service.LanguageService;
import com.mpos.service.LocalizedFieldService;
import com.mpos.service.MenuService;
import com.mpos.service.StoreService;

@Controller
@RequestMapping(value = "/menu")
public class MenuController extends BaseController {

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(MenuController.class);

	@Resource
	private MenuService menuService;
	@Resource
	private LanguageService languageService;
	@Resource
	private LocalizedFieldService localizedFieldService;
	@Resource
	private StoreService storeService;
	
	/**
	 * 操作内容
	 */
	private String handleContent = "";
	/**
	 * 日志级别
	 */
	private short level = LogManageTools.NOR_LEVEL;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView menu(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		List<Tlanguage> languages = languageService.getLangListByStoreId(getSessionUser(request).getStoreId());
		String hql = "select new Tstore(storeId,storeName) from Tstore";
		List<Tstore> stores = storeService.select(hql, null);
		mav.addObject("role", getSessionUser(request).getAdminRole().getRoleId());
		mav.addObject("stores",stores);
		mav.addObject("lanList", languages);
		mav.setViewName("menu/menu");
		return mav;
	}
	

	@RequestMapping(value = "/menuList", method = RequestMethod.GET)
	@ResponseBody
	public String menuList(HttpServletRequest request, DataTableParamter dtp) {
		addStoreCondition(request, dtp);
		PagingData pagingData = menuService.loadMenuList(dtp,getLocale(request));
		if (pagingData.getAaData() == null) {
			Object[] objs = new Object[] {};
			pagingData.setAaData(objs);
		}
		pagingData.setSEcho(dtp.sEcho);

		String rightsListJson = JSON.toJSONString(pagingData);
		return rightsListJson;
	}

	@RequestMapping(value = "/addMenu", method = RequestMethod.POST)
	@ResponseBody
	public String addMenu(HttpServletRequest request, PageModel page) {
		JSONObject respJson = new JSONObject();
		try {
			addStore(page.getMenu(),request,page.getMenu().getStoreId());
			menuService.saveMenu(page.getMenu());
			localizedFieldService.createLocalizedFieldList(page.setOneTlocalizedFieldValue(page.getMenu()));
			handleContent = "添加客户端菜单:"+page.getMenu().getTitle()+"成功;新增ID为:"+page.getMenu().getMenuId();
			respJson.put("status", true);
			respJson.put("info", getMessage(request,"operate.success"));
		} catch (MposException be) {
			respJson.put("status", false);
			respJson.put("info",
					getMessage(request, be.getErrorID(), be.getMessage()));
			handleContent = "添加客户端菜单:"+page.getMenu().getTitle()+"失败";
			level = LogManageTools.FAIL_LEVEL;
		}
		LogManageTools.writeAdminLog(handleContent,level, request);
		return JSON.toJSONString(respJson);
	}

	@RequestMapping(value = "/getLocal/{menuId}", method = RequestMethod.GET)
	@ResponseBody
	public String getLocal(HttpServletRequest request, @PathVariable Integer menuId) {
		JSONObject respJson = new JSONObject();
		try {
			List<TlocalizedField> titleLocals = localizedFieldService.getLocalizedField(menuId,"Tmenu","title");
			respJson.put("localTitles", titleLocals);
			respJson.put("status", true);
		} catch (MposException be) {
			respJson.put("status", false);
			respJson.put("info",getMessage(request, be.getErrorID(), be.getMessage()));
		}
		return JSON.toJSONString(respJson);
	}

	@RequestMapping(value = "/editMenu", method = RequestMethod.POST)
	@ResponseBody
	public String updateMenu(HttpServletRequest request, PageModel page) {

		JSONObject respJson = new JSONObject();
		try {
			//addStore(page.getMenu(),request);
			menuService.updateMenu(page.getMenu());
			List<TlocalizedField> ls = page.setOneTlocalizedFieldValue(page.getMenu());
			localizedFieldService.updateLocalizedFieldList(ls);
			handleContent = "修改客户端菜单:"+page.getMenu().getTitle()+"成功;";
			respJson.put("status", true);
			respJson.put("info", getMessage(request,"operate.success"));
		} catch (MposException be) {
			respJson.put("status", false);
			respJson.put("info",getMessage(request, be.getErrorID(), be.getMessage()));
			handleContent = "修改客户端菜单:"+page.getMenu().getTitle()+"失败;";
			level = LogManageTools.FAIL_LEVEL;
		}
		LogManageTools.writeAdminLog(handleContent,level, request);
		return JSON.toJSONString(respJson);
	}

	@RequestMapping(value = "/menu/{ids}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteRights(@PathVariable String ids,
			HttpServletRequest request) {
		String[] idstrArr = ids.split(",");
		Integer[] idArr = ConvertTools.stringArr2IntArr(idstrArr);
		JSONObject respJson = new JSONObject();
		try {
			menuService.deleteMenuByIds(idArr);
			handleContent = "删除菜单:"+idArr.toString()+"成功;";
			respJson.put("status", true);
			respJson.put("info", getMessage(request,"operate.success"));
		} catch (MposException be) {
			respJson.put("status", false);
			respJson.put("info",
					getMessage(request, be.getErrorID(), be.getMessage()));
			handleContent = "删除菜单:"+idArr.toString()+"失败;";
			level = LogManageTools.FAIL_LEVEL;
		}
		LogManageTools.writeAdminLog(handleContent,level, request);
		return JSON.toJSONString(respJson);
	}

	@RequestMapping(value = "/loadMenu", method = RequestMethod.GET)
	@ResponseBody
	public String loadMenu(HttpServletRequest request,Integer storeId) {
		JSONObject respJson = new JSONObject();
		if(storeId==null||storeId==-1){
			storeId = getSessionUser(request).getStoreId();
		}
		try {
			List<Tmenu> menus = menuService.getAllMenu(storeId);
			Tlanguage language = languageService.get(getLocale(request));
			List<MenuModel> models = new ArrayList<MenuModel>();
			if (menus != null && menus.size() > 0) {
				for (Tmenu tmenu : menus) {
					/*TlocalizedField local_title = localizedFieldService.getLocalizedValue(tmenu.getMenuId(),language.getId(),PageTempModel.T_MENU,PageTempModel.LOCAL_MENU_TITLE);
					if(local_title!=null&&!local_title.getLocaleValue().isEmpty()){
						tmenu.setTitle(local_title.getLocaleValue());
					}*/
					MenuModel model = new MenuModel();
					model.setId(tmenu.getMenuId());
					if (tmenu.getPid() == 0) {
						model.setTitle(tmenu.getTitle());
					} else {
						model.setTitle(loadTitle(tmenu, tmenu.getTitle(),language));
					}
					models.add(model);
				}
			}
			Collections.sort(models, new Comparator<MenuModel>() {
				public int compare(MenuModel arg0, MenuModel arg1) {
					return arg0.getTitle().compareTo(arg1.getTitle());
				}
			});  
			respJson.put("status", true);
			respJson.put("menus", models);
		} catch (MposException be) {
			// TODO: handle exception
			respJson.put("status", false);
			respJson.put("info",
					getMessage(request, be.getErrorID(), be.getMessage()));
		}
		return JSON.toJSONString(respJson);
	}

	@RequestMapping(value = "/getMenu/{menuId}", method = RequestMethod.GET)
	@ResponseBody
	public String getParentTitle(HttpServletRequest request,@PathVariable Integer menuId) {
		JSONObject respJson = new JSONObject();
		try {
			PageModel page = new PageModel();
			page.setMenu(menuService.getMenu(menuId));
			respJson.put("status", true);
			respJson.put("page", page);
		} catch (MposException be) {
			// TODO: handle exception
			respJson.put("status", false);
			respJson.put("info",
					getMessage(request, be.getErrorID(), be.getMessage()));
		}
		return JSON.toJSONString(respJson);
	}
	
	private String loadTitle(Tmenu menu, String title,Tlanguage language) {
		Tmenu parent = menuService.getMenu(menu.getPid());
		/*TlocalizedField local_title = localizedFieldService.getLocalizedValue(parent.getMenuId(),language.getId(),PageTempModel.T_MENU,PageTempModel.LOCAL_MENU_TITLE);
		if(local_title!=null&&!local_title.getLocaleValue().isEmpty()){
			parent.setTitle(local_title.getLocaleValue());
		}*/
		if (parent != null && parent.getMenuId() != null) {
			title = parent.getTitle() + " >> " + title;
			if(parent.getPid()!=0){
				return loadTitle(parent, title,language);
			}
			
		}
		return title;
	}

}
