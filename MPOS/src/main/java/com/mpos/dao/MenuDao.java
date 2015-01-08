package com.mpos.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;

import com.mpos.dao.base.BaseDao;
import com.mpos.dto.Tmenu;
import com.mpos.model.MenuModel;
import com.mpos.model.PagingData;
@Repository
@SuppressWarnings("unchecked")
public class MenuDao extends BaseDao<Tmenu> {
	
	public List<Tmenu> getAll(){
		String hql="from Tmenu where status=?";
		Query query=currentSession().createQuery(hql);
		query.setParameter(0, true);
		List<Tmenu> list=query.list();
		return list;  
	}
	
	public List<Tmenu> getNoChildren(){
		String hql="from Tmenu where status=true and menuId not in (select distinct pid from Tmenu)";
		Query query=currentSession().createQuery(hql);
		List<Tmenu> list=query.list();
		return list;
	}
	
	public PagingData findPageByNameSort(Criteria criteria, int startNo, int pageSize)
    {
        CriteriaImpl impl = (CriteriaImpl)criteria;
        Projection projection = impl.getProjection();        

        int totalCount = ((Long)criteria.setProjection(
            Projections.rowCount()).uniqueResult()).intValue();
        
        criteria.setProjection(projection);
        if (projection == null)
        {
            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }        

        if (totalCount == 0)
        {
            return new PagingData();
        }
        
        List<Tmenu> list = criteria.setFirstResult(startNo).setMaxResults(pageSize).list();
        List<MenuModel> models = new ArrayList<MenuModel>();
        if (list != null && list.size() > 0) {
			for (Tmenu tmenu : list) {
				MenuModel model = new MenuModel();
				model.setId(tmenu.getMenuId());
				model.setName(tmenu.getTitle());
				model.setPid(tmenu.getPid());
				model.setSort(tmenu.getSort());
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
        PagingData page = new PagingData(totalCount, totalCount, models.toArray());        
        return page;
    }
	
	private String loadTitle(Tmenu menu, String title) {
		Tmenu parent = get(menu.getPid());
		// String res = "";
		if (parent != null && parent.getMenuId() != null) {
			title = parent.getTitle() + " >> " + title;
			return loadTitle(parent, title);
		}
		// System.out.println(title);
		return title;
	}
	
}
