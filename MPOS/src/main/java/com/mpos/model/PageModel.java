package com.mpos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mpos.dto.Tcategory;
import com.mpos.dto.TcategoryAttribute;
import com.mpos.dto.TlocalizedField;
import com.mpos.dto.Tmenu;

public class PageModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4077764078795140487L;
	private Tmenu menu;
	private Tcategory category;
	private TcategoryAttribute attribute;
	private List<TlocalizedField> localizedFieldFirst;
	private List<TlocalizedField> localizedFieldSecond;

	public Tmenu getMenu() {
		return menu;
	}

	public void setMenu(Tmenu menu) {
		this.menu = menu;
	}

	public Tcategory getCategory() {
		return category;
	}

	public void setCategory(Tcategory category) {
		this.category = category;
	}

	public TcategoryAttribute getAttribute() {
		return attribute;
	}

	public void setAttribute(TcategoryAttribute attribute) {
		this.attribute = attribute;
	}

	public List<TlocalizedField> getLocalizedFieldFirst() {
		return localizedFieldFirst;
	}

	public void setLocalizedFieldFirst(List<TlocalizedField> localizedFieldFirst) {
		this.localizedFieldFirst = localizedFieldFirst;
	}

	public List<TlocalizedField> getLocalizedFieldSecond() {
		return localizedFieldSecond;
	}

	public void setLocalizedFieldSecond(
			List<TlocalizedField> localizedFieldSecond) {
		this.localizedFieldSecond = localizedFieldSecond;
	}

	public List<TlocalizedField> setOneTlocalizedFieldValue(Object entity) {
		List<TlocalizedField> local = new ArrayList<TlocalizedField>();
		if (entity != null) {
			if (entity instanceof Tmenu) {
				Tmenu menu = (Tmenu) entity;
				if (localizedFieldFirst != null
						&& localizedFieldFirst.size() > 0) {
					for (TlocalizedField field : localizedFieldFirst) {
						field.setEntityId(menu.getMenuId());
						field.setTableName(menu.getClass().getSimpleName());
						local.add(field);
					}
				}
			}
		}
		return local;
	}

	public List<TlocalizedField> setTwoTlocalizedFieldValue(Object entity) {
		List<TlocalizedField> local = new ArrayList<TlocalizedField>();
		if (entity != null) {
			if (entity instanceof Tcategory) {
				Tcategory category = (Tcategory) entity;
				localizedFieldFirst.addAll(localizedFieldSecond);
				if (localizedFieldFirst != null
						&& localizedFieldFirst.size() > 0) {
					for (TlocalizedField field : localizedFieldFirst) {
						field.setEntityId(category.getCategoryId());
						field.setTableName(category.getClass().getSimpleName());
						local.add(field);
					}
				}
			}
			if (entity instanceof TcategoryAttribute) {
				TcategoryAttribute attribute = (TcategoryAttribute) entity;
				localizedFieldFirst.addAll(localizedFieldSecond);
				if (localizedFieldFirst != null
						&& localizedFieldFirst.size() > 0) {
					for (TlocalizedField field : localizedFieldFirst) {
						field.setEntityId(attribute.getAttributeId());
						field.setTableName(attribute.getClass().getSimpleName());
						local.add(field);
					}
				}
			}
		}
		return local;
	}

}
