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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.dto.Tlanguage;
import com.mpos.dto.TlocalizedField;
import com.mpos.dto.Tmenu;
import com.mpos.dto.Tpromotion;
import com.mpos.model.DataTableParamter;
import com.mpos.model.MenuModel;
import com.mpos.model.PagingData;
import com.mpos.model.ParamWrapper;
import com.mpos.service.LanguageService;
import com.mpos.service.LocalizedFieldService;
import com.mpos.service.MenuService;

@Controller
@RequestMapping(value = "/menu")
public class MenuController extends BaseController {

	private Logger logger = Logger.getLogger(MenuController.class);

	@Resource
	private MenuService menuService;
	@Resource
	private LanguageService languageService;
	@Resource
	private LocalizedFieldService localizedFieldService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView menu(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		List<Tlanguage> languages = languageService.loadAllTlanguage();
		mav.addObject("lanList", languages);
		mav.setViewName("menu/menu");
		return mav;
	}

	@RequestMapping(value = "/menuList", method = RequestMethod.GET)
	@ResponseBody
	public String menuList(HttpServletRequest request, DataTableParamter dtp) {
		PagingData pagingData = menuService.loadMenuList(dtp);
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
	public String addMenu(HttpServletRequest request, Tmenu menu,
			@ModelAttribute ParamWrapper locals) {

		JSONObject respJson = new JSONObject();
		try {
			menuService.saveMenu(menu);
			localizedFieldService.createLocalizedFieldList(locals
					.setValue(menu));
			respJson.put("status", true);
		} catch (MposException be) {
			respJson.put("status", false);
			respJson.put("info",
					getMessage(request, be.getErrorID(), be.getMessage()));
		}
		return JSON.toJSONString(respJson);
	}

	@RequestMapping(value = "/getMenu", method = RequestMethod.POST)
	@ResponseBody
	public String getMenu(HttpServletRequest request, Integer menuId) {

		JSONObject respJson = new JSONObject();
		try {
			List<TlocalizedField> list = localizedFieldService
					.getListByEntityIdAndEntityName(menuId,
							Tmenu.class.getSimpleName());
			respJson.put("list", list);
			respJson.put("status", true);
		} catch (MposException be) {
			respJson.put("status", false);
			respJson.put("info",
					getMessage(request, be.getErrorID(), be.getMessage()));
		}
		return JSON.toJSONString(respJson);
	}

	@RequestMapping(value = "/editMenu", method = RequestMethod.POST)
	@ResponseBody
	public String updateRights(HttpServletRequest request, Tmenu menu,
			@ModelAttribute ParamWrapper locals) {

		JSONObject respJson = new JSONObject();
		try {
			menuService.updateMenu(menu);
			localizedFieldService.updateLocalizedFieldList(locals
					.setValue(menu));
			respJson.put("status", true);
		} catch (MposException be) {
			respJson.put("status", false);
			respJson.put("info",
					getMessage(request, be.getErrorID(), be.getMessage()));
		}
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
			respJson.put("status", true);
		} catch (MposException be) {
			respJson.put("status", false);
			respJson.put("info",
					getMessage(request, be.getErrorID(), be.getMessage()));
		}
		return JSON.toJSONString(respJson);
	}

	@RequestMapping(value = "/loadMenu", method = RequestMethod.GET)
	@ResponseBody
	public String loadMenu(HttpServletRequest request) {
		JSONObject respJson = new JSONObject();
		try {
			List<Tmenu> menus = menuService.getAllMenu();
			List<MenuModel> models = new ArrayList<MenuModel>();
			if (menus != null && menus.size() > 0) {
				for (Tmenu tmenu : menus) {
					MenuModel model = new MenuModel();
					model.setId(tmenu.getMenuId());
					if (tmenu.getPid() == 0) {
						model.setTitle(tmenu.getTitle());
					} else {
						model.setTitle(loadTitle(tmenu, tmenu.getTitle()));
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

	@RequestMapping(value = "/getParentTitle/{menuId}", method = RequestMethod.GET)
	@ResponseBody
	public String getParentTitle(HttpServletRequest request,
			@PathVariable Integer menuId) {
		JSONObject respJson = new JSONObject();
		try {

			MenuModel model = new MenuModel();
			Tmenu tmenu = menuService.getMenu(menuId);
			model.setId(menuId);
			model.setTitle(loadTitle(tmenu, tmenu.getTitle()));
			respJson.put("status", true);
			respJson.put("menu", model);
		} catch (MposException be) {
			// TODO: handle exception
			respJson.put("status", false);
			respJson.put("info",
					getMessage(request, be.getErrorID(), be.getMessage()));
		}
		return JSON.toJSONString(respJson);
	}

	private String loadTitle(Tmenu menu, String title) {
		Tmenu parent = menuService.getMenu(menu.getPid());
		// String res = "";
		if (parent != null && parent.getMenuId() != null) {
			title = parent.getTitle() + " >> " + title;
			return loadTitle(parent, title);
		}
		// System.out.println(title);
		return title;
	}
}
