package com.liberty.service.impl;

import com.liberty.component.SitemapHandler;
import com.liberty.dto.sitemap.SitemapIndexLinkList;
import com.liberty.dto.sitemap.SitemapUrlList;
import com.liberty.service.SitemapService;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by user on 03.09.2017.
 */
@Service
public class SitemapServiceImpl implements SitemapService {

    @Value("${sitemap.previouslyGenerated}")
    private Boolean usePreviouslyGeneratedSitemap ;

    private static final String STORAGE_SITEMAP_PATH = "D:\\flibusta\\sitemap";


    @Autowired
    private List<SitemapHandler> sitemapHandlers;

    @Autowired
    private XStream xStream;

    private Map<String, SitemapHandler> sitemapHandlerMap = new HashMap<>();

    @PostConstruct
    private void mapHandlersAndInit(){
        for(SitemapHandler handler: sitemapHandlers){
            sitemapHandlerMap.put(handler.getSitemapType(),handler);
        }
        if(usePreviouslyGeneratedSitemap.equals(true)) {
            generateAndSaveSitemaps();
        }
    }

    @Scheduled(cron="0 0 0 * * ?")
    private void generateAndSaveSitemaps(){
        if(usePreviouslyGeneratedSitemap.equals(true)) {
            for (SitemapHandler handler : sitemapHandlers) {
                String sitemapIndex = generateIndexSitemap();
                saveSitemapIndexFileString(sitemapIndex);
                for (int page = 0; page < handler.getPagesCount(); page++) {
                    String sitemap = generateSitemapAtPage(handler.getSitemapType(), page);
                    saveToStorageSitemapPageString(sitemap, handler.getSitemapType(), page);
                }
            }
        }
    }

    public String getIndexSitemap(){
        if(usePreviouslyGeneratedSitemap.equals(false)) {
            return generateIndexSitemap();
        }else
            return loadFromStorageSitemapIndex();
    }

    public String getSitemapAtPage(String type, Integer page){
        if(usePreviouslyGeneratedSitemap.equals(false)){
            return generateSitemapAtPage(type, page);
        }else{
            return loadFromStorageSitemapPage(type,page);
        }
    }

    private String generateIndexSitemap(){
        SitemapIndexLinkList sitemapIndexLinkList = new SitemapIndexLinkList();
        for (SitemapHandler handler : sitemapHandlers) {
            sitemapIndexLinkList.addAll(handler.getSitemapLinks());
        }
        return xStream.toXML(sitemapIndexLinkList);
    }

    private String generateSitemapAtPage(String type,Integer page){
        SitemapHandler handler = getSitemapHandler(type);
        SitemapUrlList sitemapUrlList = handler.getLinksAtPage(page);
        return xStream.toXML(sitemapUrlList);
    }

    private String loadFromStorageSitemapPage(String type, Integer page){
        return loadFromStorageFile(formatSitemapPageFilePath(type, page));
    }

    private String loadFromStorageSitemapIndex(){
        return loadFromStorageFile(getSitemapIndexPageFilePath());
    }

    private void saveStringToFile(String string, String path){
        try {
            Path targetPath = new File(path).toPath();
            if (!Files.exists(targetPath.getParent())) {
                Files.createDirectories(targetPath.getParent());
            }
            Files.write(targetPath, string.getBytes(), StandardOpenOption.CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveSitemapIndexFileString(String file){
        saveStringToFile(file, STORAGE_SITEMAP_PATH+getSitemapIndexPageFilePath());
    }

    private void saveToStorageSitemapPageString(String file, String type, Integer page){
        saveStringToFile(file, STORAGE_SITEMAP_PATH+formatSitemapPageFilePath(type, page));
    }

    private Optional<File> getFile(String path) {
        File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            return Optional.of(file);
        }
        return Optional.empty();
    }

    private String loadFromStorageFile(String path){
        Optional<File> file = getFile(STORAGE_SITEMAP_PATH+path);
        String content = "";
        try {
            content = FileUtils.readFileToString(file.get(), "UTF-8");
        }catch (IOException e){
            e.printStackTrace();
        }
        return content;
    }

    private String formatSitemapPageFilePath(String type, Integer page){
        return "/sitemaps/"+type+"-"+page+".xml";
    }

    private String getSitemapIndexPageFilePath(){
        return "/sitemap.xml";
    }

    private SitemapHandler getSitemapHandler(String type){
        return sitemapHandlerMap.get(type);
    }
}
