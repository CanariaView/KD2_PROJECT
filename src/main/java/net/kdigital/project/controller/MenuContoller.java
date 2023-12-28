package net.kdigital.project.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import net.kdigital.project.domain.Journal;
import net.kdigital.project.domain.Reply;
import net.kdigital.project.domain.Report;
import net.kdigital.project.service.MenuService;

@Controller
@Slf4j
public class MenuContoller {
	
	@Autowired
	MenuService service;
	
	@Value("${wordcloud.server}")
	String url;
	
	
	@GetMapping("/products")
	public String product() {
		
		return "products";
	}

	@GetMapping("/community")
	public String community(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String memberid = authentication.getName();
		
		String email = service.getEmail(memberid);
		
		model.addAttribute("email", email);
		
		return "community";
	}
	
	@GetMapping("/selectReplyAll")
	@ResponseBody
	public List<Reply> selectReplyAll() {
		
		List<Reply> list = service.selectReplyAll();
		
		return list; 
	}
	
	@PostMapping("/insertReply")
	@ResponseBody
	public String insertReply(Reply reply) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String memberid = authentication.getName();
		
		int imageseq = service.getImageseq(memberid);
		
		reply.setImageseq(imageseq);
		
		int result = service.insertReply(reply);
		
		if (result == 1) {
			return "Success";
		}
		else
			return "Fail";
	}
	
	@GetMapping("/delete")
	@ResponseBody
	public String delete(String replyseq) {
		
		int result = service.deleteReply(replyseq);
		
		if (result == 1) {
			return "Success";
		}
		else
			return "Fail";
	}
	
	@GetMapping("/faq")
	public String faq() {
		
		return "faq";
	}
	
	@GetMapping("/contact")
	public String contact() {
		
		return "contact";
	}
	
	@GetMapping("/insights")
	public String temp(Model model) {
		
//        // FastAPI 요청
//        RestTemplate restTemplate = new RestTemplate();
//        
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
//
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
//        restTemplate.exchange(url+"/update", HttpMethod.POST, requestEntity, Object.class);
		
		// bloomberg
		List<Journal> bloombergList = new ArrayList<>();
		
		String csvFile = "src/main/resources/static/data/bloomberg_list.csv";
		String line;
		String cvsSplitBy = ",";
		
		Random rand = new Random();
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
		    while ((line = br.readLine()) != null) {
		        String[] data = line.split(cvsSplitBy);
		        String title = data[0];
		        String link = data[1];
		        String regdate = data[2];
		        bloombergList.add(new Journal(title, link, regdate));
		
		    }
		    
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Journal bloomberg = bloombergList.get(rand.nextInt(bloombergList.size()));
		
		model.addAttribute("bloomberg", bloomberg);
		
		// reuters
		List<Journal> reutersList = new ArrayList<>();
		
		 csvFile = "src/main/resources/static/data/reuters_list.csv";
		
		 try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
		     while ((line = br.readLine()) != null) {
		         String[] data = line.split(cvsSplitBy);
		         String title = data[0];
		         String link = data[1];
		         String regdate = data[2];
		         reutersList.add(new Journal(title, link, regdate));
		
		     }
		     
		 } catch (IOException e) {
		     e.printStackTrace();
		 }
		 
		 Journal reuters = reutersList.get(rand.nextInt(reutersList.size()));
		 
		 model.addAttribute("reuters", reuters);
		
		 // theEconomicst
		List<Journal> theEconomistList = new ArrayList<>();
		
		  csvFile = "src/main/resources/static/data/economist_list.csv";
		
		  try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
		      while ((line = br.readLine()) != null) {
		          String[] data = line.split(cvsSplitBy);
		          String title = data[0];
		          String link = data[1];
		          String regdate = data[2];
		          theEconomistList.add(new Journal(title, link, regdate));
		
		      }
		      
		  } catch (IOException e) {
		      e.printStackTrace();
		  }
		  
		  Journal theEconomist = theEconomistList.get(rand.nextInt(theEconomistList.size()));
		  
		  model.addAttribute("theEconomist", theEconomist);        
		
		  // miningTech
		List<Journal> mTechtList = new ArrayList<>();
		
		csvFile = "src/main/resources/static/data/mTech_list.csv";
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			while ((line = br.readLine()) != null) {
			String[] data = line.split(cvsSplitBy);
			String title = data[0];
			String link = data[1];
			String regdate = data[2];
			mTechtList.add(new Journal(title, link, regdate));
		
		}
		    
		} catch (IOException e) {
		e.printStackTrace();
		}
		
		Journal mTecht = mTechtList.get(rand.nextInt(mTechtList.size()));
		
		model.addAttribute("mTecht", mTecht);
		  
	    // mining
		List<Journal> miningList = new ArrayList<>();
		
		csvFile = "src/main/resources/static/data/mining_list.csv";
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			while ((line = br.readLine()) != null) {
			String[] data = line.split(cvsSplitBy);
			String title = data[0];
			String link = data[1];
			String regdate = data[2];
			miningList.add(new Journal(title, link, regdate));
		
		}
		    
		} catch (IOException e) {
		e.printStackTrace();
		}
		
		Journal mining = miningList.get(rand.nextInt(miningList.size()));
		
		model.addAttribute("mining", mining);
		  
		// politico
		List<Journal> politicoList = new ArrayList<>();
		
		csvFile = "src/main/resources/static/data/politico_list.csv";
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			while ((line = br.readLine()) != null) {
			String[] data = line.split(cvsSplitBy);
			String title = data[0];
			String link = data[1];
			String regdate = data[2];
			politicoList.add(new Journal(title, link, regdate));
		
		}
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Journal politico = politicoList.get(rand.nextInt(politicoList.size()));
		
		model.addAttribute("politico", politico);
		
		
		// report
		List<Report> reportList = new ArrayList<>();
		
		csvFile = "src/main/resources/static/report2023/reports-2023.csv";
		
		Charset charset = Charset.forName("CP949");

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), charset))) {
			while ((line = br.readLine()) != null) {
			String[] data = line.split(cvsSplitBy);
			
			
			String title = data[1];
			log.info("{}", title);
			
			String filename = data[0];
			log.info("{}", filename);
			
			String source = data[2];
			log.info("{}", source);
			
			String date = data[3];
			log.info("{}", date);
			
			reportList.add(new Report(title, filename, date, source));
		
		}
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Collections.shuffle(reportList);
		List<Report> reportList2 = reportList.subList(0, 6);
		
		Report report1 = reportList2.get(0);
		report1.setFilename("/project/report2023/" + report1.getFilename());
		
		Report report2 = reportList2.get(1);
		report2.setFilename("/project/report2023/" + report2.getFilename());
		
		Report report3 = reportList2.get(2);
		report3.setFilename("/project/report2023/" + report3.getFilename());
		
		Report report4 = reportList2.get(3);
		report4.setFilename("/project/report2023/" + report4.getFilename());
		
		Report report5 = reportList2.get(4);
		report5.setFilename("/project/report2023/" + report5.getFilename());
		
		Report report6 = reportList2.get(5);
		report6.setFilename("/project/report2023/" + report6.getFilename());
		
		model.addAttribute("report1", report1);
		model.addAttribute("report2", report2);
		model.addAttribute("report3", report3);
		model.addAttribute("report4", report4);
		model.addAttribute("report5", report5);
		model.addAttribute("report6", report6);
          
		return "insights";
		
	}
	
}
