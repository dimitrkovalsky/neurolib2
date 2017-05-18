package com.liberty.common;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by user on 16.05.2017.
 */
//Before using pagination bar you must create new instance of PageWrapper class from Page<T> and put it to model
    @Data
public class PageWrapper<T> {
    public static final int MAX_PAGE_ITEM_DISPLAY = 5;
    private Page<T> page;
    private List<PageItem> items;
    private int currentNumber;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PageWrapper(Page<T> page, HttpServletRequest request){
        this(page, "");
        setUrl(clearPaginationParametersFromRequest(request));
    }

    public PageWrapper(Page<T> page, String url){
        this.page = page;
        this.url = url;
        items = new ArrayList<PageItem>();

        currentNumber = page.getNumber() + 1; //start from 1 to match page.page

        init(page);

    }

    private String clearPaginationParametersFromRequest(HttpServletRequest request){
        //Clear all request parameters that relate to paging (also i clear csrf).
        LinkedHashMap<String,List<String>> paramsWithoutPagingMap = new LinkedHashMap<>();

        for(Map.Entry<String,String[]> parameter:request.getParameterMap().entrySet()){
            switch (parameter.getKey()){
                case "page":
                case "size":
                case "_csrf":
                    break;
                default:
                    paramsWithoutPagingMap.put(parameter.getKey(), Arrays.stream(parameter.getValue()).collect(Collectors.toList()));
            }

        }

        MultiValueMap<String,String> paramsWithoutPagingMultiMap = new LinkedMultiValueMap<>(paramsWithoutPagingMap);
        UriComponents uriComponents = UriComponentsBuilder.fromPath(request.getRequestURI()).queryParams(paramsWithoutPagingMultiMap).build();

        return uriComponents.toString();
    }

    private void init(Page<T> page){
        int start, size;
        if (page.getTotalPages() <= MAX_PAGE_ITEM_DISPLAY){
            start = 1;
            size = page.getTotalPages();
        } else {
            if (currentNumber <= MAX_PAGE_ITEM_DISPLAY - MAX_PAGE_ITEM_DISPLAY/2){
                start = 1;
                size = MAX_PAGE_ITEM_DISPLAY;
            } else if (currentNumber >= page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY/2){
                start = page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY + 1;
                size = MAX_PAGE_ITEM_DISPLAY;
            } else {
                start = currentNumber - MAX_PAGE_ITEM_DISPLAY/2;
                size = MAX_PAGE_ITEM_DISPLAY;
            }
        }

        for (int i = 0; i<size; i++){
            items.add(new PageItem(start+i, (start+i)==currentNumber));
        }
    }

    public int getRealNumber(){
        return currentNumber-1;
    }

    public List<T> getContent(){
        return page.getContent();
    }

    public int getSize(){
        return page.getSize();
    }

    public int getTotalPages(){
        return page.getTotalPages();
    }

    public int getRealTotalPages(){
        return page.getTotalPages()-1;
    }

    public boolean isFirstPage(){
        return page.isFirst();
    }

    public boolean isLastPage(){
        return page.isLast();
    }

    public boolean isHasPreviousPage(){
        return page.hasPrevious();
    }

    public boolean isHasNextPage(){
        return page.hasNext();
    }

    @Override
    public String toString() {
        return "PageWrapper{" +
                "page=" + page +
                ", items=" + items +
                ", currentNumber=" + currentNumber +
                ", url='" + url + '\'' +
                '}';
    }

    @Data
    public class PageItem {
        private int number;
        private boolean current;
        public PageItem(int number, boolean current){
            this.number = number;
            this.current = current;
        }

        public int getRealNumber(){
            return this.number-1;
        }

    }
}