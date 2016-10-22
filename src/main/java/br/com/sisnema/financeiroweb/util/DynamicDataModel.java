package br.com.sisnema.financeiroweb.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

import br.com.sisnema.financeiroweb.action.ActionPaginator;

public class DynamicDataModel<T> extends LazyDataModel<T> implements SelectableDataModel<T> {

	private static final long serialVersionUID = -1574382271755474850L;

	private ActionPaginator<T> paginator;
	
	private List<T> lista;

	private Boolean pesquisaAtiva=false;
	private Long count;
	private Integer defaultMaxRow=10;

	public void reiniciarPaginator(){
		pesquisaAtiva=true;
	}
	
	
	public void limparPesquisa(){
		pesquisaAtiva=false;
		lista=null;
		count=0L;
	}	
	
	@Override
	public T getRowData(String rowKey) {
		if (rowKey==null || rowKey.equals("null")){
			return null;
		}
        return lista.get(Integer.valueOf(rowKey));
	}	

	
	@Override
	public Object getRowKey(T bean) {
		int pos = lista.indexOf(bean);
		if (pos==-1){
			return null;
		}		
		return String.valueOf(lista.indexOf(bean));
	}

	
	@Override
	public List<T> load(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		
		if (paginator!=null){
			count = paginator.recuperarCount(filters);
			lista = paginator.recuperarListaPaginada(firstResult, maxResults, sortField, sortOrder.equals(SortOrder.ASCENDING), filters);
		}
        
		if (lista==null){
			lista = new ArrayList<T>();
		}		
        return lista;
	}
	
	@Override
	public int getRowCount() {
		if (count!=null){
			return count.intValue();
		}
		return 0;
	}	

	public ActionPaginator<T> getPaginator() {
		return paginator;
	}

	public List<T> getLista() {
		return lista;
	}

	public void setLista(List<T> lista) {
		this.lista = lista;
	}

	public Boolean getPesquisaAtiva() {
		return pesquisaAtiva;
	}

	public void setPesquisaAtiva(Boolean pesquisaAtiva) {
		this.pesquisaAtiva = pesquisaAtiva;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Integer getDefaultMaxRow() {
		return defaultMaxRow;
	}

	public void setDefaultMaxRow(Integer defaultMaxRow) {
		this.defaultMaxRow = defaultMaxRow;
	}

	public void setPaginator(ActionPaginator<T> paginator) {
		this.paginator = paginator;
	}
}