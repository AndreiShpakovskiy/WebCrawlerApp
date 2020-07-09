package com.shpakovskiy.webcrawler.controller;

import com.shpakovskiy.webcrawler.crawler.WebCrawler;
import com.shpakovskiy.webcrawler.statistics.OccurrencesTable;
import com.shpakovskiy.webcrawler.statistics.StatItem;
import com.shpakovskiy.webcrawler.zipper.Zipper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @RequestMapping("/")
    private ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping("/crawl")
    private ModelAndView getTerms(@RequestParam("root-page") String rootPage,
                                  @RequestParam("terms") String terms,
                                  @RequestParam("max-pages") String maxPages,
                                  @RequestParam("max-depth") String maxDepth,
                                  HttpServletRequest request) {

        int pagesToVisit = (maxPages != null && maxPages.matches("[1-9]\\d{1,5}")) ? Integer.parseInt(maxPages) : 10000;
        int depth = (maxDepth != null && maxDepth.matches("[1-9]\\d{1,5}")) ? Integer.parseInt(maxDepth) : 8;

        WebCrawler crawler = new WebCrawler(rootPage, termsToList(terms), pagesToVisit, depth);
        OccurrencesTable occurrencesTable = crawler.getOccurrencesStatistics();
        List<StatItem> top10 = occurrencesTable.getTop10();

        String downloadPath = new Zipper(request.getServletContext()
                .getRealPath("/WEB-INF/downloads/"))
                .zipCsvs(occurrencesTable.toCsv(), occurrencesTable.top10ToCsv());

        System.out.println(request.getServletContext().getRealPath("/WEB-INF/downloads/"));
        System.out.println(downloadPath);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        modelAndView.addObject("terms", termsToList(terms));
        modelAndView.addObject("top10", top10);
        modelAndView.addObject("downloadPath", downloadPath);
        return modelAndView;
    }

    private List<String> termsToList(String terms) {
        return Arrays.stream(terms.split(",")).map(String::trim).collect(Collectors.toList());
    }

}
