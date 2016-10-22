package br.com.sisnema.financeiroweb.action;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface ActionPaginator<T> extends Serializable {

	List<T> recuperarListaPaginada(Integer firstResult, Integer maxResults, 
								   String orderBy, boolean asc,
								   Map<String, Object> filters);

	Long recuperarCount(Map<String, Object> filters);
}
