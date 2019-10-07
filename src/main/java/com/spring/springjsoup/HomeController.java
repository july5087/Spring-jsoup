package com.spring.springjsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		return "home";
	}
	
	@RequestMapping(value = "/crawl.do", method = RequestMethod.GET)
	public ModelAndView crawl(ModelAndView model) {
		// Jsoup : https://jsoup.org/
		// Jsoup�� �̿��ؼ� ���̹� ������ �ؿ� �౸ ũ�Ѹ�
		//String url = "https://sports.news.naver.com/wfootball/index.nhn";
		String url = "https://sports.news.naver.com/wbaseball/index.nhn";
        Document doc = null;
         
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        // �ֿ� ������ ������ �±׸� ã�Ƽ� ���������� �Ѵ�.
        Elements element = doc.select("div.home_news");
         
        // 1. ��� �κ��� ������ �����´�.
        String title = element.select("h2").text().substring(0, 4);
        
        System.out.println("============================================================");
        System.out.println(title);
        System.out.println("============================================================");
        
       
        ArrayList<String> list_text = new ArrayList<String>(); 
        ArrayList<String> list_link = new ArrayList<String>(); 
        
        // 2. ���� ���� ������ for�� ���鼭 ���
        for(Element el : element.select("li")) { 
        	String text = el.text().toString();
        	//String text = el.select("a").attr("title");
        	String link = "https://sports.news.naver.com" + el.select("a").attr("href");
            System.out.println(text);
            System.out.println(link);
            System.out.println("--------------------------------------------------------");
            list_text.add(text);
            list_link.add(link);
        }  
		
        model.addObject("title", title);
        model.addObject("list_text", list_text);
        model.addObject("list_link", list_link);
        model.setViewName("crawl");
        
		return model;
	}
}