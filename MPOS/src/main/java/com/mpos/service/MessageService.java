package com.mpos.service;

import com.mpos.dto.Tmessage;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface MessageService {
      public void create(Tmessage message);
      public void delete(Tmessage message);
      public void delete(Integer id);
      public PagingData loadList(DataTableParamter rdtp);
}
