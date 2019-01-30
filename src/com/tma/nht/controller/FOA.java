package com.tma.nht.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.tma.nht.model.JobObject;

/*File object access*/ 
public class FOA<T extends JobObject> {
	private static final String TARGET_KEY="Target: ";
	private static final String STATES_KEY="Planned, Ongoing, Started";
	private static final String DATA_KEY="submitTime";
	
	private List<T> m_jobs;
	private List<String> m_strTargets;
	private List<String> categoryjob;
	
	public FOA(String path){
		m_jobs = new LinkedList<>();
		m_strTargets = new LinkedList<>();
		categoryjob = new LinkedList<>();
		
		readFile(path);
	}
	
	private void readFile(String path) {
		String line;
		BufferedReader inp = null;
		try {
			inp = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			
			while ((line = inp.readLine()) != null) {
				getJob(line, inp, m_jobs);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//Show friendly log by using dialog
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(inp != null){
				try {
					inp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		Collections.sort(m_jobs, new Comparator<T>() {

			@Override
			public int compare(T o1, T o2) {
				return ((JobObject)o1).getTargetId() - ((JobObject)o2).getTargetId();
			}
		});
	}

	private void getJob(String line, BufferedReader inp, List<T> jobs) {
		int targetId=0;
		String status="",data="";
		
		while(!line.equals("")){
			Document doc = Jsoup.parse(line,"UTF-8");
			String strTitle = doc.text();
			
			if(strTitle.contains(TARGET_KEY)){
				Pattern p = Pattern.compile("-?\\d+");
				Matcher m = p.matcher(strTitle);
				if(m.find())
					targetId = Integer.parseInt(m.group());
				
			}
			else if((STATES_KEY).contains(strTitle)){
				if(strTitle.equalsIgnoreCase("ongoing"))
					status = "Worked Pool";
				else if(strTitle.equalsIgnoreCase("started"))
					status = "Execution";
				else
					status = strTitle;
				
			}
			else if(strTitle.contains(DATA_KEY)&&status!=""){
				data = strTitle;
				String[] state = {status,data};
				T job = dissectStrData(data);
				job.setTargetId(targetId);
				job.setState(state);
				job.toString();
				jobs.add(job);
				
				m_strTargets.add("Target: " +targetId);
			}
			try {
				line = inp.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private T dissectStrData(String data) {
		
		String[] strParent = data.split(",");
		String[] strChild = strParent[0].split(":");
		
		long id = Long.parseLong(strChild[0].trim());
		String type = strChild[2];
		String category = strChild[1].split(" ")[2];
		
		if(!categoryjob.contains(category)){
			categoryjob.add(category);
		}
		
		String submit = "", start = "", timeout = "", server = "";
		for(int k = 1; k < strParent.length; k++){
			Optional<String> opt = Optional.ofNullable(strParent[k]);
			
			if(k==1)
				submit = opt.filter(x->x.contains("submitTime")).get().replaceAll("submitTime", "");
			else if(k==2)
				start = opt.filter(x->x.contains("start")).get().replaceAll("startTime","");
			else if(k==3)
				timeout = opt.filter(x->x.contains("timeout")).get().replaceAll("timeout:","");
			else if(k==4)
				server = opt.filter(x->x.contains("Server")).get().replaceAll("Server:", "");
		}
		return (T) new JobObject(id, category, type, submit, start, timeout, server);
	}

	/*--get(), set()--*/
	public List<T> getJobs() {
		return m_jobs;
	}
	public void setJobs(List<T> jobs) {
		this.m_jobs = jobs;
	}

	public List<String> getStrTargets() {
		return m_strTargets;
	}
	public void setStrTargets(List<String> strTargets) {
		this.m_strTargets = strTargets;
	}

	public List<String> getCategoryjob() {
		return categoryjob;
	}
	public void setCategoryjob(List<String> categoryjob) {
		this.categoryjob = categoryjob;
	}
	
	
}
