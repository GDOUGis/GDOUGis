package org.cpp.gis.utils;

public class PageUtil {
	public static Page createPage(int everyPage,int totalCount,int currentPage){
		everyPage = getEveryPage(everyPage);
		currentPage = getCurrentPage(currentPage);
		int totalPage = getTotalPage(everyPage, totalCount);
		int beginIndex = getBeginIndex(everyPage, currentPage);
		boolean hasPrePage = getHasNextPage(totalPage, currentPage);
		boolean hasNextPage = getHasNextPage(totalPage, currentPage);
		int pageInfo[] = getPageInfo(totalPage,currentPage);
		return new Page(everyPage, totalCount, totalPage, currentPage, beginIndex, hasPrePage, hasNextPage,pageInfo[0],pageInfo[1]);
				
	}
	private static int[] getPageInfo(int totalPage, int currentPage) {
		int pageInfo[] = new int[2];
		if(totalPage<=10){
			pageInfo[0] = 1;
			pageInfo[1] =  totalPage;
		}else {
			pageInfo[0] =  currentPage-4;
			pageInfo[1] =  currentPage+5;
			if( pageInfo[0]<1){
				 pageInfo[0] = 1;
				 pageInfo[1] = 10;
			}
			if( pageInfo[1]> totalPage){
				pageInfo[0] =  totalPage-9;
				pageInfo[1] =  totalPage;
			}
		}
		return pageInfo;
	}
	public static int getEveryPage(int everyPAge){
		return everyPAge == 0?10:everyPAge;		
	}
	public static int getCurrentPage(int currentPage){
		return currentPage == 0?1:currentPage;		
	}
	public static int getTotalPage(int everyPage,int totalCount){
		int totalPage = 0;
		if(totalCount != 0&&totalCount%everyPage == 0){
			totalPage = totalCount/everyPage;
		}else {
			totalPage = totalCount/everyPage+1;
			
		}
		return totalPage;	
	}
	public static int getBeginIndex(int everyPage,int currentPage){
		return (currentPage-1)*everyPage;
	}
	public static boolean getHasPrePage(int currentPage){
		return currentPage ==1?false:true;
	}
	public static boolean getHasNextPage(int totalPage,int currentPage){
		return currentPage == totalPage||totalPage ==0?false:true;
	}

}
