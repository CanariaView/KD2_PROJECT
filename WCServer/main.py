# 터미널 열기
# Ctrl + Shfit + 백킥

# package import
from fastapi import FastAPI
import uvicorn

from bs4 import BeautifulSoup
import requests

from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

from user_agent import generate_user_agent

import time
from datetime import datetime

import pandas as pd

## re 임포트
import re

## 자연어 처리 작업을 수행하기 위한 툴킷
from nltk.tokenize import RegexpTokenizer
from nltk.stem import WordNetLemmatizer
from nltk.corpus import stopwords


import warnings

warnings.filterwarnings('ignore')

last_request_date = None

bloomberg_list = None
reuters_list = None
mining_list = None
politico_list = None
economist_list = None
mTech_list = None

total = None
    
app = FastAPI()

@app.post(path="/crawl", status_code=201)
def crawl_data():
    
    print("크롤링 시작")
    
    # Reuters
    print("Reuters 크롤링")
    
    url = "https://www.reuters.com/site-search/?query=copper"

    options = webdriver.ChromeOptions()

    UA = generate_user_agent(navigator="chrome", os='win', device_type='desktop')

    options.add_argument(f'--user-agent={UA}')

    options.add_argument("--disable-blink-features=AutomationControlled") 

    options.add_experimental_option("excludeSwitches", ["enable-automation"]) 

    options.add_experimental_option("useAutomationExtension", False) 

    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)

    driver.execute_script("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})") 

    driver.get(url)

    wait = WebDriverWait(driver, 10)
    wait.until(EC.presence_of_element_located((By.CSS_SELECTOR, 'ul.search-results__list__2SxSK')))

    bs = BeautifulSoup(driver.page_source, 'html.parser')

    result = bs.find_all('li', attrs = {'class':'search-results__item__2oqiX'})

    reuters_list = pd.DataFrame(columns=["title","link","regdate"])

    text = ""

    for i in range(10):

        title = result[i].find('a').get_text()
        title = title.replace(",","")

        href = "https://www.reuters.com" + result[i].find('a')["href"]

        regdate = result[i].find('time').get_text()
        
        UA = generate_user_agent(navigator="chrome", os='win', device_type='desktop')
        
        headers = {'user-agent':f'{UA}'}

        response = requests.get(href, headers=headers)
        
        soup = BeautifulSoup(response.text, "html.parser")
        
        temp = soup.find("div", attrs={"class":"article-body__content__17Yit"}).find_all("p")

        for i in range(len(temp)):
            text += temp[i].get_text()
            text += " "
            
        new_row = {'title': title, 'link': href,"regdate":regdate}
        
        reuters_list = reuters_list.append(new_row, ignore_index=True)
        
        time.sleep(5)

    reuters_text = text
    
    # Bloomberg
    print("bloomberg 크롤링")
    
    url = "https://www.bloomberg.com/search?query=copper"

    headers = {'user-agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36'}

    response = requests.get(url, headers=headers)
    soup = BeautifulSoup(response.text, "html.parser")

    result = (soup.find_all('div', attrs = {'class':'storyItem__aaf871c1c5'})) 

    bloomberg_list = pd.DataFrame(columns=["title","link","regdate"])

    text = ""

    for i in range(10) :
        print("")
        title = result[i].find('a', attrs={'class':'headline__3a97424275'}).get_text()
        title = title.replace(",","")
        
        href = result[i].find('a', attrs={'class':'headline__3a97424275'})["href"]
        
        regdate = result[i].find('div', attrs={'class':'publishedAt__dc9dff8db4'}).get_text() 
        
        UA = generate_user_agent(navigator="chrome", os='win', device_type='desktop')
        
        headers = {'user-agent':f'{UA}'}

        response = requests.get(href, headers=headers)
        
        soup = BeautifulSoup(response.text, "html.parser")

        result1 = soup.find_all('p')
        
        for i in range(len(result1)):
            text += result1[i].get_text()
            text += " "
            
        new_row = {'title': title, 'link': href,"regdate":regdate}
        
        bloomberg_list = bloomberg_list.append(new_row, ignore_index=True)
        
        time.sleep(5)
        
    bloomberg_text = text
    
    # mining.com
    print("mining.com 크롤링")
    
    url = "https://www.mining.com/?s=copper"

    options = webdriver.ChromeOptions()

    UA = generate_user_agent(navigator="chrome", os='win', device_type='desktop')

    options.add_argument(f'--user-agent={UA}')

    options.add_argument("--disable-blink-features=AutomationControlled") 

    options.add_experimental_option("excludeSwitches", ["enable-automation"]) 

    options.add_experimental_option("useAutomationExtension", False) 

    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)

    driver.execute_script("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})") 

    driver.get(url)

    wait = WebDriverWait(driver, 10)

    bs = BeautifulSoup(driver.page_source, 'html.parser')

    result = bs.find_all("div", attrs={"class":"inner-content col-lg-8"})

    mining_list = pd.DataFrame(columns=["title","link","regdate"])

    text = ""

    for i in range(len(result)):

        title = result[i].find('a').get_text()
        title = title.replace(",","")

        href = result[i].find('a')["href"]

        regdate = result[i].find('div', attrs={"class":"post-meta mb-3"}).get_text().split("|")[1].strip()

        options = webdriver.ChromeOptions()

        UA = generate_user_agent(navigator="chrome", os='win', device_type='desktop')

        options.add_argument(f'--user-agent={UA}')

        options.add_argument("--disable-blink-features=AutomationControlled") 

        options.add_experimental_option("excludeSwitches", ["enable-automation"]) 

        options.add_experimental_option("useAutomationExtension", False) 

        driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)

        driver.execute_script("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})") 

        driver.get(href)

        wait = WebDriverWait(driver, 10)

        bs = BeautifulSoup(driver.page_source, 'html.parser')
        
        temp = bs.find_all("div", attrs={"class":"post-inner-content row"})

        for i in range(len(temp)):
            temp2 = temp[i].find_all('p')

            for j in range(len(temp2)):
                text += temp2[j].get_text()
                text += " "
            
        new_row = {'title': title, 'link': href,"regdate":regdate}
        
        mining_list = mining_list.append(new_row, ignore_index=True)
        
        time.sleep(5)

    mining_text = text
    
    # politico
    print("Politico 크롤링")
    
    url = "https://www.politico.com/search?q=copper"

    headers = {'user-agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36'}

    response = requests.get(url, headers=headers)
    soup = BeautifulSoup(response.text, "html.parser")

    result = soup.find_all("div", attrs={"class":"summary"})

    politico_list = pd.DataFrame(columns=["title","link","regdate"])

    text = ""

    for i in range(10) :
        UA = generate_user_agent(navigator="chrome", os='win', device_type='desktop')
        
        headers = {'user-agent':f'{UA}'}
        
        title = result[i].find("header").find("h3").find('a').get_text()
        title = title.replace(",","")
        
        href = result[i].find("header").find("h3").find('a')["href"]

        response = requests.get(href, headers=headers)
        
        soup = BeautifulSoup(response.text, "html.parser") 

        result1 = soup.find_all('p', attrs={"class":"story-text__paragraph"})

        for i in range(len(result1)):
            text += result1[i].get_text()
            text += " "

        regdate = result[i].find("p", attrs={"class":"timestamp"}).get_text().strip()
        
        new_row = {'title': title, 'link': href,"regdate":regdate}
            
        politico_list = politico_list.append(new_row, ignore_index=True)
        
        time.sleep(5)

    politico_text = text
    
    
    # The Economist
    print("The Economist 크롤링")
    
    url = "https://www.economist.com/search?q=copper&sort=date"

    headers = {'user-agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36'}

    response = requests.get(url, headers=headers)
    soup = BeautifulSoup(response.text, "html.parser")

    result = soup.find_all("div", attrs={"class":"css-1q84jwp ecwlksv0"})

    economist_list = pd.DataFrame(columns=["title","link","regdate"])

    text = ""

    for i in range(10) :
        UA = generate_user_agent(navigator="chrome", os='win', device_type='desktop')
        
        headers = {'user-agent':f'{UA}'}
        
        title = result[i].find("span").get_text()
        title = title.replace(",","")

        href = result[i].find('a')['href']
        
        if("weeklyedition" in href):
            continue
            
        if(href == "https://www.economist.com/"):
            continue

        response = requests.get(href, headers=headers)
        
        soup = BeautifulSoup(response.text, "html.parser") 
        
        temp = soup.find("time", attrs={"class":"css-j5ehde e1fl1tsy0"})
        
        if(temp == None):
            current_date = datetime.now()
            regdate = current_date.strftime("%Y-%m-%d")
            
            result1 = soup.find_all('div', attrs={"class":"css-111mrt0 etrcux30"})
            
            for i in range(len(result1)):
                text += result1[i].get_text()
                text += " "
            
        else:
            regdate = temp.get_text().strip()

            result1 = soup.find('div', attrs={"class":"css-13gy2f5 e1prll3w0"}).find_all("p")

            for i in range(len(result1)):
                text += result1[i].get_text()
                text += " "
        
        new_row = {'title': title, 'link': href,"regdate":regdate}
            
        economist_list = economist_list.append(new_row, ignore_index=True)
        
        time.sleep(5)

    economist_text = text
    
    # Mining Technology
    print("Mining Technoloy 크롤링")
    
    url = "https://www.mining-technology.com/s?wpsolr_q=copper&wpsolr_sort=sort_by_date_desc"

    headers = {'user-agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36'}

    response = requests.get(url, headers=headers)
    soup = BeautifulSoup(response.text, "html.parser")

    result = soup.find_all("div", attrs={"class":"cell large-8 articles padding-left-small-gap"})

    mTech_list = pd.DataFrame(columns=["title","link","regdate"])

    text = ""

    for i in range(10) :
        UA = generate_user_agent(navigator="chrome", os='win', device_type='desktop')
        
        headers = {'user-agent':f'{UA}'}
        
        title = result[i].find_all('a')[1].get_text()
        title = title.replace(",","")

        href = result[i].find_all('a')[1]['href']
        
        regdate = result[i].find("span",attrs={"class":"article-date"}).get_text()

        response = requests.get(href, headers=headers)
        
        soup = BeautifulSoup(response.text, "html.parser") 

        temp = soup.find("div", attrs={"class":"cell large-8 main-content"})
        temp = temp.find_all("p")

        for i in range(len(temp)):
            text += temp[i].get_text()
            text += " "

        new_row = {'title': title, 'link': href,"regdate":regdate}
            
        mTech_list = mTech_list.append(new_row, ignore_index=True)
        
        time.sleep(5)

    mTech_text = text

    # text 저장
    total = bloomberg_text + " " + reuters_text + " " + mining_text + " " + politico_text + " " + economist_text + " " + mTech_text + " "
    
    with open("C:/Users/Digital1/Desktop/WorkSpace/Test_Spring/project.MK3/src/main/resources/static/data/article.txt", "w", encoding="utf-8") as file:
        file.write(total)
        
    print("크롤링 끗")

@app.post(path="/update", status_code=201)
def update():
    now = datetime.now()
    request_date = now.strftime("%m-%d")
    
    # 서버 최초 구동 후 요청날짜가 저장되지 않거나 
    # 마지막 요청날짜가 현재 요청날짜와 다르면 실행
    global last_request_date
    
    if (last_request_date is None or request_date != last_request_date):    
        # 언론사 기사리스트 저장
        bloomberg_list.to_csv("C:/Users/Digital1/Desktop/WorkSpace/Test_Spring/project.MK3/src/main/resources/static/data/bloomberg_list.csv", encoding='cp949',header=False, index=False)

        reuters_list.to_csv("C:/Users/Digital1/Desktop/WorkSpace/Test_Spring/project.MK3/src/main/resources/static/data/reuters_list.csv", encoding='cp949',header=False, index=False)

        mining_list.to_csv("C:/Users/Digital1/Desktop/WorkSpace/Test_Spring/project.MK3/src/main/resources/static/data/mining_list.csv", encoding='cp949',header=False, index=False)

        politico_list.to_csv("C:/Users/Digital1/Desktop/WorkSpace/Test_Spring/project.MK3/src/main/resources/static/data/politico_list.csv", encoding='cp949',header=False, index=False)

        economist_list.to_csv("C:/Users/Digital1/Desktop/WorkSpace/Test_Spring/project.MK3/src/main/resources/static/data/economist_list.csv", encoding='cp949',header=False, index=False)

        mTech_list.to_csv("C:/Users/Digital1/Desktop/WorkSpace/Test_Spring/project.MK3/src/main/resources/static/data/mTech_list.csv", encoding='cp949',header=False, index=False)
        
        with open("C:/Users/Digital1/Desktop/WorkSpace/Test_Spring/project.MK3/src/main/resources/static/data/article.txt", 'rt', encoding='cp949') as f:
            lines2 = f.readlines()
            line2 = []
            
            for i in range(len(lines2)):
                line2.append(lines2[i])
       
        ###특수문자 제거하기###
        compile = re.compile("\W+")
        for i in range(len(line2)):

            a = compile.sub(" ",line2[i])
            line2[i] = a.lower()

        ###불용어 제거하기###
        stop_word_eng = set(stopwords.words('english'))
        add_stopwords = ['also','year','said','may','say','could','0','according','take','yet','kong','hong','ny','', 'ii', 'cfa'] # 추가하고자 하는 불용 단어 리스트
        stop_word_eng.update(add_stopwords)  # 여러 개의 단어를 한 번에 추가

        ### 표제어 추출 ### 
        ### 문장분석 X ###

        lemmatizer = WordNetLemmatizer() # 단어의 표제어 추출
        token = RegexpTokenizer('[\w]+')  # [\w]+ 단어 문자(알파벳과 숫자)의 연속
        # 정규식 패턴 [\w]+을 사용하여 단어를 토큰화

        result_pre_lem = [token.tokenize(i) for i in line2]
        middle_pre_lem= [r for i in result_pre_lem for r in i]
        final_lem = [lemmatizer.lemmatize(i) for i in middle_pre_lem if not i in stop_word_eng and len(i) > 1] # 불용어 제거
        # final_lem : 토큰화된 단어들이 하나의 리스트에 담김

        ### 빈도수 순으로 정렬 ###
        english = pd.Series(final_lem).value_counts()
        english = english.to_frame().reset_index()
        english.columns = ["title", "count"]
        # count 컬럼을 기준으로 내림차순 정렬
        english = english.sort_values('count', ascending=False)
        
        # 화면에 띄울 단어수 설정 : 200 단어
        english = english.head(200)
        
        # 인덱스 재설정
        english.reset_index(drop=True, inplace=True)
        
        english.to_json('C:/Users/Digital1/Desktop/WorkSpace/Test_Spring/project.MK3/src/main/resources/static/data/wordcloud.json', orient='records')
        
        last_request_date = request_date
        
    else:
        return

if __name__ == '__main__':
    uvicorn.run(app, host="127.0.0.1", port=8000)
        
# 서버 구동
# uvicorn main:app --reload
