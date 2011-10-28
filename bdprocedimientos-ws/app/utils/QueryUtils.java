package utils;

import java.util.List;

import play.db.jpa.GenericModel.JPAQuery;

public class QueryUtils {

	/**
	 * Fetch paginado
	 * @param query
	 * @param pageSize
	 * @param pageStartIndex
	 * @return
	 */
	public static <T> List<T> fetchPaginate(JPAQuery query, Integer pageSize, Integer pageStartIndex){
		if(pageStartIndex != null && pageStartIndex > 0){
			query = query.from(pageStartIndex);
		}
		if(pageSize != null && pageSize > 0){
			return query.fetch(pageSize);
		}
		return query.fetch();
	}
	
}
