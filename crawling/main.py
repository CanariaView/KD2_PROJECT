# 터미널 열기
# Ctrl + Shfit + 백킥

# package import
from fastapi import FastAPI
import uvicorn
from pydantic import BaseModel
from starlette.responses import JSONResponse

import pickle
import numpy as np
import pandas as pd

# Model 생성
class Item(BaseModel) :
    petalLength: float
    petalWidth : float
    sepalLength: float
    sepalWidth : float
    
app = FastAPI()

@app.post(path="/items", status_code=201)

def myiris(item : Item):
    # 피클 파일 로딩
    with open('data.pickle', 'rb') as f:
        model = pickle.load(f)
        dicted = dict(item)
        
        petalLength = dicted['petalLength']
        petalWidth = dicted['petalWidth']
        sepalLength = dicted['sepalLength']
        sepalWidth = dicted['sepalWidth']
        
        # 전처리 후 
        ary = np.array([[sepalLength, sepalWidth, petalLength, petalWidth]])

        target = ['setosa', 'versicolor', 'virginica'] # 정답
        pred = model.predict(ary)
        result = {"predict_result" : target[pred[0]]}
        
        print("========== 예측 결과 ==========", result)
        
    return JSONResponse(result)

if __name__ == '__main__':
    uvicorn.run(app, host="127.0.0.1", port=8000)
        
# 서버 구동
# uvicorn main:app --reload
