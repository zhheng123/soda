package com.soda.services.iface;

import com.soda.bean.News;

public interface NewsService {
	News findNewById(Integer newId);
}
